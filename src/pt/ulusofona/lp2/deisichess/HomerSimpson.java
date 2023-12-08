package pt.ulusofona.lp2.deisichess;

public class HomerSimpson extends Piece {
    private boolean sleep;
    HomerSimpson(String id, String typeChessPiece, int team, String name, String imagePath, int coordinatesX, int coordinatesY) {
        super(id, typeChessPiece, team, name, imagePath, coordinatesX, coordinatesY);
        this.points = 2;
        this.pieceNameType = "Homer Simpson";
        sleep = true;
    }

    public void setSleep(boolean sleep) {
        this.sleep = sleep;
    }

    @Override
    public String pieceInfoStr() {
        if (sleep){
            return "Doh! zzzzzz";
        }
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
        return (vertical == horizontal && vertical < 2);
    }
}
