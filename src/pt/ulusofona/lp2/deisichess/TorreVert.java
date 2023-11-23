package pt.ulusofona.lp2.deisichess;

public class TorreVert extends Piece {
    TorreVert(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 3;
        this.pieceNameType = "TorreVert";
        this.moveOfTypePiece = new MoveOfTypePiece(typeChessPiece);
    }
}