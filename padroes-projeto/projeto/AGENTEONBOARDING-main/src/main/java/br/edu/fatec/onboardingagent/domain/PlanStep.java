package br.edu.fatec.onboardingagent.domain;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class PlanStep {

    public enum Status {
        PENDING,
        RUNNING,
        DONE,
        FAILED
    }

    private final int id;
    private final String description;
    private final String commandName;
    private final Map<String, Object> args;
    private Status status;

    public PlanStep(int id, String description, String commandName, Map<String, Object> args) {
        this.id = id;
        this.description = Objects.requireNonNull(description, "description nao pode ser nula");
        this.commandName = Objects.requireNonNull(commandName, "commandName nao pode ser nulo");
        this.args = args == null
                ? Map.of()
                : Collections.unmodifiableMap(new LinkedHashMap<>(args));
        this.status = Status.PENDING;
    }

    public int id() {
        return id;
    }

    public String description() {
        return description;
    }

    public String commandName() {
        return commandName;
    }

    public Map<String, Object> args() {
        return args;
    }

    public Status status() {
        return status;
    }

    public void markRunning() {
        this.status = Status.RUNNING;
    }

    public void markDone() {
        this.status = Status.DONE;
    }

    public void markFailed() {
        this.status = Status.FAILED;
    }

    public boolean isFinished() {
        return status == Status.DONE || status == Status.FAILED;
    }

    public boolean hasCommand() {
        return !commandName.isBlank();
    }

    @Override
    public String toString() {
        return "[%d] %s (%s) -> %s".formatted(id, description, commandName, status);
    }
}
