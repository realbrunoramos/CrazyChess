package pt.ulusofona.lp2.deisichess;



//0- Rei

public class MoveOfTypePiece {
    String typePiece;

    public MoveOfTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }
    public boolean validMove(int x0, int y0, int x1, int y1){
        if (typePiece.equals("0")){
            return Math.abs(x0-x1) < 2 && Math.abs(y0-y1) < 2;
        }
        return false;
    }
}
