import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

public class Block {

		char				mask = 'O';
		private char		face = ' ';

		private boolean		open = false;
		private boolean		bomb = false;
		private boolean		flagged = false;
		private	long		bombsAround = 0;
		
		private List<Block>	neighborhood = null;
		
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

		void setOpen(boolean att) {
			this.open = att;
			if (att == true)
				this.mask = this.face;
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
			if (att == true)
				this.mask = 'f';
			else
				this.mask = 'O';
		}

		public void setNeighborhood(List<Block> neighborhood) {
			this.neighborhood = neighborhood;
			this.bombsAround = neighborhood.stream()
								.filter(neighbor -> neighbor.isBomb())
								.count();
			if (bombsAround > 0 && this.bomb == false)
				this.face = Long.toString(bombsAround).charAt(0);
		}
}
