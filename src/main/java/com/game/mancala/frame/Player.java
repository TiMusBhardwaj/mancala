package com.game.mancala.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.Getter;

@Getter
public class Player {
	
	private String name;

	private NormalPit[] playerPit;

	private MancalaPit mancalaPit;

	private PitOwner owner;
	
	private Pit activePit;

	public Player(PitOwner owner) {
		this.owner = owner;

		playerPit = new NormalPit[GameProperties.PIT_COUNT];
		int counter = 1;
		playerPit[0] = new NormalPit(owner);
		while (counter < playerPit.length) {
			playerPit[counter] = new NormalPit(owner);
			playerPit[counter - 1].setNext(playerPit[counter]);
			counter++;
		}
		mancalaPit = new MancalaPit(owner);
		playerPit[counter - 1].setNext(mancalaPit);
		mancalaPit.setOwner(owner);
	}

	/**
	 * @return Pits available to play
	 */
	public List<Integer> getNonZeroPit() {

		List<Integer> nonZeroPits = new ArrayList<Integer>();
		for (int count = 0; count < playerPit.length; count++) {
			if (!playerPit[count].isEmpty()) {
				nonZeroPits.add(count);
			}

		}
		return nonZeroPits;
	}

	public GameState play() {
		List<Integer> options = getNonZeroPit();
		Random randomizer = new Random();
		Integer random = options.get(randomizer.nextInt(options.size()));
		return pickBeadsAndLoop(random);
	}

	private GameState pickBeadsAndLoop(int pitNumber) {
		if (playerPit[pitNumber].isEmpty()) {
			throw new IllegalArgumentException();
		}
		activePit = playerPit[pitNumber];
		int stoneCount = playerPit[pitNumber].getCountAndEmpty();

		while (stoneCount > 0) {
			activePit = activePit.getNext();
			if(activePit instanceof MancalaPit && !activePit.getOwner().equals(owner)) {
				continue;
			}
			activePit.increment();
			stoneCount--;
		}
		if (activePit instanceof MancalaPit) {
			return owner.repeat();
		}
		if (activePit instanceof NormalPit) {
			NormalPit playerPit = (NormalPit) activePit;
			if (!playerPit.getOwner().equals(this.owner)) {
				if (getNonZeroPit().size() == 0) {
					return owner.toggleAndCollect();
				}
				return owner.toggle();
			}else if (playerPit.getStoneCount() == 1 && playerPit.getOppositePit().getStoneCount() != 0) {
				return owner.grab1andAllOpposite();
			}
		}

		return owner.toggle();
	}
	
	public GameState trickMove() {
		
		if (activePit instanceof NormalPit) {
			NormalPit playerPit = (NormalPit) activePit;
			int stonesToMove = playerPit.getCountAndEmpty() + playerPit.getOppositePit().getCountAndEmpty();
			mancalaPit.addStones(stonesToMove);
		}
		return owner.toggle();
	}
	
	public GameState collectAllToMancala() {
		for (Pit pit : playerPit) {
			mancalaPit.addStones(pit.getStoneCount());
			pit.setStoneCount(0);
		}
		
		return GameState.COUNT_FOR_VERDICT;
	}

}
