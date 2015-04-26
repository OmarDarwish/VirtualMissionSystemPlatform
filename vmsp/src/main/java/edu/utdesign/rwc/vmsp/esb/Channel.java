package edu.utdesign.rwc.vmsp.esb;

public class Channel {
   private int id;
   private boolean isOn;
   private String channelType;
   private String phoneConnectionStatus;
   private String radioConnectionStatus;

   public Channel() {
      this.id = 0;
      this.isOn = false;
      this.channelType = "";
      this.phoneConnectionStatus = "";
      this.radioConnectionStatus = "";
   }

   public Channel(int id, String channelType, String phoneConnectionStatus,
         String radioConnectionStatus) {
      this.id = id;
      this.channelType = channelType;
      this.phoneConnectionStatus = phoneConnectionStatus;
      this.radioConnectionStatus = radioConnectionStatus;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public boolean isOn() {
      return isOn;
   }

   public void setOn(boolean isOn) {
      this.isOn = isOn;
   }

   public String getChannelType() {
      return channelType;
   }

   public void setChannelType(String channelType) {
      this.channelType = channelType;
   }

   public String getPhoneConnectionStatus() {
      return phoneConnectionStatus;
   }

   public void setPhoneConnectionStatus(String phoneConnectionStatus) {
      this.phoneConnectionStatus = phoneConnectionStatus;
   }

   public String getRadioConnectionStatus() {
      return radioConnectionStatus;
   }

   public void setRadioConnectionStatus(String radioConnectionStatus) {
      this.radioConnectionStatus = radioConnectionStatus;
   }
}
