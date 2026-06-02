package model;

import java.util.ArrayList;
import java.util.List;

public class Estoque {
    private List<Produto> produtos;

    public Estoque(){
        this.produtos = new ArrayList<>();
    }

    public void cadastrarProduto(Produto produto){
        this.produtos.add(produto);
    }

    public Produto buscar(String id){
        for (Produto p : produtos){
            if (p.getId().equalsIgnoreCase(id)){
                return p;
            }
        }
        return null; 
    }

    public void listar(){
        System.out.println("=== LISTAGEM DE ESTOQUE ===");
        for (Produto p : produtos){
            System.out.println(p.getDescricao() + " | Quantidade Disponível" + p.getQuantidade());
        }
    }

    public List<Produto> getProdutos(){
        return produtos;
    }
}
