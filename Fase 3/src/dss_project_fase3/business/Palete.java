package dss_project_fase3.business;

import dss_project_fase3.business.Localizacao.Localizacao;

public class Palete {
    private QR_Code qr_code;
    private Material material;
    private Localizacao localizacao;

    public Palete(QR_Code qr_code, Material material, Localizacao localizacao) {
        this.qr_code = qr_code;
        this.material = material;
        this.localizacao = localizacao;
    }

    public Palete(Palete p) {
        this.qr_code = p.getQr_code();
        this.material = p.getMaterial();
        this.localizacao = p.getLocalizacao();
    }

    public QR_Code getQr_code() {
        return qr_code;
    }

    public void setQr_code(QR_Code qr_code) {
        this.qr_code = qr_code;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        Palete p = (Palete) o;

        return this.qr_code.equals(p.getQr_code()) &&
                this.material.equals(p.getMaterial()) &&
                this.localizacao.equals(p.getLocalizacao());
    }

    public Palete clone() {
        return new Palete(this);
    }
}
