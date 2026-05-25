# 📂 BasicBudget - Projeto Pessoal de Gestão

Este é um projeto pessoal desenvolvido para exercitar a criação de sistemas desktop robustos, com foco em uma arquitetura limpa e organizada. O sistema foi projetado para gerenciar orçamentos e produtos, servindo como uma aplicação prática de conceitos de persistência de dados e modularização.

## 🎯 Objetivo do Projeto

O foco deste desenvolvimento foi implementar um sistema desktop funcional que separa claramente as responsabilidades das classes (Arquitetura em Camadas), evitando o acoplamento excessivo e garantindo que o estado da aplicação seja gerenciado de forma consistente.

## 🛠️ Stack Tecnológica

Para a construção deste projeto, foram utilizadas as seguintes tecnologias e ferramentas:

- **Linguagem:** Java 23 (utilizando recursos modernos de compilação).
- **Interface Gráfica (UI):** JavaFX.
- **Estilização:** AtlantaFX (biblioteca focada em temas modernos para JavaFX).
- **Gerenciamento de Dependências:** Apache Maven.
- **Persistência de Dados:** Serialização em formato JSON (utilizando a biblioteca Google Gson).
- **Arquitetura:** Padrão de Camadas (Controllers, Services, Repositories) com implementação do padrão de projeto **Singleton** para centralização de instâncias.

## 🏗️ Estrutura do Código

A arquitetura foi pensada para ser organizada e de fácil manutenção:

1. **Controllers:** Responsáveis pela manipulação da interface e eventos do usuário.
2. **Services:** Camada intermediária que centraliza as regras de negócio e garante que os dados sejam acessíveis de forma única em toda a aplicação.
3. **Repositories:** Responsáveis pela comunicação com a camada de persistência (armazenamento em arquivos JSON).

## 🚀 Como Executar

Para compilar e rodar este projeto em sua máquina local, você precisará ter o JDK 23 ou superior instalado.

1. Clone o repositório:
   ```bash
   git clone [https://github.com/seu-usuario/basicBudget.git](https://github.com/seu-usuario/basicBudget.git)
   ```
