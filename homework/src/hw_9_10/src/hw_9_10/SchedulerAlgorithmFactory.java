package hw_9_10;

public class SchedulerAlgorithmFactory {

	public SchedulerAlgorithm createAlgorithm(SchedulerType type) {
		SchedulerAlgorithm theAlgorithm;
		switch (type) {
			case C_LOOK:
				theAlgorithm = new C_LOOKAlgorithm();
				break;
			case C_SCAN:
				theAlgorithm = new C_SCANAlgorithm();
				break;
			case FCFS:
				theAlgorithm = new FCFSAlgorithm();
				break;
			case LOOK:
				theAlgorithm = new LOOKAlgorithm();
				break;
			case SCAN:
				theAlgorithm = new SCANAlgorithm();
				break;
			case SSTF:
				theAlgorithm = new SSTFAlgorithm();
				break;
			default:
				theAlgorithm = null;
				break;
		}
		return theAlgorithm;
	}
}
