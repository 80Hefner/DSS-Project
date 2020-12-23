package dss_project_fase3.data;


import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Prateleira;

import java.sql.*;
import java.util.*;

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


    public static PrateleiraDAO getInstance() {
        if (PrateleiraDAO.singleton == null) {
            PrateleiraDAO.singleton = new PrateleiraDAO();
        }
        return PrateleiraDAO.singleton;
    }


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


    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }


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

    @Override
    public Iterator<Prateleira> iterator() {
        Set<Prateleira> col = getPrateleirasDB();

        return col.iterator();
    }

    @Override
    public Object[] toArray() {
        Set<Prateleira> col = getPrateleirasDB();

        return col.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Set<Prateleira> col = getPrateleirasDB();

        return (T[]) col.toArray(a);
    }

    @Override
    public boolean add(Prateleira prateleira) {  // TODO: return value sempre a true
        boolean res = true;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            stm.executeUpdate(
                        "INSERT INTO prateleiras VALUES ("+prateleira.getLocalizacao().getCorredor()+", "+prateleira.getLocalizacao().getSetor()+", '"+prateleira.getQr_code()+"') " +
                                "ON DUPLICATE KEY UPDATE CORREDOR=VALUES(CORREDOR), SETOR=VALUES(SETOR), PALETE=VALUES(PALETE)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public boolean remove(Object o) {  // TODO: return value sempre a true
        boolean r = true;
        Prateleira prat = (Prateleira) o;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM paletes WHERE CORREDOR="+prat.getLocalizacao().getCorredor()+" AND SETOR="+prat.getLocalizacao().getSetor()
                    + " AND PALETE='"+prat.getQr_code()+"'" );
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Set<Prateleira> col = getPrateleirasDB();

        return col.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Prateleira> c) {
        Set<Prateleira> col = getPrateleirasDB();

        return col.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<Prateleira> col = getPrateleirasDB();

        return col.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Set<Prateleira> col = getPrateleirasDB();

        return col.removeAll(c);
    }

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
