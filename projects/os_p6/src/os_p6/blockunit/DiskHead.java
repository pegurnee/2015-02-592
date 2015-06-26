package os_p6.blockunit;

public class DiskHead {
	private final int limit;
	private int position;

	public DiskHead(int limit) {
		this.limit = limit;
		this.position = 0;
	}

	public int currentPosition() {
		return this.position;
	}

	public int moveTo(int position) {
		this.position = position;
		return 1;
	}

	public int next() {
		this.position = (this.position + 1) % this.limit;
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
