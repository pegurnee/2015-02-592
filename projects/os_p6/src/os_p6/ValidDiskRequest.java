package os_p6;

import java.util.Arrays;

public class ValidDiskRequest implements DiskRequest {

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
		if (this.type != RequestType.PRINT) {
			this.filename = tokens[1];
			if ("add".equals(tokens[0]) || "append".equals(tokens[0])) {
				this.size = Integer.parseInt(tokens[2]);
			}
			if (this.filename == null) {
				throw new DiskAllocationException();
			}
			if (!((this.type == RequestType.DELETE) || (this.type == RequestType.READ))
					&& !(this.size > 0)) {
				throw new DiskAllocationException();
			}
		}
	}

	public final String getFilename() {
		return this.filename;
	}

	public final int getSize() {
		return this.size;
	}

	@Override
	public final RequestType getType() {
		return this.type;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ValidDiskRequest [filename=" + this.filename + ", size="
				+ this.size + ", type=" + this.type + "]";
	}
}
