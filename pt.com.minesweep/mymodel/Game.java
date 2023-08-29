import java.util.Scanner;

public class Game {

	private Field	mineField = null;
	private Scanner scanner = new Scanner(System.in);
	private String	input[] = new String[2];
	private int	x = 0;
	private int	y = 0;

	public void	endGame() {
		scanner.close();
	}

	public void runGame() {
		System.out.println("Enter Block coordinates and action, (o)pen or (f)lag");
		while (mineField.checkWin() == false && mineField.stepOnMine() == false) {
			mineField.printField();
			do {
				input[0] = scanner.nextLine();
			}
			while (validInput() == false);
			mineField.openBlock(x, y);
		}
	}

	public void	initFieldMode() {
		while (this.mineField == null) {
			System.out.println("Enter game mode: Easy, Normal or Hard");
			input[0] = scanner.nextLine();
			if (input[0].equalsIgnoreCase("hard"))
				this.mineField = new Field(50, 30, 150);
			else if (input[0].equalsIgnoreCase("normal"))
				this.mineField = new Field(20, 15, 80);
			else if (input[0].equalsIgnoreCase("easy"))
				this.mineField = new Field(10, 10, 20);
		}
	}

	private boolean	validInput() {
		if (input == null) {
			return (false);
		}
		input = input[0].split(",");
		if (input.length != 3) {
			return (false);
		}
		else if (input[2].compareTo("o") != 0
					&& input[2].compareTo("f") != 0)
			return (false);
		try {
			x = Integer.parseInt(input[0]);
			y = Integer.parseInt(input[1]);
		}
		catch (NumberFormatException e) {
			return (false);
		}
		return (true);

	}
	public static void main(String[] args) {

		Game	mineField = new Game();

		mineField.initFieldMode();
		mineField.runGame();
		mineField.endGame();
	}
}
