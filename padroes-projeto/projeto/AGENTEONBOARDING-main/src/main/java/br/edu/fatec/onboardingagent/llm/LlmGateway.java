package br.edu.fatec.onboardingagent.llm;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class LlmGateway {

    private final ChatClient chatClient;

    public LlmGateway(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String complete(String prompt) {
        String resposta = chatClient.prompt()
                .user(prompt)
                .call()
                .content();
        return resposta == null ? "" : resposta;
    }
}
