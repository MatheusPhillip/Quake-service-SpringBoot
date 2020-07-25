package com.propositodigital.quake.quakeparse.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.propositodigital.quake.quakeparse.models.Game;
import com.propositodigital.quake.quakeparse.models.Player;


public class QuakeParse {
	private int gameCounter = 0;
	private Boolean gameInProgress = false;
	private ArrayList<Game> games = new ArrayList<Game>();
	private Game game;
	private Player player;
	private GameFile gameFile = new GameFile();
	private String fileName = "games_parse.txt";
	
	public QuakeParse(String path) throws FileNotFoundException, IOException {
		File file = new File(path); 
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String st; 
			gameFile.openToWrite(fileName);
			while ((st = br.readLine()) != null) { 
			    parseLine(st);
			}
			gameFile.closeAfterWrite();
		} 
	}
	
	/*
	 * O OBJETIVO DESTE MÉTODO É CHECAR QUE TIPO DE INFORMAÇÃO A LINHA ATUAL DO AQUIVO POSSUE
	 */
	private void parseLine(String line) {
		if(line.contains("InitGame:")) { 				// CHECA SE UM JOGO COMEÇOU
			parseInitGame();
		}
		if(line.contains("ClientUserinfoChanged:")) {	// CHECA SE UM JOGA
			parsePlayerJoin(line);
		}
		if(line.contains("ShutdownGame:")) {			// CHECA SE UM JOGO TERMINOU
			parseShutdownGame();
		}
		if(line.contains("Kill:")) {					// CHECA SE HOUVE UMA MORTE DE JOGADOR
			if(line.contains("<world>")) {				// CHECA SE ESSA MORTE FOI CAUSADA PELO CENÁRIO
				parseKillByWorld(line);
			}
			else { 										// CASO UM JOGADOR TENHA MORTO O OUTRO
				parseKillByPlayer(line);				
			}
		}
	}
	
	/*
	 * O OBJETIVO DESTE MÉTODO É, DADO QUE UM JOGO FOI INICIADO, INTANCIAR ESTE JOGO
	 */
	private void parseInitGame() {
		if(this.gameInProgress) { 						// PEGA UM JOGO QUE NÃO TENHA SIDO ENCERRADO CORRETAMENTE
			System.out.println("game_" + this.gameCounter + " encerrado. dentro do pega errado");
			this.games.add(game);						// ADICIONA O ÚLTIMO JOGO NA LISTA DE JOGOS
			this.gameCounter++;							// INCREMENTA O CONTADOR DE JOGOS
			printGame();                                // EXIBE AS INFORMAÇÕES DO JOGO ENCERRADO
			saveGameInFile();
			game = new Game(this.gameCounter);			// INSTANCIA UM NOVO JOGO
			System.out.println("game_" + this.gameCounter + " iniciado.");
			this.gameInProgress = true;
		}
		else {											// CASO NÃO TENHA UM JOGO EM ANDAMENTO
			this.gameCounter++;							// INCREMENTA O CONTADOR DE JOGOS
			game = new Game(this.gameCounter);			// INSTANCIA UM NOVO JOGO
			System.out.println("game_" + this.gameCounter + " iniciado.");
			this.gameInProgress = true;					// ATRIBUE VERDADEIRO A JOGO EM ANDAMENTO
		}
	}
	
	/*
	 *  O OBJETIVO DESTE MÉTODO É DESCOBRIR O NOME DO JOGADOR QUE ENTROU NO JOGO
	 *  E INSTANCIAR UM JOGADOR NOVO
	 *  O PARÂMETRO LINE REPRESENTA A LINHA ATUAL DO ARQUIVO GAMES.LOG
	 */
	private void parsePlayerJoin(String line) {
		System.out.println("Player Encontrado");
		String playerName = line.split("\\\\")[1];		// DIVIDE A LINHA POR "\" E SALVA O NOME DO JOGADOR LOCALIZADO NA SUBSTRING[1]
		player = new Player(playerName);				// INSTANCIA UM NOVO JOGADOR
		game.addPlayer(player);							// ADICIONA ESTE JOGADOR NA LISTA DE PLAYERS DO JOGO ATUAL
		System.out.println("Nome do jogador: " + playerName);
	}
	
	/*
	 *  O OBJETIVO DESTE MÉTODO É, DADO QUE UM JOGO FOI ENCERRADO, TERMINAR O JOGO ATUAL
	 *  E SALVAR ESSE JOGO NA LISTA DE JOGOS
	 */
	private void parseShutdownGame() {
		System.out.println("game_" + this.gameCounter + " encerrado.");
		this.games.add(game);							// ADICIONA O JOGO NA LISTA DE JOGOS
		this.gameInProgress = false;					// ATRIBUE FALSO A JOGO EM ANDAMENTO
		printGame();                                	// EXIBE AS INFORMAÇÕES DO JOGO ENCERRADO
		saveGameInFile();
	}
	
	/*
	 *  O OBJETIVO DESTE MÉTODO É, DADO QUE HOUVE UMA MORTE POR CENÁRIO, CONTABILIZAR ESSA MORTE
	 *  O PARÂMETRO LINE REPRESENTA A LINHA ATUAL DO ARQUIVO GAMES.LOG
	 */
	private void parseKillByWorld(String line) {
		for(int i = 0; i < game.getPlayers().size(); i++) {
			if(line.contains(game.getPlayers().get(i).getNickName())) {			// PROCURA O NOME DO JOGADOR NA LINHA USANDO A LISTA DE JOGADORES
				//game.getPlayers().get(i).getNickName();
				game.MeanOfDeath(line, game.getPlayers().get(i).getNickName()); // INFORMA O MOTIVO DA MORTE DO JOGADOR
				game.getPlayers().get(i).addDeathByWorld();						// ATUALIZA PONTUAÇÃO DO JOGADOR DEVIDO MORTE POR CENÁRIO
				game.addWorldScore();											// INCREMENTA A PONTUAÇÃO DE MORTES POR CENÁRO
				game.addTotal_killsScore();										// INCREMENTA O NÚMERO TOTAL DE MORTES NO JOGO
				break;
			}
		}
	}
	
	/*
	 *  O OBJETIVO DESTE MÉTODO É, DADO QUE UM JOGADOR MATOU O OUTRO, 
	 *  DESCOBRIR QUAL JOGOU MORREU E QUEM O MATOU
	 *  O PARÂMETRO LINE REPRESENTA A LINHA ATUAL DO ARQUIVO GAMES.LOG
	 */
	private void parseKillByPlayer(String line) {
		String[] subString = line.split("killed");								/*
																				 * DIVIDE A LINHA ATUAL USANDO A PALAVRA KILLED
																				 * NA ESQUERDA DE KILLED ESTARÁ O NOME DO JOGADOR QUE MATOU
																				 * NA DIREITA DE KILLED ESTARÁ O NOME DO JOGADOR QUE MORREU
																				 * SUBSTRING[0] E SUBSTRING[1]
																				*/
		String playerName = "";
		String playerKilledName = "";
		for(int i = 0; i < game.getPlayers().size(); i++) {						// PROCURA O NOME DOS JOGADORES NA LISTA
			if(subString[0].contains(game.getPlayers().get(i).getNickName())) { // CHECA SE O NOME DO JOGADOR ESTÁ A ESQUERDA DE KILLED
				playerName = game.getPlayers().get(i).getNickName();			// ATRIBUE O NOME DO JOGADOR QUE MATOU A PLAYERNAME
				game.getPlayers().get(i).addScore();							// INCREMENTA A PONTUAÇÃO DESSE JOGADOR
				//break;
			}
			if(subString[1].contains(game.getPlayers().get(i).getNickName())) { // CHECA SE O NOME DO JOGADOR ESTÁ A DIREITA DE KILLED
				playerKilledName = game.getPlayers().get(i).getNickName();		// ATRIBUE O NOME DO JOGADOR QUE MORREU A PLAYERKILLEDNAME
				//break;
			}
		}
		game.addTotal_killsScore();												// INCREMENTA O NÚMERO TOTAL DE MORTES NO JOGO
		game.MeanOfDeath(line, playerName, playerKilledName);					// INFORMA O MOTIVO DA MORTE DO JOGADOR
	}
	
	/*
	 *  O OBJETIVO DESTE MÉTODO É RETORNAR A LISTA DE JOGOS OCORRIDOS
	 */
	public ArrayList<Game> getGames() {
		return games;
	}
	
	/*
	 *  ESTE MÉTODO TEM POR OBJETIVO, APÓS UM GAME SER ENCERRADO, MOSTRAR O RESULTADO DESTE ÚLTIMO GAME
	 */
	private void printGame() {
		System.out.println(game.toString());
	}
	
	private void saveGameInFile() {
		if(game != null) {
			gameFile.saveGame(game);
		}
	}
	
}
