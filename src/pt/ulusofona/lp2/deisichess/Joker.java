package pt.ulusofona.lp2.deisichess;

public class Joker extends Piece {
    String fakeTypePiece;
    Joker(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 4;
        this.pieceNameType = "Joker";
    }

    public void setFakeTypePiece(String fakeTypePiece) {
        this.fakeTypePiece = fakeTypePiece;
        moveOfTypePiece = new MoveOfTypePiece(fakeTypePiece);
    }
}
