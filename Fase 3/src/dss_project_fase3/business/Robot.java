package dss_project_fase3.business;

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
        return entregaAtual.clone();
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
