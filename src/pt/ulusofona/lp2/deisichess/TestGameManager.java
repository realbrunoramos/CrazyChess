package pt.ulusofona.lp2.deisichess;


import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {
    @Test
    public void boardMapAndTeamStatics() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4.txt"));
        String[][] boardMap = gm.theBoard.getBoard();
        TeamStatistic[] teamStatistics = gm.gameStatus.getTeamStatistics();
        String blackTeam = teamStatistics[0].toString();
        //team: Pretas | validMoves: 0 | invalidMoves: 0 | totalPoints: 0 | captures: 0
        String expTeamStaticsStr = "Pretas|0|0|0|0";
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
    public void boardMapAndTeamStatics_ValidMove_test2() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4.txt"));

        String[][] boardMap;
        gm.move(2, 1, 2, 2);
        boardMap = gm.theBoard.getBoard();
        TeamStatistic[] teamStatistics = gm.gameStatus.getTeamStatistics();
        String blackTeam = teamStatistics[0].toString();
        String expTeamStaticsStr = "team: Pretas | validMoves: 1 | invalidMoves: 0 | captures: 0";
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
    public void boardMapAndTeamStatics_ValidMoveAndCapture_test3() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4.txt"));
        String[][] boardMap;
        gm.move(2, 1, 1, 2);
        boardMap = gm.theBoard.getBoard();
        TeamStatistic[] teamStatistics = gm.gameStatus.getTeamStatistics();
        String blackTeam = teamStatistics[0].toString();
        String expTeamStaticsStr = "team: Pretas | validMoves: 1 | invalidMoves: 0 | captures: 1";
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
    public void moveAndUndo_00() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/8x8.txt"));
        gm.move(0, 0, 0, 1);
        assertEquals("Pretas|1|0|0|0", gm.gameStatus.getTeamStatistics()[0].toString());
        assertEquals("Brancas|0|0|0|0", gm.gameStatus.getTeamStatistics()[1].toString());
        gm.undo();
        assertEquals("Pretas|0|0|0|0", gm.gameStatus.getTeamStatistics()[0].toString());
        assertEquals("Brancas|0|0|0|0", gm.gameStatus.getTeamStatistics()[1].toString());
    }
    @Test
    public void boardMapAndTeamStatics_InvalidMove_test4() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4.txt"));

        String[][] boardMap;
        gm.move(2, 1, 0, 1);
        boardMap = gm.theBoard.getBoard();
        TeamStatistic[] teamStatistics = gm.gameStatus.getTeamStatistics();
        String blackTeam = teamStatistics[0].toString();
        String expTeamStaticsStr = "team: Pretas | validMoves: 0 | invalidMoves: 1 | captures: 0";
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
    public void boardMapAndTeamStatistics_CapturingLastPiece_test5() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4-AlmostEnd.txt"));

        String[][] boardMap;
        gm.move(2, 1, 1, 2);
        boardMap = gm.theBoard.getBoard();
        TeamStatistic[] teamStatistics = gm.gameStatus.getTeamStatistics();

        String blackTeam = teamStatistics[0].toString();
        String expTeamStaticsStr = "team: Pretas | validMoves: 1 | invalidMoves: 0 | captures: 1";
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
    public void getPieceInfo_CapturedPiece_test6() throws InvalidGameInputException, IOException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4.txt"));
        gm.move(2, 1, 1, 2);
        String[] pieceInfo = gm.getPieceInfo(6);
        String[] expected = {"6", "0", "1", "O Beberolas", "capturado", "", ""};
        for (int i = 0; i<7; i++){
            assertEquals(expected[i], pieceInfo[i]);
        }
    }
    @Test
    public void getPieceInfoAsString_PieceNotInGame_test7() throws InvalidGameInputException, IOException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4-AlmostEnd.txt"));
        gm.move(2, 1, 1, 2);
        String pieceInfoStr = gm.getPieceInfoAsString(6);
        String expected = "6 | 0 | 1 | O Beberolas @ (n/a)";
        assertEquals(expected, pieceInfoStr);
    }
    @Test
    public void getPieceInfo_CapturedPiece_test8() throws InvalidGameInputException, IOException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4-AlmostEnd.txt"));
        gm.move(2, 1, 1, 2);
        String pieceInfoStr = gm.getPieceInfo(6)[4];
        String expected = "capturado";
        assertEquals(expected, pieceInfoStr);
    }
    @Test
    public void getPieceInfo_CapturedPiece2_test9() throws InvalidGameInputException, IOException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4-AlmostEnd.txt"));
        String ob1 = gm.getPieceInfo(6)[4];
        String ob2 = gm.getPieceInfo(2)[4];
        String expectedOb1 = "em jogo";
        String expectedOb2 = "capturado";
        assertEquals(expectedOb1, ob1);
        assertEquals(expectedOb2, ob2);
    }
    @Test
    public void gameOver_ExhaustivePlaying_test10() throws InvalidGameInputException, IOException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4-1.txt"));
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
    public void getGameResult_fileWithBlacksVictory_test11() throws InvalidGameInputException, IOException{
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4-BlacksVictory.txt"));
        ArrayList<String> gameResult = new ArrayList<>();
        gameResult.add("JOGO DE CRAZY CHESS");
        gameResult.add("Resultado: VENCERAM AS PRETAS");
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
    public void getSquareInfo_test12() throws InvalidGameInputException, IOException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4.txt"));
        String[] result = gm.getSquareInfo(1, 0);
        String[] expected = {"1","0","0","Chefe", "00.png"};
        for (int i = 0; i<5; i++){
            assertEquals(expected[i], result[i]);
        }

    }
    @Test
    public void getGameResult_fileWithDraw_test13() throws InvalidGameInputException, IOException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4-Draw.txt"));
        gm.gameOver();
        ArrayList<String> gameResult = new ArrayList<>();
        gameResult.add("JOGO DE CRAZY CHESS");
        gameResult.add("Resultado: EMPATE");
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
    public void getCurrentTeamID_theCurrentTeam_test14() throws InvalidGameInputException, IOException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4.txt"));
        assertEquals(0, gm.getCurrentTeamID());
        gm.move(2,1, 2,2);
        assertEquals(1, gm.getCurrentTeamID());
        gm.move(1,2, 1,1);
        assertEquals(0, gm.getCurrentTeamID());
    }
    @Test
    public void getBoardSize_filesWithDifferentSizes_test15() throws InvalidGameInputException, IOException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/4x4.txt"));
        assertEquals(4, gm.getBoardSize());
        gm.loadGame(new File("test-files/8x8.txt"));
        assertEquals(8, gm.getBoardSize());
    }


}
