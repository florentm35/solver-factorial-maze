package fr.florent.solver.modele.disk;

import fr.florent.solver.modele.Path;

import java.io.*;
import java.nio.file.Files;
import java.util.Iterator;

public class PathFileReader implements Iterable<Path>, Closeable{

    private File file;

    private PathFileIterator iterator;

    private long fileSize;

    private long freeDisk;

    public PathFileReader(PathFileWriter pathFileWriter) {
        file = pathFileWriter.getFile();
        freeDisk = file.getFreeSpace();
        try {
            fileSize =  Files.size(file.toPath());
            iterator = new PathFileIterator(new BufferedReader(new FileReader(pathFileWriter.getFile())), pathFileWriter.getLineCount());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<Path> iterator() {
        return iterator;
    }

    @Override
    public void close() {
        iterator.close();
        file.delete();
    }

    public long getFileSize() {
        return fileSize;
    }

    public long getFreeDisk() {
        return freeDisk;
    }

    private static class PathFileIterator implements Iterator<Path> {

        private BufferedReader stream;
        private int size;
        private int index = 0;

        public PathFileIterator(BufferedReader stream, int size) {
            this.stream = stream;
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Path next() {
            try {
                index++;
                return PathSerializer.read(stream.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void close() {
            try {
                stream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
