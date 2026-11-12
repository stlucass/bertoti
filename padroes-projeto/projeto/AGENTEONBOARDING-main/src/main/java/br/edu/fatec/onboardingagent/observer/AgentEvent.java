package br.edu.fatec.onboardingagent.observer;

import br.edu.fatec.onboardingagent.domain.AgentContext;
import br.edu.fatec.onboardingagent.domain.EscalationSignal;
import br.edu.fatec.onboardingagent.domain.ExecutionResult;
import br.edu.fatec.onboardingagent.domain.Goal;
import br.edu.fatec.onboardingagent.domain.Plan;

import java.util.Map;

public sealed interface AgentEvent {

    record PlanCreated(String strategy, Plan plan) implements AgentEvent {
    }

    record StateChanged(String from, String to) implements AgentEvent {
    }

    record CommandStarted(String commandName, Map<String, Object> args) implements AgentEvent {
    }

    record CommandCompleted(String commandName, long durationMs, ExecutionResult result,
            AgentContext context) implements AgentEvent {
    }

    record CommandFailed(String commandName, String errorMessage, int retryCount) implements AgentEvent {
    }

    record ReasoningStep(String strategy, String thought) implements AgentEvent {
    }

    record StrategyEscalated(String from, String to, EscalationSignal signal) implements AgentEvent {
    }

    record StrategyDeescalated(String from, String to) implements AgentEvent {
    }

    record UserApprovalRequired(EscalationSignal signal) implements AgentEvent {
    }

    record GoalCompleted(Goal goal, AgentContext context) implements AgentEvent {
    }
}
