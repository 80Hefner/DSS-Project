package dss_project_fase3.data;

import dss_project_fase3.business.Localizacao.*;
import dss_project_fase3.business.Palete.*;

import java.sql.*;
import java.util.*;

/**
 * Classe PaleteDAO que implementa a interface IPaleteDAO
 */
public class PaleteDAO implements IPaleteDAO {
    private static PaleteDAO singleton = null;

    public PaleteDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS paletes (" +
                    "QR_CODE varchar(100) NOT NULL PRIMARY KEY," +
                    "MATERIAL varchar(45) DEFAULT NULL," +
                    "LOCALIZACAO varchar (20) DEFAULT NULL," +
                    "CORREDOR int DEFAULT NULL," +
                    "SETOR int DEFAULT NULL," +
                    "ID_ROBOT int DEFAULT NULL)";
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
    public static PaleteDAO getInstance() {
        if (PaleteDAO.singleton == null) {
            PaleteDAO.singleton = new PaleteDAO();
        }
        return PaleteDAO.singleton;
    }


    /**
     * @return número de paletes na base de dados
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM paletes")) {
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
     * Método que verifica se existem paletes
     * @return true se existirem 0 paletes
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     * Método que verifica se um qr_code de uma palete existe na base de dados
     * @param key qr_code da Palete
     * @return true se a Palete existe
     * @throws NullPointerException Em caso de erro
     */
    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT QR_CODE FROM paletes WHERE QR_CODE='"+key.toString()+"'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }


    /**
     * Verifica se uma Palete existe na base de dados
     * @param value     Palete que queremos procurar na Base de Dados
     * @return          true se a Base de Dados possui a Palete
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public boolean containsValue(Object value) {
        Palete a = (Palete) value;
        return this.containsKey(a.getQr_code());
    }


    /**
     * Obter uma Palete, dado o seu qr_code
     *
     * @param key   qr_code da Palete
     * @return      Palete caso exista (null noutro caso)
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public Palete get(Object key) {
        Palete a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM paletes WHERE QR_CODE='"+key+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                a = new Palete(rs.getString("QR_CODE"), rs.getString("MATERIAL"), rs.getString("LOCALIZACAO"), rs.getInt("CORREDOR"), rs.getInt("SETOR"), rs.getInt("ID_ROBOT"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    /**
     * Insere uma Palete na base de dados
     * @param key       qr_code da Palete
     * @param value     Palete
     * @return          Palete existente anteriormente na base de dados (null caso não existisse)
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public Palete put(String key, Palete value) {
        Palete res;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            res = this.get(key);

            switch (value.getLocalizacao().getClass().getSimpleName()) {
                case "Localizacao_Armazenamento":
                    Localizacao_Armazenamento armazenamento = (Localizacao_Armazenamento) value.getLocalizacao();
                    stm.executeUpdate(
                            "INSERT INTO paletes VALUES ('"+value.getQr_code().getCodigo()+"', '"+value.getMaterial().getDesignacao()+"', '"+value.getLocalizacao().getZona().toString()+"',  " +armazenamento.getCorredor()+", " +armazenamento.getSetor()+", NULL) " +
                                    "ON DUPLICATE KEY UPDATE MATERIAL=VALUES(MATERIAL), LOCALIZACAO=('"+armazenamento.getZona().toString()+"'), CORREDOR=("+armazenamento.getCorredor()+"), SETOR=("+armazenamento.getSetor()+"), ID_ROBOT=NULL");
                    break;

                case "Localizacao_Robot":
                    Localizacao_Robot robot = (Localizacao_Robot) value.getLocalizacao();
                    stm.executeUpdate(
                            "INSERT INTO paletes VALUES ('"+value.getQr_code().getCodigo()+"', '"+value.getMaterial().getDesignacao()+"', '"+value.getLocalizacao().getZona().toString()+"', NULL, NULL,"+robot.getId_robot()+") " +
                                    "ON DUPLICATE KEY UPDATE MATERIAL=VALUES(MATERIAL), LOCALIZACAO=('"+robot.getZona().toString()+"'), CORREDOR=NULL, SETOR=NULL, ID_ROBOT=("+robot.getId_robot()+")");
                    break;

                case "Localizacao_Transporte":
                    Localizacao_Transporte transporte = (Localizacao_Transporte) value.getLocalizacao();
                    stm.executeUpdate(
                            "INSERT INTO paletes VALUES ('"+value.getQr_code().getCodigo()+"', '"+value.getMaterial().getDesignacao()+"', '"+value.getLocalizacao().getZona().toString()+"', NULL, NULL, NULL) " +
                                    "ON DUPLICATE KEY UPDATE MATERIAL=VALUES(MATERIAL), LOCALIZACAO=('"+transporte.getZona().toString()+"'), CORREDOR=NULL, SETOR=NULL, ID_ROBOT=NULL");
                    break;

            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }


    /**
     * Remover uma Palete, dado o seu qr_code
     * @param key   qr_code da Palete a remover
     * @return      Palete removida
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public Palete remove(Object key) {
        Palete t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM paletes WHERE QR_CODE='"+key.toString()+"'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }


    /**
     * Adicionar um conjunto de Paletes à base de dados
     * @param paletesNovas      as Paletes a adicionar
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public void putAll(Map<? extends String, ? extends Palete> paletesNovas) {
        for (Palete a : paletesNovas.values()) {
            this.put(a.getQr_code().getCodigo(), a);
        }
    }


    /**
     * Apagar todos as Paletes
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE paletes");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    /**
     * Função que dá um set com todas as keys
     * @return  Set com todos os QR_Code das paletes da base de dados
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public Set<String> keySet() {
        Set<String> set = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT QR_CODE FROM paletes")) {
            while (rs.next()) {   // Utilizamos o get para construir as paletes
                String stringQR = rs.getString("QR_CODE");
                set.add(stringQR);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return set;
    }

    /**
     * Função que dá um set com todas os values (Paletes)
     * @return  Collection com todas as paletes da base de dados
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public Collection<Palete> values() {
        Collection<Palete> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT QR_CODE FROM paletes")) {
            while (rs.next()) {   // Utilizamos o get para construir as paletes
                String stringQR = rs.getString("QR_CODE");
                col.add(this.get(stringQR));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }


    /**
     * Função que dá um set com todos os entrySets da base de Dados
     * @return      Set com todos os entrySets da base de Dados
     * @throws NullPointerException     Em caso de erro
     */
    @Override
    public Set<Entry<String, Palete>> entrySet() {
        Set<Entry<String, Palete>> set = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT QR_CODE FROM paletes")) {
            while (rs.next()) {   // Utilizamos o get para construir as paletes
                String stringQR = rs.getString("QR_CODE");
                Palete novaPalete = this.get(stringQR);
                Entry<String,Palete> entrada = new AbstractMap.SimpleEntry<>(stringQR,novaPalete);
                set.add(entrada);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return set;
    }


    /**
     * Função que atualiza localizacao de uma dada palete, dado um qr_code
     * @param localizacao       localizacao da Palete
     * @param qr_code           qr_code da Palete
     * @throws NullPointerException     Em caso de erro
     */
    public void atualizaLocalizacao(Localizacao localizacao, String qr_code) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            switch (localizacao.getClass().getSimpleName()) {
                case "Localizacao_Armazenamento":
                    Localizacao_Armazenamento armazenamento = (Localizacao_Armazenamento) localizacao;
                    stm.executeUpdate(
                            "UPDATE paletes SET LOCALIZACAO=('"+armazenamento.getZona().toString()+"'), CORREDOR=("+armazenamento.getCorredor()+"), SETOR=("+armazenamento.getSetor()+"), ID_ROBOT=NULL WHERE QR_CODE = '"+qr_code+"'");
                    break;

                case "Localizacao_Robot":
                    Localizacao_Robot robot = (Localizacao_Robot) localizacao;
                    stm.executeUpdate(
                            "UPDATE paletes SET LOCALIZACAO=('"+robot.getZona().toString()+"'), CORREDOR=NULL, SETOR=NULL, ID_ROBOT=("+robot.getId_robot()+") WHERE QR_CODE = '"+qr_code+"'");
                    break;

                case "Localizacao_Transporte":
                    Localizacao_Transporte transporte = (Localizacao_Transporte) localizacao;
                    stm.executeUpdate(
                            "UPDATE paletes SET LOCALIZACAO=('"+transporte.getZona().toString()+"'), CORREDOR=NULL, SETOR=NULL, ID_ROBOT=NULL WHERE QR_CODE = '"+qr_code+"'");
                    break;

            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }
}
