package os_p6;

import java.util.LinkedList;

import os_p6.RequestResult.Response;

public abstract class DiskDriveAllocator {
	public enum AllocationType {
		CONTIGUOUS, INDEXED;
	}

	protected Block[] blocks;;
	protected DiskHead readWriteHead;
	protected AllocationType type;

	public DiskDriveAllocator(int numBlocks) {
		this.blocks = new Block[numBlocks];
		for (int i = 0; i < this.blocks.length; i++) {
			this.blocks[i] = new Block();
		}
		this.readWriteHead = new DiskHead(numBlocks);
	}

	public RequestResult handleRequest(DiskRequest theRequest) {

		RequestResult toReturn;
		switch (theRequest.getType()) {
			case ADD:
				toReturn = this.add((ValidDiskRequest) theRequest);
				break;
			case APPEND:
				toReturn = this.append((ValidDiskRequest) theRequest);
				break;
			case DELETE:
				toReturn = this.delete((ValidDiskRequest) theRequest);
				break;
			case PRINT:
				toReturn = this.print((ValidDiskRequest) theRequest);
				break;
			case READ:
				toReturn = this.read((ValidDiskRequest) theRequest);
				break;
			case INVALID:
				toReturn = new RequestResult(Response.FAILURE,
						((InvalidDiskRequest) theRequest).toString());
				break;
			default:
				toReturn = null;
				break;
		}
		return toReturn;
	}

	private final RequestResult print(ValidDiskRequest theRequest) {
		StringBuilder printableString = new StringBuilder(
				"============== Current Drive Contents =================\n");

		LinkedList<String> filenames = new LinkedList<>();
		LinkedList<StringBuilder> fileOutput = new LinkedList<>();
		StringBuilder blocksString = new StringBuilder("\n Details:");

		for (int i = 0; i < this.blocks.length; i++) {
			if ((i % 10) == 0) {
				blocksString.append("\n");
			}
			Block block = this.blocks[i];
			if (!block.isFree()) {
				final String filename = block.getData().getFilename();
				if (!filenames.contains(filename)) {
					filenames.add(filename);
					fileOutput.add(new StringBuilder("\n" + filenames.size()
							+ ") " + filename
							+ "\n\tBlocks:"));
				}
				final int indexOfName = filenames.indexOf(filename);
				fileOutput.get(indexOfName).append(" " + i);
				blocksString.append(indexOfName + 1);
			} else {
				blocksString.append("*");
			}
			blocksString.append(" ");
		}

		printableString.append("\n Files:");
		for (StringBuilder stringBuilder : fileOutput) {
			printableString.append(stringBuilder);
		}
		printableString.append(blocksString);

		return new RequestResult(Response.SUCCESS, printableString.toString());
	}

	protected abstract RequestResult add(ValidDiskRequest theRequest);

	protected abstract RequestResult append(ValidDiskRequest theRequest);

	protected final Block currentBlock() {
		return this.blocks[this.readWriteHead.currentPosition()];
	}

	protected abstract RequestResult delete(ValidDiskRequest theRequest);

	protected final boolean doesLocationHave(String filename) {
		return this.currentBlock().isFree() ? false : filename.equals(this
				.currentBlock().getData().getFilename());
	}

	protected final int hasNumContiguousFree() {
		final int limit = this.blocks.length;

		int numFree = 0;
		int i = this.readWriteHead.currentPosition();
		while (this.blocks[i].isFree()) {
			numFree++;
			i = (i + 1) % limit;
			if (numFree == limit) {
				break;
			}
		}
		return numFree;
	}

	protected final boolean isLocationFree() {
		return this.currentBlock().isFree();
	}

	protected abstract RequestResult read(ValidDiskRequest theRequest);
}
