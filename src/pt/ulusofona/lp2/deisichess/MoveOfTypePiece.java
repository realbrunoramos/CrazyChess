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
        return switch (typePiece) {
            case "0" -> horizontal < 2 && vertical < 2;
            case "1" -> (horizontal == 0 && vertical <= 5) || (vertical == 0 && horizontal <= 5) || (horizontal == vertical && vertical <= 5);
            case "2" -> (vertical == 2 && horizontal == 2);
            case "3" -> (vertical == horizontal && vertical < 4);
            case "4" -> (vertical == 0);
            case "5" -> (horizontal == 0);
            case "6" -> (vertical == horizontal && vertical < 2);
            default -> false;
        };
    }
}