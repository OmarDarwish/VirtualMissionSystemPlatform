package edu.utdesign.rwc.vmsp.messaging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.camel.Handler;

public class MessageServerOut{
   private BlockingQueue<GeneralMessage> toRadio;
   
   public MessageServerOut(){
      toRadio = new LinkedBlockingQueue<GeneralMessage>();
   }
   
   public MessageServerOut(BlockingQueue<GeneralMessage> q){
      toRadio = q;
   }
   
   public void setQueue(BlockingQueue<GeneralMessage> q){
      toRadio = q;
   }
   
   public BlockingQueue<GeneralMessage> getQueue(){
      return toRadio;
   }
   
   @Handler
   public void sendMessage(GeneralMessage message){
      try {
         toRadio.put(message);
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

}
