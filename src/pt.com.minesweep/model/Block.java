package pt.com.minesweep.model;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Block {

		private String			faceValue = "";
		private	Color			background = Color.LIGHT_GRAY;
		private final String	flag = "\uD83D\uDEA9";
		private boolean			open = false;
		private boolean			bomb = false;
		private boolean			flagged = false;
		private	long			bombsAround = 0;
		private List<Block>		neighborhood = null;
		final private JButton	button = new JButton();
		final private Color[]	colors = {new Color(255, 255, 255),
				new Color(0, 111, 255),
				new Color(0, 204, 0),
				new Color(255, 0, 0),
				new Color(0, 76, 176),
				new Color(153, 0, 0 ),
				new Color(102, 255, 255),
				new Color(0, 0, 0),
				new Color(160, 160, 160)
		};

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
			this.button.setBackground(new Color(184, 184, 184));
			this.button.setOpaque(true);
			this.button.setBorder(BorderFactory.createBevelBorder(0));
			this.button.putClientProperty("type", "block");
			this.button.putClientProperty("content", this);
		}
		void setOpen(boolean att) {
			Color	textColor = this.isBomb() ? Color.black : colors[(int)bombsAround];

			this.open = att;
			button.setBorder(BorderFactory.createLoweredSoftBevelBorder());
			button.setForeground(textColor);
			button.setText(this.faceValue);
			button.setBackground(this.background);
		}

		void setBomb(boolean att) {
			this.bomb = att;
			if (att == true)
				this.faceValue = "✱";
			this.background = Color.RED;
		}

		void setFlagged(boolean att) {
			this.flagged = att;
			if (att == true) {
				button.setForeground(Color.RED);
				this.button.setText(flag);
			}
			else
				this.button.setText("");
		}

		public void setNeighborhood(List<Block> neighborhood) {
			this.neighborhood = neighborhood;
			this.bombsAround = neighborhood.stream()
								.filter(neighbor -> neighbor.isBomb())
								.count();
			if (bombsAround > 0 && !this.isBomb())
				faceValue = Long.toString(bombsAround);
		}

		public JButton getButton() {
			return button;
		}
}
