package br.edu.fatec.onboardingagent.domain;

public record ExecutionResult(boolean success, String output, String errorMessage) {

    public static ExecutionResult success(String output) {
        return new ExecutionResult(true, output == null ? "" : output, null);
    }

    public static ExecutionResult failure(String errorMessage) {
        return new ExecutionResult(false, "", errorMessage);
    }

    public boolean isFailure() {
        return !success;
    }
}
