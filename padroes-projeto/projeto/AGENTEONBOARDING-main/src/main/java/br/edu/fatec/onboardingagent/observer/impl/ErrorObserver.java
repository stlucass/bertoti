package br.edu.fatec.onboardingagent.observer.impl;

import br.edu.fatec.onboardingagent.observer.AgentEvent;
import br.edu.fatec.onboardingagent.observer.AgentObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ErrorObserver implements AgentObserver {

    private static final Logger log = LoggerFactory.getLogger(ErrorObserver.class);

    private final Map<String, Integer> falhasPorComando = new LinkedHashMap<>();
    private int totalDeFalhas;

    @Override
    public void onEvent(AgentEvent event) {
        if (event instanceof AgentEvent.CommandFailed falha) {
            falhasPorComando.merge(falha.commandName(), 1, Integer::sum);
            totalDeFalhas++;
            log.warn("Falha #{} em '{}': {}", falhasPorComando.get(falha.commandName()),
                    falha.commandName(), falha.errorMessage());
        }
    }

    public int totalDeFalhas() {
        return totalDeFalhas;
    }

    public int falhasDe(String commandName) {
        return falhasPorComando.getOrDefault(commandName, 0);
    }

    public Map<String, Integer> falhasPorComando() {
        return Map.copyOf(falhasPorComando);
    }
}
