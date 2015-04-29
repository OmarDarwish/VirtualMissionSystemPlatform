package edu.utdesign.rwc.vmsp.routes;

import org.apache.camel.builder.RouteBuilder;

import edu.utdesign.rwc.vmsp.esb.MessageServerProcessor;

public class MessageServerRoutes extends RouteBuilder {

   @Override
   public void configure() throws Exception {
      // from MessageServer
      from("activemq:{{activemq.in}}").routeId("fromMessageServer")
            .unmarshal("castor").bean(MessageServerProcessor.class)
            .log("Received message from MessageServer");

      // to MessageServer
      from("direct:start").routeId("toMessageServer").marshal("castor")
            .to("activemq:{{activemq.out}}");
      
      // MessageServer status
      from("activemq:{{activemq.status}}").unmarshal("castor")
            .bean(MessageServerProcessor.class)
            .log("Received status from MessageServer");
   }
}
