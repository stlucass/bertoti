package br.edu.fatec.onboardingagent.gui.leaf;

import br.edu.fatec.onboardingagent.gui.UIComponent;
import br.edu.fatec.onboardingagent.observer.AgentEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class StatePanel extends JPanel implements UIComponent {

    private final JLabel estado = new JLabel("-");
    private final JLabel estrategia = new JLabel("estrategia: ReAct");

    public StatePanel() {
        super(new BorderLayout(4, 4));
        setBorder(BorderFactory.createTitledBorder("Estado"));
        estado.setFont(estado.getFont().deriveFont(Font.BOLD, 16f));
        add(estado, BorderLayout.CENTER);
        add(estrategia, BorderLayout.SOUTH);
    }

    @Override
    public void render() {
        estado.setText("-");
        estado.setForeground(Color.DARK_GRAY);
    }

    @Override
    public void refresh(AgentEvent event) {
        switch (event) {
            case AgentEvent.StateChanged mudanca -> {
                estado.setText(mudanca.to());
                estado.setForeground("WAITING_APPROVAL".equals(mudanca.to())
                        ? new Color(0xB8, 0x60, 0x00)
                        : Color.DARK_GRAY);
            }
            case AgentEvent.StrategyEscalated escalonamento ->
                estrategia.setText("estrategia: " + escalonamento.to() + " (escalonado)");
            case AgentEvent.StrategyDeescalated volta ->
                estrategia.setText("estrategia: " + volta.to());
            default -> {
            }
        }
    }
}
