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

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    // O método que resolve o erro "isNecessitaReceita() is undefined"
    public boolean isNecessitaReceita() {
        return necessitaReceita;
    }

    public void setNecessitaReceita(boolean necessitaReceita) {
        this.necessitaReceita = necessitaReceita;
    }

    // O método que resolve o erro "getDataValidade() is undefined"
    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }
}