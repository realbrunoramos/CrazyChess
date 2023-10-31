package pt.ulusofona.lp2.deisichess;

public class TeamStatistic {
    String team;
    int validMoves;
    int invalidMoves;
    int captures;

    public TeamStatistic(String team) {
        this.validMoves = 0;
        this.invalidMoves = 0;
        this.captures = 0;
        this.team = team;
    }

    void incValidMoves(){

        validMoves++;
    }
    void incInvalidMoves(){
        invalidMoves++;
    }
    void incCaptures(){
        captures++;
    }
    int getValidMoves(){
        return validMoves;
    }
    int getInvalidMoves(){
        return invalidMoves;
    }
    int getCaptures(){
        return captures;
    }

    @Override
    public String toString() {
        return "team= " + team + "\n" +
                "validMoves= " + validMoves + "\n" +
                "invalidMoves= " + invalidMoves + "\n" +
                "captures= " + captures;
    }
}
