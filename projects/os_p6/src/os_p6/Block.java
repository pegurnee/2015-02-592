package os_p6;

public class Block {
	private Allocatable data;

	public Block() {
		this.data = null;
	}

	public void clearData() {
		this.data = null;
	}

	public Allocatable getData() {
		return this.data;
	}

	public boolean isFree() {
		return this.data == null;
	}

	public void setData(Allocatable data) {
		this.data = data;
	}
}
