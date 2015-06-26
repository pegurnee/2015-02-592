package os_p6.io.api;

public class InvalidDiskRequest implements DiskRequest {
	private final String requestString;

	public InvalidDiskRequest(String requestString) {
		this.requestString = requestString;
	}

	@Override
	public RequestType getType() {
		return RequestType.INVALID;
	}

	@Override
	public String toString() {
		return "Invalid Disk Request: " + this.requestString;
	}

}
