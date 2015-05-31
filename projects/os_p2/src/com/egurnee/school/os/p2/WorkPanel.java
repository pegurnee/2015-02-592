package com.egurnee.school.os.p2;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class WorkPanel extends JPanel {

	enum Type {
		SEGMENT, WORKER
	}

	private static final int WORKER_SIZE = 100;
	private Color bgcolor;
	private Type panelType;

	public WorkPanel(Color bgcolor) {
		super();
		this.setSize(WORKER_SIZE, WORKER_SIZE);
		this.bgcolor = bgcolor;

	}

	public void apply(int numWorking) {
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

	public void apply(WorkerStatus status) {
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
};
