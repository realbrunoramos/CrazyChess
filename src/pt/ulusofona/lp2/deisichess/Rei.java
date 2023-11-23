package pt.ulusofona.lp2.deisichess;

public class Rei extends Piece {
    Rei(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 1000;
        this.pieceNameType = "Rei";
        this.moveOfTypePiece = new MoveOfTypePiece(typeChessPiece);
    }
}
