package dss_project_fase3.business;

public class QR_Code {
    private String codigo;

    public QR_Code(String codigo) {
        this.codigo = codigo;
    }

    public QR_Code(QR_Code q) {
        this.codigo = q.getCodigo();
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        QR_Code m = (QR_Code) o;

        return this.codigo.equals(m.getCodigo());
    }

    public QR_Code clone() {
        return new QR_Code(this);
    }

    public boolean isValid() {   // exemplo:   "QR_CODE_BEGIN&&Farinha&&QR_CODE_END"
        String[] param = this.codigo.split("&&");

        return param.length == 3 &&
                param[0].equals("QR_CODE_BEGIN") &&
                param[2].equals("QR_CODE_END");
    }
}
