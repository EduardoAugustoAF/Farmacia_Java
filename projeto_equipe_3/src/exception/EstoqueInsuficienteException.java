package exception;

public class EstoqueInsuficienteException extends RuntimeException {
    private String produtoId;
    private int qtdDisponivel;

    public EstoqueInsuficienteException(String produtoId, int qtdDisponivel){
        super("Estoque insuficiente para o produto ID: " + produtoId + ". Quantidade disponível: " + qtdDisponivel);
        this.produtoId = produtoId;
        this.qtdDisponivel = qtdDisponivel;
    }

    @Override
    public String getMessage(){
        return super.getMessage();
    }

    public String getProdutoId(){
        return produtoId;
    }

    public int getQtdDisponivel(){
        return qtdDisponivel;
    }
}
