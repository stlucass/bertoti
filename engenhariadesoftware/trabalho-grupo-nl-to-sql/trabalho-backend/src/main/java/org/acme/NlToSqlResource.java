package org.acme;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.microprofile.config.inject.ConfigProperty;

// Imports do Banco de Dados (Novos)
import io.agroal.api.AgroalDataSource;
import jakarta.inject.Inject;
// Fim Imports do Banco

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/perguntar")
public class NlToSqlResource {

    // --- Injeção da Chave de API ---
    @ConfigProperty(name = "openrouter.api.key")
    String apiKey;

    // --- Injeção do Banco de Dados (Novo) ---
    // O Quarkus vai injetar a conexão do seu application.properties
    @Inject
    AgroalDataSource dataSource;

    // --- Constantes da API ---
    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String MODEL = "meta-llama/llama-3-8b-instruct";

    // --- O Prompt Template ---
    private static final String PROMPT_TEMPLATE_SISTEMA = """
        Você é um assistente de banco de dados especialista em PostgreSQL.
        Sua única função é traduzir perguntas em linguagem natural para consultas SQL (apenas SELECT).
        Seu SQL deve ser SEGURO e não pode conter NENHUMA operação de escrita (UPDATE, DELETE, INSERT, etc).
        Se a pergunta for um 'oi' ou 'bom dia', responda com 'Olá!'.
        Se o SQL não for seguro ou for impossível de criar, responda com 'ERRO: Consulta inválida.'.
        
        O esquema do banco de dados é este:
        %s
        """;

    @POST
    @Consumes(MediaType.TEXT_PLAIN) // Recebe texto puro
    @Produces(MediaType.TEXT_PLAIN) // Devolve texto puro
    public String lidarComPergunta(String perguntaDoUsuario) {
        
        // 1. ESQUEMA CORRETO DO BANCO DE DADOS (CORRIGIDO)
        // Agora a IA sabe sobre 'telefone' e 'endereco'
        String esquemaDoBanco = """
            - Tabela 'cursos' (id SERIAL PRIMARY KEY, titulo_curso VARCHAR(100), nome_professor VARCHAR(100))
            - Tabela 'alunos' (id SERIAL PRIMARY KEY, nome_aluno VARCHAR(100), data_nascimento DATE, telefone VARCHAR(20), endereco VARCHAR(255))
            - Tabela 'matriculas' (id_matricula SERIAL PRIMARY KEY, id_aluno INTEGER, id_curso INTEGER)
            """;
        
        try {
            // 2. TRADUZIR PORTUGUÊS -> SQL (usando sua lógica do OpenRouter)
            String sqlTraduzido = traduzirParaSql(perguntaDoUsuario, esquemaDoBanco);

            // 3. VALIDAR
            // Se a IA responder "Olá!" ou "ERRO:", apenas retorne isso.
            if (!sqlTraduzido.toUpperCase().startsWith("SELECT")) {
                System.out.println("[DEBUG] IA respondeu: " + sqlTraduzido);
                return sqlTraduzido; // Retorna "Olá!" ou "ERRO: ..."
            }
            
            // 4. EXECUTAR NO BANCO (Método agora funciona!)
            String resultadoDoBanco = executarSqlSeguro(sqlTraduzido);
            
            // --- LOG DE DEBUG (Como você pediu) ---
            System.out.println("---------------------------------");
            System.out.println("[DEBUG] Pergunta do Usuário: " + perguntaDoUsuario);
            System.out.println("[DEBUG] SQL Gerado pela IA: " + sqlTraduzido);
            System.out.println("[DEBUG] Resposta do Banco: \n" + resultadoDoBanco);
            System.out.println("---------------------------------");
            
            return resultadoDoBanco;

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro no servidor: " + e.getMessage();
        }
    }

    /**
     * Este método se conecta ao OpenRouter para traduzir a pergunta.
     * (Este método estava correto)
     */
    private String traduzirParaSql(String pergunta, String esquema) throws Exception {
        URL url = new URL(OPENROUTER_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey); 
        conn.setDoOutput(true);

        String promptSistema = PROMPT_TEMPLATE_SISTEMA.formatted(esquema);

        // Monta o JSON para enviar à API
        String jsonInput = """
        {
          "model": "%s",
          "messages": [
            {"role": "system", "content": "%s"},
            {"role": "user", "content": "%s"}
          ]
        }
        """.formatted(MODEL, 
                    promptSistema.replace("\"", "\\\"").replace("\n", "\\n"), 
                    pergunta.replace("\"", "\\\""));

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }

        if (conn.getResponseCode() != 200) {
            // Adiciona log de erro
            System.err.println("API Key: " + apiKey); // Para verificar a chave no log
            throw new RuntimeException("Erro HTTP ao conectar no OpenRouter: " + conn.getResponseCode());
        }

        // Lê a resposta da IA
        ObjectMapper mapper = new ObjectMapper();
        JsonNode response = mapper.readTree(conn.getInputStream());
        return response.path("choices").get(0).path("message").path("content").asText("ERRO: Sem resposta");
    }

    /**
     * MÉTODO CORRIGIDO - Agora ele realmente executa o SQL no banco.
     */
    private String executarSqlSeguro(String sql) throws Exception {
        // Usa o pool de conexões do Quarkus
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            // Formata o resultado em uma tabela de texto
            return formatarResultSet(rs);
        }
    }

    /**
     * Novo método utilitário para formatar a resposta do banco.
     */
    private String formatarResultSet(ResultSet rs) throws Exception {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        StringBuilder sb = new StringBuilder();

        // Cabeçalho (Nomes das colunas)
        for (int i = 1; i <= columnCount; i++) {
            sb.append(String.format("%-20s", metaData.getColumnName(i)));
            sb.append("| ");
        }
        sb.append("\n");
        sb.append("-".repeat(22 * columnCount)); // Linha separadora
        sb.append("\n");

        // Dados (Linhas)
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                sb.append(String.format("%-20s", rs.getString(i)));
                sb.append("| ");
            }
            sb.append("\n");
        }
        
        if (sb.length() == 0) {
            return "A consulta não retornou resultados.";
        }

        return sb.toString();
    }
}