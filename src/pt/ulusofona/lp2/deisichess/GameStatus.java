package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;
import java.util.HashMap;

import static pt.ulusofona.lp2.deisichess.MoveAction.*;

public class GameStatus {
    public String[] nameTypePieces = {"Rainha", "Ponei Mágico", "Padre da Vila", "TorreHor", "TorreVert", "Homer Simpson"};
    public ArrayList<String> historicRoundDetails;
    public ArrayList<String> historicRoundMap;
    public TeamStatistic[] teamStatistics;
    Board theBoard;
    int currentTeam;
    int consecutivePlays;
    int roundCounter;

    int historicPos;

    public String getLastRoundDetails(){
        return historicRoundDetails.get(historicRoundDetails.size()-1);
    }



    public GameStatus() {
        teamStatistics = new TeamStatistic[2];
        teamStatistics[0] = new TeamStatistic("10");
        teamStatistics[1] = new TeamStatistic("20");
        historicRoundDetails = new ArrayList<>();
        historicRoundMap = new ArrayList<>();
        currentTeam = 10;
        roundCounter = 0;
        consecutivePlays = 0;
        theBoard = new Board();
        historicPos=-1;
    }

    public void incConsecutivePlays() {
        this.consecutivePlays++;
    }
    public void incRoundCounter() {
        this.roundCounter++;
    }

    public void setCurrentTeam(int currentTeam) {
        this.currentTeam = currentTeam;
    }
    public void setConsecutivePlays(int consecutivePlays) {
        this.consecutivePlays = consecutivePlays;
    }

    public Board getTheBoard() {
        return theBoard;
    }

    public int getCurrentTeam() {
        return currentTeam;
    }

    public int getConsecutivePlays() {
        return consecutivePlays;
    }

    public int getRoundCounter() {
        return roundCounter;
    }

    public void jokerFaces(Joker joker){
        int indexNameTypePiece = roundCounter % 6;
        joker.setPieceNameType("Joker/"+nameTypePieces[indexNameTypePiece]);
        joker.changeMoveOfTypePiece(indexNameTypePiece+1+"");
    }
    public void upDateStatus(String status) {
        //status:  currentTeam+"|"+roundCounter +"|"+ consecutivePlays +"@"+teamStatistics[0]+"@"+teamStatistics[1]
        ArrayList<String[]> board = theBoard.getBoardMap();
        for (int y=0; y<board.size(); y++){
            for (int x=0; x<board.size(); x++){
                Piece piece = theBoard.allPieces.get(board.get(y)[x]);
                if (piece!=null){
                    if (piece.getTypeChessPiece().equals("7")){
                        jokerFaces((Joker)piece);
                    }
                    piece.setInGame();
                    piece.setCoordinateX(x);
                    piece.setCoordinateY(y);
                }
            }
        }
        if(status!=null){

            String[] parts = status.split("@");
            String[] subParts = parts[0].split("\\|");

            currentTeam = Integer.parseInt(subParts[0]);
            roundCounter = Integer.parseInt(subParts[1]);
            consecutivePlays = Integer.parseInt(subParts[2]);
            //------------------------

            String[] blackStatistics = parts[1].split("\\|");
            String[] whiteStatistics = parts[2].split("\\|");

            for (int i = 0; i < 5; i++) {
                String white = whiteStatistics[i];
                String black = blackStatistics[i];
                switch (i) {
                    case 0: {
                        teamStatistics[1].setTeam(white);
                        teamStatistics[0].setTeam(black);
                    }
                    break;
                    case 1: {
                        teamStatistics[1].setValidMoves(Integer.parseInt(white));
                        teamStatistics[0].setValidMoves(Integer.parseInt(black));
                    }
                    break;
                    case 2: {
                        teamStatistics[1].setInvalidMoves(Integer.parseInt(white));
                        teamStatistics[0].setInvalidMoves(Integer.parseInt(black));
                    }
                    break;
                    case 3: {
                        teamStatistics[1].setTotalPoints(Integer.parseInt(white));
                        teamStatistics[0].setTotalPoints(Integer.parseInt(black));
                    }
                    break;
                    case 4: {
                        teamStatistics[1].setCaptures(Integer.parseInt(white));
                        teamStatistics[0].setCaptures(Integer.parseInt(black));
                    }
                }
            }
        }


    }

    public void addRoundHistoric(){
        historicRoundDetails.add(currentTeam+"|"+roundCounter +"|"+ consecutivePlays +"@"+teamStatistics[0]+"@"+teamStatistics[1]);
        ArrayList<String[]> cloneBoard = new ArrayList<>(theBoard.getBoardMap());
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< cloneBoard.size(); i++){
            String resultado = String.join(":", cloneBoard.get(i));
            sb.append(resultado);
            if (i< cloneBoard.size()-1){
                sb.append("-");
            }

        }
        historicRoundMap.add(sb+"");
        historicPos++;
    }

    public void undoMove(){
        if (historicPos>0) {
            historicRoundMap.remove(historicPos);
            historicRoundDetails.remove(historicPos);
            historicPos--;
            String[] lines = historicRoundMap.get(historicPos).split("-");
            ArrayList<String[]> pastBoard = new ArrayList<>();
            for (String line : lines){
                String[] arr = line.split(":");
                pastBoard.add(arr);
            }

            String pastMove = historicRoundDetails.get(historicPos);

            theBoard.setBoardMap(pastBoard);
            upDateStatus(pastMove);

        } else {
            String pastMove = historicRoundDetails.get(0);
            String[] lines = historicRoundMap.get(0).split("-");
            ArrayList<String[]> pastBoard = new ArrayList<>();
            for (String line : lines){
                String[] arr = line.split(":");
                pastBoard.add(arr);
            }
            theBoard.setBoardMap(pastBoard);
            upDateStatus(pastMove);
        }
    }

    private boolean pieceOnTheWay(String typePiece, int x0, int y0, int x1, int y1){
        int vertical = Math.abs(y0 - y1);
        int horizontal = Math.abs(x0 - x1);
        boolean positiveDecline = (x0-x1)*(y0-y1)<0;

        int minX = Math.min(x0, x1);
        int maxX = Math.max(x0, x1);
        int minY = Math.min(y0, y1);
        int maxY = Math.max(y0, y1);
        ArrayList<String[]> boardMap = theBoard.getBoardMap();
        switch (typePiece){
            case "1" : {
                if (horizontal==0){
                    for (int y = minY+1; y<maxY; y++){
                        if(!boardMap.get(y)[x0].equals("0")){
                            return true;
                        }
                    }
                } else if(vertical==0){
                    for (int x = minX+1; x<maxX; x++){
                        if(!boardMap.get(y0)[x].equals("0")){
                            return true;
                        }
                    }
                } else if (horizontal==vertical){
                    while (x0!=x1-1&&y0!=y1-1){
                        x0 = x0>x1?x0-1:x0+1;
                        y0 = y0>y1?y0-1:y0+1;
                        if(!boardMap.get(y0)[x0].equals("0")){
                            return true;
                        }
                    }
                }
                return false;
            }
            case "2" : {
                int cx = x0>x1?x0-1:x0+1;
                int cy = y0>y1?y0-1:y0+1;
                boolean way1;
                boolean way2;
                if (positiveDecline){
                    way1 = !boardMap.get(cy)[cx - 1].equals("0") || !boardMap.get(cy - 1)[cx - 1].equals("0") || !boardMap.get(cy - 1)[cx].equals("0");
                    way2 = !boardMap.get(cy)[cx + 1].equals("0") || !boardMap.get(cy + 1)[cx + 1].equals("0") || !boardMap.get(cy + 1)[cx].equals("0");
                }
                else {
                    way1 = !boardMap.get(cy-1)[cx].equals("0") || !boardMap.get(cy-1)[cx+1].equals("0") || !boardMap.get(cy)[cx+1].equals("0");
                    way2 = !boardMap.get(cy)[cx-1].equals("0") || !boardMap.get(cy+1)[cx-1].equals("0") || !boardMap.get(cy+1)[cx].equals("0");
                }
                return way1 && way2;
            }
            case "3" : {
                while (x0!=x1&&y0!=y1){
                    x0 = x0>x1-1?x0-1:x0+1;
                    y0 = y0>y1-1?y0-1:y0+1;
                    if(!boardMap.get(y0)[x0].equals("0")){
                        return true;
                    }
                }
                return false;
            }
            case "4" : {
                for (int x = minX+1; x<maxX; x++){
                    if(!boardMap.get(y0)[x].equals("0")){
                        return true;
                    }
                }
                return false;
            }
            case "5" : {
                for (int y = minY+1; y<maxY; y++){
                    if(!boardMap.get(y)[x0].equals("0")){
                        return true;
                    }
                }
                return false;
            }
            default: return false;
        }
    }
    MoveAction moveSituation(int x0, int y0, int x1, int y1){
        ArrayList<String[]> boardMap = theBoard.getBoardMap();

        HashMap<String, Piece> allPieces = theBoard.getAllPieces();

        String originSquare = boardMap.get(y0)[x0];
        String destinSquare = boardMap.get(y1)[x1];

        Piece movingPiece = allPieces.get(originSquare);
        Piece steppedPiece = allPieces.get(destinSquare);

        if (pieceOnTheWay(movingPiece.getTypeChessPiece(), x0, y0, x1, y1)){
            return PIECE_ON_THE_WAY;
        }
        if (steppedPiece == null){
            theBoard.changeMapSquare(originSquare, x1, y1);
            theBoard.changeMapSquare("0", x0, y0);

            return TO_FREE_SQUARE;
        } else {
            if (steppedPiece.getTeam() == currentTeam){
                return TO_OWN_TEAM_PIECE_SQUARE;
            } else {
                if (steppedPiece.getTypeChessPiece().equals("1") && movingPiece.getTypeChessPiece().equals("1")){
                    return QUEEN_KILLS_QUEEN;
                }
                theBoard.changeMapSquare(originSquare, x1, y1);
                theBoard.changeMapSquare("0", x0, y0);
                theBoard.setCaptured(destinSquare);

                teamStatistics[currentTeam/10-1].addPoints(steppedPiece.getPoints());
                return TO_OPPONENT_PIECE_SQUARE;
            }
        }
    }
    int movePointsSimulation(int x0, int y0, int x1, int y1){
        ArrayList<String[]> boardMap = theBoard.getBoardMap();
        HashMap<String, Piece> allPieces = theBoard.getAllPieces();
        String originSquare = boardMap.get(y0)[x0];
        String destinSquare = boardMap.get(y1)[x1];
        Piece movingPiece = allPieces.get(originSquare);
        Piece steppedPiece = allPieces.get(destinSquare);

        if (pieceOnTheWay(movingPiece.getTypeChessPiece(), x0, y0, x1, y1)){
            return -1;
        }
        if (steppedPiece == null){
            return 0;
        } else {
            if (steppedPiece.getTeam() == currentTeam){
                return -1;
            } else {
                if (steppedPiece.getTypeChessPiece().equals("1") && movingPiece.getTypeChessPiece().equals("1")){
                    return -1;
                }
                return steppedPiece.getPoints();
            }
        }
    }

    public ArrayList<String> getHistoricRoundDetails() {
        return historicRoundDetails;
    }

    public TeamStatistic[] getTeamStatistics() {
        return teamStatistics;
    }
}
