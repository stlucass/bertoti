package br.edu.fatec.onboardingagent.domain;

import java.util.Objects;

public record Goal(String rawText, String interpretedObjective) {

    public Goal {
        Objects.requireNonNull(rawText, "rawText nao pode ser nulo");
        if (rawText.isBlank()) {
            throw new IllegalArgumentException("rawText nao pode ser vazio");
        }
    }

    public static Goal of(String rawText) {
        return new Goal(rawText, null);
    }

    public Goal withInterpretation(String objective) {
        return new Goal(rawText, objective);
    }

    public boolean isInterpreted() {
        return interpretedObjective != null && !interpretedObjective.isBlank();
    }
}
