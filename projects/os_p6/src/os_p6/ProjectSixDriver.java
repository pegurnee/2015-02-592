package os_p6;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import os_p6.allocator.DiskDriveAllocator;
import os_p6.allocator.DiskDriveAllocator.AllocationType;
import os_p6.io.DatFileHandler;
import os_p6.io.api.DiskRequest;
import os_p6.io.api.RequestResult;

public class ProjectSixDriver {
	public static void main(String[] args) {
		// TODO
		try {
			final DatFileHandler datFileHandler = new DatFileHandler("disk.dat");
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
