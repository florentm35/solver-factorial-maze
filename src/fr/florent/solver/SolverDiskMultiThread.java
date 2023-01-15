package fr.florent.solver;

import fr.florent.solver.data.Data;
import fr.florent.solver.modele.Path;
import fr.florent.solver.modele.Point;
import fr.florent.solver.modele.disk.PathFileReader;
import fr.florent.solver.modele.disk.PathFileWriter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SolverDiskMultiThread {

    private static final int TIME_CHECK = 100;

    private static final int THREAD_POOL_SIZE = 5;

    public static final int FILE_SIZE_MAX = 1000000;

    private static Data data;

    public static void main(String[] args) {

        // Calcul tout les chemins de chaque point
        data = Data.getInstance();

        // On intialise le début des chemins
        PathFileWriter pathFileWriter = new PathFileWriter();
        pathFileWriter.addPath(Path.of(data.getPoint(Point.of(0))));
        pathFileWriter.writePath();

        // Traitement
        Queue<PathFileWriter> writerQueue = new LinkedBlockingQueue<>();
        writerQueue.add(pathFileWriter);

        AtomicInteger profondeur = new AtomicInteger();
        long processBegin = new Date().getTime();
        AtomicLong iterationBegin = new AtomicLong(processBegin);
        ProcessRunner.IEndProcess endPorcessListener = (processId, writer) -> {
            // System.out.println("Ajout d'un fichier pour le process : " + processId);
            if(writer.getProfondeur() > profondeur.get()) {
                profondeur.set(writer.getProfondeur());
                long iterationEnd = new Date().getTime();
                System.out.println(String.format("Total time : %s - Iteration time : %s - Profondeur : %d - memory usage : %d Mo - free disk : %d Mo",
                        formateTime(iterationEnd - processBegin),
                        formateTime(iterationEnd - iterationBegin.get()),
                        profondeur.get(),
                        Runtime.getRuntime().totalMemory() / 1024 / 1024,
                        writer.getFile().getFreeSpace() / 1024 / 1024));
                iterationBegin.set(iterationEnd);
            }

            writerQueue.add(writer);
        };

        // Initialisation des process
        List<ProcessRunner> lstRunner = new ArrayList<>();
        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            ProcessRunner runner = new ProcessRunner(i, endPorcessListener);
            runner.start();
            lstRunner.add(runner);
        }

        while (true) {
            PathFileWriter writer = writerQueue.poll();
            ProcessRunner runner = null;
            while (runner == null) {
                for (ProcessRunner tmp : lstRunner) {
                    if (tmp.isFree()) {
                        runner = tmp;
                        break;
                    }
                }
                try {
                    Thread.sleep(TIME_CHECK);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            runner.setPathFileWriter(writer);

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

    private static class ProcessRunner extends Thread {

        private final int processId;

        private final IEndProcess endProcess;

        private PathFileWriter pathFileWriter;

        public ProcessRunner(int processId, IEndProcess endProcess) {
            this.processId = processId;
            this.endProcess = endProcess;
        }

        private interface IEndProcess {
            void generated(int processId, PathFileWriter writer);
        }

        public void setPathFileWriter(PathFileWriter pathFileWriter) {
            this.pathFileWriter = pathFileWriter;
        }

        public boolean isFree() {
            return pathFileWriter == null;
        }

        @Override
        public void run() {
            while (true) {
                if (isFree()) {
                    try {
                        Thread.sleep(TIME_CHECK);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    continue;
                }
                try (PathFileReader pfr = new PathFileReader(pathFileWriter)) {
                    pathFileWriter = new PathFileWriter();
                    for (Path path : pfr) {
                        if (path.isTerminal()) {
                            // Solution trouvée
                            System.out.println(path);
                        } else {
                            // On creer tout les sous chemins
                            Point lastPoint = path.getLastPoint().getPoint();
                            if (lastPoint.getLstNext() != null) {
                                for (Point subPoint : lastPoint.getLstNext()) {
                                    if (!subPoint.getName().equals("0")) { // On ne veux pas retourner à 0
                                        Path pathTmp = path.addPoint(subPoint);
                                        pathFileWriter.addPath(pathTmp);
                                        if (pathFileWriter.getLineCount() > SolverDiskMultiThread.FILE_SIZE_MAX) {
                                            pathFileWriter.writePath();
                                            endProcess.generated(processId, pathFileWriter);
                                            pathFileWriter = new PathFileWriter();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    pathFileWriter.writePath();
                    endProcess.generated(processId, pathFileWriter);
                }
                pathFileWriter = null;
            }
        }
    }

}



