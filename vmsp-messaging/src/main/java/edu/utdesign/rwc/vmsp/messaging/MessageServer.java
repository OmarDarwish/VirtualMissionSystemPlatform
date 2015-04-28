package edu.utdesign.rwc.vmsp.messaging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessageServer {
   private static InRadio inRadio;
   private static OutRadio outRadio;
   
   public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext(
            "META-INF/spring/camel-context.xml");
      CamelContext camelContext = (CamelContext) context
            .getBean("camelContext");

      BlockingQueue<GeneralMessage> fromInRadioQueue = new LinkedBlockingQueue<GeneralMessage>();
      inRadio = new InRadio(fromInRadioQueue);
      Thread inRadioThread = new Thread(inRadio);
      Thread messageServerIn = new Thread(new MessageServerIn(fromInRadioQueue,camelContext));
   }
   
   public InRadio getInRadio(){
      return inRadio;
   }
   
   public OutRadio getOutRadio(){
      return outRadio;
   }
}
