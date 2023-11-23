package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> historicMoves = new ArrayList<>();
        historicMoves.add("1");
        historicMoves.add("2");
        historicMoves.add("3");
        int last = historicMoves.size()-1;
        historicMoves.remove(last--);
        System.out.println(historicMoves.get(last));
    }

}
