package pt.ulusofona.lp2.deisichess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameManager {
    int currentTeam;
    int boardDimension;
    int numPieces;
    int consecutivePlays;
    TeamStatistic[] teamStatistics = new TeamStatistic[2];
    Board theBoard;

    public void resetAll(){
        currentTeam = 0;
        teamStatistics[0] = new TeamStatistic("Pretas");
        teamStatistics[1] = new TeamStatistic("Brancas");
        boardDimension = 0;
        numPieces = 0;
        consecutivePlays = 0;
        theBoard = new Board();
    }


    //- - - Construtores - - -
    public GameManager() {
    }

    //- - - Métodos - - -

    public boolean loadGame(File file){
        resetAll();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String lineContent;
            ArrayList<String> fileLinesContent = new ArrayList<>();
            while ((lineContent = br.readLine()) != null) {
                fileLinesContent.add(lineContent);
            }
            boardDimension = Integer.parseInt(fileLinesContent.get(0));
            numPieces = Integer.parseInt(fileLinesContent.get(1));
            for (int line = 2; line<numPieces+2; line++){
                String[] array = fileLinesContent.get(line).split(":");
                theBoard.putAllPieces(array[0], new PieceInfo(array[0], array[1], array[2], array[3], array[1]+array[2]+".png", null, null));
            }
            for (int line = numPieces+2; line<fileLinesContent.size(); line++){
                String[] array = fileLinesContent.get(line).split(":");
                theBoard.addBoardMap(array);
            }
            String[][] boardMap = theBoard.getBoard();
            for (int y = 0; y<boardDimension; y++){
                for (int x = 0; x<boardDimension; x++){
                    if (!boardMap[y][x].equals("0")){
                        theBoard.setCoordinates(boardMap[y][x], y, x);
                    }
                }
            }

        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public int getBoardSize() {
        return boardDimension;
    }
    public boolean move(int x0, int y0, int x1, int y1) {

        return true;
    }

    public String[] getSquareInfo(int x, int y) {
        String[] squares = new String[5];

        return squares;
    }

    public String[] getPieceInfo(int id) {
        String[] pieceInfo = new String[7];

        return pieceInfo;
    }

    public String getPieceInfoAsString(int id) {
        PieceInfo piece = theBoard.allPieces.get(id+"");
        if (piece!=null && piece.isInGame()){
            return piece.toString();
        }
        return null;
    }


    public int getCurrentTeamID() {
        return currentTeam;
    }

    public boolean gameOver() {

        return true;
    }

    public ArrayList<String> getGameResults() {
        ArrayList<String> gameResult = new ArrayList<>();

        gameResult.add("JOGO DE CRAZY CHESS");
        gameResult.add("Resultado: "); //TODO - colocar o reultado
        gameResult.add("---");

        gameResult.add("Equipa das Pretas");
        gameResult.add(""); //TODO - nr de capturas das peças pretas
        gameResult.add(""); //TODO - nr de jogadas válidas
        gameResult.add(""); //TODO - nr de tentativas invalidas

        gameResult.add("Equipa das Brancas");
        gameResult.add(""); //TODO - nr de capturas das peças brancas
        gameResult.add(""); //TODO - nr de jogadas válidas
        gameResult.add(""); //TODO - nr de tentativas invalidas

        return gameResult;
    }


    public JPanel getAuthorsPanel() {
        JPanel panel = new JPanel();


        return panel;
    }
}
