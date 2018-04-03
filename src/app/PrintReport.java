package app;

import app.models.Board;
import app.models.Player;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by aldrinarciga on 4/3/2018.
 */
public class PrintReport extends JFrame {
    public void showReport(List<Board> boards, HashMap<Integer, String> players) throws JRException, ClassNotFoundException, SQLException {

        String reportSrcFile = this.getClass().getResource("reports/MatchReport.jrxml").getPath();

        // First, compile jrxml file.
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

        // Fields for report
        HashMap<String, Object> parameters = new HashMap<>();

        for(int x = 0 ; x < boards.size() && x < 8; x++) {
            String matchParam = "match" + (x + 1);
            String boardParam = "board" + (x + 1);
            String playerOneParam = "playerOne" + (x + 1);
            String playerTwoParam = "playerTwo" + (x + 1);

            Board board = boards.get(x);
            parameters.put(matchParam, Integer.toString(board.getMatchNumber()));
            parameters.put(boardParam, Integer.toString(board.getBoardNumber()));
            parameters.put(playerOneParam, players.get(board.getPlayerOne()));
            parameters.put(playerTwoParam, players.get(board.getPlayerTwo()));
        }

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        list.add(parameters);

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
        System.out.print("Done!");

    }
}
