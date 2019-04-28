package logic;

import logic.moves.CapturingMove;
import logic.moves.Move;
import logic.moves.MovingMove;
import logic.moves.PlacingMove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Cinek on 24.04.2019.
 */
public class Board {
    private List<Node> nodes;
    private List<Line> lines;
    private static final String BOARD_TEMPLATE =
                            "7 %s=============%s============%s\n" +
                                    "  |              |              |\n" +
                                    "6 |     %s=======%s=======%s    |\n" +
                                    "  |     |        |         |    |\n" +
                                    "5 |     |   %s===%s===%s   |    |\n" +
                                    "  |     |    |   ||    |   |    |\n" +
                                    "4 %s====%s==%s===||===%s==%s====%s\n" +
                                    "  |     |    |   ||    |   |    |\n" +
                                    "3 |     |   %s===%s===%s   |    |\n" +
                                    "  |     |        |         |    |\n" +
                                    "2 |     %s=======%s=======%s    |\n" +
                                    "  |              |              |\n" +
                                    "1 %s=============%s============%s\n" +
                                    "   a     b   c   d     e   f    g";


    public Board() {
        createNodes();
        createLines();
    }

    public void printBoard() {
        List<String> args = nodes.stream().map(node ->node.toString()).collect(Collectors.toList());
        String[] argsArray = new String[24];
        argsArray = args.toArray(argsArray);
        System.out.println(String.format(BOARD_TEMPLATE, argsArray));

    }


    public Node getNode(int index) {
        return nodes.get(index-1);
    }

    public void makeMove(Move move) {
        if (move instanceof PlacingMove) {
            makePlacingMove((PlacingMove) move);
        }
        if (move instanceof CapturingMove) {
            makeCapturingMove((CapturingMove) move);
        }
    }

    private void makePlacingMove(PlacingMove move) {
        int index = move.getNodeIndex();
        nodes.get(index-1).setNodeType(move.getNodeType());
    }

    private void makeCapturingMove(CapturingMove move) {
        int index = move.getCapturedNodeIndex();
        nodes.get(index-1).setNodeType(null);
    }

    private void makeMovingMove(MovingMove move) {
    }

    public int countMills(NodeType nodeType) {
        int numberOfMills = 0;
        for (Line line : lines) {
            if (line.isMill(nodeType)) {
                numberOfMills++;
            }
        }
        return numberOfMills;
    }


    private void createLines() {
        lines = Arrays.asList(
                // mill combinations for the outer box
                new Line(nodes.get(0), nodes.get(1), nodes.get(2)),
                new Line(nodes.get(0), nodes.get(9), nodes.get(21)),
                new Line(nodes.get(0), nodes.get(14), nodes.get(23)),
                new Line(nodes.get(21), nodes.get(22), nodes.get(23)),

                // mill combinations for the middle box
                new Line(nodes.get(3), nodes.get(4), nodes.get(5)),
                new Line(nodes.get(3), nodes.get(10), nodes.get(18)),
                new Line(nodes.get(5), nodes.get(13), nodes.get(20)),
                new Line(nodes.get(18), nodes.get(19), nodes.get(20)),

                // mill combinations for the inner box
                new Line(nodes.get(6), nodes.get(7), nodes.get(8)),
                new Line(nodes.get(6), nodes.get(11), nodes.get(15)),
                new Line(nodes.get(8), nodes.get(12), nodes.get(17)),
                new Line(nodes.get(15), nodes.get(16), nodes.get(17)),

                // mill combinations for the various lines connecting the boxes
                new Line(nodes.get(1), nodes.get(4), nodes.get(7)),
                new Line(nodes.get(9), nodes.get(10), nodes.get(11)),
                new Line(nodes.get(12), nodes.get(13), nodes.get(14)),
                new Line(nodes.get(16), nodes.get(19), nodes.get(22)));
    }

    private void createNodes() {
        nodes = new ArrayList<>(24);
        for (int index = 1; index <= 24; index++) {
            nodes.add(new Node(index));
        }
    }

}
