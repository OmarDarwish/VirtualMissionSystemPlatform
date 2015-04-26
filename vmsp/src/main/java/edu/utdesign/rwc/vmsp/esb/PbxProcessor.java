package edu.utdesign.rwc.vmsp.esb;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;

public class PbxProcessor{
   private VmspSystem system;
   
   public void processPbx(PBX pbx, CamelContext context){
      system = context.getRegistry().lookupByNameAndType("vmspSystem", VmspSystem.class);
      system.setPbx(pbx);
   }

}
