package dss_project_fase3.data;


import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Prateleira.Prateleira;

import java.sql.*;
import java.util.*;

/**
 * Classe PrateleiraDAO que implementa a interface IPrateleiraDAO
 */
public class PrateleiraDAO implements IPrateleiraDAO {
    private static PrateleiraDAO singleton = null;

    public PrateleiraDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS prateleiras (" +
                    "CORREDOR int NOT NULL," +
                    "SETOR int NOT NULL," +
                    "PALETE varchar (100) DEFAULT NULL," +
                    "PRIMARY KEY (CORREDOR, SETOR)," +
                    "FOREIGN KEY (PALETE) REFERENCES paletes(QR_CODE))";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    /**
     * Implementação do padrão Singleton
     * @return devolve a instância única desta classe
     */
    public static PrateleiraDAO getInstance() {
        if (PrateleiraDAO.singleton == null) {
            PrateleiraDAO.singleton = new PrateleiraDAO();
        }
        return PrateleiraDAO.singleton;
    }


    /**
     * @return número de prateleiras na base de dados
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM prateleiras")) {
            if(rs.next()) {
                i = rs.getInt(1);
            }
        }
        catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }


    /**
     * Método que verifica se existem prateleiras na base de dados
     * @return true se existirem 0 prateleiras
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     * Método que verifica se um objeto existe na base de dados
     * @param o objeto Prateleira
     * @return true se a Prateleira existe
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public boolean contains(Object o) {
        boolean r;
        Prateleira prat = (Prateleira) o;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT * FROM prateleiras WHERE CORREDOR="+prat.getLocalizacao().getCorredor()+" AND SETOR="+prat.getLocalizacao().getSetor()
                             + " AND PALETE='"+prat.getQr_code()+"'" )) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }


    /**
     * Método que retorna um iterador para as prateleiras na base de dados
     * @return iterador de um Set com as prateleiras todas
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public Iterator<Prateleira> iterator() {
        Set<Prateleira> col = getPrateleirasDB();

        return col.iterator();
    }


    /**
     * Método que retorna um array contendo as prateleiras na base de dados
     * @return array com as prateleiras todas
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public Object[] toArray() {
        Set<Prateleira> col = getPrateleirasDB();

        return col.toArray();
    }


    /**
     * Método que retorna um array contendo as prateleiras na base de dados
     * @param a array onde serão guardadas as prateleiras todas, caso tenha espaço suficiente
     * @return array com as prateleiras todas
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public <T> T[] toArray(T[] a) {
        Set<Prateleira> col = getPrateleirasDB();

        return (T[]) col.toArray(a);
    }


    /**
     * Método que insere uma prateleira na base de dados
     * @param prateleira prateleira a ser inserida
     * @return          boolean para confirmar que a prateleira foi adicionada
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public boolean add(Prateleira prateleira) {
        boolean res = false;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                        "INSERT INTO prateleiras VALUES ("+prateleira.getLocalizacao().getCorredor()+", "+prateleira.getLocalizacao().getSetor()+", '"+prateleira.getQr_code()+"') " +
                                "ON DUPLICATE KEY UPDATE CORREDOR=VALUES(CORREDOR), SETOR=VALUES(SETOR), PALETE=VALUES(PALETE)");
            res = true;
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    /**
     * Método que remove um objeto da base de dados
     * @param o objeto prateleira a ser removido
     * @return          boolean para confirmar que a prateleira foi removida
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public boolean remove(Object o) {
        boolean r = false;
        Prateleira prat = (Prateleira) o;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM paletes WHERE CORREDOR="+prat.getLocalizacao().getCorredor()+" AND SETOR="+prat.getLocalizacao().getSetor()
                    + " AND PALETE='"+prat.getQr_code()+"'" );
            r = true;
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }


    /**
     * Método que verifica se todos os objetos existem na base de dados
     * @param c collection com os objetos a procurar
     * @return true se todos os objetos existirem na base de dados
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        Set<Prateleira> col = getPrateleirasDB();

        return col.containsAll(c);
    }


    /**
     * Método que insere todos os objetos na base de dados
     * @param c collection com os objetos a inserir
     * @return true se todos os objetos forem inseridos na base de dados
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public boolean addAll(Collection<? extends Prateleira> c) {
        Set<Prateleira> col = getPrateleirasDB();

        return col.addAll(c);
    }


    /**
     * Método que retêm apenas os objetos que estão contidos na collection especificada
     * @param c collection com os objetos a reter
     * @return true se todos os objetos especificados forem retidos na base de dados
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        Set<Prateleira> col = getPrateleirasDB();

        return col.retainAll(c);
    }


    /**
     * Método que remove os objetos que estão contidos na collection especificada
     * @param c collection com os objetos a remover
     * @return true se todos os objetos especificados forem removidos da base de dados
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        Set<Prateleira> col = getPrateleirasDB();

        return col.removeAll(c);
    }


    /**
     * Método que apaga todos as prateleiras da base de dados
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE prateleiras");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    /**
     * Método que insere a referência de uma palete numa prateleira na base de dados
     * @param localizacao localizacao da prateleira em questão
     * @param qr_code QR_Code da palete em questão
     * @throws NullPointerException     Em caso de erro
     */
    public void inserePalete(Localizacao_Armazenamento localizacao, String qr_code) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                    "UPDATE prateleiras SET PALETE=('"+qr_code+"') WHERE CORREDOR="+localizacao.getCorredor()+" AND SETOR="+localizacao.getSetor()
            );
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    /**
     * Método que remove a referência da palete numa prateleira na base de dados
     * @param localizacao localizacao da prateleira em questão
     * @throws NullPointerException     Em caso de erro
     */
    public void removePalete(Localizacao_Armazenamento localizacao) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                    "UPDATE prateleiras SET PALETE=NULL WHERE CORREDOR="+localizacao.getCorredor()+" AND SETOR="+localizacao.getSetor()
            );
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    /**
     * Método auxiliar que recolhe todas as prateleiras da base de dados num Set
     * @return Set com as prateleiras todas
     * @throws NullPointerException     Em caso de erro
     */
    private Set<Prateleira> getPrateleirasDB() {
        Set<Prateleira> col = new TreeSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT * FROM prateleiras" )) {
            while (rs.next()) {
                int corredor = rs.getInt("CORREDOR");
                int setor = rs.getInt("SETOR");
                String qr_code = rs.getString("PALETE");

                Prateleira prateleira = new Prateleira(new Localizacao_Armazenamento(corredor, setor), qr_code);
                col.add(prateleira);
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }
}
