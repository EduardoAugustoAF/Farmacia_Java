package exception;

import java.util.Date;

public class ProdutoVencidoException extends RuntimeException{
    private Date dataValidade;
    private String nomeProduto;

    public ProdutoVencidoException(String nomeProduto, Date dataValidade) {
        super("O produto '" + nomeProduto + "' encontra-se vencido desde: " + dataValidade);
        this.nomeProduto = nomeProduto;
        this.dataValidade = dataValidade;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }
}
