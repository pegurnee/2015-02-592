package os.proj.p2;

public class Widget {
	private int numberTouched;

	public Widget() {
		this.numberTouched = 0;
	}

	public int getWorked() {
		return this.numberTouched;
	}

	public void workUpon() {
		this.numberTouched++;
	}
}
