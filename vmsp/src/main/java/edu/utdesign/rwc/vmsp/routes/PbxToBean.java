package edu.utdesign.rwc.vmsp.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PbxToBean extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("netty:tcp://{{netty.host}}:{{netty.port}}")
			.unmarshal("castor")
			.to("mock:result");
	}

}
