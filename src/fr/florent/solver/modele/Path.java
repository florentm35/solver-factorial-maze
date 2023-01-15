package fr.florent.solver.modele;

import fr.florent.solver.data.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Path {

    private Path(PointReport lastPoint, List<PointReport> lstPoint, Stack<String> letter) {
        this.lastPoint = lastPoint;
        this.lstPoint = lstPoint;
        this.letter = letter;
    }

    private final PointReport lastPoint;

    private final List<PointReport> lstPoint;

    private final Stack<String> letter;

    public static Path of(Point point) {

        PointReport pr = new PointReport(point, 0);

        List<PointReport> points = new ArrayList();
        points.add(pr);
        return new Path(pr, points, new Stack<>());

    }

    public Path addPoint(Point point) {
        Stack<String> stack = (Stack<String>) letter.clone();
        int niveau = getNiveau();
        if (point.getLetter() != null) {
            stack.add(point.getLetter());
            niveau++;
            point = Data.getInstance().getPoint(Point.of(point.getNumber()));
        } else {
            niveau--;
            point = Data.getInstance().getPoint(Point.of(!stack.empty() ? stack.pop() : null, point.getNumber()));
        }
        PointReport pr = new PointReport(point, niveau);

        List<PointReport> points = new ArrayList(lstPoint);
        points.add(pr);
        return new Path(pr, points, stack);
    }

    private int getNiveau() {
        return lastPoint != null ? lastPoint.getNiveau() : 0;
    }

    /**
     * Dernier point :
     * <ul>
     *     <li>Niveau 0</li>
     *     <li>Ne soit pas le point de départ</li>
     *     <li>Est un point de sortie</li>
     * </ul>
     */
    public boolean isTerminal() {
        return getNiveau() == 0
                && !lastPoint.getPoint().getName().equals("0")
                && lastPoint.hasOutMaze();
    }

    public PointReport getLastPoint() {
        return lastPoint;
    }

    public List<PointReport> getLstPoint() {
        return lstPoint;
    }

    public Stack<String> getLetter() {
        return letter;
    }

    @Override
    public String toString() {
        return lstPoint.stream().map(PointReport::toString).collect(Collectors.joining(","));
    }
}
