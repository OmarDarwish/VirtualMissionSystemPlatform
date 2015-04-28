package edu.utdesign.rwc.vmsp.messaging;

import java.util.concurrent.BlockingQueue;

public class InRadio implements Runnable {
   BlockingQueue<GeneralMessage> inQueue;

   public final String INITIALIZNG = "Initializing InRadio...";
   public final String WARMING_UP = "InRadio warming up...";
   public final String CONNECTED = "InRadio connected.";

   public static String state;
   
   @Deprecated
   public InRadio(){};
   
   public InRadio(BlockingQueue<GeneralMessage> inQueue) {
      state = INITIALIZNG;
      System.out.println(state);
      this.inQueue = inQueue;
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

         // send inbound messages to message server
         int i = 1;
         while (state.equals(CONNECTED)) {
            // wait 2-5 seconds before generating a message
            Thread.sleep((long) (Math.random() * 3000) + 2000);
            inQueue.put(new GeneralMessage("Generated inbound message " + i));
            i++;
         }
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }
   
   public String getState(){
      return state;
   }

}
