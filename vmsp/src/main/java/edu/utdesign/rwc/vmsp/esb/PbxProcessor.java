package edu.utdesign.rwc.vmsp.esb;

import org.apache.camel.CamelContext;

public class PbxProcessor{
   private AggregateSystem system;
   
   public synchronized void processPbx(PBX pbx, CamelContext context){
      system = context.getRegistry().lookupByNameAndType("aggregateSystem", AggregateSystem.class);
      system.setPbx(pbx);
   }

}
