package os.proj.p6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import os.proj.p6.allocator.DiskDriveAllocator;
import os.proj.p6.allocator.DiskDriveAllocator.AllocationType;
import os.proj.p6.io.DatFileHandler;
import os.proj.p6.io.api.DiskRequest;
import os.proj.p6.io.api.RequestResult;

public class ProjectSixDriver {
	private final static String INPUT_FILE_LOCATION = "assets/";

	public static void main(String[] args) {
		// TODO
		try {
			final DatFileHandler datFileHandler = new DatFileHandler(
					INPUT_FILE_LOCATION + "disk.dat");
			final DiskDriveAllocator driveAllocator = datFileHandler
					.getDriveAllocator(AllocationType.CONTIGUOUS);
			final LinkedList<DiskRequest> requests = datFileHandler
					.getRequests();
			for (DiskRequest diskRequest : requests) {
				System.out.println(diskRequest + "\n___________________");
				final RequestResult handleRequest = driveAllocator
						.handleRequest(diskRequest);
				System.out.println(handleRequest);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("fml");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("ffs");
			e.printStackTrace();
		}
	}
}
