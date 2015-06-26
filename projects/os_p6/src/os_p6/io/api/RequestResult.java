package os_p6.io.api;

import os_p6.exceptions.DiskAllocationException;

public class RequestResult {
	public enum Response {
		FAILURE, SUCCESS;
	}

	private final String output;
	private final Response type;

	public RequestResult(DiskAllocationException exception) {
		this.type = Response.FAILURE;
		this.output = exception.getMessage();
	}

	public RequestResult(DiskRequest request) {
		final ValidDiskRequest validDiskRequest = (ValidDiskRequest) request;
		final String filename = ((ValidDiskRequest) request).getFilename();
		String outputString = "";

		this.type = Response.SUCCESS;
		switch (request.getType()) {
			case ADD:
				outputString = "File added: " + filename;
				break;
			case APPEND:
				outputString = "File " + filename + " increased by "
						+ validDiskRequest.getSize();
				break;
			case DELETE:
				outputString = "File deleted: " + filename;
				break;
			case READ:
				outputString = "File read: " + filename;
				break;
			case INVALID:
			case PRINT:
			default:
				System.exit(1);
				break;
		}
		this.output = outputString;
	}

	public RequestResult(Response type, String result) {
		this.type = type;
		this.output = result;
	}

	public Response getAcknowledgement() {
		return this.type;
	}

	public String getOutput() {
		return this.output;
	}

	@Override
	public String toString() {
		return this.output;
	}

}
