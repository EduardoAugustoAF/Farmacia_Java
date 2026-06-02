package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cliente extends Pessoa implements IRelatorio {
    private Date dataNascimento;
    private List<Venda> historicoCompras;
    private int pontosFidelidade;

    public Cliente(String nome, String cpf, String telefone, Date dataNascimento){
        super(nome, cpf, telefone);
        this.dataNascimento = dataNascimento;
        this.historicoCompras = new ArrayList<>();
        this.pontosFidelidade = 0;
    }

    public void adicionarCompra(Venda venda){
        this.historicoCompras.add(venda);
        this.pontosFidelidade += 10;
    }

    @Override
    public String gerarRelatorio(){
        return "Cliente: " + getNome() + " | CPF: " + getCpf() +
               " | Pontos: " + pontosFidelidade;
    }

    @Override
    public Date getDataGeracao(){
        return new Date();
    }

    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    
    public List<Venda> getHistorico() { return historicoCompras; }
    
    public int getPontosFidelidade() { return pontosFidelidade; }
    public void setPontosFidelidade(int pontosFidelidade) { this.pontosFidelidade = pontosFidelidade; }
}
