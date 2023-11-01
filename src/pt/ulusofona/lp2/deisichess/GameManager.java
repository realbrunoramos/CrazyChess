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
    boolean empate;

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
        boolean haveOpponentPiece;
        if (x1 > boardDimension || x1 < 0 || y1 > boardDimension || y1 < 0 ||
                x0 > boardDimension || x0 < 0 || y0 > boardDimension || y0 < 0){
            teamStatistics[currentTeam].incInvalidMoves();
            return false;
        }
        String[][] boardMap = theBoard.getBoard();

        PieceInfo pieceOrigin = theBoard.allPieces.get(boardMap[y0][x0]);
        String originSquare = pieceOrigin==null?"empty":pieceOrigin.getTeam();//retorna "empty" se o quadrado estiver vazio

        PieceInfo pieceDestin = theBoard.allPieces.get(boardMap[y1][x1]);
        String destinSquare = pieceDestin==null?"empty":pieceDestin.getTeam();
        assert pieceOrigin != null;
        if (originSquare.equals(currentTeam+"") && !destinSquare.equals(currentTeam+"") && pieceOrigin.validMove(x1, y1)){

            haveOpponentPiece = theBoard.stepOnOpponentPiece(x0, y0, x1, y1);
            if (haveOpponentPiece) {
                teamStatistics[currentTeam].incCaptures();
                consecutivePlays = 0;
            } else {
                consecutivePlays++;
            }
            teamStatistics[currentTeam].incValidMoves();
            currentTeam = currentTeam == 1?0:1;
        }else {
            teamStatistics[currentTeam].incInvalidMoves();
            return false;
        }
        return true;
    }

    public String[] getSquareInfo(int x, int y) {
        theBoard.updateStatus();
        String[] squares = new String[5];
        if ((boardDimension <= x) || (boardDimension <= y)) {
            return null;
        }
        String square = theBoard.getBoard()[y][x];
        if (!square.equals("0")){
            PieceInfo piece = theBoard.allPieces.get(square);
            squares[0] = piece.getId();
            squares[1] = piece.getTypeChessPiece();
            squares[2] = piece.getTeam();
            squares[3] = piece.getName();
            squares[4] = piece.getPng();
        } else {
            return new String [0];
        }
        return squares;
    }

    public String[] getPieceInfo(int id) {
        theBoard.updateStatus();
        String[] pieceInfo = new String[7];
        PieceInfo piece = theBoard.allPieces.get(id+"");
        if (piece!=null){
            pieceInfo[0] = piece.getId();
            pieceInfo[1] = piece.getTypeChessPiece();
            pieceInfo[2] = piece.getTeam();
            pieceInfo[3] = piece.getName();
            pieceInfo[4] = piece.getStatus();
            pieceInfo[5] = piece.isInGame()?piece.getX():"";
            pieceInfo[6] = piece.isInGame()?piece.getY():"";

        }
        return pieceInfo;
    }

    public String getPieceInfoAsString(int id) {
        theBoard.updateStatus();
        PieceInfo piece = theBoard.allPieces.get(id+"");
        String coord = " @ (" + piece.getX() + ", " + piece.getY() + ")";
        if (piece!=null){
            if (!piece.isInGame()){
                return piece + " @ (n/a)";
            } else {
                return piece + coord;
            }

        }
        return null;
    }

    public int getCurrentTeamID() {
        return currentTeam;
    }

    public boolean gameOver() {
        empate = false;

        int blacksInGame = theBoard.getNumBlacksInGame();
        int whitesInGame = theBoard.getNumWhitesInGame();

        if ((blacksInGame == 0 && whitesInGame > 0) || (whitesInGame == 0 && blacksInGame > 0)){
            return true;
        }
        empate = teamStatistics[0].getCaptures() > 0 || teamStatistics[1].getCaptures() > 0 || consecutivePlays >= 10;
        return empate;
    }

    public ArrayList<String> getGameResults() {
        ArrayList<String> gameResult = new ArrayList<>();
        int blackCaptures = teamStatistics[0].getCaptures();
        int blackValidMoves = teamStatistics[0].getValidMoves();
        int blackInvalidMoves = teamStatistics[0].getInvalidMoves();

        int whiteCaptures = teamStatistics[1].getCaptures();
        int whiteValidMoves = teamStatistics[1].getValidMoves();
        int whiteInvalidMoves = teamStatistics[1].getInvalidMoves();

        String result = "EMPATE";
        if (blackCaptures!=whiteCaptures && !empate){
            result = blackCaptures>whiteCaptures? "VENCERAM AS PRETAS" : "VENCERAM AS BRANCAS";
        }
        gameResult.add("JOGO DE CRAZY CHESS");
        gameResult.add("Resultado: "+result);
        gameResult.add("---");

        gameResult.add("Equipa das Pretas");
        gameResult.add(blackCaptures+"");
        gameResult.add(blackValidMoves+"");
        gameResult.add(blackInvalidMoves+"");

        gameResult.add("Equipa das Brancas");
        gameResult.add(whiteCaptures+"");
        gameResult.add(whiteValidMoves+"");
        gameResult.add(whiteInvalidMoves+"");

        return gameResult;
    }


    public JPanel getAuthorsPanel() {
        JPanel panel = new JPanel();

        try {
            BufferedImage image = ImageIO.read(new File("src/images/Foto_Burro.jpg"));
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return panel;
    }
}
