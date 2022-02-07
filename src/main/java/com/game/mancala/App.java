package com.game.mancala;

import com.game.mancala.frame.Game;

import lombok.extern.log4j.Log4j2;

/**
 * Hello world!
 *
 */
@Log4j2
public class App 
{
    public static void main( String[] args )
    {
    	log.info("");
       Game game = new Game();
       game.start();
    }
}
