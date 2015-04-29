package edu.utdesign.rwc.vmsp.messaging;

import java.util.concurrent.BlockingQueue;

public class OutRadio implements Runnable {
   BlockingQueue<GeneralMessage> outQueue;
   private int numMessagesSent = 0;

   public final String INITIALIZNG = "Initializing OutRadio...";
   public final String WARMING_UP = "OutRadio warming up...";
   public final String CONNECTED = "OutRadio connected.";

   private String state;

   // Constructor used by Castor for marshalling/unmarshalling
   @Deprecated
   public OutRadio() {
   }

   public OutRadio(BlockingQueue<GeneralMessage> outQueue) {
      this.outQueue = outQueue;
   }

   @Override
   public void run() {
      try {
         // wait 2 seconds before changing radio state
         Thread.sleep(2000);
         state = WARMING_UP;
         System.out.println(state);
         Thread.sleep(2000);
         state = CONNECTED;
         System.out.println(state);

         while (state.equals(CONNECTED)) {
            outQueue.take();
            numMessagesSent++;
            Thread.sleep(2000);
         }
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

   public void setState(String state) {
      // TODO enforce constants
      this.state = state;
   }

   public String getState() {
      return state;
   }

   public void setNumMessagesSent(int num) {
      numMessagesSent = num;
   }

   public int getNumMessagesSent() {
      return numMessagesSent;
   }

}
