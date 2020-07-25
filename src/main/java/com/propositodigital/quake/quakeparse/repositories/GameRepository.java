package com.propositodigital.quake.quakeparse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.propositodigital.quake.quakeparse.models.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{
	


	
}
