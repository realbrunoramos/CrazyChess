package pt.ulusofona.lp2.deisichess;

public class TorreVert extends Piece {
    TorreVert(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 3;
        this.pieceNameType = "TorreVert";
    }
    @Override
    public String pieceInfoStr() {
        String base = id + " | " + pieceNameType + " | " + (points==1000?"(infinito)":points) + " | " + team + " | " + name;
        if (inGame){
            return  base + " @ (" + coordinatesX + ", " + coordinatesY + ")";
        } else {
            return base + " @ (n/a)";
        }
    }

    @Override
    boolean isValidMove(int x0, int y0, int x1, int y1) {
        int horizontal = Math.abs(x0 - x1);
        return (horizontal == 0);
    }
}
