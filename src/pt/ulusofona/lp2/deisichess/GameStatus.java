package pt.ulusofona.lp2.deisichess;

import java.util.ArrayList;

public class GameStatus {
    public ArrayList<String> historicMoves ;
    public TeamStatistic[] teamStatistics;
    public GameStatus() {
        teamStatistics = new TeamStatistic[2];
        teamStatistics[0] = new TeamStatistic("Pretas");
        teamStatistics[1] = new TeamStatistic("Brancas");
        historicMoves = new ArrayList<>();
    }

    public void addHistoricMoves(String boardMapStr){
        historicMoves.add(boardMapStr+"@"+teamStatistics[0]+"@"+teamStatistics[1]);
    }
    public String getUndoMove(){
        int last = Math.max(0, historicMoves.size()-1);
        System.out.println(historicMoves.size());
        if (last > 0){
            historicMoves.remove(last--);
        }
        String lastMove = historicMoves.get(last);
        String[] parts = lastMove.split("@");
        String[] whiteStatistics = parts[2].split("\\|");
        String[] blackStatistics = parts[1].split("\\|");
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0 : {
                    teamStatistics[1].setTeam(whiteStatistics[i]);
                    teamStatistics[0].setTeam(blackStatistics[i]);
                }
                    break;
                case 1 : {
                    teamStatistics[1].setValidMoves(Integer.parseInt(whiteStatistics[i]));
                    teamStatistics[0].setValidMoves(Integer.parseInt(blackStatistics[i]));
                }
                    break;
                case 2 : {
                    teamStatistics[1].setInvalidMoves(Integer.parseInt(whiteStatistics[i]));
                    teamStatistics[0].setInvalidMoves(Integer.parseInt(blackStatistics[i]));
                }
                    break;
                case 3 : {
                    teamStatistics[1].setTotalPoints(Integer.parseInt(whiteStatistics[i]));
                    teamStatistics[0].setTotalPoints(Integer.parseInt(blackStatistics[i]));
                }
                    break;
                case 4 : {
                    teamStatistics[1].setCaptures(Integer.parseInt(whiteStatistics[i]));
                    teamStatistics[0].setCaptures(Integer.parseInt(blackStatistics[i]));
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
