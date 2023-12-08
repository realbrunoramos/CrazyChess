package pt.ulusofona.lp2.deisichess;

public class Rei extends Piece {
    Rei(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 1000;
        this.pieceNameType = "Rei";
    }
    @Override
    public String pieceInfoStr() {
        String base = id + " | " + pieceNameType + " | " + "(infinito)" + " | " + team + " | " + name;
        if (inGame){
            return  base + " @ (" + coordinatesX + ", " + coordinatesY + ")";
        } else {
            return base + " @ (n/a)";
        }
    }

    @Override
    boolean isValidMove(int x0, int y0, int x1, int y1) {
        int vertical = Math.abs(y0 - y1);
        int horizontal = Math.abs(x0 - x1);
        return (horizontal < 2 && vertical < 2);
    }
}
