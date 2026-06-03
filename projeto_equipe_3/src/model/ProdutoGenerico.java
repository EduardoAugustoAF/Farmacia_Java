package model;

public class ProdutoGenerico extends Produto {
    private String categoria;
    private String fabricante;
    private double desconto;

    public ProdutoGenerico(String id, String nome, double preco, int quantidade,
    String categoria, String fabricante, double desconto){
        super(id, nome, preco, quantidade);
        this.categoria = categoria;
        this.fabricante = fabricante;
        this.desconto = desconto;
    }

    @Override
    public double calcularPreco() {
        return this.preco - (this.preco * this.desconto);
    }

    @Override
    public String getDescricao() {
        return "Produto: " + nome + " | Categoria: " + categoria + " | Fabricante: " + fabricante;
    }
    
    @Override
    public void adicionarEstoque(int qtd) {
        this.quantidade += qtd;
    }

    @Override
    public void removerEstoque(int qtd) {
        if (this.quantidade >= qtd) {
            this.quantidade -= qtd;
        } else {
            System.out.println("Erro: Estoque insuficiente.");
        }
    }
}
