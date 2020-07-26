package com.propositodigital.quake.quakeparse.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Player {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nickName;
	private int score;	
	
	public Player() {} // REQUISITO DO JPA

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	/*
	 *  IMPRIME A PONTUAÇÃO DO JOGADOR
	 */
	public String toStringPlayerScore() {
		return toString() + ": " + getScore();
	}
}
