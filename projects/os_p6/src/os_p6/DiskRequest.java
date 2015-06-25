package os_p6;

public interface DiskRequest {
	public enum RequestType {
		ADD, APPEND, DELETE, INVALID, PRINT, READ;
	}

	RequestType getType();
}
