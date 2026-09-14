package br.edu.fatec.onboardingagent.observer;

@FunctionalInterface
public interface AgentObserver {

    void onEvent(AgentEvent event);
}
