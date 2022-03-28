package uk.ac.bris.cs.scotlandyard.ui.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nonnull;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.atlassian.fugue.Pair;
import uk.ac.bris.cs.scotlandyard.model.*;
import uk.ac.bris.cs.scotlandyard.model.Ai;
import uk.ac.bris.cs.scotlandyard.model.Board;
import uk.ac.bris.cs.scotlandyard.model.Move;
import uk.ac.bris.cs.scotlandyard.model.Player;



public class MyAi implements Ai {

	@Nonnull @Override public String name() { return "Randomly"; }

	@Nonnull @Override public Move pickMove(
			@Nonnull Board board,
			Pair<Long, TimeUnit> timeoutPair) {

		ImmutableSet<Player> allPlayers = getAllPlayers();

		// returns a random move, replace with your own implementation
		var moves = board.getAvailableMoves().asList();
		return moves.get(new Random().nextInt(moves.size()));
	}




	public Player getMrX (ImmutableSet<Player> players){

		Player mrX = null;

		for(Player player: players){
			if (player.isMrX()) {
				mrX = player;
			}
		}

		return mrX;
	}

	public ImmutableSet<Player> getDetectives (ImmutableSet<Player> players){

		List<Player> detectives = new ArrayList<>();

		for(Player player : players){
			if (player.isDetective()){
				detectives.add(player);
			}
		}

		return ImmutableSet.copyOf(detectives);
	}

	public ImmutableSet<Player> getAllPlayers (){

		return ImmutableSet.of();
	}
}
