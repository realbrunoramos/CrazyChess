package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static pt.ulusofona.lp2.deisichess.MoveAction.*;

public class GameStatus {
    public ArrayList<String> historicRoundDetails;
    public ArrayList<String> historicRoundMap;
    public TeamStatistic[] teamStatistics;
    Board theBoard;
    int currentTeam;
    int consecutivePlays;
    int roundCounter;
    int historicPos;
    int boardSize;

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

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

    public void changeJokerBehavior(){
        int index = roundCounter%6;
        HashMap<String, Piece> pieces = theBoard.getAllPieces();
        for (Piece piece : pieces.values()){
            if (piece.getTypeChessPiece().equals("7")){
                ((Joker)piece).changeMoveOfTypePiece(index+1+"");
            }
        }

    }
    public void upDateStatus(String status) {
        //status:  currentTeam+"|"+roundCounter +"|"+ consecutivePlays +"@"+teamStatistics[0]+"@"+teamStatistics[1]
        // +"@"+id + "|" + captures + "|" + earnedPoints + "|" + validMoves + "|" + invalidMoves ...

        ArrayList<String[]> board = theBoard.getBoardMap();
        for (int y=0; y<boardSize; y++){
            for (int x=0; x<boardSize; x++){
                String pieceId = board.get(y)[x];
                Piece piece = theBoard.allPieces.get(pieceId);
                if (piece!=null){
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
            String[] piecesStatus = parts[3].split("-");
            for (String pieceStatus : piecesStatus) {
                String[] pieceParts = pieceStatus.split("\\|");
                String id = pieceParts[0];
                int captures = Integer.parseInt(pieceParts[1]);
                int earnedPoints = Integer.parseInt(pieceParts[2]);
                int validMoves = Integer.parseInt(pieceParts[3]);
                int invalidMoves = Integer.parseInt(pieceParts[4]);
                theBoard.getAllPieces().get(id).setCaptures(captures);
                theBoard.getAllPieces().get(id).setEarnedPoints(earnedPoints);
                theBoard.getAllPieces().get(id).setValidMoves(validMoves);
                theBoard.getAllPieces().get(id).setInvalidMoves(invalidMoves);
            }
        }
        changeJokerBehavior();
    }

    public void addRoundHistoric(){
        boolean bool = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (Piece piece : theBoard.getAllPieces().values()){
            if (bool){
                stringBuilder.append("-");
            }
            bool = true;
            stringBuilder.append(piece.getPieceStatisticsStr());
        }
        historicRoundDetails.add(currentTeam+"|"+roundCounter +"|"+ consecutivePlays +"@"+teamStatistics[0]+"@"+teamStatistics[1]+"@"+stringBuilder);

        ArrayList<String[]> boardMap = theBoard.getBoardMap();
        int size = boardMap.size();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< size; i++){
            String resultado = String.join(":", boardMap.get(i));
            sb.append(resultado);
            if (i< size-1){
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
        changeJokerBehavior();
    }

    private boolean pieceOnTheWay(String typePiece, int x0, int y0, int x1, int y1){
        int differenceX = x0-x1;
        int differenceY = y0-y1;
        int vertical = Math.abs(differenceY);
        int horizontal = Math.abs(differenceX);

        boolean positiveDecline = differenceX * differenceY < 0;

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
                    int x = x0;
                    int y = y0;
                    int mX = x0>x1?-1:1;
                    int mY = y0>y1?-1:1;

                    do{
                        if ((x+mX==x1) && (y+mY==y1)){
                            break;
                        }
                        x += mX;
                        y += mY;
                        if(!boardMap.get(y)[x].equals("0")){
                            return true;
                        }
                    }
                    while (x+mX!=x1 && y+mY!=y1);
                }
                return false;
            }
            case "2" : {
                int cx = x0>x1?x0-1:x0+1;
                int cy = y0>y1?y0-1:y0+1;
                boolean way1;
                boolean way2;
                if (positiveDecline){
                    way1 = !boardMap.get(cy)[cx-1].equals("0") || !boardMap.get(cy-1)[cx-1].equals("0") || !boardMap.get(cy-1)[cx].equals("0");
                    way2 = !boardMap.get(cy)[cx+1].equals("0") || !boardMap.get(cy+1)[cx+1].equals("0") || !boardMap.get(cy+1)[cx].equals("0");
                }
                else {
                    way1 = !boardMap.get(cy-1)[cx].equals("0") || !boardMap.get(cy-1)[cx+1].equals("0") || !boardMap.get(cy)[cx+1].equals("0");
                    way2 = !boardMap.get(cy)[cx-1].equals("0") || !boardMap.get(cy+1)[cx-1].equals("0") || !boardMap.get(cy+1)[cx].equals("0");
                }
                return way1 && way2;
            }
            case "3" : {
                int x = x0;
                int y = y0;
                int mX = x0>x1?-1:1;
                int mY = y0>y1?-1:1;
                do{
                    if ((x+mX==x1) && (y+mY==y1)){
                        break;
                    }
                    x += mX;
                    y += mY;
                    if(!boardMap.get(y)[x].equals("0")){
                        return true;
                    }
                }
                while (x+mX!=x1 && y+mY!=y1);
                return false;
            }
            case "4" : {
                if (horizontal <= 1){
                    return false;
                }
                for (int x = minX+1; x<maxX; x++){
                    if(!boardMap.get(y0)[x].equals("0")){
                        return true;
                    }
                }
                return false;
            }
            case "5" : {
                if (vertical <= 1){
                    return false;
                }
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

        String movingPieceType = movingPiece.getTypeChessPiece();
        Piece steppedPiece = allPieces.get(destinSquare);

        if(movingPieceType.equals("7")){
            movingPieceType = ((Joker)movingPiece).getFakeTypePiece();
        }

        if (movingPiece.getTypeChessPiece().equals("6") && roundCounter%3==0){
            return HOMER_SLEEPING;
        }

        if (pieceOnTheWay(movingPieceType, x0, y0, x1, y1)){
            return PIECE_ON_THE_WAY;
        }
        if (steppedPiece == null){
            theBoard.changeMapSquare(originSquare, x1, y1);
            theBoard.changeMapSquare("0", x0, y0);

            return TO_FREE_SQUARE;
        } else {
            String steppedPieceType = steppedPiece.getTypeChessPiece();
            if(steppedPieceType.equals("7")){
                steppedPieceType = ((Joker)steppedPiece).getFakeTypePiece();
            }
            if (steppedPiece.getTeam() == currentTeam){

                return TO_OWN_TEAM_PIECE_SQUARE;
            } else {
                if (steppedPieceType.equals("1") && movingPieceType.equals("1")){
                    return QUEEN_KILLS_QUEEN;
                }
                int opponentPoints = steppedPiece.getPoints();
                theBoard.changeMapSquare(originSquare, x1, y1);
                theBoard.changeMapSquare("0", x0, y0);
                theBoard.setCaptured(destinSquare);


                theBoard.incPieceCaptures(originSquare);

                theBoard.incPieceEarnedPoints(originSquare, opponentPoints);

                teamStatistics[currentTeam/10-1].addPoints(opponentPoints);
                return TO_OPPONENT_PIECE_SQUARE;
            }
        }
    }
    MoveAction moveSituationSimulation(int x0, int y0, int x1, int y1, int currentTeam){
        ArrayList<String[]> boardMap = theBoard.getBoardMap();

        HashMap<String, Piece> allPieces = theBoard.getAllPieces();

        String originSquare = boardMap.get(y0)[x0];
        String destinSquare = boardMap.get(y1)[x1];

        Piece movingPiece = allPieces.get(originSquare);

        String movingPieceType = movingPiece.getTypeChessPiece();
        Piece steppedPiece = allPieces.get(destinSquare);

        if(movingPieceType.equals("7")){
            movingPieceType = ((Joker)movingPiece).getFakeTypePiece();
        }

        if (movingPiece.getTypeChessPiece().equals("6") && roundCounter%3==0){
            return HOMER_SLEEPING;
        }
        if (pieceOnTheWay(movingPieceType, x0, y0, x1, y1)){
            return PIECE_ON_THE_WAY;
        }
        if (steppedPiece == null){

            return TO_FREE_SQUARE;
        } else {
            String steppedPieceType = steppedPiece.getTypeChessPiece();
            if(steppedPieceType.equals("7")){
                steppedPieceType = ((Joker)steppedPiece).getFakeTypePiece();
            }
            if (steppedPiece.getTeam() == currentTeam){

                return TO_OWN_TEAM_PIECE_SQUARE;
            } else {
                if (steppedPieceType.equals("1") && movingPieceType.equals("1")){
                    return QUEEN_KILLS_QUEEN;
                }
                return TO_OPPONENT_PIECE_SQUARE;
            }
        }
    }

    public TeamStatistic[] getTeamStatistics() {
        return teamStatistics;
    }
}
