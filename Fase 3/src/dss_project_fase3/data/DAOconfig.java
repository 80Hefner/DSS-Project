package dss_project_fase3.data;

public class DAOconfig {
    static final String USERNAME = "root";                    // Actualizar para o que preferir
    static final String PASSWORD = "123456";                   // Actualizar para o que preferir
    private static final String DATABASE = "armazem_dss";           // Actualizar para o que preferir
    private static final String DRIVER = "jdbc:mysql";
    static final String URL = DRIVER+"://localhost:3306/"+DATABASE+"?useTimezone=true&serverTimezone=GMT";
}
