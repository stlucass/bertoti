package br.edu.fatec.onboardingagent.state;

import br.edu.fatec.onboardingagent.domain.AgentContext;
import br.edu.fatec.onboardingagent.domain.EscalationSignal;
import br.edu.fatec.onboardingagent.domain.ExecutionResult;
import br.edu.fatec.onboardingagent.domain.PlanStep;

import java.util.Optional;

/**
 * Executa o passo atual do plano utilizando o CommandInvoker.
 * Se o passo envolver uma operação sensível/destrutiva que ainda não foi aprovada,
 * solicita autorização do usuário antes de prosseguir.
 */
public class ExecutingState implements AgentState {

    private final AgentStateMachine machine;

    public ExecutingState(AgentStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public String name() {
        return "EXECUTING";
    }

    @Override
    public AgentState handle(AgentContext ctx) {
        Optional<PlanStep> passo = ctx.currentStep();
        if (passo.isEmpty()) {
            return new CompletedState(machine);
        }

        PlanStep step = passo.get();

        // Operações sensíveis exigem confirmação prévia do usuário
        if (machine.selector().active().requiresApproval(step) && !ctx.isApproved(step.commandName())) {
            machine.selector().escalate(ctx, new EscalationSignal(
                    EscalationSignal.Reason.DESTRUCTIVE_STEP,
                    "O passo '%s' usa uma operacao irreversivel (%s). Posso executar? (responda sim ou nao)"
                            .formatted(step.description(), step.commandName()),
                    step));
            return new WaitingApprovalState(machine);
        }

        step.markRunning();

        ExecutionResult resultado = machine.invoker().execute(ctx, step);
        ctx.recordResult(resultado);

        if (resultado.success()) {
            step.markDone();
        } else {
            step.markFailed();
        }

        return new ObservingState(machine);
    }
}
