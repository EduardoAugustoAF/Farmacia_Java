package model;

import java.util.Date;

public class Funcionario extends Pessoa implements IRelatorio {
    private String matricula;
    private String cargo;
    private double salario;

    public Funcionario(String nome, String cpf, String telefone, String matricula,
        String cargo, double salario){
            super(nome, cpf, telefone);
            this.matricula = matricula;
            this.cargo = cargo;
            this.salario = salario;
        }
    
    public void registrarVenda(Venda venda){
        System.err.println("Venda Registrada pelo funcionário: "+ getNome());
    }

    @Override
    public String gerarRelatorio() {
        return "Funcionário: " + getNome() + " | Cargo: " + cargo + 
               " | Matrícula: " + matricula;
    }

    @Override
    public Date getDataGeracao() {
        return new Date();
    }
    
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
}

