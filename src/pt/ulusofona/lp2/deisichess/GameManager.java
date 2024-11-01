package pt.ulusofona.lp2.deisichess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class GameManager {
    private int boardSize;
    private int numPieces;
    private GameStatus gameStatus;
    private ArrayList<String> pieceDetailsSectionLines;

    public GameManager() {
    }
    public void getStarted(){
        boardSize = 0;
        numPieces = 0;
        gameStatus = new GameStatus();
        pieceDetailsSectionLines = new ArrayList<>();
    }

    public void saveGame(File file) throws IOException{

        pieceDetailsSectionLines.subList(numPieces+2, pieceDetailsSectionLines.size()).clear();
        ArrayList<String> atualBoard = gameStatus.getTheBoard().getBoardMapForTxt();
        String roundDetails = gameStatus.getLastRoundDetails();

        int pos = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int i = 0; i< numPieces+ boardSize +3; i++) {
                if(i<numPieces+2){
                    writer.write(pieceDetailsSectionLines.get(i));
                }else if (i< numPieces+ boardSize +2){
                    writer.write(atualBoard.get(pos));
                    pos++;
                } else {
                    writer.write(roundDetails);
                }
                if (i < numPieces+ boardSize +2){
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error adding content to file: " + e.getMessage());
        }

    }
    public Map<String,String> customizeBoard(){
        HashMap<String, String> rs = new HashMap<>();
        rs.put("title", "Olympus Chess");
        rs.put("imageBlackSquare", "black-box.png");
        rs.put("imageWhiteSquare", "white-box.png");
        rs.put("imageBackground", "background.png");
        rs.put("boardMarginTop", "100");
        rs.put("boardMarginBottom", "50");
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
            boardSize = Integer.parseInt(fileLinesContent.get(0));
            numPieces = Integer.parseInt(fileLinesContent.get(1));

            int lineOfBoardSection = numPieces+2;

            for (int i=0; i<lineOfBoardSection; i++){
                pieceDetailsSectionLines.add(fileLinesContent.get(i));
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
                    case "7" : theBoard.putAllPieces(id, new Joker(id, typeChessPiece, team, name, imagePath, 0, 0));
                        break;
                    case "8" : theBoard.putAllPieces(id, new Escudeiro(id, typeChessPiece, team, name, imagePath, 0, 0));

                }
            }
            for (int line = lineOfBoardSection; line<lineOfBoardSection+ boardSize; line++){
                String[] array = fileLinesContent.get(line).split(":");
                theBoard.addBoardMap(array);
                if (array.length> boardSize){
                    throw new InvalidGameInputException(line+1, mais+ boardSize +" ; Obtive: "+array.length+")");
                } else if(array.length< boardSize){
                    throw new InvalidGameInputException(line+1, menos+ boardSize +" ; Obtive: "+array.length+")");
                }
            }
            String status = null;
            int endBoardSection = lineOfBoardSection+ boardSize;
            if (fileLinesContent.size() > endBoardSection){
                status = fileLinesContent.get(endBoardSection);
            }
            gameStatus.upDateStatus(status);
            gameStatus.addRoundHistoric();
        }
    }
    public int getBoardSize() {
        return boardSize;
    }
    public void undo(){
        gameStatus.undoMove();
    }
    public boolean moveSimulation(int x0, int y0, int x1, int y1, int currentTeam) {
        Board theBoard = gameStatus.getTheBoard();

        MoveAction moveSituation;

        String originSquare = theBoard.getBoardMap().get(y0)[x0];

        Piece pieceOrigin = theBoard.getAllPieces().get(originSquare);

        String originSquareTeam = pieceOrigin==null?"":pieceOrigin.getTeam()+"";//retorna "" se o quadrado estiver vazio

        if (originSquareTeam.equals(currentTeam+"") && (pieceOrigin != null && pieceOrigin.isValidMove(x0, y0, x1, y1))){

            moveSituation = gameStatus.moveSituation(x0, y0, x1, y1, currentTeam, true);
            if (moveSituation == MoveAction.TO_OPPONENT_PIECE_SQUARE) {
                return true;
            } else if (moveSituation == MoveAction.TO_OWN_TEAM_PIECE_SQUARE || moveSituation == MoveAction.QUEEN_KILLS_QUEEN
                    || moveSituation == MoveAction.PIECE_ON_THE_WAY || moveSituation == MoveAction.HOMER_SLEEPING || moveSituation == MoveAction.UNDER_ESCUDEIRO_DEFENSE) {
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
            for (int y1 = 0; y1 < boardSize; y1++) {
                for (int x1 = 0; x1 < boardSize; x1++) {
                    if (moveSimulation(x, y, x1, y1, movingPiece.getTeam())) {
                        Piece steppedPiece = theBoard.getAllPieces().get(boardMap.get(y1)[x1]);
                        if (steppedPiece != null) {
                            finalResult.add(steppedPiece);
                        } else {
                            Rei empty = new Rei("0", "", 0, "", "", x1, y1);
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
        if (gameOver()){
            return false;
        }
        Board theBoard = gameStatus.getTheBoard();
        int current = (gameStatus.getCurrentTeam()/10)-1;
        TeamStatistic[] teamStatistics = gameStatus.getTeamStatistics();
        MoveAction moveSituation;
        if (x1 >= boardSize || x1 < 0 || y1 >= boardSize || y1 < 0 ||
                x0 >= boardSize || x0 < 0 || y0 >= boardSize || y0 < 0){
            teamStatistics[current].incInvalidMoves();
            return false;
        }
        ArrayList<String[]> boardMap = theBoard.getBoardMap();
        String originSquare = boardMap.get(y0)[x0];

        Piece pieceOrigin = theBoard.getAllPieces().get(originSquare);

        String originSquareTeam = pieceOrigin==null?"":pieceOrigin.getTeam()+"";//retorna "" se o quadrado estiver vazio

        if (originSquareTeam.equals(gameStatus.getCurrentTeam()+"") && (pieceOrigin != null && pieceOrigin.isValidMove(x0, y0, x1, y1))){
            moveSituation = gameStatus.moveSituation(x0, y0, x1, y1, 0, false);
            if (moveSituation == MoveAction.TO_OPPONENT_PIECE_SQUARE) {
                teamStatistics[current].incCaptures();
                theBoard.incPieceValidMoves(originSquare);
                gameStatus.setConsecutivePlays(0);
            } else if (moveSituation == MoveAction.TO_OWN_TEAM_PIECE_SQUARE || moveSituation == MoveAction.QUEEN_KILLS_QUEEN
                    || moveSituation == MoveAction.PIECE_ON_THE_WAY || moveSituation == MoveAction.HOMER_SLEEPING || moveSituation == MoveAction.UNDER_ESCUDEIRO_DEFENSE) {
                teamStatistics[current].incInvalidMoves();
                theBoard.incPieceInvalidMoves(originSquare);
                return false;
            }
            if (moveSituation == MoveAction.TO_FREE_SQUARE) {
                theBoard.incPieceValidMoves(originSquare);
                gameStatus.incConsecutivePlays();
            }
            if(current == 1){
                gameStatus.setCurrentTeam(10);
            }else{
                gameStatus.setCurrentTeam(20);
            }
            gameStatus.incRoundCounter();
            gameStatus.updateJokerAndHomerBehavior();
            teamStatistics[current].incValidMoves();

            gameStatus.addRoundHistoric();
            return true;
        }
        else {
            teamStatistics[current].incInvalidMoves();
            theBoard.incPieceInvalidMoves(originSquare);
            return false;
        }
    }
    public String[] getSquareInfo(int x, int y) {
        Board theBoard = gameStatus.getTheBoard();
        String[] squares = new String[5];
        if ((boardSize <= x) || (boardSize <= y)) {
            return null;
        }
        String square = theBoard.getBoardMap().get(y)[x];
        if (!square.equals("0")){
            Piece piece = theBoard.getAllPieces().get(square);
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
    public String[] getPieceInfo(int id) {
        Board theBoard = gameStatus.getTheBoard();
        String[] pieceInfo = new String[7];
        Piece piece = theBoard.getAllPieces().get(id+"");
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
        return gameStatus.pieceInfoAsStr(id);
    }
    public int getCurrentTeamID() {
        return gameStatus.getCurrentTeam();
    }
    public boolean gameOver() {
        int blacksInGame = gameStatus.getTheBoard().getNumBlacksInGame();
        int whitesInGame = gameStatus.getTheBoard().getNumWhitesInGame();
        Board theBoard = gameStatus.getTheBoard();
        boolean blackKingInGame = theBoard.blackKingInGame();
        boolean whiteKingInGame = theBoard.whiteKingInGame();
        if (!blackKingInGame || !whiteKingInGame){
            return true;
        }
        boolean exhaust = (theBoard.captureOccurred(numPieces) && gameStatus.getConsecutivePlays() >= 10);
        boolean onlyKings = (blacksInGame==1 && whitesInGame==1 );
        theBoard.setDraw(exhaust || onlyKings);
        return theBoard.isDraw();
    }
    public GameStatus getGameStatus() {
        return gameStatus;
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
