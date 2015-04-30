/**
 * 
 * @author Scott Alexander
 *
 *
 */

package pbx;

import java.util.concurrent.*;
import javax.swing.*;
import java.util.Random;



public class PBX
{
	public static final String[] channelTypes = {"HF", "VHF", "SAT", "LF"};
	public static final int NUM_DEVICES = 3;
	public static final int MAX_ID = 10000;
	
	private static Phone[] allPhones = new Phone[NUM_DEVICES];
	private static Radio[] allRadios = new Radio[NUM_DEVICES];
	private static Channel[] allChannels = new Channel[NUM_DEVICES];
	
	
	public static void main(String[] args)
	{
		boolean systemOffFlag = false;
		Thread[] threads = new Thread[NUM_DEVICES*3];
		
		//initialize PBX
		PBX pbx = new PBX();
		
		NettyServer server = new NettyServer(pbx);
		Timer timer = new Timer(1000, server);
		
		//initialize channels
		for(int i = 0, t = 0; i < NUM_DEVICES; i++, t++)
		{
			if(t > channelTypes.length) t = 0;
			allChannels[i] = new Channel(pbx, channelTypes[t], i);
		}
		
		//initialize radios
		for(int i = 0; i < NUM_DEVICES; i++)
			allRadios[i] = new Radio(pbx);
		
		//initialize phones
		for(int i = 0; i < NUM_DEVICES; i++)
			allPhones[i] = new Phone(pbx);
		
		//System.out.println(pbx.toXML());
		timer.start();
		
		
		//create threads
		int t = 0;
		for(int i = 0; i < NUM_DEVICES; i++)
			threads[t++] = new Thread(allChannels[i]);
		for(int i = 0; i < NUM_DEVICES; i++)
			threads[t++] = new Thread(allRadios[i]);
		for(int i = 0; i < NUM_DEVICES; i++)
			threads[t++] = new Thread(allPhones[i]);
		
		//start threads
		for(int i = 0; i < threads.length; i++)
			threads[i].start();
		
		try
		{
			//join threads
			for(int i = 0; i < threads.length; i++)
				threads[i].join(1000);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		while(systemOffFlag == false)
		{
			//infinite loop
		}
	}
	
	
	private long lastUpdated;
	private boolean isOn = false;
	
	private Phone[] phones;
	private Radio[] radios;
	private Channel[] channels;
	
	public PBX()
	{
		updateTimeStamp();
		
		isOn = true;
		
		phones = new Phone[NUM_DEVICES];
		radios = new Radio[NUM_DEVICES];
		channels = new Channel[NUM_DEVICES];
		
	}
	
	public Phone[] getPhones(){	return phones;	}
	public Radio[] getRadios(){	return radios;	}
	public boolean isOn(){ return isOn;	}
		
	
	public synchronized boolean connectToPBX(Device dev) throws InactiveDeviceException
	{
		if(!this.isOn) throw new InactiveDeviceException("PBX");
		
		//check if id already exists
		if(deviceConnected(dev))	return false;
		
		//if not, add device to active list
		switch(dev.getType())
		{
			case PHONE:
				for(int i = 0; i < phones.length; i++)
					if(phones[i]==null)
					{
						phones[i] = (Phone)dev;
						return true;
					}
				break;
			case RADIO:
				for(int i = 0; i < radios.length; i++)
					if(radios[i]==null)
					{
						radios[i] = (Radio)dev;
						return true;
					}
				break;
			default:
				return false;
		}
		
		return false;
	}
	
	public synchronized Channel findChannel(Device dev) throws DeviceNotConnectedException
	{
		if(deviceConnected(dev)==false) throw new DeviceNotConnectedException(dev.getID());
		
		switch(dev.getType())
		{
			case PHONE:
				for(int i = 0; i < channels.length; i++)
					if(channels[i]!=null && channels[i].phoneConnected()==false) return channels[i];
				break;
			case RADIO:
				for(int i = 0; i < channels.length; i++)
					if(channels[i]!=null && channels[i].radioConnected()==false) return channels[i];
				break;
		}
		
		return null;
	}
	
	public synchronized boolean addChannel(Channel com)
	{
		int i = com.getChannelNum();
		
		channels[i] = com;
		
		return true;
	}
	
	public synchronized boolean deviceConnected(Device dev)
	{
		for(int i = 0; i < phones.length; i++)
			if(phones[i]!=null && phones[i].getID()==dev.getID()) return true;
		for(int i = 0; i < radios.length; i++)
			if(radios[i]!=null && radios[i].getID()==dev.getID()) return true;
		return false;
	}
	
	public void updateTimeStamp()
	{
		lastUpdated = System.currentTimeMillis() / 1000L;
	}
	
	public String toString()
	{
		String s = "PBX:";
		
		s += ""+'\n'+"TimeStamp: "+lastUpdated;
		s += ""+'\n'+"isOn: "+isOn;
		s += ""+'\n'+"<Phones>";
		for(int i = 0; i < allPhones.length; i++)
			{s += (allPhones[i]!=null)? ""+'\n'+allPhones[i].toString() : "";}
		s += ""+'\n'+"</Phones>";
		s += ""+'\n'+"<Radios>";
		for(int i = 0; i < allRadios.length; i++)
			{s += (allRadios[i]!=null)? ""+'\n'+allRadios[i].toString() : "";}
		s += ""+'\n'+"</Radios>";
		s += ""+'\n'+"<Channels>";
		for(int i = 0; i < allChannels.length; i++)
			{s += (allChannels[i]!=null)?'\n'+allChannels[i].toString() : "";}
		s += ""+'\n'+"</Channels>";
		
		return s;
	}
	
	public String toXML()
	{
		return toXML("");
	}
	
	public String toXML(String indent)
	{
		String xml = ""+indent+"<PBX>"+'\n';
		
		xml += indent+'\t'+"<timestamp>"+lastUpdated+"</timestamp>"+'\n';
		xml += indent+'\t'+"<isOn>"+isOn+"</isOn>"+'\n';
		
		xml += indent+'\t'+"<Phones>"+'\n';
		for(int i = 0; i < allPhones.length; i++)
			{xml += (allPhones[i]!=null)? allPhones[i].toXML(indent+'\t'+'\t')+'\n' : "";}
		xml += indent+'\t'+"</Phones>"+'\n';
		
		xml += indent+'\t'+"<Radios>"+'\n';
		for(int i = 0; i < allRadios.length; i++)
			{xml += (allRadios[i]!=null)? allRadios[i].toXML(indent+'\t'+'\t')+'\n' : "";}
		xml += indent+'\t'+"</Radios>"+'\n';
		
		xml += indent+'\t'+"<Channels>"+'\n';
		for(int i = 0; i < allChannels.length; i++)
			{xml += (allChannels[i]!=null)? allChannels[i].toXML(indent+'\t'+'\t')+'\n' : "";}
		xml += indent+'\t'+"</Channels>"+'\n';
		
		xml += indent+"</PBX>";
		
		return xml;
	}
	
}
