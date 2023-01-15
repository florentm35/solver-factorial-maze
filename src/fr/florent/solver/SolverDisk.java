package fr.florent.solver;

import fr.florent.solver.data.Data;
import fr.florent.solver.modele.Path;
import fr.florent.solver.modele.Point;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solver {
    private static Data data;

    public static void main(String[] args) {

        // Calcul tout les chemins de chaque point
        data = Data.getInstance();

        // On intialise le début des chemins
        List<Path> lstPath = new ArrayList<>();
        lstPath.add(Path.of(data.getPoint(Point.of(0))));

        // Variable de monitoring pour les logs
        int profondeur = 1;
        long processBegin = new Date().getTime();
        long iterationBegin = processBegin;

        // Traitement
        Path terminal = null;
        while (true) {
            List<Path> tmp = new ArrayList<>();
            for (Path path : lstPath) {
                if (path.isTerminal()) {
                    // Solution trouvée on sort
                    terminal = path;
                    System.out.println(terminal);
                } else {
                    // On creer tout les sous chemins
                    Point lastPoint = path.getLastPoint().getPoint();
                    if (lastPoint.getLstNext() != null) {
                        for (Point subPoint : lastPoint.getLstNext()) {
                            if (!subPoint.getName().equals("0")) // On ne veux pas retourner à 0
                                tmp.add(path.addPoint(subPoint));
                        }
                    }
                }
            }

            lstPath = tmp;
            // Log pour le monitoring
            long iterationEnd = new Date().getTime();
            System.out.println(String.format("Total time : %s - Iteration time : %s - Profondeur : %d - Nombre path : %d - memory usage : %d Mo",
                    formateTime( iterationEnd - processBegin),
                    formateTime( iterationEnd - iterationBegin),
                    profondeur,
                    lstPath.size(),
                    Runtime.getRuntime().totalMemory() / 1024 / 1024));
            profondeur++;
            iterationBegin = iterationEnd;
        }

        // On affiche la 1ère solution
        //System.out.println(terminal);

    }

    /**
     * Permet la mise en forme du temps pour le monitoring
     */
    private static String formateTime(double value) {
        String unite = "ms";
        if (value > 1000) {
            value /= 1000.0;
            unite = "s";
            if (value > 60) {
                value /= 60.0;
                unite = "m";
                if (value > 60) {
                    value /= 60.0;
                    unite = "h";
                }
            }
        }

        return String.format("%.2f %s", value, unite);
    }
}
