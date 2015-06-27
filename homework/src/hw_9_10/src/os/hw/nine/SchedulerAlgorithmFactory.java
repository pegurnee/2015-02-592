package os.hw.nine;

import os.hw.nine.algorithm.C_LOOKAlgorithm;
import os.hw.nine.algorithm.C_SCANAlgorithm;
import os.hw.nine.algorithm.FCFSAlgorithm;
import os.hw.nine.algorithm.LOOKAlgorithm;
import os.hw.nine.algorithm.SCANAlgorithm;
import os.hw.nine.algorithm.SSTFAlgorithm;
import os.hw.nine.algorithm.SchedulerAlgorithm;

public class SchedulerAlgorithmFactory {

	public static SchedulerAlgorithm createAlgorithm(SchedulerType type) {
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
