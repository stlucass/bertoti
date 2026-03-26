# CRUD (Motos) - Lab 3

Este repositório contém o trabalho desenvolvido para o Laboratório 3, focado na criação de uma API REST e uma interface frontend para gerenciar um catálogo de motos. O projeto está organizado em subpastas que representam diferentes iterações ou versões da aplicação.

## Tecnologias Utilizadas

O projeto foi construído utilizando uma stack moderna de desenvolvimento full-stack:

### Backend
- Linguagem: Java
- Framework: Spring Boot
- API: REST utilizando Spring Web
- Persistência: Spring Data JPA
- Gerenciamento: Maven

### Frontend
- Framework: React
- Ferramenta de Build: Vite
- Estilização: Tailwind CSS
- Ícones: Lucide React
- Comunicação: Axios para integração com a API

## Endpoints e Rotas

A comunicação entre o frontend e o backend acontece através dos seguintes endpoints na base `/motos`:

- GET /motos: Retorna a lista completa de motos cadastradas.
- GET /motos/{id}: Retorna os dados detalhados de uma única moto.
- POST /motos: Cria um novo registro de moto no sistema.
- PUT /motos/{id}: Atualiza as informações de uma moto existente ou cria uma nova se não existir.
- DELETE /motos/{id}: Remove permanentemente uma moto do banco de dados.

O frontend consome essas rotas para realizar as operações de CRUD diretamente na interface.

## Como Rodar a Aplicação

Para executar o projeto localmente, siga os passos abaixo em cada parte do sistema.

### Configurando o Backend

1. Navegue até o diretório do projeto backend (por exemplo, projeto1/sbur-rest-demo).
2. Verifique se você possui o Java instalado (versão 17 ou superior).
3. Execute o comando para iniciar o servidor:
   ./mvnw spring-boot:run
   (No Windows use mvnw.cmd ou simplesmente rode através da sua IDE).

O backend ficará disponível em http://localhost:8080.

### Configurando o Frontend

1. Entre na pasta correspondente ao frontend: projeto1/sbur-rest-demo/frontend.
2. Certifique-se de ter o Node.js e o npm instalados.
3. Instale as bibliotecas necessárias:
   npm install
4. Inicie o servidor de desenvolvimento:
   npm run dev

Acesse o endereço informado no terminal (geralmente http://localhost:3000) para visualizar a aplicação no navegador.
