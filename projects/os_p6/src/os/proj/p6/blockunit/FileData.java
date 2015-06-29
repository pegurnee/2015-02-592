package os.proj.p6.blockunit;

public class FileData implements Allocatable {
	private final String filename;
	private final int position;
	private final int size;

	public FileData(String filename, int position, int size) {
		this.filename = filename;
		this.position = position;
		this.size = size;
	}

	/**
	 * @return the filename
	 */
	public final String getFilename() {
		return this.filename;
	}

	/**
	 * @return the position
	 */
	public final int getPosition() {
		return this.position;
	}

	/**
	 * @return the size
	 */
	public final int getSize() {
		return this.size;
	}

}
