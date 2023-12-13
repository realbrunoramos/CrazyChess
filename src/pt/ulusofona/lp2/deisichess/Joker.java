package pt.ulusofona.lp2.deisichess;

public class Joker extends Piece {
    private String[] nameTypePieces = {"Rainha", "Ponei Mágico", "Padre da Vila", "TorreHor", "TorreVert", "Homer Simpson"};
    private String fakeTypePiece;
    Joker(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 4;
        this.pieceNameType = "Joker";
    }

    @Override
    public String pieceInfoStr() {
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
        return switch (fakeTypePiece) {
            case "0" -> (horizontal < 2 && vertical < 2);
            case "1" -> (horizontal == 0 && vertical <= 5) || (vertical == 0 && horizontal <= 5) || (horizontal == vertical && vertical <= 5);
            case "2" -> (vertical == 2 && horizontal == 2);
            case "3" -> (vertical == horizontal && vertical < 4);
            case "4" -> (vertical == 0);
            case "5" -> (horizontal == 0);
            case "6" -> (vertical == horizontal && vertical < 2);
            default -> false;
        };
    }
    public void changeMoveOfTypePiece(String fakeTypePiece) {
        this.fakeTypePiece = fakeTypePiece;
        pieceNameType = "Joker/"+nameTypePieces[Integer.parseInt(fakeTypePiece)-1];
    }
    public String getFakeTypePiece() {
        return fakeTypePiece;
    }
}
