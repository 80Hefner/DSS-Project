package dss_project_fase3.data;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Robot.Entrega;
import dss_project_fase3.business.Robot.Robot;

import java.util.Map;

/**
 * Interface IRobotDAO que dá exntend ao Map<Integer, Robot>
 */
public interface IRobotDAO extends Map<Integer, Robot> {

    /**
     * Função que altera parametros necessarios de um Robot quando este realiza uma Entrega
     * @param id_robot      id_Robot que realiza a Entrega
     * @throws NullPointerException     Em caso de erro
     */
    void entregaRealizada(Integer id_robot);

    /**
     * Função que altera parametros necessarios de um Robot quando este aceita realizar uma Entrega
     * @param id_robot          id_Robot que realiza a Entrega
     * @param entrega           Entrega a ser realizada
     * @throws NullPointerException     Em caso de erro
     */
    void recolhePalete (Integer id_robot, Entrega entrega);

    /**
     * Função que altera parametros necessarios de um Robot quando este recolhe uma Entrega
     * @param id_robot              id_Robot respetivo à Entrega
     * @param localizacaoNova       novaLocalizacaoAtual do Robot
     * @throws NullPointerException     Em caso de erro
     */
    void encontraChegada (Integer id_robot, Localizacao localizacaoNova);
}
