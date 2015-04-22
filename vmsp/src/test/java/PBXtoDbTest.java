import java.io.File;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utdesign.rwc.vmsp.esb.PBX;

public class PBXtoDbTest extends CamelSpringTestSupport {
	@EndpointInject(uri = "netty://{netty.host}}:{{netty.port}}")
	private ProducerTemplate nettyReceiver;

	@EndpointInject(uri = "mock:result")
	private MockEndpoint result;

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(new String[] {
				"META-INF/spring/camel-context.xml",
				"META-INF/spring/camel-context-test.xml" });
	}

	@Test
	public void shouldUnmarshallToPBX() throws Exception {
		result.expectedMessageCount(1);

		File input = new File("PBX.xml");
		String content = context.getTypeConverter().convertTo(String.class,
				input);
		nettyReceiver.sendBody(content);
		assertMockEndpointsSatisfied();

		Exchange exchange = result.getReceivedExchanges().get(0);
		assertTrue(exchange.getIn().getBody() instanceof PBX);
	}

}
