package fr.florent.solver;

import fr.florent.solver.data.Data;
import fr.florent.solver.modele.Path;
import fr.florent.solver.modele.Point;

public class Test {
    private static Data data = null;

    public static void main(String[] args) {
        // Calcul tout les chemins de chaque point
        data = Data.getInstance();

        Path testPath = Path.of(data.getPoint(Point.of(0)));
        testPath = addPointAndVerify(testPath, Point.of("A3"));
        testPath = addPointAndVerify(testPath, Point.of("B1"));
        testPath = addPointAndVerify(testPath, Point.of("12"));
        testPath = addPointAndVerify(testPath, Point.of("B11"));
        testPath = addPointAndVerify(testPath, Point.of("A10"));
        testPath = addPointAndVerify(testPath, Point.of("C4"));
        testPath = addPointAndVerify(testPath, Point.of("B6"));
        testPath = addPointAndVerify(testPath, Point.of("D8"));
        testPath = addPointAndVerify(testPath, Point.of("10"));
        testPath = addPointAndVerify(testPath, Point.of("7"));
        testPath = addPointAndVerify(testPath, Point.of("7"));
        testPath = addPointAndVerify(testPath, Point.of("9"));
        testPath = addPointAndVerify(testPath, Point.of("8"));
        testPath = addPointAndVerify(testPath, Point.of("D3"));
        testPath = addPointAndVerify(testPath, Point.of("B4"));
        testPath = addPointAndVerify(testPath, Point.of("B6"));
        testPath = addPointAndVerify(testPath, Point.of("D8"));
        testPath = addPointAndVerify(testPath, Point.of("10"));
        testPath = addPointAndVerify(testPath, Point.of("7"));
        testPath = addPointAndVerify(testPath, Point.of("D10"));
        testPath = addPointAndVerify(testPath, Point.of("8"));
        testPath = addPointAndVerify(testPath, Point.of("6"));
        testPath = addPointAndVerify(testPath, Point.of("4"));
        testPath = addPointAndVerify(testPath, Point.of("5"));
        //testPath = addPointAndVerify(testPath, Point.of("1"));

        System.out.println("Is terminal : " + testPath.isTerminal());
    }

    private static Path addPointAndVerify(Path path, Point point) {
        if (!path.getLastPoint().getPoint().getLstNext().contains(point)) {
            throw new RuntimeException("The point is unvaible");
        } else {
            path = path.addPoint(data.getPoint(point));
        }
        System.out.println(path.toString());
        return path;
    }
}

