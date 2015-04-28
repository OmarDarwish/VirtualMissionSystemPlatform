package edu.utdesign.rwc.vmsp.messaging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MessageServer {
   public static void main(String[] args) {
      ApplicationContext context = new ClassPathXmlApplicationContext(
            "META-INF/spring/camel-context.xml");
      CamelContext camelContext = (CamelContext) context
            .getBean("camelContext");

      BlockingQueue<GeneralMessage> fromInRadioQueue = new LinkedBlockingQueue<GeneralMessage>();

      Thread inRadio = new Thread(new InRadio(fromInRadioQueue));
      Thread messageServerIn = new Thread(new MessageServerIn(fromInRadioQueue,camelContext));
   }
}
