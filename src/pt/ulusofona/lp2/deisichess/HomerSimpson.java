package pt.ulusofona.lp2.deisichess;

public class HomerSimpson extends Piece {
    private boolean sleep;
    HomerSimpson(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 2;
        this.pieceNameType = "Homer Simpson";
        this.moveOfTypePiece = new MoveOfTypePiece(typeChessPiece);
        sleep = true;
    }

    public void setSleep(boolean sleep) {
        this.sleep = sleep;
    }

    @Override
    public String pieceInfoStr() {
        if (sleep){
            return "Doh! zzzzzz";
        }
        String base = id + " | " + pieceNameType + " | " + (points==1000?"(infinito)":points) + " | " + team + " | " + name;
        if (inGame){
            return  base + " @ (" + coordinatesX + ", " + coordinatesY + ")";
        } else {
            return base + " @ (n/a)";
        }
    }
}
