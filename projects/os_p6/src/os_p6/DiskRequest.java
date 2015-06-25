package os_p6;

import java.util.Arrays;

public class DiskRequest {
	public enum RequestType {
		ADD, APPEND, DELETE, PRINT, READ;
	}

	public static void main(String[] args) {
		new DiskRequest("print");
		new DiskRequest("read \"fourth in line.dat\"");
		new DiskRequest("add \"second hand.txt\" 8");
	}

	private String filename;
	private int size;

	private RequestType type;

	public DiskRequest(String requestLine) {
		String[] tokens = Arrays.stream(requestLine.split("\""))
				.map(x -> x.trim()).toArray(size -> new String[size]);
		switch (tokens[0]) {
			case "add":
				System.out.println(tokens[2]);
				break;
			case "append":

				break;
			case "del":
			case "delete":

				break;
			case "read":
				System.out.println(tokens[1]);
				break;
			case "print":
				System.out.println(tokens[0]);
				break;

			default:
				// for (String string : tokens) {
				// System.out.println(string);
				// }
				break;
		}
	}
}
