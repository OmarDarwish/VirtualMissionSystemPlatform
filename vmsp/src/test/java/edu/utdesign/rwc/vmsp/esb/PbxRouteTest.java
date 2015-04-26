package edu.utdesign.rwc.vmsp.esb;

import java.io.File;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utdesign.rwc.vmsp.esb.PBX;

public class PbxRouteTest extends CamelSpringTestSupport {
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
   public void shouldUnmarshallToPbx() throws Exception {
      MockEndpoint result = getMockEndpoint("mock:result");

      context.start();
      result.expectedMessageCount(1);

      File input = new File("data/PBX.xml");
      String content = context.getTypeConverter()
            .convertTo(String.class, input);
      String uri = "netty:tcp://{{netty.host}}:{{netty.port}}?sync=false";
      template.sendBody(uri, content);
      assertMockEndpointsSatisfied();

      // check unmarshalling
      Exchange exchange = result.getReceivedExchanges().get(0);
      Object received1 = exchange.getIn().getBody();
      assertTrue(received1 instanceof PBX);

      context.stop();
   }

   @Test
   @Ignore
   public void shouldMarshallToXml() throws Exception {
      context.start();
      PBX pbx = new PBX();
      for (int i = 0; i < 3; i++) {
         pbx.getPhones().add(new Phone(i, true, "Test"));
         pbx.getRadios().add(new Radio(i, true, "Test"));
         pbx.getChannels().add(new Channel(i, "Test", "Test", "Test"));
      }
      context.getDataFormats().get("castor").getDataFormat()
            .marshal(new DefaultExchange(context), pbx, System.out);
      context.stop();
   }

}
