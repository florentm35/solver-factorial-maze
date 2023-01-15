package fr.florent.solver.modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Point {

    private final int number;
    private final String letter;

    private List<Point> lstNext;

    private Point(int number, String letter) {
        this.number = number;
        this.letter = letter;
    }

    public static Point of(String point) {
        String letter = null;
        int number;
        char firstCaracter = point.charAt(0);
        if(firstCaracter >= 'A' && firstCaracter <= 'Z') {
            letter = firstCaracter+"";
            point = point.substring(1);
        }
        number = Integer.parseInt(point);

        return Point.of(letter, number);
    }

    public static Point of(String letter, int number) {
        return new Point(number, letter);
    }

    public static Point of(int number) {
        return new Point(number, null);
    }

    public String getName() {
        return (letter != null ? letter : "") + number;
    }

    public void addNext(Point point) {
        if(lstNext == null) {
            lstNext = new ArrayList<>();
        }
        if(!lstNext.contains(point)) {
            lstNext.add(point);
        }
    }

    public List<Point> getLstNext() {
        return lstNext;
    }

    public int getNumber() {
        return number;
    }

    public String getLetter() {
        return letter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return number == point.number && Objects.equals(letter, point.letter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, letter);
    }

    @Override
    public String toString() {
        return  getName();
    }
}
