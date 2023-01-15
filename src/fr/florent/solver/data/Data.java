package fr.florent.solver.data;

import fr.florent.solver.modele.Point;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private static Data _instance;

    private List<Point> lstPoints = new ArrayList<>();

    private Data() {
        lstPoints = loadValueList();
    }

    public static Data getInstance() {
        if (_instance == null) {
            _instance = new Data();
        }
        return _instance;
    }


    public Point getPoint(Point point) {
        int index = lstPoints.indexOf(point);
        if (index > -1) {
            point = lstPoints.get(index);
        } else {
            lstPoints.add(point);
        }
        return point;
    }


    private List<Point> loadValueList() {
        addSegment(Point.of(0), Point.of("A", 3));

        addSegment(Point.of(1), Point.of(12));
        addSegment(Point.of(1), Point.of("A", 5));
        addSegment(Point.of(1), Point.of("A", 12));

        addSegment(Point.of(2), Point.of(10));
        addSegment(Point.of(2), Point.of(8));
        addSegment(Point.of(2), Point.of("A", 9));
        addSegment(Point.of(2), Point.of("C", 4));

        addSegment(Point.of(3), Point.of("B", 1));
        addSegment(Point.of(3), Point.of("B", 4));

        addSegment(Point.of(4), Point.of("B", 5));
        addSegment(Point.of(4), Point.of("B", 6));

        addSegment(Point.of(5), Point.of("D", 4));
        addSegment(Point.of(5), Point.of("D", 5));

        addSegment(Point.of(6), Point.of("D", 8));

        addSegment(Point.of(7), Point.of("D", 10));
        addSegment(Point.of(7), Point.of("B", 7));

        addSegment(Point.of(8), Point.of(10));
        addSegment(Point.of(8), Point.of(2));
        addSegment(Point.of(8), Point.of("A", 9));
        addSegment(Point.of(8), Point.of("C", 4));

        addSegment(Point.of(9), Point.of("C", 7));
        addSegment(Point.of(9), Point.of("C", 11));

        addSegment(Point.of(10), Point.of(8));
        addSegment(Point.of(10), Point.of(2));
        addSegment(Point.of(10), Point.of("A", 9));
        addSegment(Point.of(10), Point.of("C", 4));

        addSegment(Point.of(11), Point.of("A", 10));

        addSegment(Point.of(12), Point.of(1));
        addSegment(Point.of(12), Point.of("A", 5));
        addSegment(Point.of(12), Point.of("A", 12));

        addSegment(Point.of("B", 11), Point.of("B", 12));
        addSegment(Point.of("B", 4), Point.of("B", 1));
        addSegment(Point.of("B", 5), Point.of("B", 6));
        addSegment(Point.of("B", 7), Point.of("D", 10));
        addSegment(Point.of("B", 8), Point.of("D", 1));
        addSegment(Point.of("B", 8), Point.of("D", 3));
        addSegment(Point.of("B", 7), Point.of("D", 10));

        addSegment(Point.of("B", 11), Point.of("B", 12));
        addSegment(Point.of("C", 12), Point.of("C", 2));

        addSegment(Point.of("D", 12), Point.of("D", 11));



        return lstPoints;
    }

    private void addSegment(Point point, Point point2) {
        point = getPoint(point);
        point2 = getPoint(point2);
        point.addNext(point2);
        point2.addNext(point);
    }

}
