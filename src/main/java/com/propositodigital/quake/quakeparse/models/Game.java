package com.propositodigital.quake.quakeparse.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

@Entity
public class Game{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int total_kills;
	private int worldScore;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Player> players;
	
	
	public Game() {} // REQUISITO DO JPA
	
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Game(int id) {
		players = new ArrayList<Player>();
		this.total_kills = 0; // INICIALIZA A QUANTIDADE TOTAL DE MORTES NO JOGO COM 0
		this.worldScore = 0;  // INICIALIZA A PONTUAÇÃO DE MORTES PELO CENÁRIO COM 0
		this.id = id;
	}
	

	public void setTotal_kills(int total_kills) {
		this.total_kills = total_kills;
	}

	public void setWorldScore(int worldScore) {
		this.worldScore = worldScore;
	}

	public int getTotal_kills() {
		return total_kills;
	}

	// INCREMENTA MAIS 1 NA PONTUAÇÃO TOTAL DO JOGO
	public void addTotal_killsScore() {
		this.total_kills += 1;
	}


	public int getWorldScore() {
		return worldScore;
	}

	// INCREMENTA MAIS 1 NA PONTUAÇÃO DE MORTES POR CENÁRIO NO JOGO
	public void addWorldScore() {
		this.worldScore += 1;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public List<Player> getPlayers() {
		return players;
	}


	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	
	// ADICIONA UM JOGADOR NA LISTA DE JOGADORES DESTE JOGO
	public void addPlayer(Player player) {
		for(int i = 0; i < players.size(); i ++) {
			if(players.get(i).getNickName().equals(player.getNickName())) { // NÃO PERMITE NOMES DE JOGADORES REPETIDOS
				System.out.println("Player ja cadastrado nesse jogo.");
				return;
			}
		}
		players.add(player); // ADICIONA JOGADOR
	}
	
	
	/*
	 * ESTE MÉTODO TEM POR OBJETIVO DEFINIR O TIPO DE MORTE SOFRIDA PELO JOGADOR
	 * O PARÂMETRO LINE REPRESENTA A LINHA DO ARQUIVO GAMES.LOG 
	 * O PARÂMETRO PLAYERNICKNAME REPRESENTA O NOME DO JOGADOR QUE MORREU
	 */
	public void MeanOfDeath(String line, String playerNickName) {
		String meanOfDeath = line.substring(line.lastIndexOf("by") + 3, line.length()); // PEGA O ELEMENTO MOD NO FINAL DA LINHA
		String typeOfWorldKill; // REPRESENTA COMO O JOGADOR MORREU
		switch(TypesOfDeath.valueOf(meanOfDeath)) {
		case MOD_CRUSH:
			typeOfWorldKill = "foi esmagado.";
			break;
		case MOD_FALLING:
			typeOfWorldKill = "caiu de uma altura muito alta.";
			break;
		case MOD_TRIGGER_HURT:
			typeOfWorldKill = "estava ferido e caiu de uma altura que o matou.";
			break;
		default:
			typeOfWorldKill = "morte por cenario não especificada.";
			break;
		}
		System.out.println("O player \"" + playerNickName + "\" morreu pois " + typeOfWorldKill);
	
	}
	
	/*
	 * ESTE MÉTODO TEM POR OBJETIVO DEFINIR O TIPO DE MORTE SOFRIDA DE UM JOGADOR POR OUTRO
	 * O PARÂMETRO LINE REPRESENTA A LINHA DO ARQUIVO GAMES.LOG
	 * O PARÂMETRO PLAYERNICKNAME REPRESENTA O NOME DO JOGADOR QUE MATOU
	 * O PARÂMETRO PLAYERKILLEDNICKNAME REPRESENTA O NOME DO JOGADOR QUE MORREU
	 */
	public void MeanOfDeath(String line, String playerNickName, String playerKilledNickName) {
		String meanOfDeath = line.substring(line.lastIndexOf("by") + 3, line.length()); // PEGA O ELEMENTO MOD NO FINAL DA LINHA
		String typeOfGun; // REPRESENTA O NOME DE ARMA UTILIZADA 
		switch(TypesOfDeath.valueOf(meanOfDeath)) {
		case MOD_BFG:
			typeOfGun = "BFG";
			break;
		case MOD_MACHINEGUN:
			typeOfGun = "Machinegun";
			break;
		case MOD_BFG_SPLASH:
			typeOfGun = "BFGSplash";
			break;
		case MOD_RAILGUN:
			typeOfGun = "Railgun";
			break;
		case MOD_ROCKET:
			typeOfGun = "Rocket";
			break;
		case MOD_SHOTGUN:
			typeOfGun = "Shotgun";
			break;
		case MOD_ROCKET_SPLASH:
			typeOfGun = "RocketSplash";
			break;
		case MOD_TELEFRAG:
			typeOfGun = "Telefrag";
			break;
		default:
			typeOfGun = "Não especificada.";
			break;
		}
		System.out.println("O player \"" + playerNickName + "\" matou o player \"" + playerKilledNickName + "\" usando a arma " + typeOfGun + ".");
	}
	
	public void showAllPlayersScore() {
		for(int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getNickName() + ":" + players.get(i).getScore());
		}
	}
	
	/*
	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return getId() + ": " + gson.toJson(this);
		//return "Game [id=" + id + ", players=" + players.toString() + "total_kills=" + total_kills + ", worldScore=" + worldScore +"]";
	}
	*/
	
	@Override
	public String toString() {
		return "game_" + id + ": {\n" 
				  + "\t" + "total_kills: " + total_kills + ";\n" 
				  + "\t" + toStringPlayers() +"\n"
				  + "\t" + toStringPlayersScore()
				  + "}";
	}
	
	private String toStringPlayers() {
		return "players: " + players.toString();
	}
	
	private String toStringPlayersScore() {
		String score = "kills: {\n";
		for(int i = 0; i < players.size(); i++) {
			score += "\t\t";
			score += players.get(i).toStringPlayerScore();
			if(i < players.size() - 1) {
				score += ",";
			}
			score += "\n";
		}
		score += "\t" + "}";
		return score;
	}
}
