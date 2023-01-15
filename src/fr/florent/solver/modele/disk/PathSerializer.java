package fr.florent.solver.modele.disk;

import fr.florent.solver.data.Data;
import fr.florent.solver.modele.Path;
import fr.florent.solver.modele.Point;
import fr.florent.solver.modele.PointReport;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class PathSerializer {

    private static final Data data = Data.getInstance();

    public static String write(Path path) {
        String line = String.join("", path.getLetter() != null ? path.getLetter() : new Stack<>());
        line += "|";
        line += path.getLstPoint().stream().map(pr -> pr.getNiveau() + "," + pr.getPoint().getName()).collect(Collectors.joining(";"));
        return line;
    }

    public static Path read(String line) {
        String[] tmp  = line.split("\\|");
        Stack<String> letters = new Stack<>();
        for(int i = 0; i< tmp[0].length() ;  i++) {
            letters.push(tmp[0].charAt(i)+"");
        }

        List<PointReport> lstPr = new ArrayList<>();
        tmp = tmp[1].split(";");
        PointReport lastPoint = null;
        for(String prString :  tmp) {
            String[] tmpPr = prString.split(",");
            lastPoint = new PointReport(data.getPoint(Point.of(tmpPr[1])), Integer.parseInt(tmpPr[0]));
            lstPr.add(lastPoint);
        }

        return new Path(lastPoint, lstPr, letters);
    }
}
