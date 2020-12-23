package dss_project_fase3.business;

import dss_project_fase3.utils.Enums.ZonaArmazem;
import dss_project_fase3.business.Localizacao.Localizacao;
import dss_project_fase3.business.Localizacao.Localizacao_Armazenamento;
import dss_project_fase3.business.Localizacao.Localizacao_Transporte;
import dss_project_fase3.business.Robot.Robot;
import org.jgrapht.*;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;

import java.util.List;


public class GrafoArmazem {
    Graph<Vertex, DefaultWeightedEdge> grafo;

    public GrafoArmazem() {
        this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        grafo.addVertex(Vertex.ZONA_RECECAO);
        grafo.addVertex(Vertex.P00);
        grafo.addVertex(Vertex.P01);
        grafo.addVertex(Vertex.P02);
        grafo.addVertex(Vertex.P03);
        grafo.addVertex(Vertex.P04);
        grafo.addVertex(Vertex.P10);
        grafo.addVertex(Vertex.P11);
        grafo.addVertex(Vertex.P12);
        grafo.addVertex(Vertex.P13);
        grafo.addVertex(Vertex.P14);

        grafo.setEdgeWeight(grafo.addEdge(Vertex.ZONA_RECECAO, Vertex.P00),2);
        // Corredor 0
        grafo.setEdgeWeight(grafo.addEdge(Vertex.P00, Vertex.P01), 5);
        grafo.setEdgeWeight(grafo.addEdge(Vertex.P01, Vertex.P02), 5);
        grafo.setEdgeWeight(grafo.addEdge(Vertex.P02, Vertex.P03), 5);
        grafo.setEdgeWeight(grafo.addEdge(Vertex.P03, Vertex.P04), 5);
        // Entre corredores
        grafo.setEdgeWeight(grafo.addEdge(Vertex.P00, Vertex.P10), 5);
        // Corredor 1
        grafo.setEdgeWeight(grafo.addEdge(Vertex.P10, Vertex.P11), 5);
        grafo.setEdgeWeight(grafo.addEdge(Vertex.P11, Vertex.P12), 5);
        grafo.setEdgeWeight(grafo.addEdge(Vertex.P12, Vertex.P13), 5);
        grafo.setEdgeWeight(grafo.addEdge(Vertex.P13, Vertex.P14), 5);
    }

    public int getMelhorRobot(List<Robot> robots, Localizacao destino) {
        Vertex v_destino = Vertex.localizacaoToVertex(destino);
        double menor_custo = Integer.MAX_VALUE;
        int id_robot_escolhido = -1;

        for (Robot r : robots) {
            Vertex v_origem = Vertex.localizacaoToVertex(r.getLocalizacaoAtual());

            DijkstraShortestPath<Vertex,DefaultWeightedEdge> dijkstraAlg = new DijkstraShortestPath<>(this.grafo);
            ShortestPathAlgorithm.SingleSourcePaths<Vertex, DefaultWeightedEdge> iPaths = dijkstraAlg.getPaths(v_origem);
            double custo = iPaths.getWeight(v_destino);

            if (custo < menor_custo) {
                id_robot_escolhido = r.getId_robot();
                menor_custo = custo;
            }
        }

        return id_robot_escolhido;
    }
}

enum Vertex {
    ZONA_RECECAO, P00, P01, P02, P03, P04, P10, P11, P12, P13, P14;

    public static Vertex localizacaoToVertex(Localizacao localizacao) {
        Vertex v = null;

        switch(localizacao.getClass().getSimpleName()) {
            case "Localizacao_Armazenamento":
                Localizacao_Armazenamento la = (Localizacao_Armazenamento) localizacao;
                int corredor = la.getCorredor();
                int setor = la.getSetor();
                if (corredor == 0 && setor == 0) v = P00;
                else if (corredor == 0 && setor == 1) v = P01;
                else if (corredor == 0 && setor == 2) v = P02;
                else if (corredor == 0 && setor == 3) v = P03;
                else if (corredor == 0 && setor == 4) v = P04;
                else if (corredor == 1 && setor == 0) v = P10;
                else if (corredor == 1 && setor == 1) v = P11;
                else if (corredor == 1 && setor == 2) v = P12;
                else if (corredor == 1 && setor == 3) v = P13;
                else if (corredor == 1 && setor == 4) v = P14;
                break;
            case "Localizacao_Transporte":
                Localizacao_Transporte lt = (Localizacao_Transporte) localizacao;
                if (lt.getZona() == ZonaArmazem.ZONA_RECECAO) v = ZONA_RECECAO;
                break;
        }

        return v;
    }
}