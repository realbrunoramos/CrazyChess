package pt.ulusofona.lp2.deisichess;

public class TorreHor extends Piece {
    TorreHor(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 3;
        this.pieceNameType = "TorreHor";
        this.moveOfTypePiece = new MoveOfTypePiece(typeChessPiece);
    }
}
