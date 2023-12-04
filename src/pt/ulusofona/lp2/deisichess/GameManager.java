package pt.ulusofona.lp2.deisichess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class GameManager {
    int boardDimension;
    int numPieces;
    GameStatus gameStatus;
    public ArrayList<String> pieceInfoSectionLines;

    public void getStarted(){
        boardDimension = 0;
        numPieces = 0;
        gameStatus = new GameStatus();
        pieceInfoSectionLines = new ArrayList<>();
    }

    public GameManager() {
    }

    public void saveGame(File file) throws IOException{

        pieceInfoSectionLines.subList(numPieces+2, pieceInfoSectionLines.size()).clear();
        ArrayList<String> atualBoard = gameStatus.getTheBoard().getBoardMapForTxt();
        String roundDetails = gameStatus.getLastRoundDetails();

        int pos = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int i = 0; i< numPieces+boardDimension+3; i++) {
                if(i<numPieces+2){
                    writer.write(pieceInfoSectionLines.get(i));
                }else if (i< numPieces+boardDimension+2){
                    writer.write(atualBoard.get(pos));
                    pos++;
                } else {
                    writer.write(roundDetails);
                }
                if (i< numPieces+boardDimension+2){
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error adding content to file: " + e.getMessage());
        }

    }

    public Map<String,String> customizeBoard(){
        HashMap<String, String> rs = new HashMap<>();
        return rs;
    }

    public void loadGame(File file) throws InvalidGameInputException, IOException{
        getStarted();
        ArrayList<String> fileLinesContent = new ArrayList<>();
        Board theBoard = gameStatus.getTheBoard();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String lineContent;
            while ((lineContent = br.readLine()) != null) {
                fileLinesContent.add(lineContent);
            }

            boardDimension = Integer.parseInt(fileLinesContent.get(0));
            numPieces = Integer.parseInt(fileLinesContent.get(1));

            int lineOfBoardSection = numPieces+2;

            for (int i=0; i<lineOfBoardSection; i++){
                pieceInfoSectionLines.add(fileLinesContent.get(i));
            }
            String mais = "DADOS A MAIS (Esperava: ";
            String menos = "DADOS A MENOS (Esperava: ";

            for (int line = 2; line<lineOfBoardSection; line++){
                String[] array = fileLinesContent.get(line).split(":");
                if (array.length>4){
                    throw new InvalidGameInputException(line+1, mais +"4 ; Obtive: "+array.length+")");
                } else if(array.length<4){
                    throw new InvalidGameInputException(line+1, menos +"4 ; Obtive: "+array.length+")");
                }
                String imagePath = array[1]+array[2]+".png";
                String id = array[0];
                String typeChessPiece = array[1];
                int team = Integer.parseInt(array[2]);
                String name = array[3];
                switch (typeChessPiece){
                    case "0" : theBoard.putAllPieces(id, new Rei(id, typeChessPiece, team, name, imagePath, 0, 0));
                        break;
                    case "1" : theBoard.putAllPieces(id, new Rainha(id, typeChessPiece, team, name, imagePath, 0, 0));
                        break;
                    case "2" : theBoard.putAllPieces(id, new PoneiMagico(id, typeChessPiece, team, name, imagePath, 0, 0));
                        break;
                    case "3" : theBoard.putAllPieces(id, new PadreDaVila(id, typeChessPiece, team, name, imagePath, 0, 0));
                        break;
                    case "4" : theBoard.putAllPieces(id, new TorreHor(id, typeChessPiece, team, name, imagePath, 0, 0));
                        break;
                    case "5" : theBoard.putAllPieces(id, new TorreVert(id, typeChessPiece, team, name, imagePath, 0, 0));
                        break;
                    case "6" : theBoard.putAllPieces(id, new HomerSimpson(id, typeChessPiece, team, name, imagePath, 0, 0));
                        break;
                    case "7" :{
                        Joker piece = new Joker(id, typeChessPiece, team, name, imagePath, 0, 0);
                        theBoard.putAllPieces(id, piece);
                    }
                }
            }
            for (int line = lineOfBoardSection; line<lineOfBoardSection+boardDimension; line++){
                String[] array = fileLinesContent.get(line).split(":");
                theBoard.addBoardMap(array);
                for (int x = 0 ; x<array.length; x++){
                    if (array.length>boardDimension){
                        throw new InvalidGameInputException(line+1, mais+boardDimension+" ; Obtive: "+array.length+")");
                    } else if(array.length<boardDimension){
                        throw new InvalidGameInputException(line+1, menos+boardDimension+" ; Obtive: "+array.length+")");
                    }
                }
            }
            String status = null;
            int endBoardSection = lineOfBoardSection+boardDimension;
            if (fileLinesContent.size() > endBoardSection){
                status = fileLinesContent.get(endBoardSection);
            }
            gameStatus.upDateStatus(status);
            gameStatus.addRoundHistoric();
        }
    }
    public int getBoardSize() {
        return boardDimension;
    }
    public void undo(){
        gameStatus.undoMove();
    }
    public boolean moveSimulation(int x0, int y0, int x1, int y1, int currentTeam) {

        Board theBoard = gameStatus.getTheBoard();

        MoveAction moveSituation;

        String originSquare = theBoard.getBoardMap().get(y0)[x0];

        Piece pieceOrigin = theBoard.allPieces.get(originSquare);

        String originSquareTeam = pieceOrigin==null?"":pieceOrigin.getTeam()+"";//retorna "" se o quadrado estiver vazio

        if (originSquareTeam.equals(currentTeam+"") && (pieceOrigin != null && pieceOrigin.isValidMove(x1, y1))){

            if (pieceOrigin.getTypeChessPiece().equals("6") && gameStatus.getRoundCounter()%3==0){
                return false;
            }
            moveSituation = gameStatus.moveSituationSimulation(x0, y0, x1, y1, currentTeam);
            if (moveSituation == MoveAction.TO_OPPONENT_PIECE_SQUARE) {
                return true;
            } else if (moveSituation == MoveAction.TO_OWN_TEAM_PIECE_SQUARE || moveSituation == MoveAction.QUEEN_KILLS_QUEEN
                    || moveSituation == MoveAction.PIECE_ON_THE_WAY) {

                return false;
            }

            return true;
        }
        else {
            return false;
        }
    }
    public List<Comparable> getHints(int x, int y){
        Board theBoard = gameStatus.getTheBoard();
        ArrayList<String[]> boardMap = gameStatus.getTheBoard().getBoardMap();

        Piece movingPiece = theBoard.getAllPieces().get(boardMap.get(y)[x]);

        ArrayList<Comparable> finalResult = new ArrayList<>();
        if (movingPiece!=null){
            if (movingPiece.getTeam()!=gameStatus.getCurrentTeam()){
                return null;
            }
            for (int y1 = 0; y1 < boardDimension; y1++) {
                for (int x1 = 0; x1 < boardDimension; x1++) {
                    if (moveSimulation(x, y, x1, y1, movingPiece.getTeam())) {
                        Piece steppedPiece = theBoard.getAllPieces().get(boardMap.get(y1)[x1]);
                        if (steppedPiece != null) {
                            finalResult.add(steppedPiece);
                        } else {
                            Piece empty = new Piece("0", "", 0, "", "", x1, y1);
                            empty.setPoints(0);
                            finalResult.add(empty);
                        }
                    }
                }
            }
            Collections.sort(finalResult);
            return finalResult;
        }
        return null;
    }
    public boolean move(int x0, int y0, int x1, int y1) {

        Board theBoard = gameStatus.getTheBoard();
        int current = (gameStatus.getCurrentTeam()/10)-1;
        TeamStatistic[] teamStatistics = gameStatus.getTeamStatistics();
        MoveAction moveSituation;
        if (x1 > boardDimension || x1 < 0 || y1 > boardDimension || y1 < 0 ||
                x0 > boardDimension || x0 < 0 || y0 > boardDimension || y0 < 0){
            teamStatistics[current].incInvalidMoves();
            return false;
        }
        String originSquare = theBoard.getBoardMap().get(y0)[x0];

        Piece pieceOrigin = theBoard.allPieces.get(originSquare);

        String originSquareTeam = pieceOrigin==null?"":pieceOrigin.getTeam()+"";//retorna "" se o quadrado estiver vazio

        if (originSquareTeam.equals(gameStatus.getCurrentTeam()+"") && (pieceOrigin != null && pieceOrigin.isValidMove(x1, y1))){

            if (pieceOrigin.getTypeChessPiece().equals("6") && gameStatus.getRoundCounter()%3==0){
                teamStatistics[current].incInvalidMoves();
                theBoard.incPieceInvalidMoves(originSquare);
                return false;
            }

            moveSituation = gameStatus.moveSituation(x0, y0, x1, y1);
            if (moveSituation == MoveAction.TO_OPPONENT_PIECE_SQUARE) {
                teamStatistics[current].incCaptures();
                gameStatus.setConsecutivePlays(0);
            } else if (moveSituation == MoveAction.TO_OWN_TEAM_PIECE_SQUARE || moveSituation == MoveAction.QUEEN_KILLS_QUEEN
                     || moveSituation == MoveAction.PIECE_ON_THE_WAY) {
                teamStatistics[current].incInvalidMoves();
                return false;
            }
            gameStatus.incConsecutivePlays();
            gameStatus.incRoundCounter();
            teamStatistics[current].incValidMoves();
            if(current == 1){
                gameStatus.setCurrentTeam(10);
            }else{
                gameStatus.setCurrentTeam(20);
            }
            gameStatus.addRoundHistoric();
            gameStatus.changeJokerBehavior();
            return true;
        }
        else {
            teamStatistics[current].incInvalidMoves();
            return false;
        }
    }

    public String[] getSquareInfo(int x, int y) {
        Board theBoard = gameStatus.getTheBoard();
        String[] squares = new String[5];
        if ((boardDimension <= x) || (boardDimension <= y)) {
            return null;
        }
        String square = theBoard.getBoardMap().get(y)[x];
        if (!square.equals("0")){
            Piece piece = theBoard.allPieces.get(square);
            squares[0] = piece.getId();
            squares[1] = piece.getTypeChessPiece();
            squares[2] = piece.getTeam()+"";
            squares[3] = piece.getName();
            squares[4] = piece.getImagePath();
        } else {
            return new String [0];
        }
        return squares;
    }
    public ArrayList<String> statisticLists(StatType statType){
        Board theBoard = gameStatus.getTheBoard();
        HashMap<String, Piece> allPieces = theBoard.getAllPieces();
        ArrayList<Pair<Double, String>> pairList = new ArrayList<>();
        switch (statType){
            case PECAS_MAIS_5_CAPTURAS -> {
                for (Piece piece : allPieces.values()){
                    if (piece.getCaptures()>5){
                        pairList.add(new Pair<>((double)piece.getCaptures(), piece.getTeam()==10?"PRETA":"BRANCA"+ ":" +piece.getName()+ ":" +piece.getCaptures()));

                    }
                }
                pairList.sort(Comparator.comparing(Pair::getFirst, Comparator.reverseOrder()));
                ArrayList<String> result = new ArrayList<>();
                for (int i = 0; i<pairList.size(); i++) {
                    result.add(pairList.get(i).getSecond());
                }
                return result;
            }
            case PECAS_MAIS_BARALHADAS -> {
                for (Piece piece : allPieces.values()){
                    int valid = piece.getValidMoves();
                    int invalid = piece.getInvalidMoves();
                    if (invalid>0) {
                        pairList.add(new Pair<>((double) (invalid - valid), piece.getTeam() + ":" + piece.getName() + ":" + valid + ":" + invalid));
                    }
                }
                pairList.sort(Comparator.comparing(Pair::getFirst, Comparator.reverseOrder()));
                ArrayList<String> result = new ArrayList<>();
                for (int i = 0; i<pairList.size(); i++) {
                    result.add(pairList.get(i).getSecond());
                }
                return result;
            }
            case TIPOS_CAPTURADOS -> {
                ArrayList<String> capturedNameTypes = new ArrayList<>();
                for (Piece piece : allPieces.values()){
                    if (!piece.isInGame() && !capturedNameTypes.contains(piece.getPieceNameType())){
                        capturedNameTypes.add(piece.getPieceNameType());
                    }
                }
                Collections.sort(capturedNameTypes);
                return capturedNameTypes;
            }
            case TOP_5_CAPTURAS -> {
                for (Piece piece : allPieces.values()){
                    if (piece.getCaptures()>0){
                        pairList.add(new Pair<>((double)piece.getCaptures(), piece.getName() + (piece.getTeam()==10?" (PRETA)":" (BRANCA)") +" fez " +piece.getCaptures() + " capturas"));
                    }
                }
                pairList.sort(Comparator.comparing(Pair::getFirst, Comparator.reverseOrder()));
                ArrayList<String> result = new ArrayList<>();
                for (int i = 0; i<(Math.min((pairList.size()), 5)); i++) {
                    result.add(pairList.get(i).getSecond());
                }
                return result;
            }
            case TOP_5_PONTOS -> {
                for (Piece piece : allPieces.values()){
                    if (piece.getEarnedPoints()>0){
                        pairList.add(new Pair<>((double)piece.getEarnedPoints(), piece.getName() + (piece.getTeam()==10?" (PRETA)":" (BRANCA)") + " tem " +piece.getEarnedPoints() + " pontos"));
                    }
                }
                pairList.sort((p1, p2) -> {
                    int compareDouble = Double.compare(p2.getFirst(), p1.getFirst());
                    if (compareDouble == 0) {
                        return p1.getSecond().compareTo(p2.getSecond());
                    }
                    return compareDouble;
                });

                ArrayList<String> result = new ArrayList<>();
                for (int i = 0; i<(Math.min((pairList.size()), 5)); i++) {
                    result.add(pairList.get(i).getSecond());
                }
                return result;
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }
    public String[] getPieceInfo(int id) {
        Board theBoard = gameStatus.getTheBoard();
        String[] pieceInfo = new String[7];
        Piece piece = theBoard.allPieces.get(id+"");
        if (piece!=null){
            pieceInfo[0] = piece.getId();
            pieceInfo[1] = piece.getTypeChessPiece();
            pieceInfo[2] = piece.getTeam()+"";
            pieceInfo[3] = piece.getName();
            pieceInfo[4] = piece.getStatus();
            pieceInfo[5] = piece.isInGame()?piece.getX()+"":"";
            pieceInfo[6] = piece.isInGame()?piece.getY()+"":"";
        }
        return pieceInfo;
    }

    public String getPieceInfoAsString(int id) {
        Board theBoard = gameStatus.getTheBoard();
        Piece piece = theBoard.allPieces.get(id+"");

        if (piece==null){
            return null;
        }

        if (piece.getTypeChessPiece().equals("6") && gameStatus.getRoundCounter() % 3 == 0){
            return "Doh! zzzzzz";
        }

        String base = id + " | " + piece.getPieceNameType() + " | " + (piece.getPoints()==1000?"(infinito)":piece.getPoints()) + " | " + piece.getTeam() + " | " + piece.getName();
        if (piece.isInGame()){
            return  base + " @ (" + piece.getX() + ", " + piece.getY() + ")";
        } else {
            return base + " @ (n/a)";
        }
    }

    public int getCurrentTeamID() {
        return gameStatus.getCurrentTeam();
    }

    public boolean gameOver() {
        int blacksInGame = gameStatus.theBoard.getNumBlacksInGame();
        int whitesInGame = gameStatus.theBoard.getNumWhitesInGame();
        Board theBoard = gameStatus.getTheBoard();
        boolean blackKingInGame = theBoard.blackKingInGame();
        boolean whiteKingInGame = theBoard.whiteKingInGame();
        if (!blackKingInGame || !whiteKingInGame){
            return true;
        }
        theBoard.setDraw((theBoard.captureOccurred(numPieces) && gameStatus.getConsecutivePlays() == 10) || (blacksInGame==1&&whitesInGame==1&&blackKingInGame&&whiteKingInGame));
        return theBoard.isDraw();
    }

    public ArrayList<String> getGameResults() {
        Board theBoard = gameStatus.getTheBoard();
        TeamStatistic[] teamStatistics = gameStatus.getTeamStatistics();

        boolean blackKingInGame = theBoard.blackKingInGame();
        boolean whiteKingInGame = theBoard.whiteKingInGame();
        ArrayList<String> gameResult = new ArrayList<>();
        int blackCaptures = teamStatistics[0].getCaptures();
        int blackValidMoves = teamStatistics[0].getValidMoves();
        int blackInvalidMoves = teamStatistics[0].getInvalidMoves();

        int whiteCaptures = teamStatistics[1].getCaptures();
        int whiteValidMoves = teamStatistics[1].getValidMoves();
        int whiteInvalidMoves = teamStatistics[1].getInvalidMoves();

        String result = "EMPATE";
        if (!theBoard.isDraw()){
            result = blackKingInGame && !whiteKingInGame? "VENCERAM AS PRETAS" : "VENCERAM AS BRANCAS";
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

            BufferedImage image = ImageIO.read(new File("src/images/crazy_Chess_credits.png"));
            JLabel label = new JLabel(new ImageIcon(image));
            panel.add(label);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return panel;
    }
}
