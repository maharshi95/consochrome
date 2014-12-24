package com.sanskimars.consortium;
import static com.sanskimars.consortium.Data.*;

import java.util.ArrayList;
import java.util.Random;
/**
 * Class to simulate and keep the data regarding each level of the game, which includes
 * timeout, buffer size, number of steps, score.
 * @author Maharshi
 *
 */
public class Level {
	private final Random rgen = new Random();
	
	private long timeout;
	private int bufferSize;
	private int nsteps;
	private long score;
	private int level;
	private ArrayList<Integer> indices;
	
	public Level(int level) {
		this.level = level;
		timeout = getTimeout(level);
		nsteps = getNSteps(level);
		bufferSize = getBufferSize(level);
		indices = new ArrayList<Integer>();
		score = 0;
		
		int r = row(level);
		int n = 8;
		
		if(r <= LEVELS_PER_BUFFER/3)
			n = 3;
		else if(r <= 2*LEVELS_PER_BUFFER/3)
			n = 6;
		
		ArrayList<Integer> dummy = new ArrayList<Integer>();
		boolean[] array = new boolean[N_COLORS];
		
		for(int i=0; i<N_COLORS; i++) {
			dummy.add(i);
		}
		
		for(int i=0; i<n; i++) {
			int j = rgen.nextInt(N_COLORS);
			while(array[dummy.get(j)] == true)
				j = rgen.nextInt(N_COLORS);
			array[dummy.get(j)] = true;
		}
		
		for(int i=0; i<N_COLORS; i++) {
			if(array[i] == true)
				indices.add(i);
		}
		
	}
	
	public long timeout() {
		return timeout;
	}
	
	public int bufferSize() {
		return bufferSize;
	}
	
	public int nsteps() {
		return nsteps;
	}
	
	public long score() {
		return score;
	}
	
	public void addToScore(long stepScore) {
		score += stepScore;
	}
	
	public int getRandomIndex() {
		int i = rgen.nextInt(indices.size());
		return indices.get(i);
	}
	
	/**
	 * 
	 * @param level
	 * @return timeout milliseconds for each step in the given level
	 */
	private static int getTimeout(int level) {
		int min = getTimeoutMinLimit(col(level));
		int max = getTimeoutMaxLimit(col(level));
		int r  = row(level);
		int L = LEVELS_PER_BUFFER;
		return (max*(L-r) + min*(r-1))/(L-1);
	}
	
	private static int getTimeoutMinLimit(int col) {
		return TIMEOUT_MIN + (col - 1)*TIMEOUT_INCREMENT;
	}
	
	private static int getTimeoutMaxLimit(int col) {
		return TIMEOUT_MAX  + (col-1)*TIMEOUT_INCREMENT;
	}
	
	private static int col(int level) {
		return (level-1)/LEVELS_PER_BUFFER + 1;
	}
	
	private static int row(int level) {
		return (level-1)%LEVELS_PER_BUFFER + 1;
	}
	private static int getBufferSize(int level) {
		return col(level);
	}
	
	private static int getNSteps(int level) {
		return 10;
	}

	public String toString() {
		String str = "Lv: " + level + " T:" + timeout + " C:" + indices.size() + " B:" +bufferSize + " I:" + indices.toString();
		return str;
	}
	
}
