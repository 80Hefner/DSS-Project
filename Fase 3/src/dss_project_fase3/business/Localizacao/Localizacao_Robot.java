package dss_project_fase3.business.Localizacao;

import dss_project_fase3.business.ZonaArmazem;

public class Localizacao_Robot extends Localizacao {
    private int id_robot;

    public Localizacao_Robot(int id_robot) {
        super(ZonaArmazem.ROBOT);
        this.id_robot = id_robot;
    }

    public Localizacao_Robot(Localizacao_Robot l) {
        super(l.getZona());
        this.id_robot = l.getId_robot();
    }

    public int getId_robot() {
        return id_robot;
    }

    public void setId_robot(int id_robot) {
        this.id_robot = id_robot;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Localizacao_Robot l = (Localizacao_Robot) o;

        return super.getZona().equals(l.getZona()) &&
                this.id_robot == l.getId_robot();
    }

    public Localizacao_Robot clone() {
        return new Localizacao_Robot(this);
    }
}