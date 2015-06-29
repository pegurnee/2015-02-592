package os.proj.p6.allocator;

import java.util.LinkedList;

import os.proj.p6.blockunit.FileData;
import os.proj.p6.io.api.RequestResult;
import os.proj.p6.io.api.ValidDiskRequest;
import os.proj.p6.io.api.RequestResult.Response;

public class ContiguousAllocator extends DiskDriveAllocator {

	private class ValidHole implements Comparable<ValidHole> {
		private final int size;
		private final int startLoc;

		ValidHole(int size, int startLoc) {
			this.size = size;
			this.startLoc = startLoc;
		}

		@Override
		public int compareTo(ValidHole o) {
			final int sizeCompare = Integer.compare(o.size, this.size);
			return sizeCompare == 0 ? Integer
					.compare(this.startLoc, o.startLoc) : sizeCompare;
		}
	}

	public ContiguousAllocator(int numBlocks) {
		super(numBlocks);
		this.type = AllocationType.CONTIGUOUS;
	}

	@Override
	protected RequestResult add(ValidDiskRequest theRequest) {
		// TODO Auto-generated method stub
		final int startPosition = this.readWriteHead.currentPosition();
		final LinkedList<ValidHole> hole = new LinkedList<>();
		int moves = 0;
		boolean success = false;
		RequestResult toReturn;

		do {
			int numFree = this.hasNumContiguousFree();

			if (numFree >= theRequest.getSize()) {
				hole.add(new ValidHole(numFree, this.readWriteHead
						.currentPosition()));
				success = true;
			}
			moves += this.readWriteHead.next();
		} while (!success
					&& (this.readWriteHead.currentPosition() != startPosition));

		if (!success) {
			toReturn = new RequestResult(Response.FAILURE,
					"There is no room to add " + theRequest.getFilename());
		} else {
			hole.sort(null);
			this.readWriteHead.moveTo(hole.getFirst().startLoc);
			for (int i = 0; i < theRequest.getSize(); i++) {
				this.currentBlock().setData(
						new FileData(theRequest.getFilename(), i, theRequest
								.getSize()));
				moves += this.readWriteHead.next();
			}
			toReturn = new RequestResult(theRequest);
		}

		return toReturn;
	}

	@Override
	protected RequestResult append(ValidDiskRequest theRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected RequestResult delete(ValidDiskRequest theRequest) {
		// TODO Auto-generated method stub
		final String filename = theRequest.getFilename();
		final int startPosition = this.readWriteHead.currentPosition();
		int moves = 0;
		boolean success = false;
		RequestResult toReturn;

		do {
			if (this.doesLocationHave(filename)) {
				final FileData fileData = (FileData) this.currentBlock()
						.getData();
				int position = this.readWriteHead.currentPosition()
								- fileData.getPosition();
				if (position < 0) {
					position += this.blocks.length;
				}
				moves += this.readWriteHead.moveTo(position);

				for (int i = 0; i < fileData.getSize(); i++) {
					this.currentBlock().clearData();
					moves += this.readWriteHead.next();
				}
				success = true;
			} else {
				moves += this.readWriteHead.next();
			}
		} while (!success
				&& (startPosition != this.readWriteHead.currentPosition()));

		if (!success) {
			toReturn = new RequestResult(Response.FAILURE,
					theRequest.getFilename() + " to delete not found.");
		} else {
			toReturn = new RequestResult(theRequest);
		}

		return toReturn;
	}

	@Override
	protected RequestResult read(ValidDiskRequest theRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
