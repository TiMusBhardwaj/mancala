package com.game.mancala.frame;

public enum PitOwner {

	PLAYER_1 {
		public GameState repeat() {
			return GameState.PLAYER_1_TURN;
		}
		
		public GameState toggle() {
			return GameState.PLAYER_2_TURN;
		}

		public GameState toggleAndCollect() {
			return GameState.COLLECT_ALL_PLAYER_2;
		}
		public GameState grab1andAllOpposite() {
			return GameState.TRICK_MOVE_PLAYER_1;
		}
	}, PLAYER_2 {
		public GameState repeat() {
			return GameState.PLAYER_2_TURN;
		}
		
		public GameState toggle() {
			return GameState.PLAYER_1_TURN;
		}

		public GameState toggleAndCollect() {
			return GameState.COLLECT_ALL_PLAYER_1;
		}
		public GameState grab1andAllOpposite() {
			return GameState.TRICK_MOVE_PLAYER_2;
		}
	};
	
	public abstract GameState repeat();
	public abstract GameState toggle();
	public abstract GameState toggleAndCollect();
	public abstract GameState grab1andAllOpposite();
}
