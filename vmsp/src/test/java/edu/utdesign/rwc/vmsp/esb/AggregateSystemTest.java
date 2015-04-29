package edu.utdesign.rwc.vmsp.esb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utdesign.rwc.vmsp.esb.PBX;
import edu.utdesign.rwc.vmsp.messaging.GeneralMessage;
import edu.utdesign.rwc.vmsp.messaging.InRadio;
import edu.utdesign.rwc.vmsp.messaging.MessageServer;
import edu.utdesign.rwc.vmsp.messaging.OutRadio;

public class AggregateSystemTest extends CamelSpringTestSupport {
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
   public void shouldUnmarshallToAggregateSystem() throws Exception {
      context.start();
      Object result = context
            .getDataFormats()
            .get("castor")
            .getDataFormat()
            .unmarshal(new DefaultExchange(context),
                  new FileInputStream(new File("data/AggregateSystem.xml")));

      assertTrue(result instanceof AggregateSystem);
      context.stop();
   }

   @Test
   public void shouldMarshallToXml() throws Exception {
      context.start();
      // create a fake PBX
      PBX pbx = new PBX();
      for (int i = 0; i < 3; i++) {
         pbx.getPhones().add(new Phone(i, true, "Test"));
         pbx.getRadios().add(new Radio(i, true, "Test"));
         pbx.getChannels().add(new Channel(i, "Test", "Test", "Test"));
      }
      // create a fake MessageServer
      MessageServer ms = new MessageServer();
      ms.setInRadio(new InRadio());
      ms.setOutRadio(new OutRadio());
      
      // create a fake AggregateSystem
      AggregateSystem system = new AggregateSystem();
      system.setPbx(pbx);
      system.setMessageServer(ms);
      
      // send some artificial messages
      for (int i = 0; i < 20; i++) {
         String msg = "Test message " + i;
         //wait so that messages have different time stamps
         Thread.sleep(200);
         system.addMessage(new GeneralMessage(msg));
      }
      
      context
            .getDataFormats()
            .get("castor")
            .getDataFormat()
            .marshal(new DefaultExchange(context), system,
                  new FileOutputStream(new File("data/AggregateSystem.xml")));
      context.stop();
   }

}
