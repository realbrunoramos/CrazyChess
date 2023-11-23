package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class GameStatus {
    public ArrayList<String> historicMoves ;
    public TeamStatistic[] teamStatistics;
    int turnCounter;


    public GameStatus() {
        teamStatistics = new TeamStatistic[2];
        teamStatistics[0] = new TeamStatistic("Pretas");
        teamStatistics[1] = new TeamStatistic("Brancas");
        historicMoves = new ArrayList<>();
        turnCounter = 0;
    }

    public void addHistoricMoves(ArrayList<String> boardMap){
        String boardMapStr = boardMap.toString().replaceAll(", ", "-").replaceAll("\\[|]|", "");
        historicMoves.add(boardMapStr+"@"+teamStatistics[0]+"@"+teamStatistics[1]);
    }
    public String getUndoPlayAndSetTeamStatics(){
        int last = Math.max(0, historicMoves.size()-1);

        if (last > 0){
            historicMoves.remove(last--);
        }
        String lastMove = historicMoves.get(last);
        String[] parts = lastMove.split("@");
        String[] whiteStatistics = parts[2].split("\\|");
        String[] blackStatistics = parts[1].split("\\|");
        for (int i = 0; i < 5; i++) {
            String white = whiteStatistics[i];
            String black = blackStatistics[i];
            switch (i) {
                case 0 : {
                    teamStatistics[1].setTeam(white);
                    teamStatistics[0].setTeam(black);
                }
                    break;
                case 1 : {
                    teamStatistics[1].setValidMoves(Integer.parseInt(white));
                    teamStatistics[0].setValidMoves(Integer.parseInt(black));
                }
                    break;
                case 2 : {
                    teamStatistics[1].setInvalidMoves(Integer.parseInt(white));
                    teamStatistics[0].setInvalidMoves(Integer.parseInt(black));
                }
                    break;
                case 3 : {
                    teamStatistics[1].setTotalPoints(Integer.parseInt(white));
                    teamStatistics[0].setTotalPoints(Integer.parseInt(black));
                }
                    break;
                case 4 : {
                    teamStatistics[1].setCaptures(Integer.parseInt(white));
                    teamStatistics[0].setCaptures(Integer.parseInt(black));
                }
            }
        }
        return parts[0];
    }




    public ArrayList<String> getHistoricMoves() {
        return historicMoves;
    }

    public TeamStatistic[] getTeamStatistics() {
        return teamStatistics;
    }
}
