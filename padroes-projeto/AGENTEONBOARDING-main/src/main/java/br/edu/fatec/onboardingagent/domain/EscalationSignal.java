package br.edu.fatec.onboardingagent.domain;

import java.util.Objects;

public record EscalationSignal(Reason reason, String questionToHuman, PlanStep blockedStep) {

    public enum Reason {
        NO_VALID_PLAN,
        LOW_CONFIDENCE,
        REPLAN_LOOP,
        COMMAND_FAILED,
        DESTRUCTIVE_STEP,
        AMBIGUOUS_INPUT
    }

    public EscalationSignal {
        Objects.requireNonNull(reason, "reason nao pode ser nulo");
        Objects.requireNonNull(questionToHuman, "questionToHuman nao pode ser nula");
        if (questionToHuman.isBlank()) {
            throw new IllegalArgumentException("escalonamento sem pergunta ao humano nao faz sentido");
        }
    }

    public static EscalationSignal of(Reason reason, String questionToHuman) {
        return new EscalationSignal(reason, questionToHuman, null);
    }

    @Override
    public String toString() {
        return "Escalonamento[%s]: %s".formatted(reason, questionToHuman);
    }
}
