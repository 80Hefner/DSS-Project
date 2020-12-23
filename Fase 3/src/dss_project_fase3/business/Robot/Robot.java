package dss_project_fase3.business.Robot;

public class Robot {
    private final int id_robot;
    private boolean disponivel;
    private Entrega entregaAtual;

    public Robot(int id_robot) {
        this.id_robot = id_robot;
        this.disponivel = true;
        this.entregaAtual = null;
    }

    public Robot(int id_robot, boolean disponivel, Entrega entregaAtual) {
        this.id_robot = id_robot;
        this.disponivel = disponivel;
        this.entregaAtual = entregaAtual;
    }

    public Robot(Robot r) {
        this.id_robot = r.getId_robot();
        this.disponivel = r.isDisponivel();
        this.entregaAtual = r.getEntregaAtual();
    }

    public Robot(int id_robot, boolean disponivilidade, String qr_code, String localizacao_origem, int corredor_origem, int setor_origem, String localizacao_destino, int corredor_destino, int setor_destino) {
        this.id_robot = id_robot;
        this.disponivel = disponivilidade;
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
