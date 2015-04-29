package edu.utdesign.rwc.vmsp.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import edu.utdesign.rwc.vmsp.esb.PbxProcessor;

@Component
public class PbxRoutes extends RouteBuilder {

   @Override
   public void configure() throws Exception {
      from("netty:tcp://{{netty.host}}:{{netty.port}}?sync=false")
            .routeId("pbxToBean").unmarshal("castor")
            .bean(PbxProcessor.class)
            .log("Recieved info from PBX")
            .to("mock:result");
   }
   

}
