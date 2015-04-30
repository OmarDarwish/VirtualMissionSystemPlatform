package edu.utdesign.rwc.vmsp.esb;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

   @SuppressWarnings("resource")
   public static void main(String[] args){
      ApplicationContext context = new ClassPathXmlApplicationContext(
            "META-INF/spring/camel-context.xml");
      CamelContext camelContext = (CamelContext) context
            .getBean("camelContext");
      
     //Start the PbxTcpListener
      ProducerTemplate pbxTemplate = camelContext.createProducerTemplate();
      pbxTemplate.setDefaultEndpointUri("direct:pbxToBean");
      PbxTcpClient client = new PbxTcpClient(pbxTemplate);
      
      try {
         Thread pbxClientThread = new Thread(client);
         camelContext.start();
         pbxClientThread.start();
         
         while(true){
            Thread.sleep(Long.MAX_VALUE);
         }
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
