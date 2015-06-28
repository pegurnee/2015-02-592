/**
 * MessageQueue.java
 *
 * This program implements the bounded buffer using message passing.
 * Note that this solutions is NOT thread-safe. A thread safe solution
 * can be developed using Java synchronization which is discussed in Chapter 7.
 *
 * @author Greg Gagne, Peter Galvin, Avi Silberschatz
 * @version 1.0 - July 15, 1999
 * Copyright 2000 by Greg Gagne, Peter Galvin, Avi Silberschatz
 * Applied Operating Systems Concepts - John Wiley and Sons, Inc.
 */
 
import java.util.*;
 
public class MessageQueue
{
   public MessageQueue() {
      queue = new Vector();
   }
   
   /*
    * This implements a non-blocking send
    */
   public void send(Object item) {
      queue.addElement(item);
   }
   
   /*
    * This implements a non-blocking receive
    */
   public Object receive() {
      Object item;
      
      if (queue.size() == 0)
         return null;
      else {
         item = queue.firstElement();        
         queue.removeElementAt(0);
         
         return item;
      }
   }

   private Vector queue;
}
