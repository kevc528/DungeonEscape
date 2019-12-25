import java.util.*;

// class for generating the maze as a 2D array
public class Maze {
	
	private int width;
	private int height;
	
	private int[][] board;
	
	// maze initialized as 2D array, 1 means wall and 0 means path
	public Maze(int h, int w) {
		this.height = h;
		this.width = w;
		this.board = new int[height][width];
		for (int x = 0; x < h; x++) {
			for (int y = 0; y < w; y++) {
				board[x][y] = 1;
			}
		}
		board = this.generateMaze();
	}
	
	public int[][] generateMaze() {
		board[1][1] = 0;
		dfsAlg(1, 1);
		return board;
	}
	
	public int[][] getGeneratedBoard(){
		return board;
	}
	
	Random random = new Random();
	
	private static Integer[] getRandomDirections() {
		List<Integer> directions = new ArrayList<Integer>();
		directions.add(1); // 1 for up
		directions.add(2); // 2 for right
		directions.add(3); // 3 for down
		directions.add(4); // 4 for left
		Collections.shuffle(directions);
		return directions.toArray(new Integer[4]);
	}
	
	// using depth first search to generate a maze path
	private void dfsAlg(int h, int w) {
		Integer[] directions = getRandomDirections();
		for (int x = 0; x < directions.length; x++) {
			int i = directions[x];
			if (i == 1) {
				if (h - 2 <= 0) {
					continue;
				}
				if (board[h-2][w] != 0) {
					board[h-2][w] = 0;
					board[h-1][w] = 0;
					dfsAlg(h-2, w);
				}
			}
			
			if (i == 2) {
				if (w + 2 >= width - 1) {
					continue;
				}
				if (board[h][w+2] != 0) {
					board[h][w+2] = 0;
					board[h][w+1] = 0;
					dfsAlg(h, w+2);
				}
			}
			
			if (i == 3) {
				if (h + 2 >= height - 1) {
					continue;
				}
				if (board[h+2][w] != 0) {
					board[h+2][w] = 0;
					board[h+1][w] = 0;
					dfsAlg(h+2, w);
				}
			}
			
			if (i == 4) {
				if (w - 2 <= 0) {
					continue;
				}
				if (board[h][w-2] != 0) {
					board[h][w-2] = 0;
					board[h][w-1] = 0;
					dfsAlg(h, w-2);
				}
			}
		}
	}
}
