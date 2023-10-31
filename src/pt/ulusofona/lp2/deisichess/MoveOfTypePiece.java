package pt.ulusofona.lp2.deisichess;

public class MoveOfTypePiece {
    boolean valid;
    public MoveOfTypePiece(String typePiece, int x0, int y0, int x1, int y1) {
        if (typePiece.equals("0")){
            valid = Math.abs(x0-x1) < 2 && Math.abs(y0-y1) < 2;
        }
    }
    public boolean moveValid(){
        return valid;
    }
}
