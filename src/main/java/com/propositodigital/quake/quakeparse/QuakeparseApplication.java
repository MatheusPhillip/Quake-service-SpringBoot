package com.propositodigital.quake.quakeparse;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.propositodigital.quake.quakeparse.models.Game;
import com.propositodigital.quake.quakeparse.repositories.GameRepository;
import com.propositodigital.quake.quakeparse.utils.QuakeParse;



@SpringBootApplication
//@EnableAutoConfiguration
public class QuakeparseApplication implements CommandLineRunner{
	
	@Autowired
	private GameRepository gameRepository;

	public static void main(String[] args) {
		
		SpringApplication.run(QuakeparseApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		try {
			File file = new File("games.log");
			String path = file.getAbsolutePath();
			QuakeParse parse = new QuakeParse(path);
			for(Game game : parse.getGames()) {
				//games.put(game.getId(), game);
				//games.add(game);
				//bubbleSortPlayersScore(game.getPlayers());
				gameRepository.save(game);
			}
			//sortGamesById();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
