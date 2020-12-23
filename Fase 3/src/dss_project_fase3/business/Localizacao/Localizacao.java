package dss_project_fase3.business.Localizacao;

import dss_project_fase3.utils.Enums.ZonaArmazem;

/**
 * Classe abestrata relativa a uma Localização no Armazem
 */
public abstract class Localizacao {
    private ZonaArmazem zona;

    /**
     * Construtor parametrizado da Localizacao
     * @param zona           Zona Armazem
     */
    public Localizacao(ZonaArmazem zona) {
        this.zona = zona;
    }

    /**
     * Construtor de cópia da Localizacao
     * @param l           Localizacao a copiar
     */
    public Localizacao(Localizacao l) {
        this.zona = l.getZona();
    }

    /**
     * Getter da ZonaArmazem da Localizacao
     * @return           Zona Armazem
     */
    public ZonaArmazem getZona() {
        return zona;
    }

    /**
     * Setter da ZonaArmazem da Localizacao
     * @param zona           Zona Armazem a colocar
     */
    public void setZona(ZonaArmazem zona) {
        this.zona = zona;
    }

    /**
     * Função de equals da Localizacao
     * @param o           Objeto ao qual queremos comparar a Localizacao
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Localizacao l = (Localizacao) o;

        return this.zona.equals(l.getZona());
    }

    /**
     * Função que transforma a Localizacao numa String
     * @return           String resultante da função
     */
    public abstract String toString(String offset);

    /**
     * Função que dá clone à Localizacao
     * @return           Cópia da Localizacao
     */
    public abstract Localizacao clone();
}