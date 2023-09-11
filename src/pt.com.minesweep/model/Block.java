package pt.com.minesweep.model;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Block {

		char					mask = 'O';
		private char			face = ' ';
		private boolean			open = false;
		private boolean			bomb = false;
		private boolean			flagged = false;
		private	long			bombsAround = 0;
		private List<Block>		neighborhood = null;
		final private JButton	button = new JButton();
		
		char getMask() {
			return this.mask;
		}
		
		boolean isOpen() {
			return this.open;
		}
		
		boolean isBomb() {
			return this.bomb;
		}
		
		boolean isFlagged() {
			return this.flagged;
		}

		long	getMines() {
			return (this.bombsAround);
		}

		List<Block>	getNeighborhood() {
			return (neighborhood);
		}

		public Block() {
			this.button.putClientProperty("type", "block");
			this.button.putClientProperty("content", this);
		}
		void setOpen(boolean att) {
			this.open = att;
			if (att == true)
				this.mask = this.face;
			button.setBackground(new Color(255, 0, 0));
			if (!this.isBomb()) {
				this.button.setText(this.face + "");
				button.setBackground(new Color(0, 255, 0));
			}
		}

		void setBomb(boolean att) {
			this.bomb = att;
			if (att == true)
				this.face = '*';
		}

		void setMask(char att) {
			this.mask = att;
		}

		void setFlagged(boolean att) {
			this.flagged = att;
			if (att == true) {
				this.mask = 'f';
				this.button.setText("P");
			}
			else {
				this.button.setText("");
				this.mask = 'O';
			}
		}

		public void setNeighborhood(List<Block> neighborhood) {
			this.neighborhood = neighborhood;
			this.bombsAround = neighborhood.stream()
								.filter(neighbor -> neighbor.isBomb())
								.count();
			if (bombsAround > 0 && this.bomb == false)
				this.face = Long.toString(bombsAround).charAt(0);
		}

	public JButton getButton() {
		return button;
	}
}
