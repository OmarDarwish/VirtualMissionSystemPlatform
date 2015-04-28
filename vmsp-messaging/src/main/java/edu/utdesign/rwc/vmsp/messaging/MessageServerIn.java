package edu.utdesign.rwc.vmsp.messaging;

import java.util.concurrent.BlockingQueue;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

public class MessageServerIn implements Runnable {
   
   private BlockingQueue<GeneralMessage> fromInRadio;
   private CamelContext context;

   public MessageServerIn(BlockingQueue<GeneralMessage> fromRadio,
         CamelContext context) {

      this.fromInRadio = fromRadio;
      this.context = context;
   }

   @Override
   public void run() {
      ProducerTemplate template = context.createProducerTemplate();

      // safe due to BlockingList
      try {
         while (true) {
            GeneralMessage message = fromInRadio.take();
            System.out.println("MessageServerIn packaging InRadio message: "
                  + message.getBody());
            template.sendBody("direct:start", message);
         }
      } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }
}
