package com.game.mancala.frame;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MancalaPit extends Pit{

	private Pit next;
	private PitOwner owner; 
	

	public MancalaPit(PitOwner owner) {
		super(0);
		this.owner = owner;
	}

	public int getCountAndEmpty() {
		
		throw new IllegalStateException();
	}

	
}
