package br.edu.fatec.onboardingagent.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Plan {

    private final List<PlanStep> steps;
    private final double confidence;

    public Plan(List<PlanStep> steps, double confidence) {
        Objects.requireNonNull(steps, "steps nao pode ser nulo");
        this.steps = Collections.unmodifiableList(List.copyOf(steps));
        this.confidence = confidence;
    }

    public static Plan empty() {
        return new Plan(List.of(), 0.0);
    }

    public List<PlanStep> steps() {
        return steps;
    }

    public double confidence() {
        return confidence;
    }

    public int size() {
        return steps.size();
    }

    public boolean isValid() {
        return !steps.isEmpty() && steps.stream().allMatch(PlanStep::hasCommand);
    }

    public boolean isValid(Set<String> availableCommands) {
        Objects.requireNonNull(availableCommands, "availableCommands nao pode ser nulo");
        return isValid() && steps.stream()
                .allMatch(step -> availableCommands.contains(step.commandName()));
    }

    public List<String> unknownCommands(Set<String> availableCommands) {
        Objects.requireNonNull(availableCommands, "availableCommands nao pode ser nulo");
        return steps.stream()
                .map(PlanStep::commandName)
                .filter(name -> !availableCommands.contains(name))
                .distinct()
                .toList();
    }

    @Override
    public String toString() {
        return "Plan{passos=%d, confianca=%.2f}".formatted(steps.size(), confidence);
    }
}
