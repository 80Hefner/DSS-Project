package dss_project_fase3;

import dss_project_fase3.ui.TextUI;

public class Main {

    public static void main(String[] args) {
        try {
            new TextUI().run();
        }
        catch (Exception e) {
            System.out.println("Não foi possível arrancar o programa\n" + e.getMessage());
        }
    }
}
