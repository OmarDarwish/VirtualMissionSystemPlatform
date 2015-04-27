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
      PBX pbx = new PBX();
      for (int i = 0; i < 3; i++) {
         pbx.getPhones().add(new Phone(i, true, "Test"));
         pbx.getRadios().add(new Radio(i, true, "Test"));
         pbx.getChannels().add(new Channel(i, "Test", "Test", "Test"));
      }
      AggregateSystem system = new AggregateSystem();
      system.setPbx(pbx);
      context
            .getDataFormats()
            .get("castor")
            .getDataFormat()
            .marshal(new DefaultExchange(context), system,
                  new FileOutputStream(new File("data/AggregateSystem.xml")));
      context.stop();
   }

}
