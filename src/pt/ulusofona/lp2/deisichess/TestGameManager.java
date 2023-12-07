package pt.ulusofona.lp2.deisichess;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameManager {
    private pt.ulusofona.lp2.deisichess.StatisticsKt StatisticsKt;
    private String[] nameTypePieces = {"Rainha", "Ponei Mágico", "Padre da Vila", "TorreHor", "TorreVert", "Homer Simpson"};
    @Test
    public void gameOverAndMove_exhaustiveGame() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        StatisticsKt stats = StatisticsKt;
        int pos = 0;
        gm.loadGame(new File("test-files/8x8.txt"));
        HashMap<String, Piece> pieces = gm.getGameStatus().getTheBoard().getAllPieces();

        assertEquals(10, gm.getCurrentTeamID());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;
        assertEquals(0, gm.getGameStatus().getConsecutivePlays());

        int x0 = 0;
        int y0 = 0;
        int x1 = 0;
        int y1 = 1;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(20, gm.getCurrentTeamID());
        assertEquals(1, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;;

        x0 = 7;
        y0 = 7;
        x1 = 5;
        y1 = 5;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(10, gm.getCurrentTeamID());
        assertEquals(2, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;

        x0 = 2;
        y0 = 0;
        x1 = 0;
        y1 = 2;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(20, gm.getCurrentTeamID());
        assertEquals(3, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());


        x0 = 1;
        y0 = 7;
        x1 = 7;
        y1 = 1;
        assertFalse(gm.move(x0, y0, x1, y1));
        assertEquals(20, gm.getCurrentTeamID());
        assertEquals(3, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;
        assertEquals("[20:A Barulhenta do Bairro:1:0]", (stats.getStatsCalculator(StatType.PECAS_MAIS_BARALHADAS).invoke(gm)).toString());


        x0 = 1;
        y0 = 7;
        x1 = 6;
        y1 = 2;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(10, gm.getCurrentTeamID());
        assertEquals(4, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;

        x0 = 5;
        y0 = 0;
        x1 = 5;
        y1 = 5;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(20, gm.getCurrentTeamID());
        assertEquals(0, gm.getGameStatus().getConsecutivePlays());
        assertEquals("[O Maior Grande (PRETA) fez 1 capturas, Torre Trapalhona (BRANCA) fez 0 capturas, Torre Poderosa (BRANCA) fez 0 capturas, Padreco (BRANCA) fez 0 capturas, O Poderoso Chefao (PRETA) fez 0 capturas]", (stats.getStatsCalculator(StatType.TOP_5_CAPTURAS).invoke(gm)).toString());
        assertEquals("[O Maior Grande (PRETA) tem 4 pontos]", (stats.getStatsCalculator(StatType.TOP_5_PONTOS).invoke(gm)).toString());

        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;


        x0 = 2;
        y0 = 7;
        x1 = 0;
        y1 = 5;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(10, gm.getCurrentTeamID());
        assertEquals(1, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;

        x0 = 3;
        y0 = 0;
        x1 = 6;
        y1 = 3;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(20, gm.getCurrentTeamID());
        assertEquals(2, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;

        x0 = 3;
        y0 = 7;
        x1 = 2;
        y1 = 6;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(10, gm.getCurrentTeamID());
        assertEquals(3, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;

        x0 = 1;
        y0 = 0;
        x1 = 1;
        y1 = 3;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(20, gm.getCurrentTeamID());
        assertEquals(4, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;


        x0 = 0;
        y0 = 5;
        x1 = 2;
        y1 = 3;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(10, gm.getCurrentTeamID());
        assertEquals(5, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;;

        x0 = 0;
        y0 = 2;
        x1 = 2;
        y1 = 4;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(20, gm.getCurrentTeamID());
        assertEquals(6, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;

        x0 = 6;
        y0 = 7;
        x1 = 7;
        y1 = 6;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(10, gm.getCurrentTeamID());
        assertEquals(7, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;

        x0 = 2;
        y0 = 4;
        x1 = 4;
        y1 = 2;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(20, gm.getCurrentTeamID());
        assertEquals(8, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;

        x0 = 2;
        y0 = 6;
        x1 = 0;
        y1 = 4;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertEquals(10, gm.getCurrentTeamID());
        assertEquals(9, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());
        pos = (pos==5)?0:pos+1;
        assertFalse(gm.gameOver());

        x0 = 1;
        y0 = 3;
        x1 = 1;
        y1 = 5;
        assertTrue(gm.move(x0, y0, x1, y1));
        assertTrue(gm.gameOver());
        assertEquals(20, gm.getCurrentTeamID());
        assertEquals(10, gm.getGameStatus().getConsecutivePlays());
        assertEquals("Joker/"+nameTypePieces[pos],pieces.get("8").getPieceNameType());

        x0 = 2;
        y0 = 3;
        x1 = 4;
        y1 = 5;
        assertFalse(gm.move(x0, y0, x1, y1));
        assertTrue(gm.gameOver());



    }
    @Test
    public void undo() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        gm.loadGame(new File("test-files/8x8.txt"));
        int x0;
        int y0;
        int x1;
        int y1;

        assertEquals("Rainha",gm.getGameStatus().getTheBoard().getAllPieces().get("8").getPieceNameType().split("/")[1]);
        assertEquals(0, gm.getGameStatus().getRoundCounter());
        x0 = 0;
        y0 = 0;
        x1 = 0;
        y1 = 1;

        assertTrue(gm.move(x0, y0, x1, y1));

        gm.undo();

        assertEquals("Rainha",gm.getGameStatus().getTheBoard().getAllPieces().get("8").getPieceNameType().split("/")[1]);
        assertEquals(0, gm.getGameStatus().getRoundCounter());


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
        boolean isInGame = gm.getGameStatus().getTheBoard().getAllPieces().get("5").isInGame();
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
        String[] arr = {"(0,7) -> 1000", "(5,2) -> 3", "(7,3) -> 3", "(4,7) -> 3"};
        for (int i =0; i<4; i++){
            assertEquals(arr[i], cmps.get(i).toString());
        }
    }
    @Test
    public void readAndSaveFiles() throws IOException, InvalidGameInputException {
        GameManager gm = new GameManager();
        File file = new File("test-files/4x4.txt");
        gm.loadGame(file);
        gm.move(0, 0, 0,1);
        gm.move(0, 9, 0,8);
        gm.undo();
        gm.undo();
        gm.saveGame(file);
        file = new File("test-files/6x6.txt");
        gm.loadGame(file);
        gm.move(0, 0, 0,1);
        gm.move(0, 9, 0,8);
        gm.undo();
        gm.undo();
        gm.saveGame(file);
    }

}
