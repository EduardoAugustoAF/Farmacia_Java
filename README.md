# 🏥 Sistema de Gestão de Farmácia (Farmacia_Java)

Um sistema de gerenciamento para farmácias desenvolvido inteiramente em **Java**. O objetivo principal deste projeto é projetar e implementar uma arquitetura de software aplicando na prática conceitos avançados de **Programação Orientada a Objetos (POO)**.

---

## 🚀 Visão Geral do Projeto

O sistema simula as operações reais de uma farmácia, permitindo o controle de estoque, o cadastro de diferentes tipos de produtos, a gestão de clientes e o processamento de vendas. É um projeto focado em código limpo, organização e boas práticas de desenvolvimento.

### 🧠 Conceitos de POO Aplicados

* 🧬 **Herança:** Uso de classes base (como `Produto` ou `Pessoa`) que são estendidas por classes mais específicas (`Medicamento`, `Cosmetico`, `Cliente`), reaproveitando o código de forma inteligente.
* 🎭 **Polimorfismo:** Implementação de métodos que se comportam de maneiras diferentes dependendo do objeto (ex: a regra para `calcularDesconto()` muda se o item for um medicamento comum ou um cosmético).
* 📜 **Interfaces:** Criação de contratos padronizados para isolar comportamentos do sistema (ex: `IVendavel` ou `IAutenticavel`), garantindo um código flexível e fácil de manter.
* 📦 **Coleções (Collections):** Utilização de estruturas de dados do Java (como `List`, `Set` e `Map`) para gerenciar o estoque, os carrinhos de compras e os históricos em memória com eficiência.

---

## ⚙️ Funcionalidades Principais

* 💊 **Gestão de Estoque:** Cadastro, atualização e baixa de medicamentos comuns, controlados e cosméticos.
* 🛒 **Fluxo de Vendas:** Adição de produtos ao carrinho, cálculo automático de subtotais e aplicação de descontos específicos.
* 📋 **Regras de Negócio:** Tratamento diferenciado para itens controlados que exigem retenção de receita médica.
* 🔍 **Consultas Rápidas:** Filtros e buscas na lista de produtos utilizando recursos nativos do Java.

---


​## 👥 Divisão de Tarefas
​Divisão de Tarefas
​Pra organizar o desenvolvimento, dividimos o grupo entre a galera que vai focar na lógica por trás do sistema (Back-end) e quem vai cuidar das telas e da interação com o usuário (Front-end).
​⚙️ Back-End
​**Marlon Ian**
​Criar a estrutura principal das classes (Produto, Medicamento, Cosmetico, Pessoa e Cliente).
​Aplicar a herança e o polimorfismo (como a lógica diferenciada do calcularDesconto()).
​Criar as regras de negócio para medicamentos controlados que precisam de receita.
​**Eduardo Augusto**
​Criar as interfaces do sistema (como IVendavel ou IAutenticavel).
​Montar e gerenciar as listas e mapas (List, Map, Set) para salvar o estoque, clientes e o histórico de vendas na memória.
​Desenvolver os métodos de busca e filtros rápidos de produtos.
​🎨 Front-End
​**Pedro Vieira**
​Desenvolver a tela de vendas e o fluxo do carrinho de compras (atualizando os valores e subtotais).
​Criar os avisos e validações na tela (como o alerta de retenção de receita para remédios controlados).
​Ligar os campos da tela de vendas com as funções de backend.
​**Milton Carlos**
​Desenvolver as telas de gerenciamento do estoque (cadastro, edição e exclusão de produtos).
​Criar a parte visual da barra de busca e dos filtros de produtos.
​Fazer a tela de cadastro e listagem dos clientes.
