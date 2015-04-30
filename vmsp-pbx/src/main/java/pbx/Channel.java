

package pbx;

import java.util.Random;

import pbx.Device.DeviceType;


/**
 * 
 * @author Scott Alexander
 *
 */
public class Channel implements Runnable
{
	private String channelType;
	private int channelNum;
	private Phone phone = null;
	private Radio radio = null;
	private String phoneStatus;
	private String radioStatus;
	private boolean isOn = false;
	
	private PBX pbx;
	private Random numgen = new Random();
	
	public Channel(PBX p, String chan, int num)
	{
		pbx = p;
		channelType = chan;
		channelNum = num;
		updatePhoneStatus();
		updateRadioStatus();
	}
	
	public synchronized String getChannelType(){	return channelType;	}
	public synchronized boolean radioConnected(){	return (radio == null)? false : true;	}
	public synchronized boolean phoneConnected(){	return (phone == null)? false : true;	}
	public synchronized Radio getRadio(){	return radio;	}
	public synchronized Phone getPhone(){	return phone;	}
	public synchronized int getChannelNum(){	return channelNum;	}
	public synchronized boolean isOn(){	return isOn;	}
	
	public synchronized boolean setPhone(Phone p)
	{
		if(p == null) return false;
		if(phone != null && phone.getID() != p.getID()) return false;
		
		phone = p;
		updatePhoneStatus();
		return true;
	}
	
	public synchronized boolean setRadio(Radio r)
	{
		if(r == null) return false;
		if(radio != null && radio.getID() != r.getID()) return false;
		
		radio = r;
		updateRadioStatus();
		return true;
	}
	
	private void updatePhoneStatus()
	{
		if(phone==null) phoneStatus = "Not Connected";
		else phoneStatus = ""+phone.getID();
	}
	
	private void updateRadioStatus()
	{
		if(radio==null) radioStatus = "Not Connected";
		else radioStatus = ""+radio.getID();
	}
	
	public synchronized void disconnectPhone(){	phone = null; updatePhoneStatus();	}
	public synchronized void disconnectRadio(){	radio = null; updateRadioStatus();	}
	
	
	public void run()
	{
		
		try
		{
		//pause for 1-5 seconds
		Thread.sleep((1+numgen.nextInt(6))*1000);
		
		isOn = true;
		pbx.addChannel(this);
		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage()+'\n'+e.getStackTrace());
		}
	}
	
	public String toString()
	{
		String s = "Channel:";
		s += ""+'\n'+'\t'+"Channel Type: "+channelType;
		s += ""+'\n'+'\t'+"Channel Number: "+channelNum;
		s += ""+'\n'+'\t'+"isOn: "+isOn;
		s += ""+'\n'+'\t'+"Phone: "+phoneStatus;
		s += ""+'\n'+'\t'+"Radio: "+radioStatus;
		
		return s;
	}
	
	public String toXML(String indent)
	{
		String xml = indent+"<Channel>"+'\n';
		
		xml += indent+'\t'+"<channelNum>"+channelNum+"</channelNum>"+'\n';
		xml += indent+'\t'+"<channelType>"+channelType+"</channelType>"+'\n';
		xml += indent+'\t'+"<isOn>"+isOn+"</isOn>"+'\n';
		xml += indent+'\t'+"<phoneStatus>"+phoneStatus+"</phoneStatus>"+'\n';
		xml += indent+'\t'+"<radioStatus>"+radioStatus+"</radioStatus>"+'\n';
		
		xml += indent+"</Channel>";
		
		return xml;
	}
}
