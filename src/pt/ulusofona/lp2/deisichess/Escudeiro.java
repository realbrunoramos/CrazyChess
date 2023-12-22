package pt.ulusofona.lp2.deisichess;

public class Escudeiro extends Piece{
    Escudeiro(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 4;
        this.pieceNameType = "Escudeiro";
    }

    @Override
    protected String pieceInfoStr() {
        String base = id + " | " + pieceNameType + " | " + points + " | " + team + " | " + name;
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
        return (horizontal == 0 && vertical <= 2) || (vertical == 0 && horizontal <= 2) || (horizontal == vertical && vertical <= 2);
    }
}
