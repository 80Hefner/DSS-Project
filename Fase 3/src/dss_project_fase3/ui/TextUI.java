package dss_project_fase3.ui;

import dss_project_fase3.business.ArmazemFacade;
import dss_project_fase3.business.Enums.ZonaArmazem;
import dss_project_fase3.business.Exceptions.InvalidQRCodeException;
import dss_project_fase3.business.IArmazemFacade;
import dss_project_fase3.business.Palete.Palete;
import dss_project_fase3.business.Palete.QR_Code;

import java.util.List;
import java.util.Scanner;

/**
 * Interface do projeto, em modo texto.
 */
public class TextUI {
    // O model tem a 'lógica de negócio'.
    private IArmazemFacade model;

    // Menus da aplicação
    private Menu menu;

    // Scanner para leitura
    private Scanner sc;

    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {
        // Criar o menu
        String[] opcoes = {
                "Comunicar código QR",
                "Comunicar ordem de transporte",
                "Notificar recolha de palete",
                "Notificar entrega de palete",
                "Consultar listagem de localizações"};
        this.menu = new Menu(opcoes);
        this.model = new ArmazemFacade(2, 5);
        sc = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        do {
            menu.executa();
            switch (menu.getOpcao()) {
                case 1:
                    trataComunicarCodigoQR();
                    break;
                case 2:
                    trataComunicarOrdemTransporte();
                    break;
                case 3:
                    trataNotificarRecolhaPalete();
                    break;
                case 4:
                    trataNotificarEntregaPalete();
                    break;
                case 5:
                    trataConsultarListagemLocalizacoes();
                    break;
            }
        } while (menu.getOpcao()!=0); // A opção 0 é usada para sair do menu.
        System.out.println("Até breve!...");
    }

    // Métodos auxiliares
    private void trataComunicarCodigoQR() {
        try {
            System.out.print("Código QR da palete: ");
            String qr_code_str = sc.nextLine();
            QR_Code qr_code = new QR_Code(qr_code_str);
            qr_code.isValid();

            this.model.comunicar_codigo_qr(new QR_Code(qr_code));
        }
        catch (NullPointerException en) {
            System.out.println(en.getMessage());
        }
        catch (InvalidQRCodeException ei) {
            System.out.println(ei.getMessage());
        }
    }

    private void trataComunicarOrdemTransporte() {
        this.model.comunicar_ordem_transporte();
    }

    private void trataNotificarRecolhaPalete() {
        try {
            System.out.print("Código QR da palete: ");
            String qr_code_str = sc.nextLine();
            QR_Code qr_code = new QR_Code(qr_code_str);
            qr_code.isValid();

            this.model.notificar_recolha_palete(new QR_Code(qr_code));
        }
        catch (NullPointerException en) {
            System.out.println(en.getMessage());
        }
        catch (InvalidQRCodeException ei) {
            System.out.println(ei.getMessage());
        }
    }

    private void trataNotificarEntregaPalete() {
        try {
            System.out.print("Código QR da palete: ");
            String qr_code_str = sc.nextLine();
            QR_Code qr_code = new QR_Code(qr_code_str);
            qr_code.isValid();

            this.model.notificar_entrega_palete(new QR_Code(qr_code));
        }
        catch (NullPointerException en) {
            System.out.println(en.getMessage());
        }
        catch (InvalidQRCodeException ei) {
            System.out.println(ei.getMessage());
        }
    }

    private void trataConsultarListagemLocalizacoes() {
        List<Palete> paletes = this.model.consultar_listagem_localizacoes();

        System.out.println("Paletes no armazém:");
        for (int i = 0; i < paletes.size(); i++) {
            Palete p = paletes.get(i);
            System.out.println(p.toString("\t"));
        }
    }
}
