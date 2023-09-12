package pt.com.minesweep.model;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Field {
	
	private Block[][]	field = null;
	final private int	line;
	final private int	column;
	private int			opened = 0;
	final private int	totalMines;
	final private int	totalBlocks;
	private	boolean		lost = false;


	public Field(int x, int y, int mines) {
		this.line = x;
		this.column = y;
		this.totalBlocks = x * y;
		this.totalMines = mines;
		newField(x, y);
		fillBombs(mines);
		setAllNeighborhoods();
	}

	void	newField(int x, int y) {
		this.field = new Block[x][y];

		for (int i = 0; i < x; i++)
		{
			for (int j = 0; j < y; j++)
				field[i][j] = new Block();
		}
	}

	void	setAllNeighborhoods() {
		for (int i = 0; i < this.line; i++)
		{
			for (int j = 0; j < this.column; j++)
				field[i][j].setNeighborhood(getNeighborhood(i, j));
		}
	}

	void	fillBombs(int mines) {
		int[]	position = new int[2];

		while (mines > 0)
		{
			position[0] = (int)(Math.random() * this.line);
			position[1] = (int)(Math.random() * this.column);
			if (!field[position[0]][position[1]].isBomb()) {
				field[position[0]][position[1]].setBomb(true);
				mines--;
			}
		}
	}

	public boolean	checkWin() { return (opened == (totalBlocks - totalMines));	}

	private List<Block> getNeighborhood(int x, int y) {
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
	
	public boolean	stepOnMine() { return (this.lost); }

	public void openBlock(Block clicked) {
		if (clicked == null || clicked.isFlagged())
			return ;
		this.opened += clicked.isOpen() ? 0 : 1;
		clicked.setOpen(true);
		if (clicked.getMines() > 0 && !clicked.isBomb())
			chord(clicked);
		else if (!clicked.isBomb())
			openNeighborhood(clicked);
		else
		{
			Arrays.stream(field)
					.flatMap(Arrays::stream)
					.filter(Block::isBomb)
					.forEach(b -> b.setOpen(true));
			this.lost = true;
		}
	}

	private void chord(Block block) {
		long	flags = block.getNeighborhood().stream()
				.filter(Block::isFlagged)
				.count();
		if (flags == block.getMines()) {
			block.getNeighborhood().stream()
					.filter(b -> !b.isOpen() && !b.isFlagged())
					.forEach(this::openBlock);
		}
	}
	public void	flagBlock(Block clicked) {
		if (clicked == null || clicked.isOpen())
			return ;
		clicked.setFlagged(!clicked.isFlagged());
	}

	private boolean	blockExist(int x, int y) {

		return (x > -1 && x < this.line
				&& y > -1 && y < this.column);
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

	public List<JButton> getButtons() {
		List<JButton> collect = new ArrayList<>();

		Arrays.stream(field)
				.flatMap(Arrays::stream)
				.forEach(b -> collect.add(b.getButton()));
		return (collect);
	}
}

//	void	printField() {
//		System.out.print("     ");
//		for (int i = 0; i < this.line; i++)
//			System.out.print(i + " ");
//		System.out.println();
//		System.out.print("     ");
//		for (int i = 0; i < this.line; i++)
//			System.out.print("- ");
//		System.out.println();
//		for (int i = 0; i < this.line; i++)
//		{
//			if (i < 10)
//				System.out.print("0" + i + " | ");
//			else
//				System.out.print(i + " | ");
//			for (Block block : field[i])
//				System.out.print(block.getMask() + " ");
//			System.out.println("|");
//		}
//		System.out.print("    ");
//		for (int i = 0; i < this.line; i++)
//			System.out.print("- ");
//		System.out.println();
//	}
