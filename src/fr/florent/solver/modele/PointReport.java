package fr.florent.solver.modele;

import java.util.Objects;


public class PointReport {

    public PointReport(Point point, int niveau) {
        this.point = point;
        this.niveau = niveau;
    }

    private Point point;
    private int niveau;

    public int getNiveau() {
        return niveau;
    }


    /**
     * A un déscendant autre que 0 sans lettre
     */
    public boolean hasOutMaze() {
        if(this.point.getLstNext() != null) {
            for (Point subPoint : this.point.getLstNext()) {
                if (!subPoint.getName().equals("0") && subPoint.getLetter() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public Point getPoint() {
        return point;
    }

    @Override
    public String toString() {
        return point + "(" + niveau + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointReport that = (PointReport) o;
        return niveau == that.niveau && point.equals(that.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point, niveau);
    }

}
