package exception;

public class ReceitaInvalidaException extends RuntimeException{
    private String medicamentoNome;
    private String motivo;

    public ReceitaInvalidaException(String medicamentoNome, String motivo) {
        super("Receita inválida para o medicamento '" + medicamentoNome + "'. Motivo: " + motivo);
        this.medicamentoNome = medicamentoNome;
        this.motivo = motivo;
    }

    @Override
    public String getMessage(){
        return super.getMessage();
    }
    
    public String getMedicamentoNome(){
        return medicamentoNome;
    }

    public String getMotivo(){
        return motivo;
    }
}

