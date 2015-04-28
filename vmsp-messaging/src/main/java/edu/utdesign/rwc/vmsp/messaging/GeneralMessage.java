package edu.utdesign.rwc.vmsp.messaging;

import java.util.Date;

public class GeneralMessage {
   private Date timeStamp;
   private String body;

   public GeneralMessage() {

   }

   public GeneralMessage(String body) {
      timeStamp = new Date();
      this.body = body;
   }

   public void setBody(String body) {
      this.body = body;
   }

   public String getBody() {
      return body;
   }

   public void setTimestamp(long epoch) {
      timeStamp = new Date(epoch);
   }

   public void setTimestamp(Date timeStamp) {
      this.timeStamp = timeStamp;
   }

   public long getTimeStamp() {
      return timeStamp.getTime();
   }
}
