package com.propositodigital.quake.quakeparse.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

@Entity
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nickName;
	private int score;
	
	@ManyToMany(mappedBy = "players")
	private List<Game> games;
	
	public Player() {} // REQUISITO DO JPA

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Player(String nickName) {
		this.nickName = nickName;
		this.score = 0; // INICIALIZA A PONTUAÇÃO DO JOGADOR COM 0
	}
	
	public String getNickName() {
		return nickName;
	}
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public int getScore() {
		return score;
	}
	
	// ADICIONA MAIS 1 NA PONTUAÇÃO ATUAL DO JOGADOR
	public void addScore() {
		this.score += 1;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	// CASO O JOGADOR MORRA POR AÇÃO DO CENÁRIO
	// SUBTRAI 1 DA PONTUAÇÃO ATUAL DO JOGADOR
	public void addDeathByWorld() {
		this.score -= 1;
	}

	@Override
	public String toString() {
		return "\"" + nickName + "\"";
	}
	
	public String toStringPlayerScore() {
		return toString() + ": " + getScore();
	}
}
