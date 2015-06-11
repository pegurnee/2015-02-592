package com.egurnee.school.os.p4;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class RandomTimeInput {
	static String folder = "assets/";

	public static void main(String[] args) {
		Scanner in = null;
		PrintWriter out = null;

		final String file = "";

		try {
			in = new Scanner(new FileInputStream(folder + file));
			String output = "";
			while (in.hasNext()) {
				output += in.next().trim() + " " + submitTime() + " "
							+ taskTime() + "\n";
			}
			in.close();

			System.out.println(output);
			out = new PrintWriter(new File(folder + file));
			out.print(output);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			in.close();
			out.close();
		}

	}

	private static int submitTime() {
		return time(10) * 100;
	}

	private static int taskTime() {
		return (time(50) * 10) + 100;
	}

	private static int time(int limit) {
		return (int) (Math.random() * limit);
	}

}
