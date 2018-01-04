public class ThreadA {
	public static void main(String[] args) {
		ThreadB b = new ThreadB();
		b.start();
		try {
			b.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(b.isInterrupted())
		System.out.println("Total is: " + b.total);
 
	}
}
 
class ThreadB extends Thread {
	int total;
 
	@Override
	public void run() {
		for (int i = 0; i < 999999999; i++) {
			if(i==999999)
				notify();
		}
	}
}