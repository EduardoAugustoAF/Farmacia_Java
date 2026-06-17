package main;

import exception.EstoqueInsuficienteException;
import exception.ProdutoVencidoException;
import exception.ReceitaInvalidaException;
import model.Cliente;
import model.Estoque;
import model.Funcionario;
import model.Medicamento;
import model.Produto;
import model.ProdutoGenerico;
import model.Venda;
import service.FarmaciaService;
import util.EntradaDados;
import util.FormatadorUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Modo console do sistema usando Scanner para entrada de dados do usuario.
 *
 * A interface grafica continua sendo a tela principal do projeto, mas esta
 * classe permite demonstrar o uso do Scanner na apresentacao da disciplina.
 */
public class MainScanner {
    private final EntradaDados entrada;
    private final Estoque estoque;
    private final FarmaciaService service;
    private final List<Cliente> clientes;
    private final List<Funcionario> funcionarios;
    private int contadorVenda;

    public MainScanner() {
        this.entrada = new EntradaDados();
        this.estoque = new Estoque();
        this.service = new FarmaciaService();
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.contadorVenda = 1;
        carregarDadosIniciais();
    }

    public static void main(String[] args) {
        new MainScanner().executar();
    }

    public void executar() {
        int opcao;
        do {
            exibirMenu();
            opcao = entrada.lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    cadastrarFuncionario();
                    break;
                case 3:
                    cadastrarProduto();
                    break;
                case 4:
                    listarEstoque();
                    break;
                case 5:
                    realizarVenda();
                    break;
                case 0:
                    System.out.println("Encerrando o modo console...");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }

            if (opcao != 0) {
                entrada.pausar();
            }
        } while (opcao != 0);

        entrada.fechar();
    }

    private void exibirMenu() {
        System.out.println("\n====================================");
        System.out.println("   SISTEMA DE FARMACIA - SCANNER");
        System.out.println("====================================");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Cadastrar funcionario");
        System.out.println("3 - Cadastrar produto");
        System.out.println("4 - Listar estoque");
        System.out.println("5 - Realizar venda");
        System.out.println("0 - Sair");
        System.out.println("------------------------------------");
    }

    private void cadastrarCliente() {
        System.out.println("\n--- Cadastro de Cliente ---");
        String nome = entrada.lerTexto("Nome: ");
        int idade = entrada.lerInteiro("Idade: ");
        String cpf = entrada.lerTexto("CPF: ");
        String telefone = entrada.lerTexto("Telefone: ");

        Cliente cliente = new Cliente(nome, cpf, telefone, calcularNascimentoPorIdade(idade));
        clientes.add(cliente);

        System.out.println("\nCliente cadastrado com sucesso!");
        System.out.println(cliente.gerarRelatorio());
        System.out.println("Idade informada: " + idade + " anos");
    }

    private void cadastrarFuncionario() {
        System.out.println("\n--- Cadastro de Funcionario ---");
        String nome = entrada.lerTexto("Nome: ");
        String cpf = entrada.lerTexto("CPF: ");
        String telefone = entrada.lerTexto("Telefone: ");
        String matricula = entrada.lerTexto("Matricula: ");
        String cargo = entrada.lerTexto("Cargo: ");
        double salario = entrada.lerDecimal("Salario: R$ ");

        Funcionario funcionario = new Funcionario(nome, cpf, telefone, matricula, cargo, salario);
        funcionarios.add(funcionario);

        System.out.println("\nFuncionario cadastrado com sucesso!");
        System.out.println(funcionario.gerarRelatorio());
    }

    private void cadastrarProduto() {
        System.out.println("\n--- Cadastro de Produto ---");
        System.out.println("1 - Medicamento");
        System.out.println("2 - Produto generico");
        int tipo = entrada.lerInteiro("Tipo do produto: ");

        String id = entrada.lerTexto("ID/Codigo: ");
        String nome = entrada.lerTexto("Nome: ");
        double preco = entrada.lerDecimal("Preco: R$ ");
        int quantidade = entrada.lerInteiro("Quantidade em estoque: ");

        Produto produto;
        if (tipo == 1) {
            String principioAtivo = entrada.lerTexto("Principio ativo: ");
            String dosagem = entrada.lerTexto("Dosagem: ");
            boolean necessitaReceita = entrada.lerBooleano("Necessita receita medica?");
            produto = new Medicamento(id, nome, preco, quantidade, principioAtivo, dosagem, necessitaReceita, criarDataValidadePadrao());
        } else {
            String categoria = entrada.lerTexto("Categoria: ");
            String fabricante = entrada.lerTexto("Fabricante: ");
            double descontoPercentual = entrada.lerDecimal("Desconto em porcentagem. Exemplo 10 para 10%: ");
            produto = new ProdutoGenerico(id, nome, preco, quantidade, categoria, fabricante, descontoPercentual / 100.0);
        }

        estoque.cadastrarProduto(produto);
        System.out.println("\nProduto cadastrado com sucesso!");
        System.out.println(produto.getDescricao());
    }

    private void listarEstoque() {
        System.out.println("\n--- Estoque Atual ---");
        if (estoque.getProdutos().isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : estoque.getProdutos()) {
            System.out.println(
                produto.getId() + " | " +
                produto.getNome() + " | " +
                FormatadorUtil.formatarMoeda(produto.calcularPreco()) + " | Qtd: " +
                produto.getQuantidade()
            );
        }
    }

    private void realizarVenda() {
        if (estoque.getProdutos().isEmpty()) {
            System.out.println("Nao ha produtos cadastrados para venda.");
            return;
        }

        Cliente cliente = selecionarOuCadastrarCliente();
        Funcionario funcionario = selecionarOuCadastrarFuncionario();
        Venda venda = new Venda("V" + contadorVenda++, cliente, funcionario);

        boolean continuar;
        do {
            listarEstoque();
            String idProduto = entrada.lerTexto("\nDigite o ID do produto para adicionar na venda: ");
            Produto produto = estoque.buscar(idProduto);

            if (produto == null) {
                System.out.println("Produto nao encontrado.");
            } else {
                int quantidade = entrada.lerInteiro("Quantidade: ");
                boolean possuiReceita = entrada.lerBooleano("Cliente possui receita valida?");

                try {
                    service.adicionarItemVenda(venda, produto, quantidade, possuiReceita);
                    System.out.println("Item adicionado com sucesso.");
                } catch (ReceitaInvalidaException | ProdutoVencidoException | EstoqueInsuficienteException e) {
                    System.out.println("Erro ao adicionar item: " + e.getMessage());
                }
            }

            continuar = entrada.lerBooleano("Deseja adicionar outro produto?");
        } while (continuar);

        if (venda.getItens().isEmpty()) {
            System.out.println("Venda cancelada, pois nenhum item foi adicionado.");
            return;
        }

        service.finalizarVenda(venda);
        System.out.println(venda.gerarRelatorio());
    }

    private Cliente selecionarOuCadastrarCliente() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente para continuar.");
            cadastrarCliente();
            return clientes.get(clientes.size() - 1);
        }

        System.out.println("\nClientes cadastrados:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + " - " + clientes.get(i).getNome());
        }
        int indice = entrada.lerInteiro("Escolha o cliente: ") - 1;

        if (indice >= 0 && indice < clientes.size()) {
            return clientes.get(indice);
        }

        System.out.println("Cliente invalido. Sera usado o primeiro cliente cadastrado.");
        return clientes.get(0);
    }

    private Funcionario selecionarOuCadastrarFuncionario() {
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionario cadastrado. Cadastre um funcionario para continuar.");
            cadastrarFuncionario();
            return funcionarios.get(funcionarios.size() - 1);
        }

        System.out.println("\nFuncionarios cadastrados:");
        for (int i = 0; i < funcionarios.size(); i++) {
            System.out.println((i + 1) + " - " + funcionarios.get(i).getNome());
        }
        int indice = entrada.lerInteiro("Escolha o funcionario: ") - 1;

        if (indice >= 0 && indice < funcionarios.size()) {
            return funcionarios.get(indice);
        }

        System.out.println("Funcionario invalido. Sera usado o primeiro funcionario cadastrado.");
        return funcionarios.get(0);
    }

    private void carregarDadosIniciais() {
        Cliente cliente = new Cliente("Cliente Padrao", "000.000.000-00", "(00) 00000-0000", calcularNascimentoPorIdade(30));
        Funcionario funcionario = new Funcionario("Atendente Padrao", "111.111.111-11", "(00) 99999-9999", "F001", "Atendente", 1800.00);

        clientes.add(cliente);
        funcionarios.add(funcionario);

        estoque.cadastrarProduto(new Medicamento("MED001", "Dipirona", 8.50, 50, "Dipirona sodica", "500mg", false, criarDataValidadePadrao()));
        estoque.cadastrarProduto(new Medicamento("MED002", "Amoxicilina", 32.90, 20, "Amoxicilina", "500mg", true, criarDataValidadePadrao()));
        estoque.cadastrarProduto(new ProdutoGenerico("GEN001", "Protetor Solar", 45.00, 15, "Dermocosmetico", "Solar Farma", 0.05));
    }

    private Date calcularNascimentoPorIdade(int idade) {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.YEAR, -idade);
        return calendario.getTime();
    }

    private Date criarDataValidadePadrao() {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.YEAR, 2);
        return calendario.getTime();
    }
}
