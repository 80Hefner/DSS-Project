package dss_project_fase3.business.Palete;

import dss_project_fase3.business.Localizacao.*;
import static dss_project_fase3.business.Enums.ZonaArmazem.*;

public class Palete {
    private QR_Code qr_code;
    private Material material;
    private Localizacao localizacao;

    public Palete(QR_Code qr_code, Material material, Localizacao localizacao) {
        this.qr_code = qr_code;
        this.material = material;
        this.localizacao = localizacao;
    }

    public Palete(String qr_code, String material, String localizacao, int corredor, int setor, int id_robot) {
        this.qr_code = new QR_Code(qr_code);
        this.material = new Material(material);
        switch (localizacao){
            case "ZONA_RECECAO":
                this.localizacao = new Localizacao_Transporte(ZONA_RECECAO);
                break;
            case "ZONA_ENTREGA":
                this.localizacao = new Localizacao_Transporte(ZONA_ENTREGA);
                break;
            case "ZONA_ARMAZENAMENTO":
                this.localizacao = new Localizacao_Armazenamento(corredor,setor);
                break;
            case "ROBOT":
                this.localizacao = new Localizacao_Robot(id_robot);
                break;
        }
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

    public String toString(String offset) {
        StringBuilder sb = new StringBuilder();

        sb.append(offset).append("Palete: ").append(this.qr_code.getCodigo()).append("\n");
        sb.append(this.material.toString(offset + "\t"));
        sb.append(this.localizacao.toString(offset + "\t"));

        return sb.toString();
    }

    public Palete clone() {
        return new Palete(this);
    }
}
