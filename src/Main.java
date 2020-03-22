import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Main {
	public static void main(String[] args) {
		final int N = 10; // initieel aantal blokken
		final int M = 100; // aantal transacties
		System.out.println("EDITED");
		System.out.println("[ freelist | list1 | list2 ]");
		// Maak gelinkte lijst (gelinkte lijst van freeList is private)
		LinkedList<Block> list = new LinkedList<Block>();
		for (int i = 0; i < N; i++) {
			list.add(new Block());
		}

		// BLOCKLISTS
		BlockList freeList = new BlockList(list);
		BlockList list1 = new BlockList();
		BlockList list2 = new BlockList();

		// Maak semafoor aan
		Semaphore sem = new Semaphore(N - 1);

		// Maak threads aan
		Thread1 t1 = new Thread1(freeList, list1, list2, sem, M);
		Thread2 t2 = new Thread2(freeList, list1, list2, sem, M);
		Thread3 t3 = new Thread3(freeList, list1, list2, sem, M);

		// Strat threads op
		t1.start();
		t2.start();
		t3.start();
	}
}
