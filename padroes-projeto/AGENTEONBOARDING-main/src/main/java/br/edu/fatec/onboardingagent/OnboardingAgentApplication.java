package br.edu.fatec.onboardingagent;

import br.edu.fatec.onboardingagent.domain.AgentContext;
import br.edu.fatec.onboardingagent.domain.ExecutionResult;
import br.edu.fatec.onboardingagent.domain.Goal;
import br.edu.fatec.onboardingagent.domain.PlanStep;
import br.edu.fatec.onboardingagent.gui.MainWindow;
import br.edu.fatec.onboardingagent.observer.AgentEventPublisher;
import br.edu.fatec.onboardingagent.observer.impl.ProgressObserver;
import br.edu.fatec.onboardingagent.observer.impl.TraceObserver;
import br.edu.fatec.onboardingagent.state.AgentState;
import br.edu.fatec.onboardingagent.state.AgentStateMachine;
import br.edu.fatec.onboardingagent.strategy.StrategySelector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;

/**
 * Ponto de entrada da aplicação.
 * Inicia o agente de onboarding que guia o usuário no aprendizado e uso do Git/GitHub.
 */
@SpringBootApplication
public class OnboardingAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnboardingAgentApplication.class, args);
    }

    /**
     * Inicia a interface gráfica desktop (Swing) quando configurado com --agent.ui=swing.
     */
    @Bean
    @ConditionalOnProperty(name = "agent.ui", havingValue = "swing")
    public CommandLineRunner janelaDoAgente(AgentStateMachine machine, StrategySelector selector,
                                            AgentEventPublisher publisher, TraceObserver trace,
                                            ProgressObserver progresso) {
        return args -> javax.swing.SwingUtilities.invokeLater(() ->
                new MainWindow(machine, selector, publisher, trace, progresso).setVisible(true));
    }

    /**
     * Modo interativo via terminal (padrão).
     * Lê os objetivos digitados pelo usuário e executa as ações no repositório.
     */
    @Bean
    @ConditionalOnProperty(name = "agent.ui", havingValue = "console", matchIfMissing = true)
    public CommandLineRunner consoleDoAgente(AgentStateMachine machine, StrategySelector selector,
                                            TraceObserver trace, ProgressObserver progresso) {
        return args -> {
            // Usa a mesma entrada do teclado para não conflitar com perguntas que o agente possa fazer
            BufferedReader teclado = selector.humanInTheLoop().input();

            System.out.println();
            System.out.println("=".repeat(70));
            System.out.println("  GitHub Onboarding Agent - digite seu objetivo (ou 'sair')");
            System.out.println("  Planejador: 'react' (padrao) ou 'plan' para PlanThenExecute");
            System.out.println("=".repeat(70));

            while (true) {
                System.out.println();
                System.out.print("objetivo> ");
                String linha = teclado.readLine();

                if (linha == null) {
                    System.out.println("(sem entrada disponivel - encerrando o console)");
                    return;
                }
                linha = linha.trim();
                if (linha.isEmpty()) {
                    continue;
                }
                if (linha.equalsIgnoreCase("sair") || linha.equalsIgnoreCase("exit")) {
                    System.out.println("Ate a proxima.");
                    return;
                }
                if (linha.equalsIgnoreCase("react")) {
                    selector.selectReAct();
                    System.out.println("Planejador agora e ReAct.");
                    continue;
                }
                if (linha.equalsIgnoreCase("plan")) {
                    selector.selectPlanThenExecute();
                    System.out.println("Planejador agora e PlanThenExecute.");
                    continue;
                }

                executar(machine, selector, trace, progresso, linha);
            }
        };
    }

    private void executar(AgentStateMachine machine, StrategySelector selector,
                          TraceObserver trace, ProgressObserver progresso, String objetivo) {
        // Retorna para a estratégia padrão caso uma execução anterior tenha sido interrompida para interação humana
        if (selector.isEscalated()) {
            selector.selectReAct();
        }

        trace.limpar();
        // Carrega o histórico do usuário para continuar a trilha de onde parou
        AgentContext ctx = new AgentContext(Goal.of(objetivo), progresso.carregar());
        AgentState fim = machine.run(ctx);

        System.out.println();
        System.out.println("-".repeat(70));
        System.out.println("Estados percorridos: " + String.join(" -> ", machine.trail()));
        System.out.println("Estrategia ao final: " + selector.active().name());

        if (ctx.plan().size() > 0) {
            System.out.println("Plano (confianca " + ctx.plan().confidence() + "):");
            for (PlanStep passo : ctx.plan().steps()) {
                System.out.printf("  [%s] %d. %s (%s)%n",
                        passo.status(), passo.id(), passo.description(), passo.commandName());
            }
        }

        if (!ctx.history().isEmpty()) {
            System.out.println("Saida dos comandos:");
            for (ExecutionResult resultado : ctx.history()) {
                System.out.println("  " + (resultado.success()
                        ? resultado.output().replace("\n", "\n  ")
                        : "FALHOU: " + resultado.errorMessage()));
            }
        }

        ctx.escalationHistory().forEach(sinal ->
                System.out.println("Escalonamento [" + sinal.reason() + "]: " + sinal.questionToHuman()));

        System.out.printf("Trilha de aprendizado: %d%% concluida%n",
                Math.round(ctx.journey().progress() * 100));
        System.out.println("Situacao final: " + fim.name());
        System.out.println("-".repeat(70));

        // Exibe a trilha de execução e decisões tomadas
        trace.imprimir();
    }
}
