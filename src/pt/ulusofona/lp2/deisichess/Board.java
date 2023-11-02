package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    HashMap<String, PieceInfo> allPieces;
    ArrayList<String[]> boardMap;

    public Board() {
        this.boardMap = new ArrayList<>();
        this.allPieces = new HashMap<>();
    }
    public void updateStatus(){
        ArrayList<String> except = new ArrayList<>();
        int boardSize = boardMap.size();
        for (int y = 0; y<boardSize; y++){
            for (int x = 0; x<boardSize; x++){
                String square = boardMap.get(y)[x];
                if (!square.equals("0")){
                    except.add(square);
                }
            }
        }
        for (String key : allPieces.keySet()){
            PieceInfo value = allPieces.get(key);
            if (!except.contains(key)){
                value.captured();
            }
        }
    }
    void putAllPieces(String pieceId, PieceInfo piece){
        this.allPieces.put(pieceId, piece);
    }
    void setCoordinates(String pieceId, int y, int x){
        this.allPieces.get(pieceId).setCoordinateX(x+"");
        this.allPieces.get(pieceId).setCoordinateY(y+"");
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
                PieceInfo piece = allPieces.get(boardMap.get(y)[x]);
                if (piece!=null){
                    if (piece.getTeam().equals("0")) {
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
                PieceInfo piece = allPieces.get(boardMap.get(y)[x]);
                if (piece!=null){
                    if (piece.getTeam().equals("1")) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    boolean stepOnOpponentPiece(int x0, int y0, int x1, int y1){
        //Retorna true se tiver peça adversária na casa que moveu
        //Retorna false se não tiver nada
        String steppedSquare = boardMap.get(y1)[x1];
        boolean result = !steppedSquare.equals("0"); //tem uma peça adversária
        if (result){
            allPieces.get(steppedSquare).captured();
        }
        allPieces.get(boardMap.get(y0)[x0]).setCoordinateX(x1+"");
        allPieces.get(boardMap.get(y0)[x0]).setCoordinateY(y1+"");

        boardMap.get(y1)[x1] = boardMap.get(y0)[x0];
        boardMap.get(y0)[x0] = "0";
        return result;
    }
    boolean captureOccurred(int numPieces){
        int piecesPerTeam = numPieces/2;
        return (piecesPerTeam-getNumBlacksInGame() != 0) || (piecesPerTeam-getNumWhitesInGame() != 0);
    }
}
