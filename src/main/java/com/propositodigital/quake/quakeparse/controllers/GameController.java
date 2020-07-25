package com.propositodigital.quake.quakeparse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.propositodigital.quake.quakeparse.services.GameService;

@RestController
@RequestMapping("/game")
@CrossOrigin
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<?> getGameById(@PathVariable int id) {
		return this.gameService.getGameById(id);
	}
	
}
