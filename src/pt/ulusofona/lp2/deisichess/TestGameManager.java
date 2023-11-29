package pt.ulusofona.lp2.deisichess;


import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {

    @Test
    public void validMove_ReiValidMove() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/8x8.txt"));

        ArrayList<String[]> boardMap;
        int x0 = 0;
        int y0 = 0;
        int x1 = 0;
        int y1 = 1;

        boardMap = gm.gameStatus.getTheBoard().getBoardMap();
        String pieceId = boardMap.get(y0)[x0];

        gm.move(x0, y0, x1, y1);

        boolean isValidMove = gm.gameStatus.getTheBoard().getAllPieces().get(pieceId).isValidMove(x1, y1);

        TeamStatistic[] teamStatistics = gm.gameStatus.getTeamStatistics();
        String blackTeam = teamStatistics[0].toString();
        //team: Pretas | validMoves: 1 | invalidMoves: 0 | totalPoints: 0 | captures: 0
        String expTeamStaticsStr = "10|1|0|0|0";
        String[][] expected = {
                {"0", "2", "3", "4", "5", "6", "7", "8"},
                {"1", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"0", "0", "0", "0", "0", "0", "0", "0"},
                {"9", "10", "11", "12", "13", "14", "15", "16"},
        };
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                assertEquals(expected[y][x], boardMap.get(y)[x]);
            }
        }

        assertEquals(expTeamStaticsStr, blackTeam);

        assertTrue(isValidMove);
    }
}
