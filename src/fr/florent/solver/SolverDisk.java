package fr.florent.solver;

import fr.florent.solver.data.Data;
import fr.florent.solver.modele.Path;
import fr.florent.solver.modele.Point;
import fr.florent.solver.modele.disk.PathFileWriter;
import fr.florent.solver.modele.disk.PathFileReader;

import java.util.Date;

public class SolverDisk {
    private static Data data;

    public static void main(String[] args) {

        // Calcul tout les chemins de chaque point
        data = Data.getInstance();

        // On intialise le début des chemins

        PathFileWriter pathFileWriter = new PathFileWriter();
        pathFileWriter.addPath(Path.of(data.getPoint(Point.of(0))));
        pathFileWriter.writePath();


        // Variable de monitoring pour les logs
        int profondeur = 1;
        long processBegin = new Date().getTime();
        long iterationBegin = processBegin;
        long fileSize;
        long freeDisk;

        // Traitement
        Path terminal = null;
        while (true) {
            try (PathFileReader pfr = new PathFileReader(pathFileWriter)) {
                fileSize = pfr.getFileSize();
                freeDisk = pfr.getFreeDisk();

                pathFileWriter = new PathFileWriter();
                for (Path path : pfr) {
                    if (path.isTerminal()) {
                        // Solution trouvée on sort
                        terminal = path;
                        System.out.println(terminal);
                    } else {
                        // On creer tout les sous chemins
                        Point lastPoint = path.getLastPoint().getPoint();
                        if (lastPoint.getLstNext() != null) {
                            for (Point subPoint : lastPoint.getLstNext()) {
                                if (!subPoint.getName().equals("0")) { // On ne veux pas retourner à 0
                                    Path pathTmp = path.addPoint(subPoint);
                                    pathFileWriter.addPath(pathTmp);
                                }
                            }
                        }
                    }
                }
                pathFileWriter.writePath();
            }
            // Log pour le monitoring
            long iterationEnd = new Date().getTime();
            System.out.println(String.format("Total time : %s - Iteration time : %s - Profondeur : %d - Nombre path : %d - memory usage : %d Mo - disk usage : %d Mo - free disk : %d Mo",
                    formateTime(iterationEnd - processBegin),
                    formateTime(iterationEnd - iterationBegin),
                    profondeur,
                    pathFileWriter.getLineCount(),
                    Runtime.getRuntime().totalMemory() / 1024 / 1024,
                    fileSize / 1024 / 1024,
                    freeDisk / 1024 / 1024));
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

