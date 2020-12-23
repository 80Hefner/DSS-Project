package dss_project_fase3.business.Localizacao;

import dss_project_fase3.utils.Enums.ZonaArmazem;

/**
 * Classe Localizacao_Armazenamento que estende a classe abestrata Localizacao
 */
public class Localizacao_Transporte extends Localizacao{

    /**
     * Construtor parametrizado da Localizacao_Transporte
     * @param zona           ZonaArmazem
     */
    public Localizacao_Transporte(ZonaArmazem zona) {
        super(zona);
    }

    /**
     * Construtor de cópia da Localizacao_Transporte
     * @param l           Localizacao_Transporte a copiar
     */
    public Localizacao_Transporte(Localizacao_Transporte l) {
        super(l.getZona());
    }

    /**
     * Função de equals da Localizacao_Transporte
     * @param o           Objeto ao qual queremos comparar a Localizacao_Transporte
     */
    public boolean equals(Object o) { return super.equals(o);}

    /**
     * Função que transforma a Localizacao_Transporte numa String
     * @return           String resultante da função
     */
    public String toString(String offset) {
        if (this.getZona() == ZonaArmazem.ZONA_ENTREGA) return offset + "Localização: Zona de Entrega\n";
        else return offset + "Localização: Zona de Receção\n";
    }

    /**
     * Função que dá clone à Localizacao_Transporte
     * @return           Cópia da Localizacao_Transporte
     */
    public Localizacao_Transporte clone() { return new Localizacao_Transporte(this);}
}
