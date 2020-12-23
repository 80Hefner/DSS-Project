package dss_project_fase3.ui;

import dss_project_fase3.business.ArmazemFacade;
import dss_project_fase3.business.IArmazemFacade;
import dss_project_fase3.business.Palete.Palete;
import dss_project_fase3.business.Palete.QR_Code;
import dss_project_fase3.utils.Exceptions.*;

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

    /**
     * Opção responsavel por comunicar um Código QR
     */
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

    /**
     * Opção responsável por comunicar ordem de transporte
     */
    private void trataComunicarOrdemTransporte() {
        try {
            this.model.comunicar_ordem_transporte();
        }
        catch (NullPointerException en) {
            System.out.println(en.getMessage());
        }
        catch (EmptyTransportQueueException et) {
            System.out.println(et.getMessage());
        }
        catch (InvalidTransportOrderException el) {
            System.out.println(el.getMessage());
        }
    }

    /**
     * Opção responsável por notificar recolha de Palete
     */
    private void trataNotificarRecolhaPalete() {
        try {
            System.out.print("ID Robot: ");
            int id_robot = sc.nextInt();

            this.model.notificar_recolha_palete(id_robot);
        }
        catch (NullPointerException en) {
            System.out.println(en.getMessage());
        }
        catch (InvalidRobotIDException eid) {
            System.out.println(eid.getMessage());
        }
        catch (InvalidRequestFromRobot eir) {
            System.out.println(eir.getMessage());
        }
    }

    /**
     * Opção responsável por notificar entrega da Palete
     */
    private void trataNotificarEntregaPalete() {
        try {
            System.out.print("ID Robot: ");
            int id_robot = sc.nextInt();

            this.model.notificar_entrega_palete(id_robot);
        }
        catch (NullPointerException en) {
            System.out.println(en.getMessage());
        }
        catch (InvalidRobotIDException eid) {
            System.out.println(eid.getMessage());
        }
        catch (InvalidRequestFromRobot eir) {
            System.out.println(eir.getMessage());
        }
    }

    /**
     * Opção responsável por consultar todas as localizações de todas as paletes
     */
    private void trataConsultarListagemLocalizacoes() {
        List<Palete> paletes = this.model.consultar_listagem_localizacoes();

        System.out.println("Paletes no armazém:");
        for (Palete p : paletes) {
            System.out.println(p.toString("\t"));
        }
    }
}
