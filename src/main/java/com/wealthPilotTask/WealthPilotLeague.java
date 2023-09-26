package com.wealthPilotTask;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class WealthPilotLeague {
    private String league;
    private String country;

    @JsonProperty("teams")
    private List<Team> listOfTeams;

    public String getLeague() {
        return league;
    }

    public String getCountry() {
        return country;
    }

    public List<Team> getListOfTeams() {
        return listOfTeams;
    }
}