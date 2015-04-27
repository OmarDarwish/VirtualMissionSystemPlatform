package edu.utdesign.rwc.vmsp.esb;

import java.util.Date;

public class AggregateSystem {
   private PBX pbx;
   private Date lastUpdated;
   
   public PBX getPbx() {
      return pbx;
   }

   public void setPbx(PBX pbx) {
      this.pbx = pbx;
      lastUpdated = new Date();
   }
   
   public Date getLastUpdatedDate(){
      return lastUpdated;
   }
   
   public long getLastUpdated(){
      return lastUpdated.getTime();
   }
   
}
