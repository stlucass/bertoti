package com.example.ollama.ui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.CompletableFuture;

public class ChatController {
    @FXML private TextArea txtConversation;
    @FXML private TextField txtQuestion;
    @FXML private TextField txtHost;
    @FXML private ComboBox<String> cboModel;
    @FXML private Button btnSend;

    @FXML
    public void initialize() {
        String defaultHost = System.getenv().getOrDefault("OLLAMA_HOST", "http://localhost:11434");
        txtHost.setText(defaultHost);

        cboModel.getItems().addAll("qwen3:1.7b", "qwen2.5:1.5b", "llama3.1:8b");
        String defaultModel = System.getenv().getOrDefault("OLLAMA_MODEL", "qwen3:1.7b");
        if (!cboModel.getItems().contains(defaultModel)) {
            cboModel.getItems().add(0, defaultModel);
        }
        cboModel.getSelectionModel().select(defaultModel);

        txtConversation.setEditable(false);
        txtConversation.setWrapText(true);

        txtQuestion.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && !e.isShiftDown()) {
                e.consume();
                onSend();
            }
        });

        appendSystem("Pronto. Digite sua pergunta e clique Enviar.");
    }

    @FXML
    public void onSend() {
        String question = txtQuestion.getText().trim();
        if (question.isEmpty()) return;

        String host = txtHost.getText().trim();
        String model = cboModel.getValue();

        setBusy(true);
        appendUser(question);
        txtQuestion.clear();

        CompletableFuture
                .supplyAsync(() -> {
                    try {
                        String answer = OllamaService.askOnce(host, model, question);
                        // remove bloco <think>...</think>
                        answer = answer == null ? "" : answer.replaceAll("(?s)<think>.*?</think>", "").trim();
                        return answer;
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return "Erro (Interrupted): a operação foi interrompida.";
                    } catch (Exception ex) {
                        StringWriter sw = new StringWriter();
                        ex.printStackTrace(new PrintWriter(sw));
                        return "Erro (" + ex.getClass().getSimpleName() + "): " + (ex.getMessage()==null? "(sem mensagem)": ex.getMessage()) + "\n" + sw.toString();
                    }
                })
                .thenAccept(answer -> Platform.runLater(() -> {
                    appendAssistant(answer);
                    setBusy(false);
                }));
    }

    private void setBusy(boolean busy) {
        btnSend.setDisable(busy);
        txtQuestion.setDisable(busy);
        cboModel.setDisable(busy);
        txtHost.setDisable(busy);
    }

    private void appendSystem(String msg) {
        txtConversation.appendText("[sistema] " + msg + "\n");
    }

    private void appendUser(String msg) {
        txtConversation.appendText("Você: " + msg + "\n");
    }

    private void appendAssistant(String msg) {
        txtConversation.appendText("Assistente: " + msg + "\n\n");
    }
}
