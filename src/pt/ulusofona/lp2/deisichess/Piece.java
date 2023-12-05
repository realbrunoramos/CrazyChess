package pt.ulusofona.lp2.deisichess;

public class Piece implements Comparable<Piece>{
    protected int points;
    protected String pieceNameType;
    final String capturedMsg = "capturado";
    final String inGameMsg = "em jogo";
    String id;
    String typeChessPiece;
    int team;
    String name;
    String imagePath;
    int coordinatesX;
    int coordinatesY;
    String status;
    boolean inGame;
    int captures;
    int validMoves;
    int invalidMoves;
    int earnedPoints;
    MoveOfTypePiece moveOfTypePiece;

    public int compareTo(Piece nextPiece){
        return Integer.compare(nextPiece.getPoints(), this.getPoints());
    }

    Piece(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        this.id = id;
        this.typeChessPiece = typeChessPiece;
        this.team = team;
        this.name = name;
        this.imagePath = imagePath;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;

        status = capturedMsg;
        inGame = false;

        captures = 0;
        validMoves = 0;
        invalidMoves = 0;
        earnedPoints = 0;
    }

    public int getCaptures() {
        return captures;
    }

    public int getValidMoves() {
        return validMoves;
    }

    public int getInvalidMoves() {
        return invalidMoves;
    }

    public int getEarnedPoints() {
        return earnedPoints;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCaptures(int captures) {
        this.captures = captures;
    }
    public void setValidMoves(int validMoves) {
        this.validMoves = validMoves;
    }
    public void setInvalidMoves(int invalidMoves) {
        this.invalidMoves = invalidMoves;
    }
    public void setEarnedPoints(int earnedPoints) {
        this.earnedPoints = earnedPoints;
    }

    public String getPieceStatisticsStr(){
        return id + "|" + captures + "|" + earnedPoints + "|" + validMoves + "|" + invalidMoves;
    }

    public void incCaptures() {
        this.captures++;
    }
    public void incValidMoves() {
        this.validMoves++;
    }
    public void incInvalidMoves() {
        this.invalidMoves++;
    }
    public void incEarnedPoints(int points) {
        this.earnedPoints+=points;
    }

    public String getPieceNameType() {
        return pieceNameType;
    }
    public void setPieceNameType(String pieceNameType) {
        this.pieceNameType = pieceNameType;
    }
    public int getPoints() {
        return points;
    }
    public void setInGame(){
        status = inGameMsg;
        inGame = true;
    }
    public void setCoordinateX(int x) {
        this.coordinatesX = x;
    }
    public void setCoordinateY(int y) {
        this.coordinatesY = y;
    }

    public String getId() {
        return id;
    }
    public String getTypeChessPiece() {
        return typeChessPiece;
    }
    public int getTeam() {
        return team;
    }
    boolean isValidMove(int x0, int y0, int x1, int y1){
        return moveOfTypePiece.validMove(x0, y0, x1, y1);
    }
    public String getName() {
        return name;
    }
    public int getX() {
        return coordinatesX;
    }
    public int getY() {
        return coordinatesY;
    }

    public String getStatus() {
        return status;
    }
    public String getImagePath(){
        return imagePath;
    }
    public void captured() {
        status = capturedMsg;
        inGame = false;
    }

    public boolean isInGame() {
        return inGame;
    }

    @Override
    public String toString() {
        return  "(" + coordinatesX + "," + coordinatesY + ") -> " + points;
    }

}
