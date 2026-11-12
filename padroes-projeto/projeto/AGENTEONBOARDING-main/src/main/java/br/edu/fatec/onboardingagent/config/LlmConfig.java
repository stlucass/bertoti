package br.edu.fatec.onboardingagent.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LlmConfig {

    private static final String SYSTEM_PROMPT = """
            Voce e um agente de onboarding que ensina Git e GitHub para desenvolvedores iniciantes.
            Responda sempre em portugues, de forma direta e objetiva.
            Quando o formato da resposta for especificado, siga-o exatamente, sem texto extra.
            """;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem(SYSTEM_PROMPT)
                .build();
    }
}
