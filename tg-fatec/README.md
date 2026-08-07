## Sobre mim
<p align="center">
  <img src="https://github.com/stlucass/Portfolio/raw/main/img/profile_photo.png" width="150" height="150" style="border-radius: 50%; display: block; margin: 0 auto;">
</p>

Minha jornada na área de TI começou em 2022 no Colégio Comercial de Caçapava, onde tive meu primeiro contato com algoritmos, lógica de programação, banco de dados, redes e engenharia de software.

Desde que iniciei o curso de Banco de Dados na FATEC Jessen Vidal, venho aprimorando minhas habilidades em desenvolvimento backend e modelagem de dados, participando de projetos integradores que me permitem aplicar o conhecimento teórico na construção de soluções práticas.

Profissionalmente, atuo como estagiário de Cibersegurança na Embraer, onde realizo análises de segurança, auditoria de sistemas e atuo na proteção de dados críticos. Essa vivência tem me ensinado a desenvolver e manter sistemas em ambientes que exigem altíssima confiabilidade.

Meu principal objetivo profissional é criar soluções robustas e seguras, unindo meu conhecimento em engenharia de software e banco de dados com práticas avançadas de proteção da informação.

## Contatos
* [Git](https://github.com/stlucass)
* [LinkedIn](https://www.linkedin.com/in/lucas-castro-39a427285/)
* [E-mail](mailto:lucascastrosantos20007@gmail.com)

## Meus Principais Conhecimentos

**Aplicações, Ferramentas e Dados**

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Python](https://img.shields.io/badge/Python-14354C?style=for-the-badge&logo=python&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Jira](https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

---

## Meus Projetos

### Em 2025-1
![banner-api-1](./assets/capa_1_semestre.png)

## Calculadora de Sequências Lógicas

### Empresa Parceira: Fictícia (Projeto Acadêmico Interno)

### Problema:
O ensino e a prática de sequências matemáticas complexas muitas vezes esbarram na dificuldade de realizar cálculos padronizados de forma rápida e sem erros manuais. Alunos e profissionais não possuíam uma ferramenta didática, leve e de terminal que pudesse calcular e exibir o passo a passo da formação de sequências numéricas (como Fatorial e Quadrados Perfeitos), dificultando a visualização lógica por trás da matemática.

### Solução Entregue pela Equipe:
Foi desenvolvida uma Calculadora de Sequências Lógicas construída inteiramente em VisualG. O sistema interativo de terminal permite que o usuário escolha qual sequência matemática deseja calcular, insira os parâmetros de limite e visualize não apenas o resultado final, mas a construção passo a passo da operação matemática. O sistema conta com validações, loops de repetição para múltiplos cálculos e uma interface de texto padronizada.

**Repositório do Projeto:** [API-2025-1](https://github.com/stlucass/API-2025-1)

#### Tecnologias Utilizadas

- **VisualG**: Pseudocódigo estruturado utilizado para implementar toda a lógica de programação, manipulação de variáveis, laços de repetição e exibição no console.
- **Git e GitHub**: Utilizados para o controle de versão e trabalho colaborativo, permitindo que a equipe unificasse as sequências individuais em um único algoritmo principal.
- **Notion**: Utilizado para a documentação técnica e organização do backlog (tarefas) da equipe.

#### Contribuições Pessoais

<details>
  <summary><b>1. Desenvolvimento do Algoritmo da Sequência Fatorial</b></summary>
  <br>
  <b>O que realizei:</b><br>
  Fui responsável por programar a lógica matemática da Sequência Fatorial, permitindo ao usuário digitar um número e visualizar toda a cadeia de multiplicação decrescente até o resultado final, com a opção de realizar novos cálculos consecutivamente.
  <br><br>
  <b>Como realizei:</b><br>
  Utilizei estruturas de repetição aninhadas (repita...ate) para garantir que o menu não se encerrasse antes da vontade do usuário. Além disso, apliquei estruturas de decisão (se...entao...senao) para formatar a saída de texto no console, garantindo que o sinal de multiplicação (" x ") não fosse exibido no último número da cadeia.
  <br><br>
  <b>Importância:</b><br>
  Garantia de uma usabilidade contínua (o sistema não "crasha" após o primeiro cálculo) e demonstração clara da lógica matemática para o usuário, cumprindo o objetivo didático do software.
  <br><br>
  <b>Trecho:</b><br>
  Algoritmo em VisualG demonstrando controle de loops e estado de variáveis.

```
escreval ("        Sequencia Fatorial               ")
escreval ("-----------------------------------------")
repita
   escreva("Digite o numero desejado: ")
   leia (NumeroDesejado)
   Contador <- NumeroDesejado
   Fatorial <- 1
   escreval("----------------------------------------------------------------")
   
   repita
      se Contador > 1 entao
         escreva (Contador, " x")
         Fatorial <- Fatorial * Contador
         Contador <- Contador - 1
      senao
         escreva (Contador)
         Fatorial <- Fatorial * Contador
         Contador <- Contador - 1
      fimse
   ate (Contador = 0)
   
   escreval(" ")
   escreval ("----------------------------------------------------------------")
   escreval("O valor fatorial de ", NumeroDesejado, " e igual a: ", Fatorial)
   escreval(" ")
   
   escreva ("Deseja fazer outro calculo ? [S/N] ")
   leia (Resposta)
   limpatela
ate(Resposta = "N") OU (Resposta = "n")
```
</details>

#### Hard Skills Desenvolvidas

- **Lógica de Programação**: Autonomia no uso de variáveis, entrada/saída de dados e controle de fluxo.
- **Estruturas de Repetição**: Autonomia na aplicação de laços complexos e aninhados (repita...ate, para...faca).
- **Estruturas de Decisão**: Autonomia na bifurcação de lógicas (se...entao...senao).
- **Controle de Versão**: Conhecimento inicial de Git/GitHub para versionamento de algoritmos.

#### Soft Skills Desenvolvidas

- **Atenção aos Detalhes**: Identificação proativa e correção de erros de codificação de caracteres (encoding) que prejudicavam a interface do sistema.
- **Trabalho em Equipe e Padronização**: Compreensão da importância de manter um código padronizado quando o projeto junta o código de múltiplos desenvolvedores diferentes.

---

### Em 2025-2
<p align="center">
  <img src="./assets/capa_2_semestre.jpeg" alt="banner-api-2">
</p>
## TG Connect

### Empresa Parceira: Fictícia (Projeto Acadêmico Interno - Cliente: Prof. Emanuel Mineda)

### Problema:
A gestão dos Trabalhos de Graduação (TG) na modalidade Portfólio sofria com a desorganização e a dispersão de dados. O processo envolvia múltiplos sistemas não integrados, o que causava perda de informações críticas entre alunos e orientadores. A falta de uma plataforma centralizada dificultava a rastreabilidade das entregas, prejudicava a comunicação acadêmica e impedia a coordenação de consolidar indicadores de progresso de forma rápida e segura.

### Solução Entregue pela Equipe:
O TG Connect foi desenvolvido como um sistema desktop unificado capaz de gerenciar todo o ciclo de vida dos TGs. A plataforma centralizou a comunicação, o armazenamento seguro de versões de documentos e a geração de indicadores de evolução. Com perfis de acesso distintos para Alunos, Orientadores e Coordenação, a ferramenta eliminou a perda de informações e garantiu rastreabilidade e governança para a gestão acadêmica.

**Repositório do Projeto:** [API-2025-2](https://github.com/stlucass/API-2025-2)

#### Tecnologias Utilizadas

- **Java**: Linguagem orientada a objetos utilizada para construir a interface desktop e a lógica de negócios da aplicação.
- **MySQL**: Banco de dados relacional responsável por armazenar com segurança os dados de alunos, professores, documentos e feedbacks.
- **IntelliJ IDEA**: IDE utilizada para o desenvolvimento e compilação do sistema.
- **Git e GitHub**: Controle de versão e colaboração distribuída do código-fonte e documentações.
- **Jira & Notion**: Plataformas utilizadas para a gestão do framework Scrum, priorização do Backlog do Produto e elaboração da documentação técnica e de negócio.

#### Contribuições Pessoais (Product Owner)

<details>
  <summary><b>1. Gestão Ágil do Produto e Refinamento do Backlog</b></summary>
  <br>
  <b>O que realizei:</b><br>
  Atuei como Product Owner (PO), sendo o principal elo entre as necessidades do cliente e a equipe técnica de 8 desenvolvedores. Fui responsável pela criação, priorização e atualização contínua do Backlog do Produto e dos Backlogs de Sprint.
  <br><br>
  <b>Como realizei:</b><br>
  Traduzi os requisitos de negócio em User Stories (Histórias de Usuário) claras e priorizadas no Jira e no GitHub. A cada Sprint, acompanhei e atualizei o status de conclusão das US, garantindo que o escopo de maior valor (como login seguro e submissão de arquivos) fosse entregue primeiro.
  <br><br>
  <b>Importância:</b><br>
  Manteve o time focado no escopo que gerava valor imediato para o cliente, evitando atrasos ou o desenvolvimento de recursos não solicitados, garantindo a entrega do produto final completo e homologado.
  <br><br>
  <b>Trecho:</b><br>
  Histórico de rastreabilidade documentado no repositório comprovando o controle de entregas por Sprint.

| US ID | Título da História de Usuário | Prioridade | Sprint | Status |
| :---: | :--- | :---: | :---: | :---: |
| US01  | Como Coordenador, quero cadastrar alunos para que possam acessar o sistema. | Alta | Sprint 1 | Concluído |
| US04  | Como Aluno, quero realizar o upload do meu portfólio em PDF. | Alta | Sprint 2 | Concluído |
| US07  | Como Orientador, quero visualizar as submissões para atribuir notas e feedbacks. | Média | Sprint 3 | Concluído |

**Critérios de Pronto (DoD)**
- O código atende a todos os critérios de aceitação da User Story.
- A modelagem no MySQL foi atualizada sem afetar tabelas existentes.
- O erro de carregamento (fix: erro PDF não carregado) foi tratado com blocos try/catch.
- Manual do Usuário e Técnico atualizados com as novas funcionalidades.
- Geração do build executável aprovada pelo PO.
</details>

#### Hard Skills Desenvolvidas

- **Gestão Ágil de Produtos**: Autonomia em planejamento estratégico, versionamento de entregas e estruturação de Backlog utilizando Jira/Scrum.
- **Engenharia de Requisitos**: Autonomia na escrita de User Stories e mapeamento de regras de negócios.
- **Java e MySQL**: Compreensão técnica da arquitetura, atuando em correções pontuais de sistema e compilação do projeto.

#### Soft Skills Desenvolvidas

- **Comunicação e Liderança de Equipe**: Forte alinhamento diário com o time técnico e com os stakeholders, atuando como o principal responsável por traduzir a linguagem de negócios para a equipe de desenvolvimento.
- **Resolução de Problemas sob Pressão**: Tomada de decisão ágil ao identificar falhas em homologação, paralisando entregas defeituosas (ex: visualização de PDF) e priorizando soluções imediatas na Sprint para não comprometer o projeto final.

---

### Em 2026-3
<p align="center">
  <img src="./assets/capa_3_semestre.jpeg" alt="banner-api-2">
</p>
## FlowTrack

### Empresa Parceira: [IPEM – Instituto de Pesos e Medidas (Regional São José dos Campos)](https://www.ipem.sp.gov.br/)

### Problema:
O controle de abastecimento e a gestão das viaturas da frota do IPEM eram realizados de forma estritamente manual, utilizando pranchetas físicas. Esse processo gerava lentidão na consolidação dos dados mensais, impossibilitava a análise comparativa de consumo entre diferentes veículos e comprometia severamente a rastreabilidade das informações e rotas. A ausência de um sistema automatizado prejudicava a eficiência operacional e a confiabilidade dos relatórios gerenciais utilizados para tomada de decisão pela instituição.

### Solução Entregue pela Equipe:
A equipe desenvolveu o FlowTrack, um sistema web integrado projetado para substituir os registros físicos por um ambiente totalmente digital e mapeado. A aplicação permite o registro seguro de abastecimentos, gerando indicadores automatizados de consumo e visualização de rotas. O sistema facilitou a gestão da frota, garantindo rastreabilidade, auditoria e fornecendo uma base de dados sólida para as decisões gerenciais do IPEM, eliminando os erros do processo manual.

**Repositório do Projeto:** [API-2026-3](https://github.com/stlucass/API-2026-3)

#### Tecnologias Utilizadas

- **Java**: Linguagem principal que utilizamos para desenvolver o backend da nossa aplicação. Ela oferece robustez, tipagem estática e segurança, o que é fundamental.
- **Spring Boot**: Base da infraestrutura backend, responsável pelo gerenciamento de dependências, configuração do ambiente e exposição das rotas da API RESTful.
- **MySQL**: Banco de dados relacional escolhido para modelar e armazenar as informações operacionais das viaturas, abastecimentos e gestão de usuários.
- **ViaCEP e Nominatim**: APIs externas consumidas no backend para buscar endereços automaticamente e realizar a geocodificação das rotas.
- **OpenLayers**: Biblioteca aplicada para a renderização interativa dos mapas no sistema web.
- **Git e GitHub**: Utilizados para o controle de versão e trabalho colaborativo simultâneo entre os integrantes do time.
- **Jira**: Plataforma adotada para o gerenciamento das Sprints, seguindo o framework Scrum, mantendo o controle das *user stories* e tarefas de desenvolvimento.

#### Contribuições Pessoais

<details>
  <summary><b>1. Suporte na Estruturação Inicial e Refatoração de Dados (Banco de Dados Relacional)</b></summary>
  <br>
  <b>O que realizei:</b><br>
  Atuei na criação inicial do modelo de dados e na refatoração contínua das tabelas e relacionamentos do banco de dados relacional ao longo das sprints. Isso foi necessário para acompanhar a evolução dos requisitos do sistema, como a adição de novas regras para abastecimentos, manutenções, notificações e tipos de serviços.
  <br><br>
  <b>Como realizei:</b><br>
  Utilizei a linguagem SQL nativa (DDL) no arquivo de migração/schema do MySQL e as anotações do framework Spring Data JPA / Hibernate no backend. Modelei e refatorei as tabelas principais (vehicle, vehicle_usage, fueling, maintenance, service_type, maintenance_notification, user), estabelecendo chaves primárias, estrangeiras e restrições de integridade referencial para garantir a consistência das relações.
  <br><br>
  <b>Importância:</b><br>
  Essa estruturação foi indispensável para sustentar a integridade e a escalabilidade da aplicação. A refatoração contínua permitiu acomodar novas funcionalidades requeridas pelo cliente ao longo das sprints sem perder o histórico de dados nem violar restrições de chave estrangeira.
  <br><br>
  <b>Trecho:</b><br>
  Arquivo SQL: schema.sql (Criação e relacionamento entre as tabelas vehicle_usage, user, vehicle, com constraints fk_vehicle_usage_vehicle, fk_vehicle_usage_driver).
  Entidade JPA Backend: VehicleUsage.java / User.java / Vehicle.java.

```sql
-- criação de tabelas e FKs relacionais
CREATE TABLE IF NOT EXISTS vehicle_usage (
    id INT NOT NULL AUTO_INCREMENT,
    vehicle_id INT NOT NULL,
    driver_id INT NOT NULL,
    companion_id INT NULL,
    reason ENUM('...') NOT NULL,
    departure_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    arrival_date DATETIME NULL,
    departure_mileage INT NOT NULL,
    arrival_mileage INT NULL,
    status ENUM('ABERTO','ENCERRADO','CANCELADO') NOT NULL DEFAULT 'ABERTO',
    PRIMARY KEY (id),
    CONSTRAINT fk_vehicle_usage_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    CONSTRAINT fk_vehicle_usage_driver FOREIGN KEY (driver_id) REFERENCES user(id)
);
```
</details>

<details>
  <summary><b>2. Validação de Regras de Negócio e Garantia de Qualidade (QA Backend)</b></summary>
  <br>
  <b>O que realizei:</b><br>
  Atuei na validação, desenvolvimento e ajuste de regras de negócio críticas no backend, garantindo a qualidade (QA) do fluxo de saída/chegada de veículos e registro de abastecimentos.
  <br><br>
  <b>Como realizei:</b><br>
  Programei regras de validação em Java na camada de serviço (VehicleUsageService e FuelingService). Implementei verificações para barrar saídas se o veículo já estiver em uso ou indisponível, impedindo que um condutor tenha mais de uma saída simultânea em aberto. Além disso, validei a consistência temporal e de odômetro: o KM de chegada de uma viagem ou de um abastecimento deve ser estritamente superior ao KM registrado anteriormente.
  <br><br>
  <b>Importância:</b><br>
  Garantia de consistência total nos relatórios operacionais. Evitou erros humanos como lançamentos com odômetro retroativo ou duplicidade no uso das viaturas, assegurando dados confiáveis para a tomada de decisão gerencial.
  <br><br>
  <b>Trecho:</b><br>
  Arquivo Java: VehicleUsageService.java (Validação de status e KM de devolução) e FuelingService.java (Validação de KM no abastecimento).

```java
// Trecho de VehicleUsageService.java - Validação do status e KM de chegada
if (vehicle.getStatus() == Vehicle.VehicleStatus.EM_USO) {
    throw new RuntimeException("Viatura já está em uso.");
}
if (repo.existsByDriverIdAndStatus(req.driverId(), UsageStatus.ABERTO)) {
    throw new RuntimeException("Existe outra saída em andamento");
}

// Validação de KM na devolução
if (req.arrivalMileage() <= usage.getDepartureMileage()) {
    throw new RuntimeException("KM de chegada deve ser maior que o KM de saída (" + usage.getDepartureMileage() + ").");
}

// Trecho de FuelingService.java - Validação de KM no abastecimento
if (fuelingMileage <= referenceMileage) {
    throw new RuntimeException("KM deve ser maior que o KM atual da viatura (" + referenceMileage + ")");
}
```
</details>

<details>
  <summary><b>3. Ajustes de UI/UX e Criação do Dashboard Principal (Frontend Dinâmico)</b></summary>
  <br>
  <b>O que realizei:</b><br>
  Criei a estrutura do Dashboard (Painel Executivo) principal e desenvolvi recursos avançados de interface do usuário (UI/UX) para manipulação interativa de dados em tabelas, incluindo redimensionamento drag-and-drop de colunas, exibição/ocultação dinâmica de colunas e seletores de filtros por período.
  <br><br>
  <b>Como realizei:</b><br>
  Utilizei JavaScript para implementar as funções utilitárias makeTableResizable (que permite arrastar a borda das colunas da tabela para ajustar seu tamanho em tempo real) e makeColumnToggler (que adiciona um menu suspenso para o usuário ocultar ou exibir colunas). No Dashboard (dashboard.js), programei a renderização assíncrona dos cartões de métricas e filtros dinâmicos de período conectados à API REST.
  <br><br>
  <b>Importância:</b><br>
  Transformou a experiência do usuário (UX), permitindo analisar grandes volumes de dados de frota de forma confortável e customizada. O Dashboard Executivo centralizou os principais indicadores da aplicação em uma visão limpa e dinâmica.
  <br><br>
  <b>Trecho:</b><br>
  Arquivo JS: dashboard.js (Painel Executivo) e ui.js (makeTableResizable e makeColumnToggler).

```javascript
// Trecho de ui.js - Redimensionamento dinâmico de colunas na tabela (UX)
makeTableResizable(table) {
    const ths = table.querySelectorAll('thead th');
    ths.forEach((th) => {
        const handle = document.createElement('div');
        handle.classList.add('resize-handle');
        th.appendChild(handle);

        handle.addEventListener('mousedown', (e) => {
            startX = e.clientX;
            startWidth = th.getBoundingClientRect().width;

            const onMouseMove = (moveEvent) => {
                const deltaX = moveEvent.clientX - startX;
                const newWidth = Math.max(30, startWidth + deltaX);
                th.style.width = `${newWidth}px`;
            };
            document.addEventListener('mousemove', onMouseMove);
        });
    });
}
```
</details>

#### Hard Skills Desenvolvidas

- **Modelagem de Banco de Dados Relacional**: MySQL / SQL DDL.
- **Mapeamento Objeto-Relacional (ORM)**: Spring Data JPA & Hibernate.
- **Refatoração de Esquemas e Integridade**: Garantia de integridade referencial com FOREIGN KEYs.
- **Regras de Negócio em Java**: Aplicação em Spring Boot.
- **Garantia de Qualidade de Dados (QA)**: Lógica de validação avançada, tratamento de exceções e controle de estado (*Transaction Management*).
- **Frontend e UX**: JavaScript, design de interface e desenvolvimento de Dashboards com componentes reutilizáveis.
- **Controle de Versão**: Git/GitHub (Commit, Push, Pull, criação de branches e resolução de conflitos).

#### Soft Skills Desenvolvidas

- **Atenção aos Detalhes Técnicos**
  - **O que realizei:** Garanti a validação estrita dos dados antes que fossem enviados ao banco de dados.
  - **Como realizei:** Implementando verificações preventivas contra dados nulos ou formatos incorretos recebidos do frontend e das APIs de localização.
  - **Importância:** Como o cliente (IPEM) é uma entidade governamental que trabalha com auditorias, garantir a absoluta precisão dos dados do consumo de frota foi vital para a confiabilidade do produto final.

- **Aprendizado Contínuo e Autodidatismo**
  - **O que realizei:** Aprendi a implementar o Spring Boot e a realizar a integração com ferramentas de geocodificação em tempo recorde.
  - **Como realizei:** Pesquisando documentações oficiais, como as do Spring e Nominatim, e realizando testes de mesa paralelos ao desenvolvimento do projeto para entender as tecnologias não aplicadas nos semestres anteriores.
  - **Importância:** Essa autonomia acadêmica me permitiu assumir e entregar as tarefas complexas de mapeamento, assegurando o cumprimento das Sprints estipuladas no Jira sem onerar outros membros da equipe.

- **Trabalho em Equipe e Alinhamento Técnico**
  - **O que realizei:** Mantive integração total com a equipe de frontend e com os líderes Scrum (PO e SM).
  - **Como realizei:** Por meio de comunicação constante e repasse técnico de como os endpoints criados deveriam ser consumidos pela interface web, ajustando as respostas do backend conforme a necessidade da renderização do frontend.
  - **Importância:** Essa comunicação proativa evitou falhas na integração de sistemas, minimizou retrabalho próximo à data da Feira de Soluções e manteve o fluxo de trabalho colaborativo contínuo.
