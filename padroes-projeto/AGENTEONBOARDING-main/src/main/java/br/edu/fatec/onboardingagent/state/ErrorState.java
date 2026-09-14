package br.edu.fatec.onboardingagent.state;

import br.edu.fatec.onboardingagent.domain.AgentContext;
import br.edu.fatec.onboardingagent.domain.EscalationSignal;

public class ErrorState implements AgentState {

    private final AgentStateMachine machine;

    public ErrorState(AgentStateMachine machine) {
        this.machine = machine;
    }

    @Override
    public String name() {
        return "ERROR";
    }

    @Override
    public AgentState handle(AgentContext ctx) {
        String erro = ctx.lastResult()
                .map(r -> r.errorMessage() == null ? "falha sem mensagem" : r.errorMessage())
                .orElse("falha desconhecida");

        if (ctx.retryCount() < machine.maxRetries()) {
            ctx.recordRetry();
            return new ExecutingState(machine);
        }

        if (ctx.replanCount() < machine.maxReplans()) {
            ctx.recordReplan();
            return new PlanningState(machine);
        }

        machine.selector().escalate(ctx, new EscalationSignal(
                EscalationSignal.Reason.COMMAND_FAILED,
                "Tentei %d vezes e replanejei %d vezes, e continua falhando: %s. Como voce quer seguir?"
                        .formatted(ctx.retryCount(), ctx.replanCount(), erro),
                ctx.currentStep().orElse(null)));
        return new WaitingApprovalState(machine);
    }
}
