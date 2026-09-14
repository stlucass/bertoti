package br.edu.fatec.onboardingagent.command;

import br.edu.fatec.onboardingagent.domain.AgentContext;
import br.edu.fatec.onboardingagent.domain.ExecutionResult;

import java.util.Map;

public interface AgentCommand {

    String name();

    String description();

    ExecutionResult execute(AgentContext ctx, Map<String, Object> args);

    default boolean isDestructive() {
        return false;
    }
}
