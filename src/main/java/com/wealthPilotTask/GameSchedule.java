package com.wealthPilotTask;

import org.apache.commons.lang3.tuple.Pair;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class GameSchedule {

    private WealthPilotLeague wealthPilotLeague;

    GameSchedule(WealthPilotLeague wealthPilotLeague){
        this.wealthPilotLeague=wealthPilotLeague;
    }

    public List<Match> assignTeamMatches(){
        List<Team> listOfTeams=wealthPilotLeague.getListOfTeams();

        List<Match> matchsList=new ArrayList<>();
        for (int i = 0; i < listOfTeams.size() ; i++) {
            for (int j = i; j < listOfTeams.size() ; j++) {
                if(i!=j) {
                    Match match= new Match(listOfTeams.get(i),listOfTeams.get(j), null);
                    matchsList.add(match);
                }
            }
        }
        Collections.shuffle(matchsList);
        return matchsList;
    }

    public void displayLeagueMatches(List<Match> listOfTeamMatches, LocalDate startDate ){
        List<Match> leagueOneTeamMatches = assignDateAndTimeToMatches( listOfTeamMatches, startDate );

        for (int i = 0; i < leagueOneTeamMatches.size(); i++) {
            System.out.println("Game "+ (i + 1 ) +":  " +leagueOneTeamMatches.get(i).getLocalDateTime() + "   " +  leagueOneTeamMatches.get(i).getTeam1().getName()  +"  vs  " + leagueOneTeamMatches.get(i).getTeam2().getName());
        }
    }

    private List<Match> assignDateAndTimeToMatches(List<Match> listOfMatches,LocalDate startDate){

        for (Match match:listOfMatches) {
            match.setLocalDateTime(calculateGameDate(startDate));
            startDate = startDate.plusWeeks(1);
        }

        return listOfMatches;

    }

    private LocalDateTime calculateGameDate(LocalDate currentDate){
        LocalDate nextSaturday = currentDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        LocalTime gameTime = LocalTime.of(17, 0);

        return nextSaturday.atTime(gameTime);
    }

    public void displayAsMuchMatchesInOneDaySchedule(LocalDate startDate){
        Map<LocalDateTime, List<Match>> mapsOfLeague=scheduleAsMuchMatchesInOneDay(startDate);

        for (LocalDateTime dateTime : mapsOfLeague.keySet()) {
            List<Match> matches = mapsOfLeague.get(dateTime);
            System.out.println("Match Day " +dateTime.toLocalDate());
            for (Match match : matches) {
                System.out.println( match.getTeam1().getName() + " vs " + match.getTeam2().getName());
            }
            System.out.println();
        }
    }

    private  Map<LocalDateTime, List<Match>> scheduleAsMuchMatchesInOneDay(LocalDate startDate) {
        Map<LocalDateTime, List<Match>> mapsOfLeague=new HashMap<>();
        List<Team> listOfTeams=wealthPilotLeague.getListOfTeams();
        List<Match> listOfMatches=new ArrayList<>();

        int numberOfTeams = listOfTeams.size();

        if (numberOfTeams % 2 != 0) {
            throw new IllegalArgumentException("The number of teams must be even.");
        }

        int totalMatchDays = numberOfTeams - 1;

        for (int day = 1; day <= totalMatchDays; day++) {
            LocalDateTime dateTime=calculateGameDate(startDate);

            for (int i = 0; i < numberOfTeams / 2; i++) {
                Team team1 = listOfTeams.get(i);
                Team team2 = listOfTeams.get(numberOfTeams - 1 - i);
                listOfMatches.add(new Match(team1,team2,dateTime));
            }

            mapsOfLeague.put(dateTime,listOfMatches);
            listOfMatches=new ArrayList<>();
            startDate = startDate.plusWeeks(1);
            rotateTeams(listOfTeams);
        }

        return mapsOfLeague;
    }

    private void rotateTeams(List<Team> teams) {
        Team lastTeam = teams.get(teams.size() - 1);
        teams.remove(lastTeam);
        teams.add(1, lastTeam);
    }
}
