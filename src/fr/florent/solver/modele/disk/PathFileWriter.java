package fr.florent.solver.modele.disk;

import fr.florent.solver.modele.Path;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class PathFileWriter {

    private static final String PATH_DATA = "C:\\temps";

    private static final int CACHE_SIZE = 10000;

    private int index = 0;
    private int lineCount = 0;

    private File file;
    private Path[] cachePath = new Path[CACHE_SIZE];

    /**
     * Variable pour le monitoring
     */
    private int profondeur;

    public PathFileWriter() {
        try {
            file = Files.createTempFile(java.nio.file.Path.of(PATH_DATA), "slf_", null).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPath(Path path) {
        if(index >= CACHE_SIZE){
            writePath();
        }
        profondeur = path.getLstPoint().size();
        cachePath[index] = path;
        index++;
        lineCount++;
    }

    public void writePath() {
        if (index != 0) {
            if(file.getFreeSpace() / 1024 /1024 /1024 < 10) {
                throw new RuntimeException("Not free space");
            }
            try(FileWriter fw = new FileWriter(file, true)) {
                for(Path path : cachePath) {
                    if(path == null) {
                        break;
                    }
                    fw.write(PathSerializer.write(path));
                    fw.write("\n");
                }
                fw.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Arrays.fill(cachePath, null);
            index = 0;
        }
    }

    public int getLineCount() {
        return lineCount;
    }

    public File getFile() {
        return file;
    }

    public int getProfondeur() {
        return profondeur;
    }
}
