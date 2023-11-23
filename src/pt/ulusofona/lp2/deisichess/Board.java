package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;
import java.util.HashMap;

import static pt.ulusofona.lp2.deisichess.MoveAction.*;

public class Board {
    HashMap<String, Piece> allPieces;
    ArrayList<String[]> boardMap;
    boolean draw;
    public Board() {
        this.boardMap = new ArrayList<>();
        this.allPieces = new HashMap<>();
        draw = false;
    }

    public void setBoardMap(ArrayList<String[]> boardMap) {
        this.boardMap = boardMap;
    }

    public ArrayList<String> getBoardMapStr(){
        StringBuilder sb = new StringBuilder();
        ArrayList<String> result = new ArrayList<>();
        for (int y = 0; y <boardMap.size(); y++){
            for (int x = 0; x<boardMap.size(); x++){
                sb.append(boardMap.get(y)[x]);
                if(x<boardMap.size()-1){
                    sb.append(":");
                }
            }
            result.add(sb+"");
            sb = new StringBuilder();
        }
        return result;
    }
    void putAllPieces(String pieceId, Piece piece){
        this.allPieces.put(pieceId, piece);
    }
    void setCoordinates(String pieceId, int y, int x){
        this.allPieces.get(pieceId).setCoordinateX(x);
        this.allPieces.get(pieceId).setCoordinateY(y);
    }
    void setDraw(boolean condition){
        draw = condition;
    }
    boolean isDraw(){
        return draw;
    }
    void addBoardMap(String[] boardLine){
        this.boardMap.add(boardLine);
    }
    String[][] getBoard(){
        String[][] result = new String[boardMap.size()][];
        for (int i = 0; i < boardMap.size(); i++) {
            result[i] = boardMap.get(i);
        }
        return result;
    }
    int getNumBlacksInGame(){
        int sizeBoard = boardMap.size();
        int count = 0;
        for (int y = 0; y<sizeBoard; y++){
            for (int x = 0; x<sizeBoard; x++){
                Piece piece = allPieces.get(boardMap.get(y)[x]);
                if (piece!=null){
                    if (piece.getTeam() == 0) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    int getNumWhitesInGame(){
        int sizeBoard = boardMap.size();
        int count = 0;
        for (int y = 0; y<sizeBoard; y++){
            for (int x = 0; x<sizeBoard; x++){
                Piece piece = allPieces.get(boardMap.get(y)[x]);
                if (piece!=null){
                    if (piece.getTeam() == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    MoveAction stepOnOpponentPiece(int currentTeam, int x0, int y0, int x1, int y1){
        String steppedSquare = boardMap.get(y1)[x1];
        Piece piece = allPieces.get(steppedSquare);

        boolean result = !steppedSquare.equals("0"); //tem uma peça adversária
        if (result){
            allPieces.get(steppedSquare).captured();
        }
        allPieces.get(boardMap.get(y0)[x0]).setCoordinateX(x1);
        allPieces.get(boardMap.get(y0)[x0]).setCoordinateY(y1);

        boardMap.get(y1)[x1] = boardMap.get(y0)[x0];
        boardMap.get(y0)[x0] = "0";

        if (piece == null){
            return TO_FREE_SQUARE;
        } else {
            if (piece.getTeam() == currentTeam){
                return TO_SAME_PIECE_SQUARE;
            } else {
                return TO_OPPONENT_PIECE_SQUARE;
            }
        }
    }
    boolean captureOccurred(int numPieces){
        int piecesPerTeam = numPieces/2;
        return (piecesPerTeam-getNumBlacksInGame() != 0) || (piecesPerTeam-getNumWhitesInGame() != 0);
    }
}
