package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;
import java.util.HashMap;

import static pt.ulusofona.lp2.deisichess.MoveAction.*;

public class GameStatus {

    public ArrayList<String> historicRoundDetails;
    public TeamStatistic[] teamStatistics;
    Board theBoard;
    int currentTeam;
    int consecutivePlays;
    int roundCounter;


    public GameStatus() {
        teamStatistics = new TeamStatistic[2];
        teamStatistics[0] = new TeamStatistic("10");
        teamStatistics[1] = new TeamStatistic("20");
        historicRoundDetails = new ArrayList<>();
        currentTeam = 10;
        roundCounter = 0;
        consecutivePlays = 0;
        theBoard = new Board();
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

    public void setRoundCounter(int roundCounter) {
        this.roundCounter = roundCounter;
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


    public void addRoundDetails(){
        String boardMapStr = theBoard.getBoardMapStr().toString().replaceAll(", ", "-").replaceAll("\\[|]|", "");
        historicRoundDetails.add(currentTeam+"|"+roundCounter +"|"+ consecutivePlays +"@"+boardMapStr+"@"+teamStatistics[0]+"@"+teamStatistics[1]);
    }
    public void addAllRoundDetailsFromTxt(String moveDetails){
        historicRoundDetails.add(moveDetails);
    }

    public void upDateStatus(String status) {
//currentTeam+"|"+roundCounter +"|"+ consecutivePlays +"@"+boardMapStr+"@"+teamStatistics[0]+"@"+teamStatistics[1]
        String[] parts = status.split("@");
        String[] subParts = parts[0].split("\\|");

        currentTeam = Integer.parseInt(subParts[0]);
        roundCounter = Integer.parseInt(subParts[1]);
        consecutivePlays = Integer.parseInt(subParts[2]);

        //---------------------
        String[] boardLines = parts[1].split("-");
        for (String s : boardLines){
            String[] horizontalLine = s.split(":");
            theBoard.addBoardMapLine(horizontalLine);
        }
        //------------------------

        String[] blackStatistics = parts[2].split("\\|");
        String[] whiteStatistics = parts[3].split("\\|");

        for (int i = 0; i < 5; i++) {
            String white = whiteStatistics[i];
            String black = blackStatistics[i];
            switch (i) {
                case 0 : {
                    teamStatistics[1].setTeam(white);
                    teamStatistics[0].setTeam(black);
                }
                break;
                case 1 : {
                    teamStatistics[1].setValidMoves(Integer.parseInt(white));
                    teamStatistics[0].setValidMoves(Integer.parseInt(black));
                }
                break;
                case 2 : {
                    teamStatistics[1].setInvalidMoves(Integer.parseInt(white));
                    teamStatistics[0].setInvalidMoves(Integer.parseInt(black));
                }
                break;
                case 3 : {
                    teamStatistics[1].setTotalPoints(Integer.parseInt(white));
                    teamStatistics[0].setTotalPoints(Integer.parseInt(black));
                }
                break;
                case 4 : {
                    teamStatistics[1].setCaptures(Integer.parseInt(white));
                    teamStatistics[0].setCaptures(Integer.parseInt(black));
                }
            }
        }
    }
    public void undoMove(){
        int size = historicRoundDetails.size();
        int indLast = size-1;
        if (size>0) {
            if (indLast > 0) {
                historicRoundDetails.remove(indLast--);
                String pastMove = historicRoundDetails.get(indLast);
                upDateStatus(pastMove);
            } else {
                String pastMove = historicRoundDetails.get(0);
                if(pastMove!=null){
                    upDateStatus(pastMove);
                    historicRoundDetails.remove(0);
                }
            }
        }
    }

    private boolean pieceOnTheWay(String typePiece, int x0, int y0, int x1, int y1){
        int vertical = Math.abs(y0 - y1);
        int horizontal = Math.abs(x0 - x1);
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
                boolean way1 = !boardMap.get(cx-1)[cy].equals("0") || !boardMap.get(cx-1)[cy-1].equals("0") || !boardMap.get(cx)[cy-1].equals("0");
                boolean way2 = !boardMap.get(cx+1)[cy].equals("0") || !boardMap.get(cx+1)[cy+1].equals("0") || !boardMap.get(cx)[cy+1].equals("0");
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
            boardMap.get(y1)[x1] = originSquare;
            boardMap.get(y0)[x0] = "0";
            return TO_FREE_SQUARE;
        } else {
            if (steppedPiece.getTeam() == currentTeam){
                return TO_OWN_TEAM_PIECE_SQUARE;
            } else {
                if (steppedPiece.getTypeChessPiece().equals("1") && movingPiece.getTypeChessPiece().equals("1")){
                    return QUEEN_KILLS_QUEEN;
                }
                boardMap.get(y1)[x1] = originSquare;
                boardMap.get(y0)[x0] = "0";
                allPieces.get(destinSquare).captured();

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
