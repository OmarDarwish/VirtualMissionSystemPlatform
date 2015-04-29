package edu.utdesign.rwc.vmsp.esb;

import org.apache.camel.CamelContext;

import edu.utdesign.rwc.vmsp.messaging.GeneralMessage;
import edu.utdesign.rwc.vmsp.messaging.MessageServer;

public class MessageServerProcessor {
   private AggregateSystem system;

   public synchronized void processMessageServer(MessageServer messageServer,
         CamelContext context) {
      system = context.getRegistry().lookupByNameAndType("aggregateSystem",
            AggregateSystem.class);
      system.setMessageServer(messageServer);
   }

   public void processMessage(GeneralMessage msg, CamelContext context) {
      system = context.getRegistry().lookupByNameAndType("aggregateSystem",
            AggregateSystem.class);
      system.addMessage(msg);
   }
}
