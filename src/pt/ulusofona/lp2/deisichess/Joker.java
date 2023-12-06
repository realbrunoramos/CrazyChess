package pt.ulusofona.lp2.deisichess;

public class Joker extends Piece {
    private String[] nameTypePieces = {"Rainha", "Ponei Mágico", "Padre da Vila", "TorreHor", "TorreVert", "Homer Simpson"};
    private String fakeTypePiece;
    Joker(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 4;
        this.pieceNameType = "Joker";
    }
    public void changeMoveOfTypePiece(String fakeTypePiece) {
        moveOfTypePiece = new MoveOfTypePiece(fakeTypePiece);
        this.fakeTypePiece = fakeTypePiece;
        pieceNameType = "Joker/"+nameTypePieces[Integer.parseInt(fakeTypePiece)-1];
    }

    public String getFakeTypePiece() {
        return fakeTypePiece;
    }
}
