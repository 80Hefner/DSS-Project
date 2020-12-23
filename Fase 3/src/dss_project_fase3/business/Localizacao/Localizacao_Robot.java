package dss_project_fase3.business.Localizacao;

import dss_project_fase3.utils.Enums.ZonaArmazem;

/**
 * Classe Localizacao_Robot que estende a classe abestrata Localizacao
 */
public class Localizacao_Robot extends Localizacao {
    private int id_robot;

    /**
     * Construtor parametrizado da Localizacao_Robot
     * @param id_robot           Id do Robot
     */
    public Localizacao_Robot(int id_robot) {
        super(ZonaArmazem.ROBOT);
        this.id_robot = id_robot;
    }

    /**
     * Construtor de cópia da Localizacao_Robot
     * @param l           Localizacao_Robot a copiar
     */
    public Localizacao_Robot(Localizacao_Robot l) {
        super(l.getZona());
        this.id_robot = l.getId_robot();
    }

    /**
     * Getter do ID_ROBOT da Localizacao_Robot
     * @return           ID_ROBOT da Localizacao_Robot
     */
    public int getId_robot() {
        return id_robot;
    }

    /**
     * Setter do ID_ROBOT da Localizacao_Robot
     * @param id_robot           ID_ROBOT da Localizacao_Robot
     */
    public void setId_robot(int id_robot) {
        this.id_robot = id_robot;
    }

    /**
     * Função de equals da Localizacao_Robot
     * @param o           Objeto ao qual queremos comparar a Localizacao_Robot
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Localizacao_Robot l = (Localizacao_Robot) o;

        return super.getZona().equals(l.getZona()) &&
                this.id_robot == l.getId_robot();
    }

    /**
     * Função que transforma a Localizacao_Robot numa String
     * @return           String resultante da função
     */
    public String toString(String offset) {
        StringBuilder sb = new StringBuilder();

        sb.append(offset).append("Localização: Robot\n");
        sb.append(offset).append("\tID Robot: ").append(this.id_robot).append("\n");

        return sb.toString();
    }

    /**
     * Função que dá clone à Localizacao_Robot
     * @return           Cópia da Localizacao_Robot
     */
    public Localizacao_Robot clone() {
        return new Localizacao_Robot(this);
    }
}