# Ollama Qwen3 Chat (JavaFX) — FINAL

Projeto Maven com JavaFX (compatível com Scene Builder) para conversar com o Ollama usando o modelo `qwen3:1.7b`.
- **pom.xml no diretório raiz** ✅
- **FXML válido (sem caracteres extras)** ✅
- **Remoção de `<think>`** ✅

## Pré-requisitos
- Java 17+
- Maven 3.8+
- Ollama em execução e com o modelo baixado:
  ```bash
  ollama serve
  ollama pull qwen3:1.7b
  ```

## Executar
No diretório do projeto (onde está o pom.xml):
```bash
mvn -q clean javafx:run
```

## IntelliJ IDEA
1. File → Open… → selecione `pom.xml`.
2. Project SDK = Java 17+.
3. Run `MainApp.main()`.
4. Edite a interface com o Scene Builder abrindo `src/main/resources/com/example/ollama/ui/chat-view.fxml`.

## Uso
- Ajuste **Host** (padrão `http://localhost:11434`) e **Modelo** (`qwen3:1.7b`) no topo.
- Digite a pergunta e **Enter** / **Enviar**.
- Conversa aparece no centro (texto puro).

Bom proveito!
