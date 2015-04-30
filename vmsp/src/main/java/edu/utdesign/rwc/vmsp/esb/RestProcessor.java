package edu.utdesign.rwc.vmsp.esb;

import org.apache.camel.CamelContext;

import edu.utdesign.rwc.vmsp.messaging.GeneralMessage;

public class RestProcessor {
   private AggregateSystem system;
   
   public AggregateSystem getStatus(CamelContext context){
      system = context.getRegistry().lookupByNameAndType("aggregateSystem",
            AggregateSystem.class);
      return system;
   }
}
