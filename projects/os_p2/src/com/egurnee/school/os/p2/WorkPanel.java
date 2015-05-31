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
	private int numInBuffer;
	private Type panelType;
	private WorkerStatus status;

	public WorkPanel(Color bgcolor) {
		super();
		this.setSize(WORKER_SIZE, WORKER_SIZE);
		this.bgcolor = bgcolor;

	}

	public void apply(int numWorking) {
		this.panelType = Type.SEGMENT;
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
		this.numInBuffer = numWorking;
	}

	public void apply(WorkerStatus status) {
		this.panelType = Type.WORKER;
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
		this.status = status;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.panelType != null) {
			switch (this.panelType) {
				case SEGMENT:
					g.setColor(Color.BLACK);
					g.drawRect(2, 30, 30, 30);
					g.drawRect(34, 30, 30, 30);
					g.drawRect(66, 30, 30, 30);
					switch (this.numInBuffer) {
						case 3:
							g.fillRect(2, 30, 30, 30);
						case 2:
							g.fillRect(34, 30, 30, 30);
						case 1:
							g.fillRect(66, 30, 30, 30);
					}
					break;
				case WORKER:
					g.setColor(this.bgcolor);
					g.fillRect(0, 0, WORKER_SIZE, WORKER_SIZE);
					g.setColor(Color.BLACK);
					g.drawString(this.status.getState(), 3, 40);
					break;
				default:
					break;
			}
		}
	}
};
