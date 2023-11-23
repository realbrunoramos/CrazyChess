package pt.ulusofona.lp2.deisichess;

//0- Rei

public class MoveOfTypePiece {
    String typePiece;

    public MoveOfTypePiece(String typePiece) {
        this.typePiece = typePiece;
    }
    public boolean validMove(int x0, int y0, int x1, int y1){
        int vertical = Math.abs(y0 - y1);
        int horizontal = Math.abs(x0 - x1);
        switch (typePiece){
            case "0": {
                return horizontal < 2 && vertical < 2;
            }
            case "1" : {
                return (x0 == x1 && vertical <= 5) || (y0 == y1 && horizontal <= 5);
            }
            case "2" : {
                return (vertical == 2 && horizontal == 2);
            }
            case "3" : {
                return (vertical == horizontal && vertical<4);
            }
            case "4" : {
                return (vertical == 0);
            }
            case "5" : {
                return (horizontal == 0);
            }
            case "6" : {
                return (vertical == horizontal && vertical==1);
            }
        }
        return false;
    }
}