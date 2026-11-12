package br.edu.fatec.onboardingagent.gui;

import br.edu.fatec.onboardingagent.observer.AgentEvent;

import javax.swing.JPanel;
import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

public class UIComposite extends JPanel implements UIComponent {

    private final transient List<UIComponent> children = new ArrayList<>();

    public UIComposite(LayoutManager layout) {
        super(layout);
    }

    public UIComposite adicionar(UIComponent child) {
        children.add(child);
        if (child instanceof Component componenteSwing) {
            super.add(componenteSwing);
        }
        return this;
    }

    public UIComposite adicionar(UIComponent child, Object restricaoDeLayout) {
        children.add(child);
        if (child instanceof Component componenteSwing) {
            super.add(componenteSwing, restricaoDeLayout);
        }
        return this;
    }

    public List<UIComponent> filhos() {
        return List.copyOf(children);
    }

    @Override
    public void render() {
        children.forEach(UIComponent::render);
    }

    @Override
    public void refresh(AgentEvent event) {
        for (UIComponent child : children) {
            try {
                child.refresh(event);
            } catch (Exception e) {
                System.err.println("Painel " + child.getClass().getSimpleName()
                        + " falhou ao tratar evento: " + e.getMessage());
            }
        }
    }
}
