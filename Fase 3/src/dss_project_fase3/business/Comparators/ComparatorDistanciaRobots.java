package dss_project_fase3.business.Comparators;

import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Robot.Robot;

import java.util.Comparator;
/**
 * Classe com Comparator de Distancia de Robots em relação á zona receção
 */
public class ComparatorDistanciaRobots implements Comparator<Robot> {

    /**
     * @brief              Função que compara dois Robots e as suas distancias à Zona Receção
     * @param r1           Robot
     * @param r2           Robot
     * @return             Inteiro que vai servir de comparação, como nas funções gerais de compare
     */
    @Override
    public int compare(Robot r1, Robot r2) {

        Localizacao localizacaoRobot1 = r1.getLocalizacaoAtual();
        Localizacao localizacaoRobot2 = r2.getLocalizacaoAtual();

        int distanciaRobot1 = 0;
        int distanciaRobot2 = 0;

        if (localizacaoRobot1.getClass().getSimpleName().equals("Localizacao_Armazenamento")) {
            Localizacao_Armazenamento locArmRobot1 = (Localizacao_Armazenamento) localizacaoRobot1;
            distanciaRobot1 = 2 + (locArmRobot1.getCorredor()*5) + (locArmRobot1.getSetor()*5);
        } else if (localizacaoRobot1.getClass().getSimpleName().equals("Localizacao_Transporte")) {
            distanciaRobot1 = 0;
        }

        if (localizacaoRobot2.getClass().getSimpleName().equals("Localizacao_Armazenamento")) {
            Localizacao_Armazenamento locArmRobot2 = (Localizacao_Armazenamento) localizacaoRobot2;
            distanciaRobot2 = 2 + (locArmRobot2.getCorredor()*5) + (locArmRobot2.getSetor()*5);
        } else if (localizacaoRobot2.getClass().getSimpleName().equals("Localizacao_Transporte")) {
            distanciaRobot2 = 0;
        }

        return (distanciaRobot1-distanciaRobot2);
    }
}