package dss_project_fase3.data;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Robot.Entrega;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;
import dss_project_fase3.business.Robot.Robot;

import java.sql.*;
import java.util.*;

public class RobotDAO implements IRobotDAO{

    private static RobotDAO singleton = null;

    public RobotDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS robots (" +
                    "ID_ROBOT int NOT NULL PRIMARY KEY," +
                    "DISPONIVEL BOOLEAN NOT NULL," +
                    "QR_CODE varchar(100) DEFAULT NULL," +
                    "LOCALIZACAO_ATUAL varchar (20) DEFAULT NULL," +
                    "CORREDOR_ATUAL int DEFAULT NULL," +
                    "SETOR_ATUAL int DEFAULT NULL," +
                    "LOCALIZACAO_ORIGEM varchar (20) DEFAULT NULL," +
                    "CORREDOR_ORIGEM int DEFAULT NULL," +
                    "SETOR_ORIGEM int DEFAULT NULL," +
                    "LOCALIZACAO_DESTINO varchar (20) DEFAULT NULL," +
                    "CORREDOR_DESTINO int DEFAULT NULL," +
                    "SETOR_DESTINO int DEFAULT NULL)";  // Assume-se uma relação 1-n entre Turma e Aluno
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
    public static RobotDAO getInstance() {
        if (RobotDAO.singleton == null) {
            RobotDAO.singleton = new RobotDAO();
        }
        return RobotDAO.singleton;
    }


    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT count(*) FROM robots")) {
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
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs =
                     stm.executeQuery("SELECT ID_ROBOT FROM robots WHERE ID_ROBOT="+key)) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Robot a = (Robot) value;
        return this.containsKey(a.getId_robot());
    }

    @Override
    public Robot get(Object key) {
        Robot a = null;
        Integer id_robot = (Integer) key;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT * FROM robots WHERE ID_ROBOT="+id_robot)) {
            if (rs.next()) {  // A chave existe na tabela
                Integer inteiroID = rs.getInt("ID_ROBOT");
                Boolean disponivel = rs.getBoolean("DISPONIVEL");
                String qr_code = rs.getString("QR_CODE");
                String atual = rs.getString("LOCALIZACAO_ATUAL");
                int corredor_atual = rs.getInt("CORREDOR_ATUAL");
                int setor_atual = rs.getInt("SETOR_ATUAL");
                String origem = rs.getString("LOCALIZACAO_ORIGEM");
                int corredor_origem = rs.getInt("CORREDOR_ORIGEM");
                int setor_origem = rs.getInt("SETOR_ORIGEM");
                String destino = rs.getString("LOCALIZACAO_DESTINO");
                int corredor_destino = rs.getInt("CORREDOR_DESTINO");
                int setor_destino = rs.getInt("SETOR_DESTINO");

                if (disponivel == true) a = new Robot(id_robot);
                else a = new Robot(rs.getInt("ID_ROBOT"), rs.getBoolean("DISPONIVEL"), rs.getString("QR_CODE"), rs.getString("LOCALIZACAO_ATUAL"), rs.getInt("CORREDOR_ATUAL"), rs.getInt("SETOR_ATUAL"), rs.getString("LOCALIZACAO_ORIGEM"), rs.getInt("CORREDOR_ORIGEM"), rs.getInt("SETOR_ORIGEM"), rs.getString("LOCALIZACAO_DESTINO"), rs.getInt("CORREDOR_DESTINO"), rs.getInt("SETOR_DESTINO"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return a;
    }

    @Override
    public Robot put(Integer key, Robot value) {
        Robot res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // É partido do pressuposto que o robot só transporta (ZONA_RECECAO -> ZONA_ARMAZENAMENTO e ZONA_ARMAZENAMENTO -> ZONA_ENTREGA)
            // É ainda de notar que LocalizaçãoAtual vai ser igual a LocalizaçãoOrigem
            switch (value.getEntregaAtual().getOrigem().getClass().getSimpleName()) {

                case "Localizacao_Transporte":
                    Localizacao_Transporte transporteOrigem = (Localizacao_Transporte) value.getEntregaAtual().getOrigem();
                    Localizacao_Armazenamento armazenamentoDestino = (Localizacao_Armazenamento) value.getEntregaAtual().getDestino();
                    stm.executeUpdate(
                            "INSERT INTO robots VALUES ("+value.getId_robot()+", "+value.isDisponivel()+", '"+value.getEntregaAtual().getQr_code()+"', '"+transporteOrigem.getZona().toString()+"',  NULL, NULL, '"+transporteOrigem.getZona().toString()+"',  NULL, NULL, '"+armazenamentoDestino.getZona().toString()+"', "+armazenamentoDestino.getCorredor()+", " +armazenamentoDestino.getSetor()+ ") " +
                                    "ON DUPLICATE KEY UPDATE DISPONIVEL="+value.isDisponivel()+", QR_CODE='"+value.getEntregaAtual().getQr_code()+"', LOCALIZACAO_ATUAL='"+transporteOrigem.getZona().toString()+"', CORREDOR_ATUAL=NULL, SETOR_ATUAL=NULL, LOCALIZACAO_ORIGEM='"+transporteOrigem.getZona().toString()+"', CORREDOR_ORIGEM=NULL, SETOR_ORIGEM=NULL, LOCALIZACAO_DESTINO='"+armazenamentoDestino.getZona().toString()+"', CORREDOR_DESTINO="+armazenamentoDestino.getCorredor()+", SETOR_DESTINO="+armazenamentoDestino.getSetor() );
                    break;

                case "Localizacao_Armazenamento":
                    Localizacao_Armazenamento armazenamentoOrigem = (Localizacao_Armazenamento) value.getEntregaAtual().getOrigem();
                    Localizacao_Transporte transporteDestino = (Localizacao_Transporte) value.getEntregaAtual().getDestino();
                    stm.executeUpdate(
                            "INSERT INTO robots VALUES ("+value.getId_robot()+", "+value.isDisponivel()+", '"+value.getEntregaAtual().getQr_code()+"', '"+armazenamentoOrigem.getZona().toString()+"', "+armazenamentoOrigem.getCorredor()+", " +armazenamentoOrigem.getSetor()+", '"+armazenamentoOrigem.getZona().toString()+"', "+armazenamentoOrigem.getCorredor()+", " +armazenamentoOrigem.getSetor()+", '"+transporteDestino.getZona().toString()+"',  NULL, NULL) " +
                                    "ON DUPLICATE KEY UPDATE DISPONIVEL="+value.isDisponivel()+", QR_CODE='"+value.getEntregaAtual().getQr_code()+"', LOCALIZACAO_ORIGEM='"+armazenamentoOrigem.getZona().toString()+"', CORREDOR_ORIGEM="+armazenamentoOrigem.getCorredor()+", SETOR_ORIGEM="+armazenamentoOrigem.getSetor()+", LOCALIZACAO_ORIGEM='"+armazenamentoOrigem.getZona().toString()+"', CORREDOR_ORIGEM="+armazenamentoOrigem.getCorredor()+", SETOR_ORIGEM="+armazenamentoOrigem.getSetor()+", LOCALIZACAO_DESTINO='"+transporteDestino.getZona().toString()+"', CORREDOR_DESTINO=NULL, SETOR_DESTINO=NULL" );
                    break;

            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Robot remove(Object key) {
        Robot t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("DELETE FROM robots WHERE ID_ROBOT="+key);
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Robot> robotsNovos) {
        for (Robot a : robotsNovos.values()) {
            this.put(a.getId_robot(), a);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE robots");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> set = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT ID_ROBOT FROM robots")) {
            while (rs.next()) {   // Utilizamos o get para construir as paletes
                set.add( rs.getInt("ID_ROBOT") );
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return set;
    }

    @Override
    public Collection<Robot> values() {
        Collection<Robot> col = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT ID_ROBOT FROM robots")) {
            while (rs.next()) {   // Utilizamos o get para construir as paletes
                Integer inteiroID = rs.getInt("ID_ROBOT");
                col.add(this.get(inteiroID));
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return col;
    }

    @Override
    public Set<Entry<Integer, Robot>> entrySet() {
        Set<Entry<Integer, Robot>> set = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement();
             ResultSet rs = stm.executeQuery("SELECT ID_ROBOT FROM robots")) {
            while (rs.next()) {   // Utilizamos o get para construir as paletes
                Integer inteiroID = rs.getInt("ID_ROBOT");
                Robot novoRobot = this.get(inteiroID);
                Entry<Integer,Robot> entrada = new AbstractMap.SimpleEntry<>(inteiroID,novoRobot);
                set.add(entrada);
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return set;
    }


    @Override
    public void entregaRealizada(Integer id_robot) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            Localizacao loc = this.get(id_robot).getEntregaAtual().getDestino();

            if (loc.getClass().getSimpleName().equals("Localizacao_Transporte")) {
                stm.executeUpdate(
                        "UPDATE robots SET DISPONIVEL=TRUE, QR_CODE=NULL, LOCALIZACAO_ATUAL='"+loc.getZona().toString()+"', CORREDOR_ATUAL=NULL, SETOR_ATUAL=NULL, LOCALIZACAO_ORIGEM=NULL, CORREDOR_ORIGEM=NULL, SETOR_ORIGEM=NULL, LOCALIZACAO_DESTINO=NULL, CORREDOR_DESTINO=NULL, SETOR_DESTINO=NULL WHERE ID_ROBOT="+id_robot);
            }
            else if (loc.getClass().getSimpleName().equals("Localizacao_Armazenamento")) {
                Localizacao_Armazenamento loca = (Localizacao_Armazenamento) loc;
                stm.executeUpdate(
                        "UPDATE robots SET DISPONIVEL=TRUE, QR_CODE=NULL, LOCALIZACAO_ATUAL='"+loc.getZona().toString()+"', CORREDOR_ATUAL="+loca.getCorredor()+", SETOR_ATUAL="+loca.getSetor()+", LOCALIZACAO_ORIGEM=NULL, CORREDOR_ORIGEM=NULL, SETOR_ORIGEM=NULL, LOCALIZACAO_DESTINO=NULL, CORREDOR_DESTINO=NULL, SETOR_DESTINO=NULL WHERE ID_ROBOT="+id_robot);
            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public void recolhePalete(Integer id_robot, Entrega entrega) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

            // É partido do pressuposto que o robot só transporta (ZONA_RECECAO -> ZONA_ARMAZENAMENTO e ZONA_ARMAZENAMENTO -> ZONA_ENTREGA)
            switch (entrega.getOrigem().getClass().getSimpleName()) {

                case "Localizacao_Transporte":
                    Localizacao_Transporte transporteOrigem = (Localizacao_Transporte) entrega.getOrigem();
                    Localizacao_Armazenamento armazenamentoDestino = (Localizacao_Armazenamento) entrega.getDestino();
                    stm.executeUpdate(
                            "UPDATE robots SET DISPONIVEL=FALSE, QR_CODE='"+entrega.getQr_code()+"', LOCALIZACAO_ORIGEM='"+transporteOrigem.getZona().toString()+"', CORREDOR_ORIGEM=NULL, SETOR_ORIGEM=NULL, LOCALIZACAO_DESTINO='"+armazenamentoDestino.getZona().toString()+"', CORREDOR_DESTINO="+armazenamentoDestino.getCorredor()+", SETOR_DESTINO="+armazenamentoDestino.getSetor()+" WHERE ID_ROBOT="+id_robot);
                    break;

                case "Localizacao_Armazenamento":
                    Localizacao_Armazenamento armazenamentoOrigem = (Localizacao_Armazenamento) entrega.getOrigem();
                    Localizacao_Transporte transporteDestino = (Localizacao_Transporte) entrega.getDestino();
                    stm.executeUpdate(
                            "UPDATE robots SET DISPONIVEL=FALSE, QR_CODE='"+entrega.getQr_code()+"', LOCALIZACAO_ORIGEM='"+armazenamentoOrigem.getZona().toString()+"', CORREDOR_ORIGEM="+armazenamentoOrigem.getCorredor()+", SETOR_ORIGEM="+armazenamentoOrigem.getSetor()+", LOCALIZACAO_DESTINO='"+transporteDestino.getZona().toString()+"', CORREDOR_DESTINO=NULL, SETOR_DESTINO=NULL WHERE ID_ROBOT="+id_robot);
                    break;

            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public void encontraChegada (Integer id_robot, Localizacao localizacaoNova) {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
             Statement stm = conn.createStatement()) {

             switch (localizacaoNova.getClass().getSimpleName()) {

                case "Localizacao_Transporte":
                    Localizacao_Transporte localizacaoTrans = (Localizacao_Transporte) localizacaoNova;
                    stm.executeUpdate(
                            "UPDATE robots SET LOCALIZACAO_ATUAL='"+localizacaoTrans.getZona().toString()+"', CORREDOR_ATUAL=NULL, SETOR_ATUAL=NULL WHERE ID_ROBOT="+id_robot);
                    break;

                case "Localizacao_Armazenamento":
                    Localizacao_Armazenamento localizacaoArm = (Localizacao_Armazenamento) localizacaoNova;
                    stm.executeUpdate(
                            "UPDATE robots SET LOCALIZACAO_ATUAL='"+localizacaoArm.getZona().toString()+"', CORREDOR_ATUAL="+localizacaoArm.getCorredor()+", SETOR_ATUAL="+localizacaoArm.getSetor()+" WHERE ID_ROBOT="+id_robot);
                    break;

            }

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }
}