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
		var moves = availableMoves(board);
		Move move = moves.get(new Random().nextInt(moves.size()));
		return move;
	}

	private List<Move> availableMoves(Board board){
		List<Move> allMoves = new ArrayList<>(board.getAvailableMoves());
		List<Piece.Detective> detectivePieces = new ArrayList<>(getDetectives(board));

		for(Move move : allMoves){
			int destination = getDestination(move); //The destinations of mrx could move to

			for(Piece.Detective detective : detectivePieces){
				if(board.getDetectiveLocation(detective).equals(destination)){ //If the destination have a detective there, mrx remove the choice of this path
					allMoves.remove(move);
				}
			}
		}

		return allMoves;

	}

	//Use visitor to get the destination of two kinds of move
	private Integer getDestination(Move move){
		return move.accept(new Move.Visitor<Integer>() {
			@Override
			public Integer visit(Move.SingleMove move) {
				return move.destination;
			}

			@Override
			public Integer visit(Move.DoubleMove move) {
				return move.destination2;
			}
		});
	}

	//Get detectives' pieces
	public ImmutableList<Piece.Detective> getDetectives(Board board){
		List<Piece.Detective> detectives = new ArrayList<>();
		List<Piece> allPieces = new ArrayList<>(board.getPlayers());

		for(Piece piece: allPieces){
			if (piece.isDetective()){
				detectives.add((Piece.Detective)piece);
			}
		}

		return ImmutableList.copyOf(detectives);
	}
}
