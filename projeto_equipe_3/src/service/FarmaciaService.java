package service;

import exception.EstoqueInsuficienteException;
import exception.ProdutoVencidoException;
import exception.ReceitaInvalidaException;
import model.Medicamento;
import model.Produto;
import model.Venda;

public class FarmaciaService {
    public void adicionarItemVenda(Venda venda, Produto produto, int quantidade, boolean possuiReceitaValida) {
        if (produto instanceof Medicamento) {
            Medicamento med = (Medicamento) produto;
            
            if (med.isVencido()) {
                throw new ProdutoVencidoException(med.getNome(), med.getDataValidade());
            }
        
            if (med.isNecessitaReceita() && !possuiReceitaValida) {
                throw new ReceitaInvalidaException(med.getNome(), "Documento ausente ou fora da validade.");
            }
        }

        if (produto.getQuantidade() < quantidade) {
            throw new EstoqueInsuficienteException(produto.getId(), produto.getQuantidade());
        }
        produto.removerEstoque(quantidade);
        venda.adicionarItem(produto, quantidade);
        System.out.println(quantidade + "x " + produto.getNome() + " adicionado(s) à venda.");
    }

    public void finalizarVenda(Venda venda) {
        System.out.println("\n--- Iniciando processamento da venda: " + venda.getId() + " ---");
        
        try {
            if (venda.getItens().isEmpty()) {
                throw new IllegalStateException("A venda não possui itens registrados.");
            }

            double total = venda.calcularTotal();
            
            venda.getCliente().adicionarCompra(venda);
            venda.getFuncionario().registrarVenda(venda);
            
            System.out.println("Venda finalizada com sucesso!");
            System.out.println("Total pago: R$ " + total);
            
        } catch (IllegalStateException e) {
            System.err.println("Erro na operação comercial: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro crítico ao processar a venda: " + e.getMessage());
        } finally {
            System.out.println("Liberação do terminal de caixa concluída.\n");
        }
    }
}