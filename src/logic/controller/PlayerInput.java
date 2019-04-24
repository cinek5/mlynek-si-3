package logic.controller;

import logic.NodeType;
import logic.Phase;
import logic.moves.Move;

import java.util.Scanner;

/**
 * Created by Cinek on 24.04.2019.
 */
public class PlayerInput implements GameInput {

    private Scanner scanner;
    private MoveParser moveParser;
    private NodeType nodeType;

    public PlayerInput(NodeType nodeType)
    {
        scanner = new Scanner(System.in);
        moveParser = new MoveParser();
        this.nodeType = nodeType;
    }


    @Override
    public Move getMove(GameController controller) {

        String line = scanner.nextLine();
        if (controller.isWasMillInPreviousTurn())
        {
            moveParser.parseCapturingMove(line, nodeType );
        }
        else
        {
            Phase gamePhase = controller.getGamePhase();
            if (gamePhase == Phase.PLACIING)
            {
                moveParser.parsePlacingMove(line, nodeType);
            }
        }

        return null;
    }
}
