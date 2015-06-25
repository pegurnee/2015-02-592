package os_p6;

import java.util.LinkedList;

public abstract class DiskDriveAllocator {
	protected Block[] blocks;
	protected DiskHead readWriteHead;

	public RequestResult handleRequest(DiskRequest theRequest) {
		RequestResult toReturn;
		switch (theRequest.getType()) {
			case ADD:
				toReturn = this.add(theRequest);
				break;
			case APPEND:
				toReturn = this.append(theRequest);
				break;
			case DELETE:
				toReturn = this.delete(theRequest);
				break;
			case PRINT:
				toReturn = this.print(theRequest);
				break;
			case READ:
				toReturn = this.read(theRequest);
				break;
			default:
				toReturn = null;
				break;
		}
		return toReturn;
	}

	protected abstract RequestResult add(DiskRequest theRequest);

	protected abstract RequestResult append(DiskRequest theRequest);

	protected abstract RequestResult delete(DiskRequest theRequest);

	protected RequestResult print(DiskRequest theRequest) {
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
					fileOutput.add(new StringBuilder(fileOutput.size() + ". "
														+ filename
														+ "\n\tBlocks:"));
				}
				final int indexOfName = filenames.indexOf(filename);
				fileOutput.get(indexOfName).append(" " + i);
				blocksString.append(indexOfName);
			} else {
				blocksString.append("*");
			}
			blocksString.append(" ");
		}

		printableString.append("\n Files:\n");
		for (StringBuilder stringBuilder : fileOutput) {
			printableString.append(stringBuilder);
		}
		printableString.append(blocksString);

		return new RequestResult(printableString.toString());
	};

	protected abstract RequestResult read(DiskRequest theRequest);
}
