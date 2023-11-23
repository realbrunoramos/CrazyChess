package pt.ulusofona.lp2.deisichess;

public class HomerSimpson extends Piece {
    HomerSimpson(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 2;
        this.pieceNameType = "Homer Simpson";
        this.moveOfTypePiece = new MoveOfTypePiece(typeChessPiece);
    }
}
