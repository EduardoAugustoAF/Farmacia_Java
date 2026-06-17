# Sistema de Gestão de Farmácia (Farmacia_Java)

Sistema acadêmico desenvolvido em **Java** para simular as operações de uma farmácia, aplicando conceitos de **Programação Orientada a Objetos (POO)** e boas práticas de desenvolvimento de software.

Além da implementação da lógica de negócio, o projeto conta com uma **interface gráfica em Java Swing**, permitindo a interação do usuário com os módulos de estoque, vendas e relatórios.


---

## Objetivo do Projeto

O sistema foi desenvolvido para solucionar problemas comuns encontrados no ambiente farmacêutico, como:

* Controle inadequado de estoque;
* Comercialização de medicamentos que exigem receita médica;
* Erros em processos de venda e cálculo de valores;
* Falta de centralização das informações de clientes, funcionários e produtos.

A proposta foi criar uma solução simples, organizada e escalável, utilizando os principais conceitos estudados na disciplina de Programação Orientada a Objetos.

---

## Conceitos de POO Aplicados

### Herança

Utilização de classes abstratas para representar estruturas comuns do sistema.

Exemplos:

* `Pessoa` → `Cliente` e `Funcionario`
* `Produto` → `Medicamento` e `ProdutoGenerico`

### Polimorfismo

Implementação de comportamentos específicos para cada tipo de produto, permitindo diferentes regras de cálculo e processamento.

### Encapsulamento

Proteção dos atributos internos das classes através de métodos de acesso e validações.

### Interfaces

Padronização de comportamentos utilizando interfaces como:

* `IVendavel`
* `IEstocavel`

### Collections

Uso de estruturas como:

* `ArrayList`
* `Map`
* `Set`

para gerenciamento de estoque, vendas e cadastros.

---

## Funcionalidades

### Gestão de Estoque

* Cadastro de produtos;
* Controle de quantidade disponível;
* Busca por nome ou código;
* Atualização automática após vendas.

### Gestão de Vendas

* Registro de vendas;
* Cálculo automático de totais;
* Geração de relatórios;
* Validação de estoque disponível.

### Controle de Medicamentos

* Verificação de validade;
* Controle de medicamentos que exigem receita médica;
* Tratamento de exceções específicas.

### Interface Gráfica

Desenvolvida em **Java Swing**, permitindo:

* Cadastro de produtos;
* Controle de estoque;
* Registro de vendas;
* Visualização de relatórios;
* Navegação por abas.

### Modo Console

Também foi implementado um modo alternativo utilizando `Scanner`, permitindo a execução das principais operações diretamente pelo terminal.

---

# Equipe do Projeto

| Integrante                                  | Contribuições                                                                                                                                          |
| ------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Marlon Ian Ferreira Dantas**              | Desenvolvimento do site de documentação, implementação de funcionalidades do back-end e organização geral do projeto. |
| **Eduardo Augusto Alves de Farias**         | Desenvolvimento da estrutura principal do sistema, modelagem das classes, regras de negócio e implementação da lógica principal da aplicação.          |
| **Pedro Vieira Salvador Lopes**             | Estruturação da documentação, desenvolvimento do site no Google Sites, apoio na integração entre documentação, integração da interface gráfica e sistema.                               |
| **Milton Carlos Ferreira da Silva Segundo** | Design da documentação, organização visual do site e apoio na estruturação dos conteúdos apresentados.                                                 |
| **Vinícius Cavalcante Lima**                | Apoio no desenvolvimento do projeto, validação das funcionalidades e participação nas atividades de implementação e testes.                            |

---

## Tecnologias Utilizadas

* Java
* Java Swing
* Programação Orientada a Objetos (POO)
* Collections Framework
* Git
* GitHub
* Google Sites

---

## Estrutura do Projeto

```text
src/
├── model/
├── service/
├── exception/
├── util/
├── view/
└── main/
```

---

## Desenvolvido para

**Centro Universitário de João Pessoa – UNIPÊ**

Disciplina: **Programação Orientada a Objetos**

Curso: **Ciência da Computação**

Período: **3º Período**


