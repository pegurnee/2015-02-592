package os.proj.p2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import os.proj.p2.assembly.AssemblyLine;
import os.proj.p2.gui.WorkPanel;
import os.proj.p2.workers.WorkerStatus;

public class Factory extends JFrame {

	private static final int FACTORY_HEIGHT = 500;
	private static final int FACTORY_WIDTH = 800;

	/**
	 * default generated
	 */
	private static final long serialVersionUID = 9172256208819417181L;

	public static void main(String[] args) {
		// new AssemblyLine().runAll();
		new Factory().setVisible(true);
	}

	private final AssemblyLine dataModel;
	private final WorkPanel[] panelsAssembly;
	private final WorkPanel[] panelsWorker;

	public Factory() {
		this.setResizable(false);
		this.setSize(FACTORY_WIDTH, FACTORY_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());

		JPanel center = new JPanel(new GridLayout(1, 7));
		this.add(center, BorderLayout.CENTER);

		this.dataModel = new AssemblyLine();
		this.panelsAssembly = new WorkPanel[this.dataModel.getCounts().length];
		this.panelsWorker = new WorkPanel[this.dataModel.getStatuses().length];

		for (int i = 0; i < this.panelsAssembly.length; i++) {
			this.panelsAssembly[i] = new WorkPanel(Color.RED);
		}
		for (int i = 0; i < this.panelsWorker.length; i++) {
			this.panelsWorker[i] = new WorkPanel(Color.BLUE);
		}
		for (int i = 0; i < this.panelsAssembly.length; i++) {
			center.add(this.panelsWorker[i]);
			center.add(this.panelsAssembly[i]);
		}
		center.add(this.panelsWorker[this.panelsWorker.length - 1]);

		this.repaint();

		Timer r = new Timer(100, e -> {
			int[] counts = Factory.this.dataModel.getCounts();
			for (int i = 0; i < Factory.this.panelsAssembly.length; i++) {
				Factory.this.panelsAssembly[i].apply(counts[i]);
			}
			WorkerStatus[] statuses = this.dataModel.getStatuses();
			for (int i = 0; i < this.panelsWorker.length; i++) {
				this.panelsWorker[i].apply(statuses[i]);
			}
			Factory.this.repaint();

		});
		r.start();
		this.dataModel.runAll();
	}
}
