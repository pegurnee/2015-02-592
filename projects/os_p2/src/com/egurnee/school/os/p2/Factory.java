package com.egurnee.school.os.p2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Factory extends JFrame {

	private class WorkPanel extends JPanel {
		private Color bgcolor;

		public WorkPanel(Color bgcolor) {
			super();
			this.setSize(WORKER_SIZE, WORKER_SIZE);
			this.bgcolor = bgcolor;
		}

		private void apply(int numWorking) {
			switch (numWorking) {
				case 0:
					this.bgcolor = Color.WHITE;
					break;
				case 1:
					this.bgcolor = Color.LIGHT_GRAY;
					break;
				case 2:
					this.bgcolor = Color.GRAY;
					break;
				case 3:
					this.bgcolor = Color.DARK_GRAY;
					break;
				default:
					break;
			}
		}

		private void apply(WorkerStatus status) {
			switch (status) {
				case FINISHED:
					this.bgcolor = Color.RED;
					break;
				case WAITING:
					this.bgcolor = Color.GREEN;
					break;
				case WORKING:
					this.bgcolor = Color.YELLOW;
					break;
				default:
					break;

			}
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(this.bgcolor);
			g.fillRect(0, 0, WORKER_SIZE, WORKER_SIZE);
		}
	}

	private static final int FACTORY_HEIGHT = 500;
	private static final int FACTORY_WIDTH = 800;

	private static final int WORKER_SIZE = 100;

	public static void main(String[] args) {
		new AssemblyLine().runAll();
		new Factory().setVisible(true);
	}

	private final WorkPanel[] assemblyPanels;
	private final AssemblyLine dataModel;
	private final WorkPanel[] workerPanels;

	public Factory() {
		this.setResizable(false);
		this.setSize(FACTORY_WIDTH, FACTORY_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.BLACK);
		this.setLayout(new GridLayout(1, 7));

		this.dataModel = new AssemblyLine();
		this.assemblyPanels = new WorkPanel[this.dataModel.getCounts().length];
		this.workerPanels = new WorkPanel[this.dataModel.getStatuses().length];

		for (int i = 0; i < this.assemblyPanels.length; i++) {
			this.assemblyPanels[i] = new WorkPanel(Color.RED);
		}
		for (int i = 0; i < this.workerPanels.length; i++) {
			this.workerPanels[i] = new WorkPanel(Color.BLUE);
		}
		for (int i = 0; i < this.assemblyPanels.length; i++) {
			this.add(this.workerPanels[i]);
			this.add(this.assemblyPanels[i]);
		}
		this.add(this.workerPanels[this.workerPanels.length - 1]);

		this.repaint();

		Timer t = new Timer(100, e -> Factory.this.repaint());
		// t.start();
		Timer r = new Timer(100, e -> {
			int[] counts = Factory.this.dataModel.getCounts();
			for (int i = 0; i < Factory.this.assemblyPanels.length; i++) {
				Factory.this.assemblyPanels[i].apply(counts[i]);
			}
			WorkerStatus[] statuses = this.dataModel.getStatuses();
			for (int i = 0; i < this.workerPanels.length; i++) {
				this.workerPanels[i].apply(statuses[i]);
			}
			Factory.this.repaint();

		});
		r.start();
		this.dataModel.runAll();
	}
}
