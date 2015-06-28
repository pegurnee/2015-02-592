# Java Animation

*Last modified: "May 25, 2015, by evett"*

![image][1]

A common, and recommended way to provide a dynamic graphical user interface in Java is to use a **Model-Viewer-Controller** architecture. This is a common technique for implementing GUI's.

The basic structure:

* There is an object (or objects) that defines the "state of the world".  (The underlying data model.)
* The GUI strives to always represent the current state of the world.
* A simple GUI consists of a single `JFrame` containing a single `JPanel`.  The panel's *paintComponent()* method invokes drawing operations so as to render an image that reflects the "state of the world".  (Thus, the panel necessarily has a reference to the data model.  The panel's *paintComponent()* method uses the "state of the world" to determine what it draws.)
* There is a single timer object (a Swing `Timer`) that fires at regular intervals (usually around 20 times per second).
* We set up a listener for the timer that is bound to a `JPanel` (within a `JFrame`).   Each time the listener "hears" the timer, it asks the panel to draw itself (i.e., invokes *repaint()* on the panel).
* The end result is that the panel should be drawn at regular intervals (around 10-30 times per second).

So now we have a GUI that is redrawing itself 10 times per second, or so, and the GUI reflects the underlying data model. The next step is to see that the data model is being altered regularly. If the model changes over time, its visual representation should corresondingly change over time, resulting in animation. To do that we create at least one thread that regularly alters the data model. In my example I call this a `Worker` object. If you look at the `Worker` code you will see that it contains no GUI code. All it does is change the data in the model. The animation comes from the GUI timer regularly firing so as to trigger a redraw of the panel. When the panel it draws a representation of the underlying model. If the model has changed (been changed by the `Worker`(s)) the frame of animation will differ from the previous one. In aggregate all these renderings will yield an animation.

The following code provides an example. When run, the program shows a small red square moving somewhat randomly diagonally from the upper-left corener to the lower-right. The program consists of a driver ([DemoMultiThreadedAnimation][2]) that creates the underlying data model object (a [DataModel][3] instance), a GUI consisting of a Frame and Panel that are bound to the `DataModel` object, and two [Workers][4] (each a `Thread`) that are also bound to the `DataModel` object. One of the `Workers` moves the red square to the right, and the other moves the square downward. The `DataModel` object serves as the intermediary between the two `Worker` threads and the GUI thread (in Java, this is the "Swing" thread). The threads run concurrently: the `Workers` change the `DataModel` intermittently. The GUI thread, prompted by the `Timer`, renders the GUI to reflect the `DataModel` regularly (every 10th to 20th of a second). The program terminates after the position of the square is changed (by the `Workers`) 100 times.

For your programming assignment (if you are attempting the extra credit graphical interface) the underlying data model should consist of the `Widget` each worker is working on (if any), and the contents of the conveyer belts (other `Widgets`). There will be four workers instead of the two shown here. Otherwise, the rest of the example code will remain unchanged. You should derive a way to use an aspect of the underlying data model to trigger (to the GUI thread) that the program should terminate.

[1]: animation.gif
[2]: http://www.emunix.emich.edu/~evett/OS/Code/DemoMultiThreadedAnimation.java
[3]: http://www.emunix.emich.edu/~evett/OS/Code/DataModel.java
[4]: http://www.emunix.emich.edu/~evett/OS/Code/Worker.java