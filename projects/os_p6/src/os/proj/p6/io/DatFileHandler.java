package os.proj.p6.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import os.proj.p6.allocator.ContiguousAllocator;
import os.proj.p6.allocator.DiskDriveAllocator;
import os.proj.p6.allocator.IndexedAllocator;
import os.proj.p6.allocator.DiskDriveAllocator.AllocationType;
import os.proj.p6.exceptions.DiskAllocationException;
import os.proj.p6.io.api.DiskRequest;
import os.proj.p6.io.api.InvalidDiskRequest;
import os.proj.p6.io.api.ValidDiskRequest;

public class DatFileHandler {
	private final String filepath;
	private final int numBlocks;

	public DatFileHandler(String filepath) throws FileNotFoundException {
		this.filepath = filepath;
		try (Scanner in = new Scanner(new FileInputStream(filepath))) {
			this.numBlocks = in.nextInt();
		}
	}

	public DiskDriveAllocator getDriveAllocator(AllocationType type) {
		DiskDriveAllocator allocator = null;
		switch (type) {
			case CONTIGUOUS:
				allocator = new ContiguousAllocator(this.numBlocks);
				break;
			case INDEXED:
				allocator = new IndexedAllocator(this.numBlocks);
				break;
			default:
				break;
		}
		return allocator;
	}

	public LinkedList<DiskRequest> getRequests() throws IOException {
		List<String> lines = Files.lines(Paths.get(this.filepath))
				.filter(x -> !x.matches("^-?\\d+$"))
				.collect(Collectors.toList());

		LinkedList<DiskRequest> toReturn = new LinkedList<>();
		for (String string : lines) {
			try {
				toReturn.add(new ValidDiskRequest(string));
			} catch (DiskAllocationException e) {
				toReturn.add(new InvalidDiskRequest(string));
			}
		}
		return toReturn;
	}
}
