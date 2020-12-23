package dss_project_fase3.business.Robot;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;

import static dss_project_fase3.utils.Enums.ZonaArmazem.*;

/**
 * Classe Robot
 */
public class Robot {
    private final int id_robot;
    private boolean disponivel;
    private Localizacao localizacaoAtual;
    private Entrega entregaAtual;

    /**
     * Construtor parametrizado de Robot
     * @param id_robot           ID do robot a criar
     */
    public Robot(int id_robot) {
        this.id_robot = id_robot;
        this.disponivel = true;
        this.localizacaoAtual = new Localizacao_Transporte(ZONA_RECECAO);
        this.entregaAtual = null;
    }

    /**
     * Construtor parametrizado do Robot
     * @param id_robot              id_Robot associado a um Robot
     * @param disponivel            disponibilidade do Robot para transporte de Paletes
     * @param localizacaoAtual      Localização atual do Robot
     * @param entregaAtual          Entrega que Robot se encontra a fazer
     */
    public Robot(int id_robot, boolean disponivel, Localizacao localizacaoAtual, Entrega entregaAtual) {
        this.id_robot = id_robot;
        this.disponivel = disponivel;
        this.localizacaoAtual = localizacaoAtual;
        this.entregaAtual = entregaAtual;
    }

    /**
     * Construtor de cópia do Robot
     * @param r           Robot a copiar
     */
    public Robot(Robot r) {
        this.id_robot = r.getId_robot();
        this.disponivel = r.isDisponivel();
        this.localizacaoAtual = r.getLocalizacaoAtual();
        this.entregaAtual = r.getEntregaAtual();
    }

    /**
     * Construtor parametrizado do Robot
     * @param id_robot                  id_Robot associado a um Robot
     * @param disponivilidade           Disponibilidade do Robot para transporte de Paletes
     * @param localizacao_atual         Designação da Localização atual do Robot
     * @param corredor_atual            Corredor atual do Robot
     * @param setor_atual               Setor atual do Robot
     * @param localizacao_origem        Designação da Localização origem associada a uma Entrega
     * @param corredor_origem           Corredor origem da Entrega
     * @param setor_origem              Setor origem da Entrega
     * @param localizacao_destino       Designação da Localização destino associada a uma Entrega
     * @param corredor_destino          Corredor destino da Entrega
     * @param setor_destino             Setor destino da Entrega
     */
    public Robot(int id_robot, boolean disponivilidade, String qr_code, String localizacao_atual, int corredor_atual, int setor_atual, String localizacao_origem, int corredor_origem, int setor_origem, String localizacao_destino, int corredor_destino, int setor_destino) {
        this.id_robot = id_robot;
        this.disponivel = disponivilidade;
        if (localizacao_atual != null) {
            switch (localizacao_atual){
                case "ZONA_RECECAO":
                    this.localizacaoAtual = new Localizacao_Transporte(ZONA_RECECAO);
                    break;
                case "ZONA_ENTREGA":
                    this.localizacaoAtual = new Localizacao_Transporte(ZONA_ENTREGA);
                    break;
                case "ZONA_ARMAZENAMENTO":
                    this.localizacaoAtual = new Localizacao_Armazenamento(corredor_atual,setor_atual);
                    break;
            }
        }
        this.entregaAtual = new Entrega(qr_code, localizacao_origem, corredor_origem, setor_origem, localizacao_destino, corredor_destino, setor_destino);
    }

    /**
     * Getter do ID_Robot associado a um Robot
     * @return           ID_Robot associado a um Robot
     */
    public int getId_robot() {
        return id_robot;
    }

    /**
     * Função que verifica se Robot se encontra disponível para transportar Paleetes
     * @return      Disponibilidade do Robot para transportar paletes
     */
    public boolean isDisponivel() {
        return disponivel;
    }

    /**
     * Setter da Disponibilidade do Robot para transportar paletes
     * @param disponivel           Disponibilidade do Robot para transportar paletes a colocar
     */
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    /**
     * Getter da Entrega Atual associada a um Robot
     * @return           Entrega Atual associada a um Robot
     */
    public Entrega getEntregaAtual() {
        if (this.entregaAtual != null) return entregaAtual.clone();
        else return null;
    }

    /**
     * Setter da Entrega Atual associada a um Robot
     * @param entregaAtual           Entrega Atual associada a um Robot a colocar
     */
    public void setEntregaAtual(Entrega entregaAtual) {
        this.entregaAtual = entregaAtual.clone();
    }

    /**
     * Getter da Localizacao Atual associada a um Robot
     * @return           Localizacao Atual associada a um Robot
     */
    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    /**
     * Setter da Localizacao Atual associada a um Robot
     * @param localizacaoAtual          Localizacao Atual associada a um Robot a colocar
     */
    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    /**
     * Função que faz alterações necessárias quando um Robot recolhe uma Entrega
     * @param entrega       Entrega recolida pelo Robot
     */
    public void pegarEntrega(Entrega entrega) {
        this.disponivel = false;
        this.entregaAtual = entrega;
    }

    /**
     * Função que faz alterações necessárias quando um Robot pousa uma Entrega
     */
    public void pousarEntrega() {
        this.disponivel = true;
        this.entregaAtual = null;
    }

    /**
     * Função de equals de Robot
     * @param o           Objeto ao qual queremos comparar ao Robot
     */
    public boolean equals(Object o) {
        return this == o;
    }

    /**
     * Função que transforma o Robot numa String
     * @return           String resultante da função
     */
    public String toString(String offset) {
        StringBuilder sb = new StringBuilder();

        sb.append(offset).append("Robot ").append(this.id_robot).append("\n");
        sb.append(offset).append("\tDisponivel: ").append(this.disponivel).append("\n");
        if (this.entregaAtual != null) sb.append(this.entregaAtual.toString(offset + "\t"));

        return sb.toString();
    }
}
