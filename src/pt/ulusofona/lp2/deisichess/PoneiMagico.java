package pt.ulusofona.lp2.deisichess;

public class PoneiMagico extends Piece {
    PoneiMagico(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 5;
        this.pieceNameType = "Ponei Mágico";
        this.moveOfTypePiece = new MoveOfTypePiece(typeChessPiece);
    }
}
