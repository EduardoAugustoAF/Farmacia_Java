package util;

import java.util.Scanner;

/**
 * Classe utilitaria criada para concentrar a entrada de dados via Scanner.
 * Ela evita repeticao de codigo e deixa o modo console mais organizado.
 */
public class EntradaDados {
    private final Scanner scanner;

    public EntradaDados() {
        this.scanner = new Scanner(System.in);
    }

    public String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                String entrada = scanner.nextLine().trim();
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Digite um numero inteiro.");
            }
        }
    }

    public double lerDecimal(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                String entrada = scanner.nextLine().trim().replace(",", ".");
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Digite um numero decimal. Exemplo: 12.50");
            }
        }
    }

    public boolean lerBooleano(String mensagem) {
        while (true) {
            System.out.print(mensagem + " (S/N): ");
            String entrada = scanner.nextLine().trim();

            if (entrada.equalsIgnoreCase("S") || entrada.equalsIgnoreCase("SIM")) {
                return true;
            }
            if (entrada.equalsIgnoreCase("N") || entrada.equalsIgnoreCase("NAO") || entrada.equalsIgnoreCase("NÃO")) {
                return false;
            }

            System.out.println("Opcao invalida. Responda com S ou N.");
        }
    }

    public void pausar() {
        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    public void fechar() {
        scanner.close();
    }
}
