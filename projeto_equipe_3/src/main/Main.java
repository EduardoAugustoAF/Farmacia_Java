package main;

import view.FarmaciaGUI;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("scanner")) {
            new MainScanner().executar();
            return;
        }

        FarmaciaGUI.abrir();
    }
}
