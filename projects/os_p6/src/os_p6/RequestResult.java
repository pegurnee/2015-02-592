package os_p6;

public class RequestResult {
	private final String output;

	public RequestResult(DiskAllocationException exception) {
		this.output = exception.getMessage();
	}

	public RequestResult(String result) {
		this.output = result;
	}

	public String getOutput() {
		return this.output;
	}

	@Override
	public String toString() {
		return this.output;
	}

}
