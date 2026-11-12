package br.edu.fatec.onboardingagent.state;

import br.edu.fatec.onboardingagent.domain.AgentContext;

public interface AgentState {

    String name();

    AgentState handle(AgentContext ctx);
}
