package model;

public interface IEstocavel {
    void adicionarEstoque(int quantidade);
    void removerEstoque(int quantidade);
    int getQuantidade();
}
