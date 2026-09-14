package br.edu.fatec.onboardingagent.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class AgentContext {

    private Goal goal;
    private Plan plan;
    private int currentStepIndex;

    private final List<ExecutionResult> history = new ArrayList<>();
    private final List<EscalationSignal> escalationHistory = new ArrayList<>();

    private int replanCount;
    private int retryCount;

    private EscalationSignal pendingEscalation;

    private String humanResponse;

    private final Set<String> approvedCommands = new LinkedHashSet<>();

    private final LearningJourney journey;

    public AgentContext(Goal goal) {
        this(goal, new LearningJourney());
    }

    public AgentContext(Goal goal, LearningJourney journey) {
        this.goal = Objects.requireNonNull(goal, "goal nao pode ser nulo");
        this.journey = Objects.requireNonNull(journey, "journey nao pode ser nula");
        this.plan = Plan.empty();
        this.currentStepIndex = 0;
    }

    public Goal goal() {
        return goal;
    }

    public void updateGoal(Goal updated) {
        this.goal = Objects.requireNonNull(updated, "goal nao pode ser nulo");
    }

    public Plan plan() {
        return plan;
    }

    public void installPlan(Plan newPlan) {
        this.plan = Objects.requireNonNull(newPlan, "plan nao pode ser nulo");
        this.currentStepIndex = 0;
        this.retryCount = 0;
    }

    public int currentStepIndex() {
        return currentStepIndex;
    }

    public Optional<PlanStep> currentStep() {
        if (currentStepIndex < 0 || currentStepIndex >= plan.size()) {
            return Optional.empty();
        }
        return Optional.of(plan.steps().get(currentStepIndex));
    }

    public boolean hasMoreSteps() {
        return currentStepIndex < plan.size();
    }

    public void advance() {
        this.currentStepIndex++;
        this.retryCount = 0;
    }

    public boolean isPlanFinished() {
        return plan.size() > 0 && currentStepIndex >= plan.size();
    }

    public void recordResult(ExecutionResult result) {
        history.add(Objects.requireNonNull(result, "result nao pode ser nulo"));
    }

    public List<ExecutionResult> history() {
        return Collections.unmodifiableList(history);
    }

    public Optional<ExecutionResult> lastResult() {
        return history.isEmpty() ? Optional.empty() : Optional.of(history.get(history.size() - 1));
    }

    public int replanCount() {
        return replanCount;
    }

    public void recordReplan() {
        this.replanCount++;
    }

    public int retryCount() {
        return retryCount;
    }

    public void recordRetry() {
        this.retryCount++;
    }

    public void resetRetryCount() {
        this.retryCount = 0;
    }

    public void recordEscalation(EscalationSignal signal) {
        Objects.requireNonNull(signal, "signal nao pode ser nulo");
        escalationHistory.add(signal);
        this.pendingEscalation = signal;
    }

    public void clearEscalation() {
        this.pendingEscalation = null;
    }

    public boolean isEscalated() {
        return pendingEscalation != null;
    }

    public Optional<EscalationSignal> pendingEscalation() {
        return Optional.ofNullable(pendingEscalation);
    }

    public List<EscalationSignal> escalationHistory() {
        return Collections.unmodifiableList(escalationHistory);
    }

    public Optional<String> humanResponse() {
        return Optional.ofNullable(humanResponse);
    }

    public void submitHumanResponse(String response) {
        this.humanResponse = response;
    }

    public void clearHumanResponse() {
        this.humanResponse = null;
    }

    public void approveCommand(String commandName) {
        approvedCommands.add(commandName);
    }

    public boolean isApproved(String commandName) {
        return approvedCommands.contains(commandName);
    }

    public Set<String> approvedCommands() {
        return Collections.unmodifiableSet(approvedCommands);
    }

    public LearningJourney journey() {
        return journey;
    }

    @Override
    public String toString() {
        return "AgentContext{objetivo='%s', passo=%d/%d, replans=%d, retries=%d, escalado=%s}"
                .formatted(goal.rawText(), currentStepIndex, plan.size(),
                        replanCount, retryCount, isEscalated());
    }
}
