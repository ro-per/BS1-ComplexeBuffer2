import java.util.*;

public class BlockList {
	private LinkedList<Block> list;

	public BlockList() {
		this.list = new LinkedList<Block>();
	}

	public BlockList(LinkedList<Block> list) {
		this.list = list;
	}

	public synchronized void link(Block b) {
		// voeg het block achteraan de lijst toe
		list.add(b);
		this.notifyAll();
	}

	public synchronized Block unlink() {
		// verwijder het eerste block uit de lijst
		while (list.isEmpty()) { // anders runtime error
			try {
				wait();
				this.notifyAll();

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return list.remove();
	}

	public int getCount() {
		// geef de grootte van de lijst terug
		return list.size();
	}

	public boolean isLeeg() {
		return list.isEmpty();
	}
}
