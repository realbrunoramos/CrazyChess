package pt.ulusofona.lp2.deisichess;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {
    @Test
    public void boardMapAndTeamStatics_test1() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4.txt"));
        String[][] boardMap = gm.theBoard.getBoard();

        String blackTeam = gm.teamStatistics[0].toString();
        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 0\ninvalidMoves= 0\ncaptures= 0";

        String[][] expected = {
                {"0", "1", "0", "2"},
                {"0", "0", "3", "0"},
                {"0", "6", "0", "0"},
                {"0", "5", "4", "0"}
        };
        for (int y = 0; y<4; y++){
            for (int x = 0; x<4; x++){
                assertEquals(expected[y][x], boardMap[y][x]);
            }
        }
        assertEquals(expTeamStaticsStr, blackTeam);
    }
    @Test
    public void boardMapAndTeamStatics_ValidMove_test2() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4.txt"));

        String[][] boardMap;
        gm.move(2, 1, 2, 2);
        boardMap = gm.theBoard.getBoard();
        String blackTeam = gm.teamStatistics[0].toString();
        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 1\ninvalidMoves= 0\ncaptures= 0";
        String[][] expected = {
                {"0", "1", "0", "2"},
                {"0", "0", "0", "0"},
                {"0", "6", "3", "0"},
                {"0", "5", "4", "0"}
        };
        for (int y = 0; y<4; y++){
            for (int x = 0; x<4; x++){
                assertEquals(expected[y][x], boardMap[y][x]);
            }
        }
        assertEquals(expTeamStaticsStr, blackTeam);
    }
    @Test
    public void boardMapAndTeamStatics_ValidMoveAndCapture_test3() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4.txt"));

        String[][] boardMap;
        gm.move(2, 1, 1, 2);
        boardMap = gm.theBoard.getBoard();
        String blackTeam = gm.teamStatistics[0].toString();
        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 1\ninvalidMoves= 0\ncaptures= 1";
        String[][] expected = {
                {"0", "1", "0", "2"},
                {"0", "0", "0", "0"},
                {"0", "3", "0", "0"},
                {"0", "5", "4", "0"}
        };
        for (int y = 0; y<4; y++){
            for (int x = 0; x<4; x++){
                assertEquals(expected[y][x], boardMap[y][x]);
            }
        }
        assertEquals(expTeamStaticsStr, blackTeam);
    }
    @Test
    public void boardMapAndTeamStatics_InvalidMove_test4() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4.txt"));

        String[][] boardMap;
        gm.move(2, 1, 0, 1);
        boardMap = gm.theBoard.getBoard();
        String blackTeam = gm.teamStatistics[0].toString();
        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 0\ninvalidMoves= 1\ncaptures= 0";
        String[][] expected = {
                {"0", "1", "0", "2"},
                {"0", "0", "3", "0"},
                {"0", "6", "0", "0"},
                {"0", "5", "4", "0"}
        };
        for (int y = 0; y<4; y++){
            for (int x = 0; x<4; x++){
                assertEquals(expected[y][x], boardMap[y][x]);
            }
        }

        assertEquals(expTeamStaticsStr, blackTeam);
    }
    @Test
    public void boardMapAndTeamStatistics_CapturingLastPiece_test5() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4-AlmostEnd.txt"));

        String[][] boardMap;
        gm.move(2, 1, 1, 2);
        boardMap = gm.theBoard.getBoard();

        String blackTeam = gm.teamStatistics[0].toString();
        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 1\ninvalidMoves= 0\ncaptures= 1";
        String[][] expected = {
                {"0", "0", "0", "0"},
                {"0", "0", "0", "0"},
                {"0", "3", "0", "0"},
                {"0", "0", "0", "0"}
        };
        for (int y = 0; y<4; y++){
            for (int x = 0; x<4; x++){
                assertEquals(expected[y][x], boardMap[y][x]);
            }
        }
        assertTrue(gm.gameOver());
        assertEquals(expTeamStaticsStr, blackTeam);
    }
    @Test
    public void getPieceInfo_CapturedPiece_test6() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4.txt"));
        gm.move(2, 1, 1, 2);
        String[] pieceInfo = gm.getPieceInfo(6);
        String[] expected = {"6", "0", "1", "O Beberolas", "capturado", "", ""};
        for (int i = 0; i<7; i++){
            assertEquals(expected[i], pieceInfo[i]);
        }
    }
    @Test
    public void getPieceInfoAsString_PieceNotInGame_test7() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4-AlmostEnd.txt"));
        gm.move(2, 1, 1, 2);
        String pieceInfoStr = gm.getPieceInfoAsString(6);
        String expected = "6 | 0 | 1 | O Beberolas @ (n/a)";
        assertEquals(expected, pieceInfoStr);
    }
    @Test
    public void getPieceInfo_CapturedPiece_test8() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4-AlmostEnd.txt"));
        gm.move(2, 1, 1, 2);
        String pieceInfoStr = gm.getPieceInfo(6)[4];
        String expected = "capturado";
        assertEquals(expected, pieceInfoStr);
    }
    @Test
    public void getPieceInfo_CapturedPiece2_test9() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4-AlmostEnd.txt"));
        gm.theBoard.updateStatus();
        String ob1 = gm.getPieceInfo(6)[4];
        String ob2 = gm.getPieceInfo(2)[4];
        String expectedOb1 = "em jogo";
        String expectedOb2 = "capturado";
        assertEquals(expectedOb1, ob1);
        assertEquals(expectedOb2, ob2);
    }
    @Test
    public void gameOver_ExhaustivePlaying_test10() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4-1.txt"));
        gm.theBoard.updateStatus();
        assertFalse(gm.gameOver());
        gm.move(2, 1, 2, 2);
        gm.move(1, 2, 1, 1);
        gm.move(2, 2, 2, 1);
        assertFalse(gm.gameOver());
        gm.move(1, 1, 1, 2);
        gm.move(2, 1, 2, 2);
        gm.move(1, 2, 1, 1);
        assertFalse(gm.gameOver());
        gm.move(2, 2, 2, 1);
        gm.move(1, 1, 1, 2);
        gm.move(2, 1, 2, 2);
        gm.move(1, 2, 1, 1);
        assertTrue(gm.gameOver());

    }
    @Test
    public void getGameResult_fileWithWhitesVictory_test11() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4-WhiteVictory.txt"));
        ArrayList<String> gameResult = new ArrayList<>();
        gameResult.add("JOGO DE CRAZY CHESS");
        gameResult.add("Resultado: VENCERAM AS BRANCAS");
        gameResult.add("---");
        gameResult.add("Equipa das Pretas");
        gameResult.add("0");
        gameResult.add("0");
        gameResult.add("0");
        gameResult.add("Equipa das Brancas");
        gameResult.add("0");
        gameResult.add("0");
        gameResult.add("0");
        ArrayList<String> expected = gm.getGameResults();
        for (int i = 0; i<11; i++){
            assertEquals(expected.get(i), gameResult.get(i));
        }
    }

    @Test
    public void getSquareInfo_test12() {
        GameManager gm = new GameManager();
        gm.resetAll();
        gm.loadGame(new File("test-files/4x4.txt"));
        String[] result = gm.getSquareInfo(1, 0);
        String[] expected = {"1","0","0","Chefe", "00.png"};
        for (int i = 0; i<5; i++){
            assertEquals(expected[i], result[i]);
        }

    }


}
