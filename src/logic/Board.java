package logic;

import logic.moves.CapturingMove;
import logic.moves.Move;
import logic.moves.MovingMove;
import logic.moves.PlacingMove;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Created by Cinek on 24.04.2019.
 */
public class Board {
    private List<Node> nodes;
    private List<Line> lines;
    private int numberOfWhitePiecesToBePlaced =9;
    private int numberOfBlackPiecesToBePlaced =9;

    private int numberOfWhitePiecesOnBoard = 0;
    private int numberOfBlackPiecesOnBoard = 0;
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

    public int getNumberOfWhitePiecesOnBoard() {
        return numberOfWhitePiecesOnBoard;
    }

    public int getNumberOfBlackPiecesOnBoard() {
        return numberOfBlackPiecesOnBoard;
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
        NodeType nodeType = move.getNodeType();
        nodes.get(index-1).setNodeType(nodeType);

        if (nodeType == NodeType.BLACK)
        {
            numberOfBlackPiecesToBePlaced--;
            numberOfBlackPiecesOnBoard++;
        }
        else
        {
            numberOfWhitePiecesToBePlaced--;
            numberOfWhitePiecesOnBoard++;
        }

    }

    private void makeCapturingMove(CapturingMove move) {
        int index = move.getCapturedNodeIndex();
        NodeType nodeType = move.getCapturedNodeType();
        nodes.get(index-1).setNodeType(null);
        if (nodeType == NodeType.BLACK)
        {
            numberOfBlackPiecesOnBoard--;
        }
        else
        {
            numberOfWhitePiecesOnBoard--;
        }
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

    public int getNumberOfWhitePiecesToBePlaced() {
        return numberOfWhitePiecesToBePlaced;
    }

    public int getNumberOfBlackPiecesToBePlaced() {
        return numberOfBlackPiecesToBePlaced;
    }

    private void createLines() {
        lines = asList(
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

        // adjacent nodes for outer box
        nodes.get(0).setAdjacentNodesIndexes(asList(1, 9));
        nodes.get(1).setAdjacentNodesIndexes(asList(0, 2, 4));
        nodes.get(2).setAdjacentNodesIndexes(asList(1, 14));
        nodes.get(9).setAdjacentNodesIndexes(asList(0, 10, 21));
        nodes.get(14).setAdjacentNodesIndexes(asList(2, 13, 23));
        nodes.get(21).setAdjacentNodesIndexes(asList(9, 22));
        nodes.get(22).setAdjacentNodesIndexes(asList(19, 21, 23));
        nodes.get(23).setAdjacentNodesIndexes(asList(14, 22));

        // adjacent nodes for middle box
        nodes.get(3).setAdjacentNodesIndexes(asList(4, 10));
        nodes.get(4).setAdjacentNodesIndexes(asList(1, 3, 5, 7));
        nodes.get(5).setAdjacentNodesIndexes(asList(4, 13));
        nodes.get(10).setAdjacentNodesIndexes(asList(3, 9, 11, 18));
        nodes.get(13).setAdjacentNodesIndexes(asList(5, 12, 14, 20));
        nodes.get(18).setAdjacentNodesIndexes(asList(10, 19));
        nodes.get(19).setAdjacentNodesIndexes(asList(16, 18, 20, 22));
        nodes.get(20).setAdjacentNodesIndexes(asList(13, 19));

        // adjacent nodes for inner box
        nodes.get(6).setAdjacentNodesIndexes(asList(7, 11));
        nodes.get(7).setAdjacentNodesIndexes(asList(4, 6, 8));
        nodes.get(8).setAdjacentNodesIndexes(asList(7, 12));
        nodes.get(11).setAdjacentNodesIndexes(asList(6, 10, 15));
        nodes.get(12).setAdjacentNodesIndexes(asList(8, 13, 17));
        nodes.get(15).setAdjacentNodesIndexes(asList(11, 16));
        nodes.get(16).setAdjacentNodesIndexes(asList(15, 17, 19));
        nodes.get(17).setAdjacentNodesIndexes(asList(12, 16));
    }




}
