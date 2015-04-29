package edu.utdesign.rwc.vmsp.esb;

import java.util.Date;
import java.util.LinkedList;

import edu.utdesign.rwc.vmsp.messaging.GeneralMessage;
import edu.utdesign.rwc.vmsp.messaging.MessageServer;

public class AggregateSystem {
   private PBX pbx;
   private MessageServer messageServer;
   private Date lastUpdated;
   private LinkedList<GeneralMessage> messagesReceived;
   
   public AggregateSystem(){
      messagesReceived = new LinkedList<GeneralMessage>();
   }
   
   public PBX getPbx() {
      return pbx;
   }

   public void setPbx(PBX pbx) {
      this.pbx = pbx;
      lastUpdated = new Date();
   }
   
   public MessageServer getMessageServer(){
      return messageServer;
   }
   
   public void setMessageServer(MessageServer messageServer){
      this.messageServer = messageServer;
      lastUpdated = new Date();
   }
   
   public Date getLastUpdatedDate(){
      return lastUpdated;
   }
   
   public long getLastUpdated(){
      return lastUpdated.getTime();
   }
   
   public void addMessage(GeneralMessage msg){
      messagesReceived.add(msg);
   }
   
   public LinkedList<GeneralMessage> getMessages(){
      return messagesReceived;
   }
   
}
