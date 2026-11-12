package br.edu.fatec.onboardingagent.observer.impl;

import br.edu.fatec.onboardingagent.gui.UIComponent;
import br.edu.fatec.onboardingagent.observer.AgentEvent;
import br.edu.fatec.onboardingagent.observer.AgentObserver;

import javax.swing.SwingUtilities;

public class GuiObserver implements AgentObserver {

    private final UIComponent raiz;

    public GuiObserver(UIComponent raiz) {
        this.raiz = raiz;
    }

    @Override
    public void onEvent(AgentEvent event) {
        SwingUtilities.invokeLater(() -> raiz.refresh(event));
    }
}
