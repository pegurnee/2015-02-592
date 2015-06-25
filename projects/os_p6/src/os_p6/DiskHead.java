package os_p6;

public class DiskHead {
	private int position;

	public DiskHead() {
		this.position = 0;
	}

	public int moveTo(int position) {
		this.position = position;
		return 1;
	}

	public int next() {
		this.position++;
		return 1;
	}

	public int reset() {
		this.position = 0;
		return 1;
	}

	public int travelTo(int position) {
		int toReturn = Math.abs(this.position - position);
		this.position = position;
		return toReturn;
	}
}
