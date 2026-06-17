package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Venda implements IRelatorio {
    private String id;
    private Date dataVenda;
    private Map<Produto, Integer> itens;
    private Cliente cliente;
    private Funcionario funcionario;

    public Venda(String id, Cliente cliente, Funcionario funcionario){
        this.id = id;
        this.dataVenda = new Date();
        this.itens = new HashMap<>();
        this.cliente = cliente;
        this.funcionario = funcionario;
    }

    public void adicionarItem(Produto produto, int quantidade){
        int quantidadeAtual = this.itens.containsKey(produto) ? this.itens.get(produto) : 0;
        this.itens.put(produto, quantidadeAtual + quantidade);
    }

    public double calcularTotal(){
        double total = 0.0;
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()){
            Produto produto = entry.getKey();
            int qtdVendida = entry.getValue();
            total += produto.calcularPreco() * qtdVendida;
        }

        return total;
    }

    @Override
    public String gerarRelatorio(){
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("           CUPOM FISCAL DE VENDA         \n");
        sb.append("=========================================\n");
        sb.append("ID Venda: ").append(id).append("\n");
        sb.append("Data: ").append(dataVenda).append("\n");
        sb.append("Atendido por: ").append(funcionario.getNome()).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append("\n");
        sb.append("-----------------------------------------\n");
        sb.append("Itens comprados:\n");
        
        for (Map.Entry<Produto, Integer> entry : itens.entrySet()) {
            Produto p = entry.getKey();
            int qtd = entry.getValue();
            sb.append(p.getNome()).append(" x").append(qtd)
              .append(" -> Unid: R$ ").append(p.calcularPreco())
              .append(" | Subtotal: R$ ").append(p.calcularPreco() * qtd).append("\n");
        }
        
        sb.append("-----------------------------------------\n");
        sb.append("VALOR TOTAL: R$ ").append(calcularTotal()).append("\n");
        sb.append("=========================================\n");
        return sb.toString();
    }

    @Override
    public Date getDataGeracao(){
        return this.dataVenda;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Date getDataVenda() { return dataVenda; }

    public Map<Produto, Integer> getItens() { return itens; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }
}
