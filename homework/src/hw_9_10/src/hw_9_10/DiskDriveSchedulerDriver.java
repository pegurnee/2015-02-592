package hw_9_10;

import java.util.LinkedList;

public class DiskDriveSchedulerDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LinkedList<Integer> test = new LinkedList<Integer>();
		int[] nums = { 2296, 2069, 1212, 3681, 2800, 544, 1618, 356, 1523, 4965 };
		// int[] nums = { 86, 1470, 913, 1774, 948, 1509, 1022, 1750, 130 };
		// int start = 143;
		int start = 2_150;
		// int prev = 125;
		int prev = 1_805;
		int cylinders = 5000;
		for (int i : nums) {
			test.add(i);
		}

		final SchedulerType[] enums = SchedulerType.values();
		for (SchedulerType schedulerType : enums) {
			System.out.println(SchedulerAlgorithmFactory.createAlgorithm(
					schedulerType).runWith(new LinkedList<Integer>(test),
							start, prev, cylinders));
		}
	}
}
