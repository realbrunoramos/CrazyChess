package pt.ulusofona.lp2.deisichess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class GameManager {
    boolean haveBackUp;
    int boardDimension;
    int numPieces;
    GameStatus gameStatus;
    public String[] nameTypePieces = {"Rainha", "Ponei Mágico", "Padre da Vila", "TorreHor", "TorreVert", "Homer Simpson"};
    public ArrayList<String> pieceInfoSectionLines;
    public ArrayList<String> boardSectionLines;

    public void getStarted(){
        boardDimension = 0;
        numPieces = 0;
        haveBackUp = false;
        gameStatus = new GameStatus();
        pieceInfoSectionLines = new ArrayList<>();
        boardSectionLines = new ArrayList<>();
    }
//TODO alterar o mapa do ficheiro
    public GameManager() {
    }

    public void saveGame(File file) throws IOException{
        TeamStatistic[] teamStatistics = gameStatus.getTeamStatistics();
        pieceInfoSectionLines.subList(numPieces+2, pieceInfoSectionLines.size()).clear();
        ArrayList<String> atualBoard = gameStatus.getTheBoard().getBoardMapStr();
        int pos = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (int i = 0; i< numPieces+boardDimension+2; i++) {
                if(i<numPieces+2){
                    writer.write(pieceInfoSectionLines.get(i));
                }else{
                    writer.write(atualBoard.get(pos));
                    pos++;
                }
                writer.newLine();
            }
            ArrayList<String> historic = gameStatus.getHistoricRoundDetails();
            for (int i = 0; i<historic.size(); i++){
                writer.write(historic.get(i));
                if (i<historic.size()-1){
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error adding content to file: " + e.getMessage());
        }

    }

    public void attributingPieces() throws InvalidGameInputException {
        String inicialMsgMais = "DADOS A MAIS (Esperava: ";
        String inicialMsgMenos = "DADOS A MENOS (Esperava: ";

        int startPieceInfos = 2;
        int startBoardSection = numPieces+2;
        int endBoardSection = startBoardSection+boardDimension;

        Board theBoard = gameStatus.getTheBoard();
        int roundCounter = gameStatus.getRoundCounter();
        int y = 0;
        for (int line = startPieceInfos; line<endBoardSection; line++){
            if (line<startBoardSection){
                String[] array = pieceInfoSectionLines.get(line).split(":");
                if (array.length>4){
                    throw new InvalidGameInputException(line+1, inicialMsgMais+"4 ; Obtive: "+array.length+")");
                } else if(array.length<4){
                    throw new InvalidGameInputException(line+1, inicialMsgMenos+"4 ; Obtive: "+array.length+")");
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
                        int indexNameTypePiece = roundCounter % 6;
                        piece.setPieceNameType("Joker/"+nameTypePieces[indexNameTypePiece]);
                        piece.sendFakeTypePiece(indexNameTypePiece+1+"");
                        theBoard.putAllPieces(id, piece);
                    }
                }
            } else if (!haveBackUp){
                String[] array = boardSectionLines.get(y).split(":");
                //TODO
                theBoard.addBoardMapLine(array);
                for (int x = 0 ; x<array.length; x++){
                    if (array.length>boardDimension){
                        throw new InvalidGameInputException(line+1, inicialMsgMais+boardDimension+" ; Obtive: "+array.length+")");
                    } else if(array.length<boardDimension){
                        throw new InvalidGameInputException(line+1, inicialMsgMenos+boardDimension+" ; Obtive: "+array.length+")");
                    }
                    Piece piece = theBoard.allPieces.get(array[x]);
                    if (piece!=null){
                        piece.setInGame();
                        piece.setCoordinateX(x);
                        piece.setCoordinateY(y);
                    }
                }
                y++;
            } else {
                break;
            }

        }
        if (haveBackUp){
            theBoard.getBoardMap().clear();
            for (int y2 = 0; y2<boardDimension; y2++){
                String[] array = boardSectionLines.get(y2).split(":");
                theBoard.addBoardMapLine(array);
                for (int x = 0 ; x<array.length; x++){
                    Piece piece = theBoard.allPieces.get(array[x]);
                    if (piece!=null){
                        piece.setInGame();
                        piece.setCoordinateX(x);
                        piece.setCoordinateY(y2);
                    }
                }
            }
        }


    }
    public Map<String,String> customizeBoard(){
        return new HashMap<>();
    }
    public void loadGame(File file) throws InvalidGameInputException, IOException{
        getStarted();
        ArrayList<String> allDataLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String lineContent;

            while ((lineContent = br.readLine()) != null) {
                allDataLines.add(lineContent);
            }
            boardDimension = Integer.parseInt(allDataLines.get(0));
            numPieces = Integer.parseInt(allDataLines.get(1));

            int startBoardSection = numPieces+2;
            int endBoardSection = startBoardSection+boardDimension;

            for (int l = 0; l<startBoardSection; l++){
                pieceInfoSectionLines.add(allDataLines.get(l));
            }

            haveBackUp = allDataLines.size() > endBoardSection;

            if (haveBackUp){
                String moveDetails = "";
                for (int i = endBoardSection; i < allDataLines.size(); i++){
                    moveDetails = allDataLines.get(i);
                    gameStatus.addAllRoundDetailsFromTxt(moveDetails);
                }
                gameStatus.upDateStatus(moveDetails);
                String[] parts = moveDetails.split("@");
                String[] map = parts[1].split("-");
                boardSectionLines.addAll(Arrays.asList(map));
            } else {
                for (int l = startBoardSection; l<endBoardSection; l++){
                    boardSectionLines.add(allDataLines.get(l));
                }
            }

            attributingPieces();
            gameStatus.addRoundDetails();
        }
    }
    public int getBoardSize() {
        return boardDimension;
    }
    public void undo(){
        gameStatus.undoMove();
        try {
            attributingPieces();
        } catch (InvalidGameInputException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Comparable> getHints(int x, int y){
        ArrayList<Comparable> cmp = new ArrayList<>();
        Board boardMap = gameStatus.getTheBoard();
        Piece movingPiece = boardMap.getAllPieces().get(boardMap.getBoardMap().get(y)[x]);
        //TODO
        if (movingPiece!=null){
            for (int y1 = 0; y1 < boardDimension; y1++) {
                for (int x1 = 0; x1 < boardDimension; x1++) {

                    if (movingPiece.validMove(x1, y1)){
                        int points = gameStatus.movePointsSimulation(x, y, x1, y1);
                        if (points >= 0) {
                            cmp.add(points);
                        }
                    }
                }
            }
            return cmp;
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

        Piece pieceOrigin = theBoard.allPieces.get(theBoard.getBoardMap().get(y0)[x0]);

        if (pieceOrigin!=null){
            if (pieceOrigin.getTypeChessPiece().equals("6") && gameStatus.getRoundCounter()%3==0){
                teamStatistics[current].incInvalidMoves();
                return false;
            }
        }
        String originSquareTeam = pieceOrigin==null?"":pieceOrigin.getTeam()+"";//retorna "" se o quadrado estiver vazio

        if (originSquareTeam.equals(gameStatus.getCurrentTeam()+"") && (pieceOrigin != null && pieceOrigin.validMove(x1, y1))){
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
            for (Piece piece : theBoard.allPieces.values()){
                int indexNameTypePiece = gameStatus.getRoundCounter() % 6;

                if (piece.getPieceNameType().split("/")[0].equals("Joker")){
                    piece.setPieceNameType("Joker/"+nameTypePieces[indexNameTypePiece]);
                    ((Joker)piece).sendFakeTypePiece(indexNameTypePiece+1+"");
                }
            }
            if(current == 1){
                gameStatus.setCurrentTeam(10);
            }else{
                gameStatus.setCurrentTeam(20);
            }
            gameStatus.addRoundDetails();
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

        if (piece.typeChessPiece.equals("6") && gameStatus.getRoundCounter() % 3 == 0){
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

        Board theBoard = gameStatus.getTheBoard();
        boolean blackKingInGame = theBoard.blackKingInGame();
        boolean whiteKingInGame = theBoard.whiteKingInGame();
        if (!blackKingInGame || !whiteKingInGame){
            return true;
        }
        theBoard.setDraw((theBoard.captureOccurred(numPieces) && gameStatus.getConsecutivePlays() == 10));
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
