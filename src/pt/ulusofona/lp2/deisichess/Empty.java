package pt.ulusofona.lp2.deisichess;

public class Empty extends Piece{
    Empty(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 0;
        this.pieceNameType = "";
    }

    @Override
    public String pieceInfoStr() {
        return "";
    }

    @Override
    boolean isValidMove(int x0, int y0, int x1, int y1) {
        return false;
    }
}
