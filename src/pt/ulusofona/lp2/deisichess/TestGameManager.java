package pt.ulusofona.lp2.deisichess;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {
    private pt.ulusofona.lp2.deisichess.StatisticsKt StatisticsKt;

    @Test
    public void gameOverAndMove_exhaustiveGame() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/8x8.txt"));

        assertEquals(0, gm.gameStatus.getConsecutivePlays());
        int x0 = 0;
        int y0 = 0;
        int x1 = 0;
        int y1 = 1;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(1, gm.gameStatus.getConsecutivePlays());

        x0 = 7;
        y0 = 7;
        x1 = 5;
        y1 = 5;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(2, gm.gameStatus.getConsecutivePlays());

        x0 = 2;
        y0 = 0;
        x1 = 0;
        y1 = 2;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(3, gm.gameStatus.getConsecutivePlays());

        x0 = 1;
        y0 = 7;
        x1 = 7;
        y1 = 1;
        assertFalse(gm.move(x0, y0, x1, y1));
        assertEquals(3, gm.gameStatus.getConsecutivePlays());

        x0 = 1;
        y0 = 7;
        x1 = 6;
        y1 = 2;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(4, gm.gameStatus.getConsecutivePlays());

        x0 = 5;
        y0 = 0;
        x1 = 5;
        y1 = 6;
        assertFalse(gm.move(x0, y0, x1, y1));
        assertEquals(4, gm.gameStatus.getConsecutivePlays());

        x0 = 3;
        y0 = 0;
        x1 = 6;
        y1 = 3;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(5, gm.gameStatus.getConsecutivePlays());

        x0 = 2;
        y0 = 7;
        x1 = 4;
        y1 = 5;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(6, gm.gameStatus.getConsecutivePlays());

        x0 = 6;
        y0 = 0;
        x1 = 7;
        y1 = 1;
        assertFalse(gm.move(x0, y0, x1, y1));
        assertEquals(6, gm.gameStatus.getConsecutivePlays());

        x0 = 4;
        y0 = 0;
        x1 = 2;
        y1 = 0;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(7, gm.gameStatus.getConsecutivePlays());

        x0 = 6;
        y0 = 2;
        x1 = 4;
        y1 = 4;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(8, gm.gameStatus.getConsecutivePlays());

        x0 = 0;
        y0 = 2;
        x1 = 2;
        y1 = 4;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(9, gm.gameStatus.getConsecutivePlays());

        x0 = 4;
        y0 = 5;
        x1 = 2;
        y1 = 3;
        assertFalse(gm.move(x0, y0, x1, y1));
        assertEquals(9, gm.gameStatus.getConsecutivePlays());

        x0 = 5;
        y0 = 5;
        x1 = 7;
        y1 = 5;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(10, gm.gameStatus.getConsecutivePlays());

        assertFalse(gm.gameOver());
    }
    @Test
    public void undo() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/8x8.txt"));
        int x0;
        int y0;
        int x1;
        int y1;

        assertEquals("Rainha",gm.gameStatus.getTheBoard().getAllPieces().get("8").getPieceNameType().split("/")[1]);
        assertEquals(0, gm.gameStatus.getRoundCounter());
        x0 = 0;
        y0 = 0;
        x1 = 0;
        y1 = 1;

        assertTrue(gm.move(x0, y0, x1, y1));

        gm.undo();
        System.out.println(gm.gameStatus.getRoundCounter());
        assertEquals("Rainha",gm.gameStatus.getTheBoard().getAllPieces().get("8").getPieceNameType().split("/")[1]);
        assertEquals(0, gm.gameStatus.getRoundCounter());

        x0 = 7;
        y0 = 7;
        x1 = 5;
        y1 = 5;

        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals("Ponei Mágico",gm.gameStatus.getTheBoard().getAllPieces().get("8").getPieceNameType().split("/")[1]);
        assertEquals(1, gm.gameStatus.getRoundCounter());

        gm.undo();

        assertEquals("Rainha",gm.gameStatus.getTheBoard().getAllPieces().get("8").getPieceNameType().split("/")[1]);
        assertEquals(0, gm.gameStatus.getRoundCounter());

        assertTrue(gm.move(x0, y0, x1, y1));
        x0 = 0;
        y0 = 0;
        x1 = 0;
        y1 = 1;

        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals("Ponei Mágico",gm.gameStatus.getTheBoard().getAllPieces().get("8").getPieceNameType().split("/")[1]);
        assertEquals(1, gm.gameStatus.getRoundCounter());

        x0 = 2;
        y0 = 0;
        x1 = 0;
        y1 = 2;

        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals("Padre da Vila",gm.gameStatus.getTheBoard().getAllPieces().get("8").getPieceNameType().split("/")[1]);
        assertEquals(2, gm.gameStatus.getRoundCounter());

        gm.undo();
        assertEquals("Ponei Mágico",gm.gameStatus.getTheBoard().getAllPieces().get("8").getPieceNameType().split("/")[1]);
        assertEquals(1, gm.gameStatus.getRoundCounter());

        assertFalse(gm.gameOver());
    }
    @Test
    public void gameOver_empateTwoPieces() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/8x8-empate.txt"));
        assertTrue(gm.gameOver());
    }
    @Test
    public void gameOver_victoryFile() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/8x8-jaVemVitoria.txt"));
        String[] pieceInfo = gm.getPieceInfo(5);
        boolean isInGame = gm.gameStatus.getTheBoard().getAllPieces().get("5").isInGame();
        String[] exp = {"5", "4", "10", "Artolas", "capturado", "", ""};
        assertFalse(isInGame);
        for (int x = 0; x<7; x++){
            assertEquals(exp[x], pieceInfo[x]);
        }
        assertTrue(gm.gameOver());
    }
    @Test
    public void getHints_suggests() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/points.txt"));
        List<Comparable> cmps = gm.getHints(4,3);
        String[] arr = {"(0,7) -> 1000", "(1,0) -> 8", "(5,2) -> 3", "(7,3) -> 3", "(4,7) -> 3"};
        for (int i =0; i<5; i++){
            assertEquals(arr[i], cmps.get(i));
        }
    }
    @Test
    public void statisticsList() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/8x8-jaVemVitoria.txt"));
        StatisticsKt stats = StatisticsKt;
        System.out.println(stats.getStatsCalculator(StatType.PECAS_MAIS_BARALHADAS).invoke(gm));
        System.out.println(stats.getStatsCalculator(StatType.TIPOS_CAPTURADOS).invoke(gm));
        System.out.println(stats.getStatsCalculator(StatType.TOP_5_PONTOS).invoke(gm));

    }

}
