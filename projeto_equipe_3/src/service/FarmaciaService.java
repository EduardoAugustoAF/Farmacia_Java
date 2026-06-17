package service;

import exception.EstoqueInsuficienteException;
import exception.ProdutoVencidoException;
import exception.ReceitaInvalidaException;
import model.Medicamento;
import model.Produto;
import model.Venda;

public class FarmaciaService {
    public void adicionarItemVenda(Venda venda, Produto produto, int quantidade, boolean possuiReceitaValida) {
        validarProdutoParaVenda(venda, produto, quantidade, possuiReceitaValida);
        venda.adicionarItem(produto, quantidade);
        System.out.println(quantidade + "x " + produto.getNome() + " adicionado(s) ao carrinho.");
    }

    public void finalizarVenda(Venda venda) {
        System.out.println("\n--- Iniciando processamento da venda: " + venda.getId() + " ---");

        if (venda.getItens().isEmpty()) {
            throw new IllegalStateException("A venda não possui itens registrados.");
        }

        for (java.util.Map.Entry<Produto, Integer> item : venda.getItens().entrySet()) {
            Produto produto = item.getKey();
            int quantidade = item.getValue();

            if (produto.getQuantidade() < quantidade) {
                throw new EstoqueInsuficienteException(produto.getId(), produto.getQuantidade());
            }
        }

        for (java.util.Map.Entry<Produto, Integer> item : venda.getItens().entrySet()) {
            Produto produto = item.getKey();
            int quantidade = item.getValue();
            produto.removerEstoque(quantidade);
        }

        double total = venda.calcularTotal();

        venda.getCliente().adicionarCompra(venda);
        venda.getFuncionario().registrarVenda(venda);

        System.out.println("Venda finalizada com sucesso!");
        System.out.println("Total pago: R$ " + total);
        System.out.println("Liberação do terminal de caixa concluída.\n");
    }

    private void validarProdutoParaVenda(Venda venda, Produto produto, int quantidade, boolean possuiReceitaValida) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }

        if (produto instanceof Medicamento) {
            Medicamento med = (Medicamento) produto;

            if (med.isVencido()) {
                throw new ProdutoVencidoException(med.getNome(), med.getDataValidade());
            }

            if (med.isNecessitaReceita() && !possuiReceitaValida) {
                throw new ReceitaInvalidaException(med.getNome(), "Documento ausente ou fora da validade.");
            }
        }

        int quantidadeJaAdicionada = venda.getItens().containsKey(produto) ? venda.getItens().get(produto) : 0;
        int quantidadeTotalNoCarrinho = quantidadeJaAdicionada + quantidade;

        if (produto.getQuantidade() < quantidadeTotalNoCarrinho) {
            throw new EstoqueInsuficienteException(produto.getId(), produto.getQuantidade());
        }
    }
}