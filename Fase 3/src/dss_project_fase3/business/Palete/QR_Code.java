package dss_project_fase3.business.Palete;

import dss_project_fase3.utils.Exceptions.InvalidQRCodeException;

/**
 * Classe QR_Code
 */
public class QR_Code {
    private String codigo;

    /**
     * Construtor parametrizado do QR_Code
     * @param codigo           Codigo do QR_Code
     */
    public QR_Code(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Construtor de cópia do QR_Code
     * @param q           QR_Code a copiar
     */
    public QR_Code(QR_Code q) {
        this.codigo = q.getCodigo();
    }

    /**
     * Getter do Codigo do QR_Code
     * @return           Codigo do QR_Code
     */
    public String getCodigo() {
        return this.codigo;
    }

    /**
     * Setter do Codigo do QR_Code
     * @param codigo           Codigo do QR_Code a colocar
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Função de equals de QR_Code
     * @param o           Objeto ao qual queremos comparar o QR_Code
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null || this.getClass() != o.getClass()) return false;
        QR_Code m = (QR_Code) o;

        return this.codigo.equals(m.getCodigo());
    }

    /**
     * Função que dá clone ao QR_Code
     * @return           Cópia do QR_Code
     */
    public QR_Code clone() {
        return new QR_Code(this);
    }

    /**
     *  Função que verifica se Código QR é valido
     * @throws InvalidQRCodeException   Exceção de Código_QR Inválido
     */
    public void isValid() throws InvalidQRCodeException {   // exemplo:   "QR_CODE_BEGIN&&Farinha&&QR_CODE_END"
        String[] param = this.codigo.split("&&");

        if ( param.length != 3
                || !param[0].equals("QR_CODE_BEGIN")
                || !param[2].equals("QR_CODE_END"))
            throw new InvalidQRCodeException();
    }

    /**
     * Função que encontra a Designação do Material associado a um QR_Code
     * @return  Designação do Material associado a um QR_Code
     */
    public String getMaterial() {
        return this.codigo.split("&&")[1];
    }
}
