

# Desafio Técnico - Lynx BR

Este projeto foi desenvolvido como parte de um teste prático para estágio. Consiste numa aplicação de e-commerce simplificada com back-end em Java (Spring Boot) e front-end em HTML, CSS e JavaScript e banco de dados H2.

## 🚀 Tecnologias Utilizadas

- **Back-end:** Java 17, Spring Boot 3.x, Spring Data JPA, H2 Database (em memória).
- **Front-end:** HTML5, CSS3, JavaScript Vanilla.
- **Gestão de Dependências:** Maven.

## 🛠️ Funcionalidades Implementadas

### Back-end
- **Produtos:** Endpoint para listagem de produtos com paginação e busca por nome.
- **Clientes:** Registo de novos clientes com validação de email.
- **Pedidos:** Lógica de negócio para criação de pedidos associados a clientes e produtos, incluindo o cálculo automático do valor total.
- **Validação:** Utilização de Bean Validation para garantir a integridade dos dados (como @NotBlank, @Email e @NotNull).

### Front-end
- **Interface:** Landing page com hero section e grelha de produtos.
- **Carrinho Lateral:** Barra lateral dinâmica que desliza ao clicar em "Adicionar ao Carrinho" ou no ícone do cabeçalho.
- **Responsividade:** Layout adaptável para diferentes tamanhos de ecrã, com foco na correção do posicionamento do rodapé.

## ⚙️ Como Executar o Projeto

1. Certifique-se de ter o Java 17 e o Maven instalados.
2. Clone o repositório.
3. No diretório do back-end, execute: `./mvnw spring-boot:run`.
4. O servidor iniciará em `http://localhost:8080`.
5. Aceda ao front-end através de `http://localhost:8080/frontEnd/index.html` ou abrindo o ficheiro `index.html` via Live Server.

## 📝 Observações Finais

Não consegui terminar o desafio completamente, já que ainda não sei mexer bem com integração entre front-end e back-end. Em dois dias, tentei aprender e aplicar os conceitos de consumo de APIs e persistência de dados via JavaScript, mas não foi tempo suficiente para finalizar todas as conexões necessárias e garantir o funcionamento total do fluxo de checkout.
