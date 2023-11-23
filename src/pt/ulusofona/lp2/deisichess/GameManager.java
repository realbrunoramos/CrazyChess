package pt.ulusofona.lp2.deisichess;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {
    int currentTeam;
    int boardDimension;
    int numPieces;
    int consecutivePlays;
    GameStatus gameStatus;
    public int turn;
    public int roundCounter;
    public String[] nameTypePieces = {"Rainha", "Ponei Mágico", "Padre da Vila", "TorreHor", "TorreVert", "Homer Simpson"};
    public ArrayList<String> fileLinesContent;
    Board theBoard;

    public void getStarted(){
        boardDimension = 0;
        numPieces = 0;
        currentTeam = 10;
        consecutivePlays = 0;
        roundCounter = 0;
        gameStatus = new GameStatus();
        theBoard = new Board();
        fileLinesContent = new ArrayList<>();
    }

    public GameManager() {
    }

    public void recoverStatus(String lastStatus){
        String[] parts = lastStatus.split("@");
        TeamStatistic[] teamStatistics = gameStatus.getTeamStatistics();
        String[] roundCounterConsecutivePlays = parts[0].split("\\|");
        roundCounter = Integer.parseInt(roundCounterConsecutivePlays[0]);
        consecutivePlays = Integer.parseInt(roundCounterConsecutivePlays[1]);
        String[] blackStatistics = parts[1].split("\\|");
        String[] whiteStatistics = parts[2].split("\\|");
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
    public Map<String,String> customizeBoard(){
        return new HashMap<>();
    }
    public void loadGame(File file) throws InvalidGameInputException, IOException{
        getStarted();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String lineContent;

            while ((lineContent = br.readLine()) != null) {
                fileLinesContent.add(lineContent);
            }
            boardDimension = Integer.parseInt(fileLinesContent.get(0));
            numPieces = Integer.parseInt(fileLinesContent.get(1));
            String inicialMsgMais = "DADOS A MAIS (Esperava: ";
            String inicialMsgMenos = "DADOS A MENOS (Esperava: ";
            for (int line = 2; line<numPieces+2; line++){
                String[] array = fileLinesContent.get(line).split(":");
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
            }

            int y = 0;
            int lineOfBoardSection = numPieces+2;
            for (int line = lineOfBoardSection; line<lineOfBoardSection+boardDimension; line++){
                String[] array = fileLinesContent.get(line).split(":");
                theBoard.addBoardMap(array);
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
            }
            if (fileLinesContent.size() > lineOfBoardSection+boardDimension){
                recoverStatus(fileLinesContent.get(lineOfBoardSection+boardDimension));
            }
        }
    }
    public void saveGame(File file) throws IOException{
        TeamStatistic[] teamStatistics = gameStatus.getTeamStatistics();

        ArrayList<String> atualBoard = theBoard.getBoardMapStr();
        int pos = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (int i = 0; i< fileLinesContent.size(); i++) {
                writer.newLine();
                if(i<numPieces+2){
                    writer.write(fileLinesContent.get(i));
                }else{
                    pos = i-numPieces+2;
                    writer.write(atualBoard.get(pos));
                }
            }
            writer.newLine();
            writer.write(roundCounter+"|"+consecutivePlays+"@"+teamStatistics[0]+"@"+teamStatistics[1]);

        } catch (IOException e) {
            System.err.println("Error adding content to file: " + e.getMessage());
        }

    }
    public int getBoardSize() {
        return boardDimension;
    }
    public void undo(){
        String[] boardLines = gameStatus.getUndoPlayAndSetTeamStatics().split("-");
        ArrayList<String[]> boardMap = new ArrayList<>();
        for (String s : boardLines){
            String[] horizontalLine = s.split(":");
            boardMap.add(horizontalLine);
        }
        theBoard.setBoardMap(boardMap);

    }
    public List<Comparable> getHints(int x, int y){
        return new ArrayList<>();
    }
    public boolean move(int x0, int y0, int x1, int y1) {
        gameStatus.addHistoricMoves(theBoard.getBoardMapStr());
        int current = (currentTeam/10)-1;
        TeamStatistic[] teamStatistics = gameStatus.getTeamStatistics();
        MoveAction haveOpponentPiece;
        if (x1 > boardDimension || x1 < 0 || y1 > boardDimension || y1 < 0 ||
                x0 > boardDimension || x0 < 0 || y0 > boardDimension || y0 < 0){
            teamStatistics[current].incInvalidMoves();
            return false;
        }

        String[][] boardMap = theBoard.getBoard();

        Piece pieceOrigin = theBoard.allPieces.get(boardMap[y0][x0]);
        String originSquare = pieceOrigin==null?"":pieceOrigin.getTeam()+"";//retorna "" se o quadrado estiver vazio

        if (originSquare.equals(currentTeam+"") && (pieceOrigin != null && pieceOrigin.validMove(x1, y1))){
            haveOpponentPiece = theBoard.stepOnOpponentPiece(currentTeam, x0, y0, x1, y1);
            if (haveOpponentPiece == MoveAction.TO_OPPONENT_PIECE_SQUARE) {
                teamStatistics[current].incCaptures();
                consecutivePlays = 0;
            } else if (haveOpponentPiece == MoveAction.TO_SAME_PIECE_SQUARE) {
                teamStatistics[current].incInvalidMoves();
                return false;
            }
            roundCounter++;
            consecutivePlays++;
            teamStatistics[current].incValidMoves();
            for (Piece piece : theBoard.allPieces.values()){
                int indexNameTypePiece = roundCounter % 6;

                if (piece.getPieceNameType().split("/")[0].equals("Joker")){
                    piece.setPieceNameType("Joker/"+nameTypePieces[indexNameTypePiece]);
                    ((Joker)piece).sendFakeTypePiece(indexNameTypePiece+1+"");
                }
            }
            currentTeam = current == 1?10:20;
        }else {
            teamStatistics[current].incInvalidMoves();
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
        Piece piece = theBoard.allPieces.get(id+"");

        if (piece==null){
            return null;
        }
        if (piece.typeChessPiece.equals("6")){
            return (roundCounter % 3 == 0? "Doh! zzzzzz" : piece.toString());
        } else {
            String base = id + " | " + piece.getPieceNameType() + " | " + (piece.getPoints()==1000?"(infinito)":piece.getPoints()) + " | " + piece.getTeam() + " | " + piece.getName();
            if (piece.isInGame()){
                return  base + " @ (" + piece.getX() + ", " + piece.getY() + ")";
            } else {
                return base + " @ (n/a)";
            }
        }
    }

    public int getCurrentTeamID() {
        return currentTeam;
    }

    public boolean gameOver() {
        int blacksInGame = theBoard.getNumBlacksInGame();
        int whitesInGame = theBoard.getNumWhitesInGame();
        if (blacksInGame == 0 || whitesInGame == 0){
            return true;
        }
        theBoard.setDraw((theBoard.captureOccurred(numPieces) && consecutivePlays == 10) || (blacksInGame==1 && whitesInGame==1));
        return theBoard.isDraw();
    }

    public ArrayList<String> getGameResults() {
        TeamStatistic[] teamStatistics = gameStatus.getTeamStatistics();

        int blacksInGame = theBoard.getNumBlacksInGame();
        int whitesInGame = theBoard.getNumWhitesInGame();

        ArrayList<String> gameResult = new ArrayList<>();
        int blackCaptures = teamStatistics[0].getCaptures();
        int blackValidMoves = teamStatistics[0].getValidMoves();
        int blackInvalidMoves = teamStatistics[0].getInvalidMoves();

        int whiteCaptures = teamStatistics[1].getCaptures();
        int whiteValidMoves = teamStatistics[1].getValidMoves();
        int whiteInvalidMoves = teamStatistics[1].getInvalidMoves();

        String result = "EMPATE";
        if (!theBoard.isDraw()){
            result = blacksInGame>whitesInGame? "VENCERAM AS PRETAS" : "VENCERAM AS BRANCAS";
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
