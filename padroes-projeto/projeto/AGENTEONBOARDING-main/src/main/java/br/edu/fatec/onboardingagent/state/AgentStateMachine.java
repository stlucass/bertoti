package br.edu.fatec.onboardingagent.state;

import br.edu.fatec.onboardingagent.command.CommandInvoker;
import br.edu.fatec.onboardingagent.command.CommandRegistry;
import br.edu.fatec.onboardingagent.domain.AgentContext;
import br.edu.fatec.onboardingagent.observer.AgentEvent;
import br.edu.fatec.onboardingagent.observer.AgentEventPublisher;
import br.edu.fatec.onboardingagent.strategy.StrategySelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AgentStateMachine {

    private static final Logger log = LoggerFactory.getLogger(AgentStateMachine.class);

    private static final int LIMITE_DE_TRANSICOES = 500;

    private final CommandInvoker invoker;
    private final CommandRegistry registry;
    private final StrategySelector selector;
    private final AgentEventPublisher publisher;
    private final int maxRetries;
    private final int maxReplans;

    private final List<String> trail = new ArrayList<>();

    public AgentStateMachine(CommandInvoker invoker,
            CommandRegistry registry,
            StrategySelector selector,
            AgentEventPublisher publisher,
            @Value("${agent.max-retries:2}") int maxRetries,
            @Value("${agent.max-replans:2}") int maxReplans) {
        this.invoker = invoker;
        this.registry = registry;
        this.selector = selector;
        this.publisher = publisher;
        this.maxRetries = maxRetries;
        this.maxReplans = maxReplans;
    }

    public AgentState run(AgentContext ctx) {
        trail.clear();
        return runFrom(new InitState(this), ctx);
    }

    public AgentState resume(AgentState from, AgentContext ctx) {
        return runFrom(from, ctx);
    }

    private AgentState runFrom(AgentState start, AgentContext ctx) {
        AgentState current = start;
        registrar(null, current);

        int transicoes = 0;
        while (!(current instanceof CompletedState)) {
            AgentState proximo = current.handle(ctx);

            if (proximo == current) {
                log.info("Maquina pausada em {}", current.name());
                ctx.pendingEscalation()
                        .ifPresent(sinal -> publisher.publish(new AgentEvent.UserApprovalRequired(sinal)));
                return current;
            }

            String anterior = current.name();
            current = proximo;
            registrar(anterior, current);

            if (++transicoes > LIMITE_DE_TRANSICOES) {
                throw new IllegalStateException(
                        "A maquina de estados passou de %d transicoes sem concluir - ciclo infinito. Trilha: %s"
                                .formatted(LIMITE_DE_TRANSICOES, trail));
            }
        }
        publisher.publish(new AgentEvent.GoalCompleted(ctx.goal(), ctx));
        return current;
    }

    private void registrar(String anterior, AgentState estado) {
        trail.add(estado.name());
        log.info("Estado: {}", estado.name());
        publisher.publish(new AgentEvent.StateChanged(anterior, estado.name()));
    }

    public List<String> trail() {
        return List.copyOf(trail);
    }

    CommandInvoker invoker() {
        return invoker;
    }

    CommandRegistry registry() {
        return registry;
    }

    public StrategySelector selector() {
        return selector;
    }

    public AgentEventPublisher publisher() {
        return publisher;
    }

    int maxRetries() {
        return maxRetries;
    }

    int maxReplans() {
        return maxReplans;
    }
}
