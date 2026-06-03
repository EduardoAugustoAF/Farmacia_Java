package model;

import java.util.Date;

public class Medicamento extends Produto {
    private String principioAtivo;
    private String dosagem;
    private boolean necessitaReceita;
    private Date dataValidade;

    public Medicamento(String id, String nome, double preco, int quantidade, String principioAtivo,
        String dosagem, boolean necessitaReceita, Date dataValidade){
            super(id, nome, preco, quantidade);
            this.principioAtivo = principioAtivo;
            this.dosagem = dosagem;
            this.necessitaReceita = necessitaReceita;
            this.dataValidade = dataValidade;
        }
    
    public boolean isVencido(){
        Date hoje = new Date();
        return dataValidade.before(hoje);
    }

    @Override
    public double calcularPreco() {
        return this.preco;
    }

    @Override
    public String getDescricao() {
        return "Medicamento: " + nome + " | Princípio Ativo: " + principioAtivo + " " + dosagem;
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