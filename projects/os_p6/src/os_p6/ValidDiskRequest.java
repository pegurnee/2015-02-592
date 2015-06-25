package os_p6;

import java.util.Arrays;

public class ValidDiskRequest implements DiskRequest {

	public static void main(String[] args) throws DiskAllocationException {
		new ValidDiskRequest("print");
		new ValidDiskRequest("read \"fourth in line.dat\"");
		new ValidDiskRequest("add \"second hand.txt\" 8");
	}

	private String filename;
	private int size;

	private RequestType type;

	public ValidDiskRequest(String requestLine) throws DiskAllocationException {
		String[] tokens = Arrays.stream(requestLine.split("\""))
				.map(x -> x.trim()).toArray(size -> new String[size]);
		switch (tokens[0]) {
			case "add":
				this.type = RequestType.ADD;
				break;
			case "append":
				this.type = RequestType.APPEND;
				break;
			case "del":
			case "delete":
				this.type = RequestType.DELETE;
				break;
			case "read":
				this.type = RequestType.READ;
				break;
			case "print":
				this.type = RequestType.PRINT;
				break;
			default:
				throw new DiskAllocationException();
		}
		if (!"print".equals(tokens[0])) {
			this.filename = tokens[1];
			if ("add".equals(tokens[0]) || "append".equals(tokens[0])) {
				this.size = Integer.parseInt(tokens[2]);
			}
		}
	}

	/**
	 * @return the filename
	 * @throws DiskAllocationException
	 */
	public final String getFilename() throws DiskAllocationException {
		if (this.filename == null) {
			throw new DiskAllocationException();
		}
		return this.filename;
	}

	/**
	 * @return the size
	 * @throws DiskAllocationException
	 */
	public final int getSize() throws DiskAllocationException {
		if (!(this.size > 0)) {
			throw new DiskAllocationException();
		}
		return this.size;
	}

	/**
	 * @return the type
	 */
	public final RequestType getType() {
		return this.type;
	}
}
