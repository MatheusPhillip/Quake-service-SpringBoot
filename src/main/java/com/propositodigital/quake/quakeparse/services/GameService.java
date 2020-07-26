package com.propositodigital.quake.quakeparse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.propositodigital.quake.quakeparse.models.Game;
import com.propositodigital.quake.quakeparse.repositories.GameRepository;

@Service
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	public ResponseEntity<?> getGameById(int id) {
		Game game = gameRepository.findById(id).orElse(null);
		if(game == null) {
			return ResponseEntity.badRequest().body("Error: Game não encontrado.");
		}
		return ResponseEntity.ok(game);
	}
}
