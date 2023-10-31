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
        boolean haveOpponentPiece;
        if (x1 > boardDimension || y1 > boardDimension || x0 > boardDimension || y0 > boardDimension){
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
        String[] pieceInfo = new String[7];
        PieceInfo piece = theBoard.allPieces.get(id+"");
        if (piece!=null && piece.isInGame()){
            pieceInfo[0] = piece.getId();
            pieceInfo[1] = piece.getTypeChessPiece();
            pieceInfo[2] = piece.getTeam();
            pieceInfo[3] = piece.getName();
            pieceInfo[4] = piece.getStatus();
            pieceInfo[5] = piece.getX();
            pieceInfo[6] = piece.getY();

        }
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

        int blacksInGame = theBoard.getNumBlacksInGame();
        int whitesInGame = theBoard.getNumWhitesInGame();

        if ((blacksInGame == 0 && whitesInGame > 0) || (whitesInGame == 0 && blacksInGame > 0)){
            return true;
        }
        return (teamStatistics[0].getCaptures() > 0 || teamStatistics[1].getCaptures() > 0)
                && blacksInGame == whitesInGame && consecutivePlays == 10;
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
        if (blackCaptures!=whiteCaptures){
            result = blackCaptures>whiteCaptures? "VENCERAM AS PRETAS" : "VENCERAM AS BRANCAS";
        }
        gameResult.add("JOGO DE CRAZY CHESS");
        gameResult.add("Resultado: "+result); //TODO - colocar o reultado
        gameResult.add("---");

        gameResult.add("Equipa das Pretas");
        gameResult.add(blackCaptures+""); //TODO - nr de capturas das peças pretas
        gameResult.add(blackValidMoves+""); //TODO - nr de jogadas válidas
        gameResult.add(blackInvalidMoves+""); //TODO - nr de tentativas invalidas

        gameResult.add("Equipa das Brancas");
        gameResult.add(whiteCaptures+""); //TODO - nr de capturas das peças brancas
        gameResult.add(whiteValidMoves+""); //TODO - nr de jogadas válidas
        gameResult.add(whiteInvalidMoves+""); //TODO - nr de tentativas invalidas

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
