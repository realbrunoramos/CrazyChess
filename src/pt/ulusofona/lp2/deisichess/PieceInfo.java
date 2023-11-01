package pt.ulusofona.lp2.deisichess;

public class PieceInfo {
    String id;

    String typeChessPiece;
    String team;
    String name;
    String png;
    String coordinatesX;
    String coordinatesY;
    String status;
    boolean inGame;


    PieceInfo(String id, String typeChessPiece, String team, String name, String png, String coordinatesX, String coordinatesY) {
        this.id = id;
        this.typeChessPiece = typeChessPiece;
        this.team = team;
        this.name = name;
        this.png = png;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
        this.status = "em jogo";
        this.inGame = true;
    }

    public void setCoordinateX(String x) {
        this.coordinatesX = x;
    }

    public void setCoordinateY(String y) {
        this.coordinatesY = y;
    }

    public String getId() {
        return id;
    }

    public String getTypeChessPiece() {
        return typeChessPiece;
    }

    public String getTeam() {
        return team;
    }
    boolean validMove(int x1, int y1){
        int x0 = Integer.parseInt(coordinatesX);
        int y0 = Integer.parseInt(coordinatesY);
        return new MoveOfTypePiece(typeChessPiece, x0, y0, x1, y1).moveValid();
    }
    public String getName() {
        return name;
    }

    public String getX() {
        return coordinatesX;
    }

    public String getY() {
        return coordinatesY;
    }

    public String getStatus() {
        return status;
    }
    public String getPng(){
        return png;
    }
    public void captured() {
        status = "capturado";
        coordinatesX = "";
        coordinatesY = "";
        inGame = false;
    }

    public boolean isInGame() {
        return inGame;
    }

    @Override
    public String toString() {
        return id + " | " + typeChessPiece + " | " + team + " | " + name ;
    }

}
