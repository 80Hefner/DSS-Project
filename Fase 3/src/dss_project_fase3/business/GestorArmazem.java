package dss_project_fase3.business;

import java.util.Set;

public class GestorArmazem {
    private Set<Prateleira> prateleirasUsadas;
    private Set<Prateleira> prateleirasLivres;

    public GestorArmazem(Set<Prateleira> prateleirasUsadas, Set<Prateleira> prateleirasLivres) {
        this.prateleirasUsadas = prateleirasUsadas;
        this.prateleirasLivres = prateleirasLivres;
    }




}
