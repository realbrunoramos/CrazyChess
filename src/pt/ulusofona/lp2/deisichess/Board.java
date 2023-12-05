package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Board {
    HashMap<String, Piece> allPieces;
    ArrayList<String[]> boardMap;
    boolean draw;
    public Board() {
        this.boardMap = new ArrayList<>();
        this.allPieces = new HashMap<>();
        draw = false;
    }
    public void changeMapSquare(String str, int x, int y){
        Piece piece = allPieces.get(str);
        if (piece!=null){
            piece.setCoordinateX(x);
            piece.setCoordinateY(y);
        }
        boardMap.get(y)[x] = str;
    }
    public void setBoardMap (ArrayList<String[]> boardMap){
        this.boardMap = boardMap;
    }
    public ArrayList<String[]> getBoardMap() {
        return boardMap;
    }
    public HashMap<String, Piece> getAllPieces() {
        return allPieces;
    }
    public void setCaptured(String pieceId){
        Piece piece = allPieces.get(pieceId);
        if (piece!=null){
            piece.captured();
        }
    }
    public void incPieceValidMoves(String pieceId){
        Piece piece = allPieces.get(pieceId);
        if (piece!=null){
            piece.incValidMoves();
        }
    }
    public void incPieceInvalidMoves(String pieceId){
        Piece piece = allPieces.get(pieceId);
        if (piece!=null){
            piece.incInvalidMoves();
        }
    }
    public void incPieceCaptures(String pieceId){
        Piece piece = allPieces.get(pieceId);
        if (piece!=null){
            piece.incCaptures();
        }
    }
    public void incPieceEarnedPoints(String pieceId, int points){
        Piece piece = allPieces.get(pieceId);
        if (piece!=null){
            piece.incEarnedPoints(points);
        }
    }

    public ArrayList<String> getBoardMapForTxt(){
        int size = boardMap.size();
        StringBuilder sb = new StringBuilder();
        ArrayList<String> result = new ArrayList<>();
        for (int y = 0; y <size; y++){
            for (int x = 0; x<size; x++){
                sb.append(boardMap.get(y)[x]);
                if(x<size-1){
                    sb.append(":");
                }
            }
            result.add(sb+"");
            sb.delete(0, sb.length());
        }
        return result;
    }
    void addBoardMap(String[] boardLine){
        this.boardMap.add(boardLine);
    }

    void putAllPieces(String pieceId, Piece piece){
        this.allPieces.put(pieceId, piece);
    }

    void setDraw(boolean condition){
        draw = condition;
    }
    boolean isDraw(){
        return draw;
    }
    int getNumBlacksInGame(){
        int sizeBoard = boardMap.size();
        int count = 0;
        for (int y = 0; y<sizeBoard; y++){
            for (int x = 0; x<sizeBoard; x++){
                Piece piece = allPieces.get(boardMap.get(y)[x]);
                if (piece!=null){
                    if (piece.getTeam() == 10) {
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
                    if (piece.getTeam() == 20) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
    boolean blackKingInGame(){
        int sizeBoard = boardMap.size();
        for (String[] strings : boardMap) {
            for (int x = 0; x < sizeBoard; x++) {
                Piece piece = allPieces.get(strings[x]);
                if (piece != null) {
                    if (piece.getTeam() == 10 && piece.isInGame() && piece.getTypeChessPiece().equals("0")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    boolean whiteKingInGame(){
        int sizeBoard = boardMap.size();
        for (String[] strings : boardMap) {
            for (int x = 0; x < sizeBoard; x++) {
                Piece piece = allPieces.get(strings[x]);
                if (piece != null) {
                    if (piece.getTeam() == 20 && piece.isInGame() && piece.getTypeChessPiece().equals("0")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean captureOccurred(int numPieces){
        int piecesPerTeam = numPieces/2;
        return (piecesPerTeam-getNumBlacksInGame() != 0) || (piecesPerTeam-getNumWhitesInGame() != 0);
    }
}
