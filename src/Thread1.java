import java.util.concurrent.Semaphore;

public class Thread1 extends Thread {
	private BlockList freeList;
	private BlockList list1;
	private BlockList list2;
	private Semaphore sem;
	private int M;

	public Thread1(BlockList freeList, BlockList list1, BlockList list2, Semaphore sem, int M) {
		this.freeList = freeList;
		this.list1 = list1;
		this.list2 = list2;
		this.sem = sem;
		this.M = M;
	}

	public void run() {
		for (int i = 0; i < M; i++) {
			try {
				sem.acquire();
			} catch (Exception e) {
				System.out.println("sem.acquire() geeft problemen");
			}
			Block b = freeList.unlink(); // -> synchronized
			b.setNumber(Math.random() * 10000);
			list1.link(b);// -> synchronized

			// Print hoeveel runs deze Threads reeds heeft gedaan
			System.out.println("THREAD 1 " + i);
		}
		// Geef het einde van de thread aan
		System.out.println("__________________________________EINDE 1__________________________________");
	}
}
