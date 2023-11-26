package pt.ulusofona.lp2.deisichess;

public abstract class Piece {
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
    MoveOfTypePiece moveOfTypePiece;


    Piece(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        this.id = id;
        this.typeChessPiece = typeChessPiece;
        this.team = team;
        this.name = name;
        this.imagePath = imagePath;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
        this.status = capturedMsg;
        this.inGame = true;
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
    boolean validMove(int x1, int y1){
        return moveOfTypePiece.validMove(coordinatesX, coordinatesY, x1, y1);
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

}
