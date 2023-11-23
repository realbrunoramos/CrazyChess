package pt.ulusofona.lp2.deisichess;

public class TeamStatistic {
    private String team;
    private int validMoves;
    private int invalidMoves;
    private int captures;
    private int totalPoints;

    public TeamStatistic(String team) {
        validMoves = 0;
        invalidMoves = 0;
        captures = 0;
        totalPoints = 0;
        this.team = team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setValidMoves(int validMoves) {
        this.validMoves = validMoves;
    }

    public void setInvalidMoves(int invalidMoves) {
        this.invalidMoves = invalidMoves;
    }

    public void setCaptures(int captures) {
        this.captures = captures;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
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
        return team + "|" + validMoves + "|" + invalidMoves + "|" + totalPoints + "|" + captures;
    }
}
