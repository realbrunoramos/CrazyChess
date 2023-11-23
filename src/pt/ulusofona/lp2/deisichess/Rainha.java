package pt.ulusofona.lp2.deisichess;

public class Rainha extends Piece {
    Rainha(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 8;
        this.pieceNameType = "Rainha";
        this.moveOfTypePiece = new MoveOfTypePiece(typeChessPiece);
    }
}
