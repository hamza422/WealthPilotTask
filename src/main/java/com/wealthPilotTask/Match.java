package com.wealthPilotTask;

import java.time.LocalDateTime;

public class Match {
    private Team team1;
    private Team team2;
    private LocalDateTime localDateTime;

    public Match(Team team1, Team team2, LocalDateTime localDateTime) {
        this.team1 = team1;
        this.team2 = team2;
        this.localDateTime=localDateTime;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime){
        this.localDateTime=localDateTime;
    }
}
