package edu.utdesign.rwc.vmsp.esb;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utdesign.rwc.vmsp.messaging.GeneralMessage;

public class MessageServerRouteTest extends CamelSpringTestSupport {

   @Override
   protected AbstractApplicationContext createApplicationContext() {
      return new ClassPathXmlApplicationContext(new String[] {
            "META-INF/spring/camel-context.xml",
            "META-INF/spring/camel-context-test.xml" });
   }

   @Override
   public boolean isUseAdviceWith() {
      return true;
   }
   
   @Test
   public void sendMessagesToMessageServer(){
      try {
         MockEndpoint result = getMockEndpoint("mock:toMessageServerResult");
         result.setExpectedMessageCount(20);
         context.start();
         //Wait for the context to get ready
         Thread.sleep(3000);
         ProducerTemplate template = context.createProducerTemplate();
         for(int i = 0; i < 20; i++) {
            String input = "Message " + i + " sent from ESB";
            GeneralMessage msg = new GeneralMessage(input);
            template.sendBody("direct:toMessageServer", msg);
         }
         assertMockEndpointsSatisfied();
         context.stop();
      } catch (Exception e) {
         
      }
   }
}
