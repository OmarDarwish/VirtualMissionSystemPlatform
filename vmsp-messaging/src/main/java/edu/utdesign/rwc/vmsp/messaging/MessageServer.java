package edu.utdesign.rwc.vmsp.messaging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessageServer {
   private InRadio inRadio;
   private OutRadio outRadio;
   
   public void setInRadio(InRadio inRadio){
      this.inRadio = inRadio;
   }
   
   public InRadio getInRadio(){
      return inRadio;
   }
   
   public void setOutRadio(OutRadio outRadio){
      this.outRadio = outRadio;
   }
   
   public OutRadio getOutRadio(){
      return outRadio;
   }
   
   public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext(
            "META-INF/spring/camel-context.xml");
      CamelContext camelContext = (CamelContext) context
            .getBean("camelContext");
      
      MessageServer messageServer = new MessageServer();
      
      BlockingQueue<GeneralMessage> fromInRadioQueue = new LinkedBlockingQueue<GeneralMessage>();
      messageServer.setInRadio(new InRadio(fromInRadioQueue));
      
      Thread inRadioThread = new Thread(messageServer.getInRadio());
      Thread messageServerIn = new Thread(new MessageServerIn(fromInRadioQueue,camelContext));
      
      try {
         camelContext.start();
         inRadioThread.start();
         messageServerIn.start();
         
         while (true) {
            //send an overall status update
            camelContext.createProducerTemplate().sendBody(
                  "direct:systemStatusUpdate", messageServer);
            Thread.sleep(30000);
         }
         
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
     
   }
}
