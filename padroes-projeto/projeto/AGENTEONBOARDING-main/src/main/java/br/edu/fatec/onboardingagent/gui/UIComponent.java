package br.edu.fatec.onboardingagent.gui;

import br.edu.fatec.onboardingagent.observer.AgentEvent;

public interface UIComponent {

    void render();

    void refresh(AgentEvent event);
}
