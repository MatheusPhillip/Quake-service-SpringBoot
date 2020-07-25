package com.propositodigital.quake.quakeparse.models;

import javax.persistence.Entity;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

@Entity
public class Player {
	
	private String nickName;
	private int score;

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
