package pt.ulusofona.lp2.deisichess;

public class PieceInfo {
    String id;
    String typeChessPiece;
    String team;
    String name;
    String imagePath;
    String coordinatesX;
    String coordinatesY;
    String status;
    boolean inGame;
    MoveOfTypePiece moveOfTypePiece;


    PieceInfo(String id, String typeChessPiece, String team, String name, String imagePath, String coordinatesX, String coordinatesY) {
        this.id = id;
        this.typeChessPiece = typeChessPiece;
        this.team = team;
        this.name = name;
        this.imagePath = imagePath;
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
        this.status = "em jogo";
        this.inGame = true;
        this.moveOfTypePiece = new MoveOfTypePiece(typeChessPiece);
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
        return moveOfTypePiece.validMove(x0, y0, x1, y1);
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
    public String getImagePath(){
        return imagePath;
    }
    public void captured() {
        status = "capturado";
        inGame = false;
    }

    public boolean isInGame() {
        return inGame;
    }

    @Override
    public String toString() {
        String base = id + " | " + typeChessPiece + " | " + team + " | " + name;
        if (inGame){
            return  base + " @ (" + coordinatesX + ", " + coordinatesY + ")";
        } else {
            return base + " @ (n/a)";
        }

    }

}
