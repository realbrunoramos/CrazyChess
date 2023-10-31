//package pt.ulusofona.lp2.deisichess;
//
//
//import org.junit.jupiter.api.Test;
//import java.io.File;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class TestGameManager {
//    @Test
//    public void test1() {
//        GameManager gm = new GameManager();
//        gm.resetAll();
//        gm.loadGame(new File("4x4.txt"));
//        String[][] boardMap = gm.theBoard.getBoard();
//
//        String blackTeam = gm.teamStatistics[0].toString();
//        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 0\ninvalidMoves= 0\ncaptures= 0";
//
//        String[][] expected = {
//                {"0", "1", "0", "2"},
//                {"0", "0", "3", "0"},
//                {"0", "6", "0", "0"},
//                {"0", "5", "4", "0"}
//        };
//        for (int y = 0; y<4; y++){
//            for (int x = 0; x<4; x++){
//                assertEquals(expected[y][x], boardMap[y][x]);
//            }
//        }
//        assertEquals(expTeamStaticsStr, blackTeam);
//    }
//    @Test
//    public void test2() {
//        GameManager gm = new GameManager();
//        gm.resetAll();
//        gm.loadGame(new File("4x4.txt"));
//
//        String[][] boardMap;
//        gm.move(2, 1, 2, 2);
//        boardMap = gm.theBoard.getBoard();
//        String blackTeam = gm.teamStatistics[0].toString();
//        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 1\ninvalidMoves= 0\ncaptures= 0";
//        String[][] expected = {
//                {"0", "1", "0", "2"},
//                {"0", "0", "0", "0"},
//                {"0", "6", "3", "0"},
//                {"0", "5", "4", "0"}
//        };
//        for (int y = 0; y<4; y++){
//            for (int x = 0; x<4; x++){
//                assertEquals(expected[y][x], boardMap[y][x]);
//            }
//        }
//        assertEquals(expTeamStaticsStr, blackTeam);
//    }
//    @Test
//    public void test3() {
//        GameManager gm = new GameManager();
//        gm.resetAll();
//        gm.loadGame(new File("4x4.txt"));
//
//        String[][] boardMap;
//        gm.move(2, 1, 1, 2);
//        boardMap = gm.theBoard.getBoard();
//        String blackTeam = gm.teamStatistics[0].toString();
//        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 1\ninvalidMoves= 0\ncaptures= 1";
//        String[][] expected = {
//                {"0", "1", "0", "2"},
//                {"0", "0", "0", "0"},
//                {"0", "3", "0", "0"},
//                {"0", "5", "4", "0"}
//        };
//        for (int y = 0; y<4; y++){
//            for (int x = 0; x<4; x++){
//                assertEquals(expected[y][x], boardMap[y][x]);
//            }
//        }
//        assertEquals(expTeamStaticsStr, blackTeam);
//    }
//    @Test
//    public void test4() {
//        GameManager gm = new GameManager();
//        gm.resetAll();
//        gm.loadGame(new File("4x4.txt"));
//
//        String[][] boardMap;
//        gm.move(2, 1, 0, 1);
//        boardMap = gm.theBoard.getBoard();
//        String blackTeam = gm.teamStatistics[0].toString();
//        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 0\ninvalidMoves= 1\ncaptures= 0";
//        String[][] expected = {
//                {"0", "1", "0", "2"},
//                {"0", "0", "3", "0"},
//                {"0", "6", "0", "0"},
//                {"0", "5", "4", "0"}
//        };
//        for (int y = 0; y<4; y++){
//            for (int x = 0; x<4; x++){
//                assertEquals(expected[y][x], boardMap[y][x]);
//            }
//        }
//        assertEquals(expTeamStaticsStr, blackTeam);
//    }
//    @Test
//    public void test5() {
//        GameManager gm = new GameManager();
//        gm.resetAll();
//        gm.loadGame(new File("4x4-AlmostEnd.txt"));
//
//        String[][] boardMap;
//        gm.move(2, 1, 1, 2);
//        boardMap = gm.theBoard.getBoard();
//
//        String blackTeam = gm.teamStatistics[0].toString();
//        String expTeamStaticsStr = "team= Pretas\nvalidMoves= 1\ninvalidMoves= 0\ncaptures= 1";
//        String[][] expected = {
//                {"0", "0", "0", "0"},
//                {"0", "0", "0", "0"},
//                {"0", "3", "0", "0"},
//                {"0", "0", "0", "0"}
//        };
//        for (int y = 0; y<4; y++){
//            for (int x = 0; x<4; x++){
//                assertEquals(expected[y][x], boardMap[y][x]);
//            }
//        }
//        assertTrue(gm.gameOver());
//        assertEquals(expTeamStaticsStr, blackTeam);
//    }
//
//
//}
