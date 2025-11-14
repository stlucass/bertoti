# Trabalho-backend

## NL to SQL com Inteligência Artificial

Este projeto é uma aplicação de **backend** desenvolvida com **Quarkus**, o *Supersonic Subatomic Java Framework*, que tem como objetivo central utilizar **Inteligência Artificial (IA)** para traduzir consultas em **Linguagem Natural (Português)** para comandos **SQL** executáveis.

## Funcionalidade Principal: NL to SQL com LLM

O recurso `NlToSqlResource.java` é o que implementa a tradução e a execução segura das consultas no banco de dados.

### Arquitetura de IA

* **Endpoint Principal:** A funcionalidade de pergunta e resposta pode ser acessada através do endpoint `POST` em `/perguntar`.
* **Modelo de Linguagem (LLM):** A tradução complexa é realizada pelo modelo **Llama 3 (8B)**.
* **Serviço de API:** O código se conecta ao serviço **OpenRouter** através da API de *chat completions* (`OPENROUTER_API_URL`) para enviar a pergunta e receber o SQL gerado.
* **Contexto:** O código fornece à IA o **esquema do banco de dados** (tabelas `cursos`, `alunos` e `matriculas`) para que ela possa gerar o SQL com nomes de colunas e tabelas corretos.

### Banco de Dados

O `PROMPT_TEMPLATE_SISTEMA` instrui explicitamente a IA a gerar **apenas comandos `SELECT`** e rejeitar operações de escrita (`UPDATE`, `DELETE`, `INSERT`), garantindo que a aplicação seja *read-only* e mais segura.

**Banco de Dados:** A aplicação utiliza **NeonDB (Postgres)**.


## 🛠️ Pré-requisitos

1.  **Java/JDK 17+**
2.  **Maven** (Já incluso como `./mvnw` no projeto)
3.  **Banco de Dados PostgreSQL** (Necessário configurar as credenciais em `application.properties`)
4.  **Chave de API do OpenRouter:** Deve ser configurada na variável de ambiente ou no arquivo `application.properties` como `openrouter.api.key`.


## ⚙️ Executando a Aplicação

### 1. Modo Desenvolvimento (Live Coding)

Para rodar a aplicação em modo de desenvolvimento, que permite o *live coding* (mudanças no código refletem instantaneamente):

```shell script
./mvnw quarkus:dev
