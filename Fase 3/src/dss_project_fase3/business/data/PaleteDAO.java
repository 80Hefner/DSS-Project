package dss_project_fase3.business.data;

import dss_project_fase3.business.Localizacao.*;
import dss_project_fase3.business.Palete.*;

import java.sql.*;
import java.util.*;

public class PaleteDAO implements Map<QR_Code, Palete> {
    private static PaleteDAO singleton = null;

    public PaleteDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS paletes (" +
                    "QR_CODE varchar(100) NOT NULL PRIMARY KEY," +
                    "NOME varchar(45) DEFAULT NULL," +
                    "LOCALIZACAO varchar (20) DEFAULT NULL," +
                    "CORREDOR int DEFAULT NULL," +
                    "SETOR int DEFAULT NULL," +
                    "ID_ROBOT int DEFAULT NULL)";  // Assume-se uma relação 1-n entre Turma e Aluno
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }


    /**
     * Implementação do padrão Singleton
     *
     * @return devolve a instância única desta classe
     */
    public static PaleteDAO getInstance() {
        if (PaleteDAO.singleton == null) {
            PaleteDAO.singleton = new PaleteDAO();
        }
        return PaleteDAO.singleton;
    }


    /**
     * @return número de alunos na base de dados
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
     * Método que verifica se existem alunos
     *
     * @return true se existirem 0 alunos
     */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     * Método que cerifica se um id de turma existe na base de dados
     *
     * @param key id da turma
     * @return true se a turma existe
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
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
     *
     * Esta implementação é provisória. Devia testar o objecto completo e não apenas a chave.
     *
     * @param value ...
     * @return ...
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public boolean containsValue(Object value) {
        Palete a = (Palete) value;
        return this.containsKey(a.getQr_code());
    }


    /**
     * Obter um aluno, dado o seu número
     *
     * @param key número do aluno
     * @return o aluno caso exista (null noutro caso)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Palete get(Object key) {
        Palete a = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM paletes WHERE QR_CODE='"+key.toString()+"'")) {
            if (rs.next()) {  // A chave existe na tabela
                // Reconstruir o aluno com os dados obtidos da BD - a chave estranjeira da turma, não é utilizada aqui.
                a = new Palete(rs.getString("RQ_CODE"), rs.getString("NOME"), rs.getString("LOCALIZACAO"), rs.getInt("CORREDOR"), rs.getInt("SETOR"), rs.getInt("ID_ROBOT"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }


    /**
     * Insere uma turma na base de dados
     *
     * ATENÇÂO: Falta devolver o valor existente (caso exista um)
     *
     * @param key o id da turma
     * @param value a turma
     * @return para já retorna sempre null (deverá devolver o valor existente, caso exista um)
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public Palete put(QR_Code key, Palete value) {
        Palete res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // Actualizar o aluno

            switch (value.getLocalizacao().getClass().getName()) {
                case "Localizacao_Armazenamento":
                    Localizacao_Armazenamento armazenamento = (Localizacao_Armazenamento) value.getLocalizacao();
                    stm.executeUpdate(
                            "INSERT INTO paletes VALUES ('"+value.getQr_code().getCodigo()+"', '"+value.getMaterial().getDesignacao()+"', '"+value.getLocalizacao().getZona().toString()+"',  " +armazenamento.getCorredor()+", " +armazenamento.getSetor()+", NULL) " +
                                    "ON DUPLICATE KEY UPDATE NOME=VALUES(NOME), LOCALIZACAO=VALUES(LOCALIZACAO), CORREDOR=VALUES(armazenamento.getCorredor()), SETOR=VALUES(armazenamento.getSetor()), ID_ROBOT=NULL");
                    break;

                case "Localizacao_Robot":
                    Localizacao_Robot robot = (Localizacao_Robot) value.getLocalizacao();
                    stm.executeUpdate(
                            "INSERT INTO paletes VALUES ('"+value.getQr_code().getCodigo()+"', '"+value.getMaterial().getDesignacao()+"', '"+value.getLocalizacao().getZona().toString()+"', NULL, NULL,"+robot.getId_robot()+") " +
                                    "ON DUPLICATE KEY UPDATE NOME=VALUES(NOME), LOCALIZACAO=VALUES(LOCALIZACAO), CORREDOR=NULL, SETOR=NULL, ID_ROBOT=VALUES(robot.getId_robot())");
                    break;

                case "Localizacao_Transporte":
                    stm.executeUpdate(
                            "INSERT INTO paletes VALUES ('"+value.getQr_code().getCodigo()+"', '"+value.getMaterial().getDesignacao()+"', '"+value.getLocalizacao().getZona().toString()+"', NULL, NULL, NULL) " +
                                    "ON DUPLICATE KEY UPDATE NOME=VALUES(NOME), LOCALIZACAO=VALUES(LOCALIZACAO), CORREDOR=NULL, SETOR=NULL, ID_ROBOT=NULL");
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
     * Remover uma turma, dado o seu id
     *
     * NOTA: Não estamos a apagar a sala...
     *
     * @param key id da turma a remover
     * @return a turma removida
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
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
     * Adicionar um conjunto de alunos à base de dados
     *
     * @param paletesNovas as alunos a adicionar
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
     */
    @Override
    public void putAll(Map<? extends QR_Code, ? extends Palete> paletesNovas) {
        for (Palete a : paletesNovas.values()) {
            this.put(a.getQr_code(), a);
        }
    }


    /**
     * Apagar todos os alunos
     *
     * @throws NullPointerException ...
     * @throws NullPointerException Em caso de erro - deveriam ser criadas exepções do projecto
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
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    @Override
    public Set<QR_Code> keySet() {
        throw new NullPointerException("Not implemented!");
    }

    /**
     * @return Todos as paletes da base de dados
     */
    @Override
    public Collection<Palete> values() {
        Collection<Palete> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT QR_CODE FROM paletes")) {
            while (rs.next()) {   // Utilizamos o get para construir as paletes
                String stringQR = rs.getString("QR_CODE");
                col.add(this.get( new QR_Code(stringQR) ));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }


    /**
     * NÃO IMPLEMENTADO!
     * @return ainda nada!
     */
    @Override
    public Set<Entry<QR_Code, Palete>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Aluno>> entrySet() not implemented!");
    }
}
