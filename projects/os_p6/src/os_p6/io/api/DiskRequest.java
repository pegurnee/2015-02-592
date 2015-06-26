package os_p6.io.api;

public interface DiskRequest {
	public enum RequestType {
		ADD, APPEND, DELETE, INVALID, PRINT, READ;
	}

	RequestType getType();
}
