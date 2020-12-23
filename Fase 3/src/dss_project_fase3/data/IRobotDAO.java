package dss_project_fase3.data;

import dss_project_fase3.business.Entrega;
import dss_project_fase3.business.Robot;

import java.util.Map;

public interface IRobotDAO extends Map<Integer, Robot> {
    void entregaRealizada(Integer id_robot);
    void recolhePalete (Integer id_robot, Entrega entrega);
}
