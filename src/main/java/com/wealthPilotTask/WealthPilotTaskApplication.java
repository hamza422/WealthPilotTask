package com.wealthPilotTask;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class WealthPilotTaskApplication implements CommandLineRunner {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WealthPilotTaskApplication.class);

	@Autowired
	private ResourceLoader resourceLoader;
	private final String FILENAME= "soccer_teams.json";

	private final LocalDate LEAGUE_ONE_STARTINGDATE= LocalDate.of(2019, 10, 3);



	public static void main(String[] args) {
		SpringApplication.run(WealthPilotTaskApplication.class,args);
	}

	@Override
	public void run(String... args) throws Exception {
		FileLoader fileLoader = new FileLoader(FILENAME, resourceLoader);
		WealthPilotLeague wealthPilotLeague=fileLoader.loadJsonFile();

		if(wealthPilotLeague!=null){
			GameSchedule gameSchedule=new GameSchedule(wealthPilotLeague);

			List<Match> listOfTeamMatches = gameSchedule.assignTeamMatches();

			System.out.println("\n \t \t \tLeague One Matches\n ");

			gameSchedule.displayLeagueMatches(listOfTeamMatches,LEAGUE_ONE_STARTINGDATE);

			LocalDate leagueOneLastMatchDate=listOfTeamMatches.get(listOfTeamMatches.size()-1).getLocalDateTime().toLocalDate();

			LocalDate leageTwoStaringDate=leagueOneLastMatchDate.plusWeeks(3);
			System.out.println("\n \n \t \t \tLeague Two Matches\n ");
			gameSchedule.displayLeagueMatches(listOfTeamMatches,leageTwoStaringDate);

			System.out.println("\n \n \t \t \tNew Variant with as much matches in one day\n ");
			gameSchedule.displayAsMuchMatchesInOneDaySchedule(LEAGUE_ONE_STARTINGDATE);

		}else{
			LOGGER.info("No wealthPilot League is available ");
		}
	}
}
