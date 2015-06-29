package os.proj.p5;

public class Frames {

	private int[] numFrames;

	public boolean contains(int page) {
		for (int i : this.numFrames) {
			if (page == i) {
				return true;
			}
		}
		return false;
	}

	public int get(int position) {
		return this.numFrames[position];
	}

	public int set(int position, int value) {
		int temp = this.numFrames[position];
		this.numFrames[position] = value;
		return temp;
	}

	public void SetNumFrames(int num) {
		this.numFrames = new int[num];
	}

	public int Size() {
		return this.numFrames.length;
	}
}
