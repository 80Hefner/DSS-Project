package dss_project_fase3.business.Localizacao;

import dss_project_fase3.business.ZonaArmazem;

public class Localizacao_Transporte extends Localizacao{
    public Localizacao_Transporte(ZonaArmazem zona) {
        super(zona);
    }

    public Localizacao_Transporte(Localizacao_Transporte l) {
        super(l.getZona());
    }

    public boolean equals(Object o) { return super.equals(o);}

    public Localizacao_Transporte clone() { return new Localizacao_Transporte(this);}
}
