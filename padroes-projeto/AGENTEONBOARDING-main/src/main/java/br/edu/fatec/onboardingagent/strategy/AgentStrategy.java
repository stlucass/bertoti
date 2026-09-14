package br.edu.fatec.onboardingagent.strategy;

import br.edu.fatec.onboardingagent.domain.AgentContext;
import br.edu.fatec.onboardingagent.domain.ExecutionResult;
import br.edu.fatec.onboardingagent.domain.EscalationSignal;
import br.edu.fatec.onboardingagent.domain.Plan;
import br.edu.fatec.onboardingagent.domain.PlanStep;
import br.edu.fatec.onboardingagent.domain.StepDecision;

import java.util.Optional;

/**
 * Contrato para as estratégias de planejamento e condução do agente.
 * Define como formular planos, tomar decisões após cada execução e quando solicitar intervenção.
 */
public interface AgentStrategy {

    /** Nome identificador da estratégia. */
    String name();

    /** Monta o plano para o objetivo do contexto. Plano invalido e resposta legitima. */
    Plan buildPlan(AgentContext ctx);

    /** Decide o que fazer depois de observar o resultado do ultimo passo. */
    StepDecision decideNext(AgentContext ctx, ExecutionResult last);

    /** Indica se o passo exige aprovacao humana antes de rodar. */
    boolean requiresApproval(PlanStep step);

    /**
     * Incapacidade auto-declarada: presente quando a estrategia nao tem como seguir
     * sozinha, com o motivo mensuravel e a pergunta a fazer ao humano.
     */
    Optional<EscalationSignal> escalationSignal(AgentContext ctx);
}
