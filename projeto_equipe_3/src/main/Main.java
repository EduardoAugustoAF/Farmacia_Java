package main;

import exception.EstoqueInsuficienteException;
import exception.ProdutoVencidoException;
import exception.ReceitaInvalidaException;
import model.*;
import service.FarmaciaService;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema da Farmácia...\n");

        // 1. Inicializando as estruturas principais
        Estoque estoque = new Estoque();
        FarmaciaService service = new FarmaciaService();

        // Datas auxiliares para simular validade
        long umDia = 86400000L;
        Date dataValida = new Date(System.currentTimeMillis() + (30 * umDia)); // 30 dias no futuro
        Date dataVencida = new Date(System.currentTimeMillis() - umDia);       // Venceu ontem
        Date dataNascimento = new Date(System.currentTimeMillis() - (8000 * umDia)); // Data genérica

        // 2. Criando Atores (Polimorfismo e Herança em ação)
        Funcionario atendente = new Funcionario("Augusto", "111.222.333-44", "9999-8888", "MAT-001", "Farmacêutico", 3500.0);
        Cliente cliente = new Cliente("Gaby", "555.666.777-88", "7777-6666", dataNascimento);

        // 3. Criando Produtos e Adicionando ao Estoque
        Medicamento dorflex = new Medicamento("MED01", "Dorflex", 15.50, 50, "Dipirona", "1g", false, dataValida);
        Medicamento amoxicilina = new Medicamento("MED02", "Amoxicilina", 45.00, 20, "Amoxicilina", "500mg", true, dataValida);
        Medicamento remedioVencido = new Medicamento("MED03", "Aspirina", 10.00, 10, "Ácido Acetilsalicílico", "500mg", false, dataVencida);
        ProdutoGenerico shampoo = new ProdutoGenerico("GEN01", "Shampoo Clear", 25.00, 15, "Higiene", "Unilever", 0.10); // 10% desconto

        estoque.cadastrarProduto(dorflex);
        estoque.cadastrarProduto(amoxicilina);
        estoque.cadastrarProduto(remedioVencido);
        estoque.cadastrarProduto(shampoo);

        estoque.listar(); // Lista o estoque antes das vendas

        System.out.println("\n=========================================");
        System.out.println("      INICIANDO ATENDIMENTO DE VENDA      ");
        System.out.println("=========================================\n");

        // 4. Iniciando uma Venda
        Venda vendaAtual = new Venda("VND-1001", cliente, atendente);

        // --- TESTANDO O TRATAMENTO DE EXCEÇÕES (try-catch) ---

        // Teste 1: Adicionar produto normal com sucesso
        try {
            service.adicionarItemVenda(vendaAtual, dorflex, 2, false);
            service.adicionarItemVenda(vendaAtual, shampoo, 1, false);
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }

        // Teste 2: Forçar erro de Receita Inválida
        try {
            System.out.println("\n>> Tentando vender antibiótico sem receita...");
            service.adicionarItemVenda(vendaAtual, amoxicilina, 1, false); // false = sem receita
        } catch (ReceitaInvalidaException e) {
            System.err.println("BLOQUEIO NO CAIXA: " + e.getMessage());
        }

        // Teste 3: Forçar erro de Produto Vencido
        try {
            System.out.println("\n>> Tentando vender produto vencido...");
            service.adicionarItemVenda(vendaAtual, remedioVencido, 1, false);
        } catch (ProdutoVencidoException e) {
            System.err.println("ALERTA DE SEGURANÇA: " + e.getMessage());
        }

        // Teste 4: Forçar erro de Estoque Insuficiente
        try {
            System.out.println("\n>> Tentando vender quantidade maior que o estoque...");
            service.adicionarItemVenda(vendaAtual, dorflex, 100, false);
        } catch (EstoqueInsuficienteException e) {
            System.err.println("FALHA DE ESTOQUE: " + e.getMessage());
        }

        // 5. Finalizando a Venda (dispara o relatório e atualiza históricos)
        System.out.println("\n");
        service.finalizarVenda(vendaAtual);

        // 6. Testando as Interfaces de Relatório
        System.out.println(vendaAtual.gerarRelatorio());
        System.out.println(cliente.gerarRelatorio());
        System.out.println(atendente.gerarRelatorio());
    }
}