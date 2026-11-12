# GitHub Onboarding Agent — Minhas contribuições individuais

Projeto da disciplina de **Padrões de Projeto** (FATEC). É um agente que ajuda a aprender Git e
GitHub: **planeja, executa operações Git reais, observa o resultado e decide o próximo passo**.

> Strategy planeja · State avança · Command executa · Observer testemunha · Composite mostra

---

## 1. O que são padrões de projeto?

São **soluções conhecidas para problemas que se repetem** na programação orientada a objetos.
Não são código pronto, e sim "receitas" de como organizar classes e interfaces para o código ficar
fácil de mudar e testar. Ajudam a evitar `if/else` gigantes e a manter **uma responsabilidade por classe**.

| Padrão | Ideia em uma frase | Analogia |
|---|---|---|
| **Strategy** | Vários jeitos de fazer a mesma coisa, trocáveis em tempo de execução. | Escolher a rota no GPS |
| **State** | O comportamento muda conforme o estado, e cada estado é uma classe. | Semáforo |
| **Command** | Uma ação vira um objeto, para executar, registrar ou cronometrar. | Comanda do garçom |
| **Observer** | Um objeto avisa vários outros sem saber quem são. | Inscritos em um canal |
| **Composite** | Tratar um objeto e um grupo de objetos do mesmo jeito. | Arquivos e pastas |

---

## 2. Padrões no projeto

| Padrão | Classes principais |
|---|---|
| **Strategy** | `AgentStrategy`, `ReActStrategy`, `PlanThenExecuteStrategy`, `HumanInTheLoopStrategy`, `StrategySelector` |
| **State** | `AgentState`, `Init`/`Planning`/`Executing`/`Observing`/`WaitingApproval`/`Error`/`Completed`, `AgentStateMachine` |
| **Command** | `AgentCommand`, `GitStatus`, `GitBranch`, `GitCommit`..., `CommandInvoker`, Receivers (`GitClient`...) |
| **Observer** | `AgentEventPublisher`, `AgentObserver`, `TraceObserver`, `ErrorObserver`... |
| **Composite** | `UIComponent`, `UIComposite`, painéis (`GoalPanel`, `PlanPanel`...) |

---

## 3. Minhas contribuições

### 3.1 Mapeamento do fluxo do padrão

Desenhei o caminho do pedido do usuário e marquei **onde cada padrão entra**, para que trabalhem juntos:

```
Usuário: "Quero enviar minha alteração para revisão"
[1] StrategySelector escolhe o planejador ........ STRATEGY
[2] PlanningState pede o plano à estratégia
[3] AgentStateMachine avança passo a passo ....... STATE
[4] ExecutingState → CommandInvoker .............. COMMAND
[5] cada transição publica um evento ............. OBSERVER
[6] ObservingState: CONTINUE | REPLAN | ESCALATE | FAIL
    └─ ESCALATE: Selector troca ReAct → HumanInTheLoop
[7] os painéis Swing se redesenham ............... COMPOSITE
```

Definições do mapeamento:
- A **Strategy decide** e o **State reage**.
- Uma Strategy nunca chama outra; só devolve `ESCALATE` e o `StrategySelector` faz a troca.
- Transições de estado:

```
INIT → PLANNING → EXECUTING → OBSERVING ─┬─ CONTINUE → EXECUTING
                                         ├─ REPLAN   → PLANNING
                                         ├─ ESCALATE → WAITING_APPROVAL
                                         └─ FAIL     → ERROR
ERROR → EXECUTING | PLANNING | WAITING_APPROVAL (tentativas esgotadas)
WAITING_APPROVAL → PLANNING (após resposta humana)
```

**Padrões envolvidos:** Strategy, State, Command, Observer e Composite.

### 3.2 Arquitetura inicial do projeto

Montei o esqueleto de pacotes, onde cada um corresponde a um padrão ou camada:

```
domain/   → classes simples (Goal, Plan, AgentContext...)
command/  → COMMAND        tool/     → Receivers
state/    → STATE          strategy/ → STRATEGY
observer/ → OBSERVER       gui/      → COMPOSITE
llm/      → única porta de acesso ao modelo
```

Decisões principais:
- `domain/` é puro (sem Spring nem IA), fácil de testar.
- Construção em fases, cada uma terminando com teste.
- Regras de proteção: **sem `enum` + `switch` para estado**, todo Command usa um Receiver real,
  Observer implementado à mão (`AgentEventPublisher`) e `state/`, `command/` e `strategy/` não
  importam `observer/impl/`.

**Padrões envolvidos:** os cinco, pois a arquitetura definiu o lugar de cada um.

### 3.3 Implementação do padrão State

**Problema:** sem o padrão, haveria um `switch` enorme para cada fase do agente.
**Solução:** cada fase é uma **classe** que faz seu trabalho e **devolve o próximo estado**.

```java
public interface AgentState {
    String name();
    AgentState handle(AgentContext ctx);   // devolve o PRÓXIMO estado
}
```

| Papel | Classe | Função |
|---|---|---|
| State | `AgentState` | contrato comum |
| ConcreteState | `InitState` | prepara o contexto |
| | `PlanningState` | pede o plano à estratégia |
| | `ExecutingState` | executa o passo via `CommandInvoker` |
| | `ObservingState` | avalia o resultado e segue a decisão da estratégia |
| | `WaitingApprovalState` | espera a resposta do humano |
| | `ErrorState` | tenta de novo, replaneja ou escala |
| | `CompletedState` | estado final |
| Context | `AgentStateMachine` | guarda o estado atual e roda o laço |

O laço principal é bem curto, essa é a vantagem do padrão:

```java
while (!(current instanceof CompletedState)) {
    current = current.handle(ctx);
}
```

Integração com os outros padrões:
- **Strategy:** `PlanningState` e `ObservingState` consultam a estratégia ativa.
- **Command:** `ExecutingState` entrega o comando ao `CommandInvoker`.
- **Observer:** a máquina publica um evento em toda transição.

Testes: plano fixo de 3 passos, sem LLM, conferindo a sequência de estados, além dos caminhos de
falha e retry (`AgentStateMachineTest`).

**Padrões envolvidos:** **State** (principal), ligado a Strategy, Command e Observer.

---

## 4. Resumo

| Contribuição | Padrões |
|---|---|
| Mapeamento do fluxo | Strategy, State, Command, Observer, Composite |
| Arquitetura inicial | Strategy, State, Command, Observer, Composite |
| Implementação do State | **State** (+ Strategy, Command, Observer) |

---

## 5. Como rodar

```bash
llama-server -m gemma-2-2b-it-Q4_K_M.gguf --port 8080 -c 4096   # LLM local
mvn spring-boot:run                                              # agente
mvn test                                                         # testes
```

Diagramas UML em `docs/` (`uml-classes.puml`, `uml-sequence.puml`, `uml-state.puml`).
