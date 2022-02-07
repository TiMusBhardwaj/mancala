package com.game.mancala.frame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;

/**
 * 
 *
 */
@Log4j2
public class Game {

	private Player player_1;
	private Player player_2;

	private GameState state = GameState.START;

	{
		init();
	}

	private void init() {
		player_1 = new Player(PitOwner.PLAYER_1);
		player_2 = new Player(PitOwner.PLAYER_2);

		setUpLinks();
		state = GameState.PLAYER_1_TURN;
	}

	private void setUpLinks() {
		player_1.getMancalaPit().setNext(player_2.getPlayerPit()[0]);
		player_2.getMancalaPit().setNext(player_1.getPlayerPit()[0]);

		// Mapping opposite pits
		NormalPit[] player_1_Pits = player_1.getPlayerPit();
		NormalPit[] player_2_Pits = player_2.getPlayerPit();
		for (int counter = 0; counter < player_1_Pits.length; counter++) {
			player_1_Pits[counter].setOppositePit(player_2_Pits[player_2_Pits.length - 1 - counter]);
			player_2_Pits[player_2_Pits.length - 1 - counter].setOppositePit(player_1_Pits[counter]);
		}

	}

	public void start() {
		while (state != GameState.END) {
			
			print();
			switch (state) {

			case PLAYER_1_TURN:
				state = player_1.play();
				validateGame();
				break;

			case PLAYER_2_TURN:
				state = player_2.play();
				validateGame();
				break;

			case COLLECT_ALL_PLAYER_1:
				state = player_1.collectAllToMancala();
				break;

			case COLLECT_ALL_PLAYER_2:
				state = player_2.collectAllToMancala();
				break;
			
			case COUNT_FOR_VERDICT:
				countForWinner();
				state = GameState.END;
				break;

			case TRICK_MOVE_PLAYER_1:
				state = player_1.trickMove();
				validateGame();
				break;

			case TRICK_MOVE_PLAYER_2:
				state = player_2.trickMove();
				validateGame();
				break;
			default:
				break;
			}
			
		}
	}

	private void countForWinner() {
		if (player_1.getMancalaPit().getStoneCount() > player_2.getMancalaPit().getStoneCount()) {
			System.out.println("Player 1 Wins ");
		} else if (player_1.getMancalaPit().getStoneCount() < player_2.getMancalaPit().getStoneCount()) {
			System.out.println("Player 2 Wins ");
		} else {
			System.out.println("Game Tie ");
		}

	}

	private void validateGame() {

		if (state != GameState.COLLECT_ALL_PLAYER_2 && player_1.getNonZeroPit().size() == 0) {
			state = GameState.COLLECT_ALL_PLAYER_2;
		}

		if (state != GameState.COLLECT_ALL_PLAYER_1 && player_2.getNonZeroPit().size() == 0) {
			state = GameState.COLLECT_ALL_PLAYER_1;
		}
	}
	
	private void print() {
		List<String> list = Arrays.asList(player_1.getPlayerPit()).stream().mapToInt(Pit :: getStoneCount)
				.mapToObj(String::valueOf)
				.collect(Collectors.toList());
		Collections.reverse(list);
		String player1State = String.join(" -- ", list);
        
        System.out.println(player1State);
		
		System.out.printf("%-8d%8d",player_1.getMancalaPit().getStoneCount() ,player_2.getMancalaPit().getStoneCount());
		System.out.println();
		String player2State = Arrays.stream(player_2.getPlayerPit())
				.mapToInt(Pit :: getStoneCount)
		        .mapToObj(String::valueOf)
		        .collect(Collectors.joining(" -- "));
		
		System.out.println(player2State);
		System.out.println();
		System.out.println("State is " + state);
		
		
	}

}
