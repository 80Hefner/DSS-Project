package dss_project_fase3.business.Robot;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;

import static dss_project_fase3.business.Enums.ZonaArmazem.*;

public class Robot {
    private final int id_robot;
    private boolean disponivel;
    private Localizacao localizacaoAtual;
    private Entrega entregaAtual;

    public Robot(int id_robot) {
        this.id_robot = id_robot;
        this.disponivel = true;
        this.localizacaoAtual = new Localizacao_Transporte(ZONA_RECECAO);
        this.entregaAtual = null;
    }

    public Robot(int id_robot, boolean disponivel, Localizacao localizacaoAtual, Entrega entregaAtual) {
        this.id_robot = id_robot;
        this.disponivel = disponivel;
        this.localizacaoAtual = localizacaoAtual;
        this.entregaAtual = entregaAtual;
    }

    public Robot(Robot r) {
        this.id_robot = r.getId_robot();
        this.disponivel = r.isDisponivel();
        this.localizacaoAtual = r.getLocalizacaoAtual();
        this.entregaAtual = r.getEntregaAtual();
    }

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
                    this.localizacaoAtual = new Localizacao_Armazenamento(corredor_origem,setor_origem);
                    break;
            }
        }
        this.entregaAtual = new Entrega(qr_code, localizacao_origem, corredor_origem, setor_origem, localizacao_destino, corredor_destino, setor_destino);
    }

    public int getId_robot() {
        return id_robot;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Entrega getEntregaAtual() {
        if (this.entregaAtual != null) return entregaAtual.clone();
        else return null;
    }

    public void setEntregaAtual(Entrega entregaAtual) {
        this.entregaAtual = entregaAtual.clone();
    }

    public void pegarEntrega(Entrega entrega) {
        this.disponivel = false;
        this.entregaAtual = entrega;
    }

    public void pousarEntrega() {
        this.disponivel = true;
        this.entregaAtual = null;
    }

    public Localizacao getLocalizacaoAtual() {
        return localizacaoAtual;
    }

    public void setLocalizacaoAtual(Localizacao localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
    }

    public boolean equals(Object o) {
        return this == o;
    }

    public String toString(String offset) {
        StringBuilder sb = new StringBuilder();

        sb.append(offset).append("Robot ").append(this.id_robot).append("\n");
        sb.append(offset).append("\tDisponivel: ").append(this.disponivel).append("\n");
        if (this.entregaAtual != null) sb.append(this.entregaAtual.toString(offset + "\t"));

        return sb.toString();
    }
}
