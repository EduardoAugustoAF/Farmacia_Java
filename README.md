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
