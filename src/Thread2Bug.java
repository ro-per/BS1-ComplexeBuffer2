import java.util.concurrent.Semaphore;

public class Thread2Bug extends Thread {
	private BlockList freeList;
	private BlockList list1;
	private BlockList list2;
	private Semaphore sem;
	private int M;

	public Thread2Bug(BlockList freeList, BlockList list1, BlockList list2, Semaphore sem, int M) {
		this.freeList = freeList;
		this.list1 = list1;
		this.list2 = list2;
		this.sem = sem;
		this.M = M;
	}

	public void run() {
		for (int i = 0; i < M; i++) {
			Block a = list1.unlink();// -> synchronized
			double temp = a.getNumber() / 2;
			Block b = freeList.unlink();// -> synchronized
			b.setNumber(temp);
			freeList.link(a);// -> synchronized

			Block c = list1.unlink();// -> synchronized
			Block d = freeList.unlink();// -> synchronized
			temp = c.getNumber() + b.getNumber();
			d.setNumber(temp);
			list1.link(c);// -> synchronized
			freeList.link(b);// -> synchronized
			list2.link(d);// -> synchronized
			// Release semaphore: ofwel hier, ofwel in Thread3
			sem.release();
			// Print hoeveel runs deze Threads reeds heeft gedaan
			System.out.println("THREAD 2 " + i);
		}
		// Geef het einde van de thread aan
		System.out.println("__________________________________EINDE 2__________________________________");
	}
}
