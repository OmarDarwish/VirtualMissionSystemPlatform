package vmsp;

import java.io.File;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Before;
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
	
	@Before
	public void mockEndpoints() throws Exception {
		context.getRouteDefinition("pbxToBean").adviceWith(context,
				new AdviceWithRouteBuilder() {
					@Override
					public void configure() throws Exception {
						mockEndpoints();
					}
				});
	}

	@Test
	public void shouldUnmarshallToPBX() throws Exception {
		MockEndpoint result = getMockEndpoint("mock://result");

		context.start();
		result.expectedMessageCount(1);

		File input = new File("data/PBX.xml");
		String content = context.getTypeConverter().convertTo(String.class,
				input);
		template.sendBody(
				"netty:tcp://{{netty.host}}:{{netty.port}}?sync=false", content);
		assertMockEndpointsSatisfied();

		Exchange exchange = result.getReceivedExchanges().get(0);
		assertTrue(exchange.getIn().getBody() instanceof PBX);
		context.stop();
	}

}
