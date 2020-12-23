package dss_project_fase3.ui;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Esta classe implementa um menu em modo texto.
 */
public class Menu {
    private static Scanner sc;
    private List<String> opcoes;
    private int op;

    /**
     * Construtor para objetos da classe Menu
     */
    public Menu(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
        this.op = 0;
    }

    /**
     * Método que executa o menu
     */
    public void executa() {
        do {
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
    }

    /**
     * Método que apresenta o menu
     */
    private void showMenu() {
        System.out.println("\n *** Menu *** ");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Sair");
    }

    /**
     * Método que lê uma opção válida
     */
    private int lerOpcao() {
        int op;

        System.out.print("Opção: ");
        try {
            sc = new Scanner(System.in);
            op = sc.nextInt();
            if (op<0 || op>this.opcoes.size()) {
                System.out.println("Opção Inválida!!!");
                op = -1;
            }
        }
        catch (InputMismatchException e) { // Não foi inserido um int
            op = -1;
            System.out.println(e.toString());
        }

        return op;
    }

    /**
     * Método que obtém a última opção lida
     */
    public int getOpcao() {
        return this.op;
    }
}
