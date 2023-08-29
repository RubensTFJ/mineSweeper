import java.util.List;
import java.util.ArrayList;

public class Field {
	
	private Block	field[][] = null;
	private Block	clicked = null;
	private int		line;
	private int		column;
	private int		opened = 0;
	private int		totalMines;
	private int		totalBlocks;
	private	boolean	lost = false;


	public Field(int x, int y, int mines) {
		if (mines > x * y) {
			System.out.println("Too many mines, using" + (x + y) + "instead.");
			mines = x + y;
		}
		this.line = x;
		this.column = y;
		this.totalBlocks = x * y;
		this.totalMines = mines;
		newField(x, y);
		fillBombs(mines);
		setAllNeighborhoods();
	}
	
	void	setAllNeighborhoods() {
		for (int i = 0; i < this.line; i++)
		{
			for (int j = 0; j < this.column; j++)
				field[i][j].setNeighborhood(getNeighborhood(i, j));
		}
	}

	void	fillBombs(int mines) {
		int	position[] = new int[2];

		while (mines > 0)
		{
			position[0] = (int)(Math.random() * this.line);
			position[1] = (int)(Math.random() * this.column);
			if (field[position[0]][position[1]].isBomb() == false) {
				field[position[0]][position[1]].setBomb(true);
				mines--;
			}
		}
	}

	void	newField(int x, int y) {
		this.field = new Block[x][y];

		for (int i = 0; i < x; i++)
		{
			for (int j = 0; j < y; j++)
				field[i][j] = new Block();
		}
	}

	boolean	checkWin() {
		if (opened == (totalBlocks - totalMines))
			return (true);
		return (false);
	}

	List<Block> getNeighborhood(int x, int y) {
		List<Block> neighborhood = new ArrayList<>();

		for (int i = -1; i < 2; i++)
		{
			for (int j = -1; j < 2; j++)
			{
				if (blockExist(x + i, y + j)
					&& (i != 0 || j != 0))
					neighborhood.add(field[x + i][y + j]);
			}
		}
		return (neighborhood);
	}
	
	public boolean	stepOnMine() {
		return (this.lost);
	}

	char openBlock(int x, int y) {
		if (!blockExist(x, y))
			return ('O');
		clicked = field[x][y];
		if (!clicked.isFlagged() && !clicked.isOpen())
		{
			clicked.setOpen(true);
			if (!clicked.isBomb())
				openNeighborhood(clicked);
			else
				this.lost = true;
		}
		return (clicked.getMask());
	}

	boolean isBomb(int x, int y) {
		return (field[x][y].isBomb());
	}
 
	void	flagBlock(int x, int y) {
		if (!blockExist(x, y))
			return ;
		clicked = field[x][y];
		if (clicked.isOpen() == true)
			return ;
		if (clicked.isFlagged())
			clicked.setFlagged(false);
		else
			clicked.setFlagged(true);
	}

	private boolean	blockExist(int x, int y) {
		if (x >= 0 && x < this.line
			&& y >= 0 && y < this.column)
			return (true);
		return (false);
	}

	private void	openNeighborhood(Block block) 
	{ 
		if (block.getNeighborhood().stream().noneMatch(Block::isBomb))
			block.getNeighborhood().stream().filter(b -> !b.isOpen()).forEach(b -> {
				b.setOpen(true);
				this.opened++;
				openNeighborhood(b);
			});
	}

	void	printField() {
		System.out.print("     ");
		for (int i = 0; i < this.line; i++)
			System.out.print(i + " ");
		System.out.println();
		System.out.print("     ");
		for (int i = 0; i < this.line; i++)
			System.out.print("- ");
		System.out.println();
		for (int i = 0; i < this.line; i++)
		{
			if (i < 10)
				System.out.print("0" + i + " | ");
			else
				System.out.print(i + " | ");
			for (Block block : field[i])
				System.out.print(block.getMask() + " ");
			System.out.println("|");
		}
		System.out.print("    ");
		for (int i = 0; i < this.line; i++)
			System.out.print("- ");
		System.out.println();
	}
}