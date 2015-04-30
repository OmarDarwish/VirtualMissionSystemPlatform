package edu.utdesign.rwc.vmsp.esb;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class RestRoutes extends RouteBuilder {
   
   @Override
   public void configure() throws Exception {
      rest("/rest")
            .get("/status").produces("application/xml").to("direct:systemStatus")
            .post("/send").to("direct:sendMessage");
      restConfiguration().component("jetty").host("localhost").port(8080).bindingMode(RestBindingMode.auto);

   }
}
