package com.egurnee.school.os.p2.sample;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
//import javafx.concurrent.Worker;
import javax.swing.JButton;
import javax.swing.Timer;

public class DemoMultiThreadedAnimation {

	public static void main(String[] args) {
		DataModel model = new DataModel();
		Worker tallGuy = new Worker(model, false, 1);
		Worker wideGuy = new Worker(model, true, 1);
		DMTAFrame fr = new DMTAFrame(model);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fr.setVisible(true);
		tallGuy.start();
		wideGuy.start();
	}
}

class DMTAFrame extends JFrame {
	public static final int DEFAULT_HEIGHT = 400;
	public static final int DEFAULT_WIDTH = 400;
	private final DataModel model; // The GUI always reflects the contents of
	// the underlying data model

	public DMTAFrame(DataModel model) {
		this.setTitle("Animation Test");
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.model = model;

		DMTAPanel panel = new DMTAPanel(model);
		this.getContentPane().add(panel);
	}
}

class DMTAPanel extends JPanel {
	/**
	 * @author matt
	 *
	 *         An instance of this class is bound to the (only) JButton in the
	 *         DMTAPanel constructor. Consequently, whenever the button is
	 *         clicked, the actionPerformed method, below, is invoked.
	 */
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			DMTAPanel.this.model.reset(); // Reset the model to its original
			// state
			DMTAPanel.this.repaint(); // Because we are changing the model, we
			// request a repaint
			// so that the display reflects the underlying model. Alternatively,
			// we could have simply waited for the next frame of animation.
			// N.B: Our ButtonListener class doesn't provide an implementaiton
			// of
			// repaint(), but because ButtonListener is actually an inner class
			// of
			// DMTAPanel, the repaint() is being invoked on that.
		}
	}

	class TimerListener implements ActionListener {
		/*
		 * (non-Javadoc)
		 *
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * ) This method is invoked at each tick of the animation Timer. Its
		 * only responsibility is to schedule a redrawing of the panel. The
		 * Worker threads are responsible for changing the underlying data. The
		 * paint method merely renders a representation of the current data
		 * model.
		 */
		@Override
		public void actionPerformed(ActionEvent event) {
			DMTAPanel.this.repaint(); // See the comment about repaint() in
			// ButtonListener.actionPerformed
		}
	}

	private static final int DELAY_ANIM_START = 1000; // Change to first
	// animation frame
	// occurs 1.0 seconds
	// after start
	private static final int RECT_WIDTH = 10, RECT_HEIGHT = 15;
	private static final int SCALE = 10; // Number of pixels per data model unit

	private static final int TIME_BETWEEN_FRAMES = 100; // msec between
	// rendering each frame
	// of animation

	private final DataModel model; // The GUI always reflects the contents of
	// the underlying data model

	/**
	 * Constructor for objects of class AnimPanel
	 */
	public DMTAPanel(DataModel model) {
		this.model = model;
		JButton myButton = new JButton("restart");
		this.add(myButton);
		myButton.addActionListener(new ButtonListener());
		TimerListener handler = new TimerListener(); // This listener (bound to
		// this panel) hears
		// the regular pings from the timer.

		// Create a Timer object to fire off events at specified intervals
		Timer t = new Timer(TIME_BETWEEN_FRAMES, handler);
		t.setInitialDelay(DELAY_ANIM_START);
		t.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Wipe background
		g.setColor(Color.RED);
		g.fillRect(this.model.getX() * SCALE, this.model.getY() * SCALE,
				RECT_WIDTH, RECT_HEIGHT);
	}
}
