
/** Rebecca Henry
 *  student number: 20152682
 *  Mon. Oct. 5, 2020
 *  CISC124 assignment 1
 */

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleSudoku {

	/**
	 * Returns a list of length 9 containing the digits from the specified row in
	 * the specified puzzle.
	 * 
	 * @param n      the row index
	 * @param puzzle a Sudoku puzzle
	 * @return a list of length 9 containing the digits from the specified row in
	 *         the specified puzzle
	 */
	public static List<Integer> row(int n, Puzzle puzzle) {
		List<Integer> row = new ArrayList<>();
		for (int i = 0; i < Puzzle.SIZE; i++) {
			row.add(puzzle.get(n, i));
		}
		return row;
	}

	public static List<Integer> col(int k, Puzzle puzzle) {
		List<Integer> col = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			col.add(puzzle.get(i, k));
		}
		return col;
	}

	public static List<Integer> block(int j, Puzzle puzzle) {
		List<Integer> block = new ArrayList<>();
		if (j == 1) {
			for (int r = 0; r < 3; r++) {
				for (int c = 0; c < 3; c++) {
					block.add(puzzle.get(r, c));
				}
			}
		} else if (j == 2) {
			for (int r = 0; r < 3; r++) {
				for (int c = 3; c < 6; c++) {
					block.add(puzzle.get(r, c));
				}
			}
		} else if (j == 3) {
			for (int r = 0; r < 3; r++) {
				for (int c = 6; c < 9; c++) {
					block.add(puzzle.get(r, c));
				}
			}
		} else if (j == 4) {
			for (int r = 3; r < 6; r++) {
				for (int c = 0; c < 3; c++) {
					block.add(puzzle.get(r, c));
				}
			}
		} else if (j == 5) {
			for (int r = 3; r < 6; r++) {
				for (int c = 3; c < 6; c++) {
					block.add(puzzle.get(r, c));
				}
			}
		} else if (j == 6) {
			for (int r = 3; r < 6; r++) {
				for (int c = 6; c < 9; c++) {
					block.add(puzzle.get(r, c));
				}
			}
		} else if (j == 7) {
			for (int r = 6; r < 9; r++) {
				for (int c = 0; c < 3; c++) {
					block.add(puzzle.get(r, c));
				}
			}
		} else if (j == 8) {
			for (int r = 6; r < 9; r++) {
				for (int c = 3; c < 6; c++) {
					block.add(puzzle.get(r, c));
				}
			}
		}
		if (j == 9) {
			for (int r = 6; r < 9; r++) {
				for (int c = 6; c < 9; c++) {
					block.add(puzzle.get(r, c));
				}
			}
		}
		return block;
	}

	public static Boolean checkDigits(List<Integer> list) {
		boolean allnine = true;
		for (int i = 1; i < 10; i++) {
			if (!(list.contains(i))) {
				allnine = false;
				break;
			}
		}
		return allnine;
	}

	public static List<Integer> checkRows(Puzzle puzzle) {
		List<Integer> invalidRows = new ArrayList<>();
		for (int k = 0; k < 9; k++) {
			boolean valid = checkDigits(row(k, puzzle));
			if (valid == false) {
				invalidRows.add(k);
			}
		}
		if (invalidRows.size() == 0) {
			return null;
		}
		return invalidRows;
	}

	public static List<Integer> checkCols(Puzzle puzzle) {
		List<Integer> invalidCols = new ArrayList<>();
		for (int j = 0; j < 9; j++) {
			boolean valid = checkDigits(col(j, puzzle));
			if (valid == false) {
				invalidCols.add(j);
			}
		}
		if (invalidCols.size() == 0) {
			return null;
		}
		return invalidCols;
	}

	public static List<Integer> checkBlocks(Puzzle puzzle) {
		List<Integer> invalidBlocks = new ArrayList<>();
		for (int j = 1; j < 10; j++) {
			boolean valid = checkDigits(block(j, puzzle));
			if (valid == false) {
				invalidBlocks.add(j);
			}
		}
		if (invalidBlocks.size() == 0) {
			return null;
		}
		return invalidBlocks;
	}

	public static Puzzle fixPuzzle(Puzzle p) {
		List<Integer> rows = (checkRows(p));
		int missing = 0;
		for (int r : rows) {
			List<Integer> row = row(r, p);
			if (row.contains(0)) {
				for (int i = 1; i < 10; i++) {
					if (!(row.contains(i))) {
						missing = i;
					}
				}
			}
		}
		p.setMissingTo(missing);
		return p;
	}

	/**
	 * Reads a puzzle from the file with specified file name.
	 * 
	 * @param fileName the puzzle file name
	 * @return a Puzzle object reference containing the digits of the puzzle
	 */
	public static Puzzle readPuzzle(String fileName) {
		Puzzle p = new Puzzle();
		InputStream stream = SimpleSudoku.class.getResourceAsStream(fileName);
		Scanner s = new Scanner(stream);
		for (int i = 0; i < 81; i++) {
			p.addValue(s.nextInt());
		}
		s.close();
		return p;
	}

	public static void print(Puzzle p) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				System.out.print(" " + p.get(r, c));
				if (c == 8) {
					System.out.println("");
				}
				if (c == 8 && r == 2 || c == 8 && r == 5) {
					System.out.println("----------------------");
				}
				if (c == 2 || c == 5) {
					System.out.print(" |");
				}
			}
		}
	}

	public static void main(String[] args) {
		Puzzle puzzle;
		if (args.length > 0) {
			puzzle = readPuzzle(args[0]);
		} else {
			puzzle = readPuzzle("puzzle01.txt"); // change the filename here to read different puzzles
		}

		Boolean allnine = checkDigits(block(1, puzzle));
		List<Integer> invalidCols = checkCols(puzzle);
		List<Integer> invalidRows = checkRows(puzzle);
		List<Integer> invalidBlocks = checkBlocks(puzzle);

		if (!allnine) {
			System.out.println("\nThis puzzle has mistakes\n");
			if (invalidCols.size() != 0) {
				System.out.println("There are mistakes in the column(s): " + invalidCols);
			}
			if (invalidRows.size() != 0) {
				System.out.println("There are mistakes in the row(s): " + invalidRows);
			}
			if (invalidBlocks.size() != 0) {
				System.out.println("There are mistakes in the block(s): " + invalidBlocks);
			}
		} else if (allnine) {
			System.out.println("The puzzle is a valid solution");
		}

		System.out.print("\nThe puzzle: \n");
		print(puzzle);

		if (puzzle.hasMissingValue()) {
			System.out.println("\n");
			System.out.println("This puzzle is missing a value.");
			System.out.print("Here is the corrected puzzle: \n");
			print(fixPuzzle(puzzle));
		}
	}

}
