package edu.utdesign.rwc.vmsp.esb;

import java.util.Date;
import java.util.HashSet;

public class PBX {
   private Date lastUpdated;
   private boolean isOn;
   private HashSet<Phone> phones;
   private HashSet<Radio> radios;
   private HashSet<Channel> channels;

   public PBX() {
      this.lastUpdated = new Date();
      this.isOn = false;
      this.phones = new HashSet<Phone>();
      this.radios = new HashSet<Radio>();
      this.channels = new HashSet<Channel>();
   }

   public PBX(boolean isOn, HashSet<Phone> phones, HashSet<Radio> radios) {
      this.isOn = isOn;
      this.phones = phones;
      this.radios = radios;
   }

   public Date getLastUpdatedDate() {
      return lastUpdated;
   }

   public long getLastUpdated() {
      return lastUpdated.getTime();
   }

   public void setLastUpdated(long lastUpdated) {
      this.lastUpdated = new Date(lastUpdated);
   }

   public void setLastUpdated(Date lastUpdated) {
      this.lastUpdated = lastUpdated;
   }

   public boolean isOn() {
      return isOn;
   }

   public void setOn(boolean isOn) {
      this.isOn = isOn;
   }

   public HashSet<Phone> getPhones() {
      return phones;
   }

   public void setPhones(HashSet<Phone> phones) {
      this.phones = phones;
   }

   public HashSet<Radio> getRadios() {
      return radios;
   }

   public HashSet<Channel> getChannels() {
      return channels;
   }

   public void setChannels(HashSet<Channel> channels) {
      this.channels = channels;
   }

   public void setRadios(HashSet<Radio> radios) {
      this.radios = radios;
   }

}
