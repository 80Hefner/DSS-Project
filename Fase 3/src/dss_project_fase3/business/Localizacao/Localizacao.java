package dss_project_fase3.business.Localizacao;

import dss_project_fase3.business.ZonaArmazem;

public abstract class Localizacao {
    private ZonaArmazem zona;

    public Localizacao(ZonaArmazem zona) {
        this.zona = zona;
    }

    public Localizacao(Localizacao l) {
        this.zona = l.getZona();
    }

    public ZonaArmazem getZona() {
        return zona;
    }

    public void setZona(ZonaArmazem zona) {
        this.zona = zona;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Localizacao l = (Localizacao) o;

        return this.zona.equals(l.getZona());
    }

    public abstract Localizacao clone();
}