package pbx;

/**
 * 
 * @author Scott Alexander
 *
 */
public class Device 
{
	public static final String STATUS_OFF = "OFF";
	public static final String STATUS_INIT = "Initializing Device";
	public static final String STATUS_WAIT = "Waiting for Connection";
	public static final String STATUS_CONN = "Connected";
	public static final String STATUS_RUNNING = "Active";
	public static final String STATUS_ERROR = "Device Error";
	
	protected enum State {OFF, INITIALIZING, WAITING, CONNECTED, RUNNING, ERROR}
	protected enum DeviceType {PHONE, RADIO}
	
	private DeviceType type;
	private int id = -1;
	private boolean isOn = false;
	private State state = State.OFF;
	private String status = STATUS_OFF;
	private String channelType = "No Channel";
	private Channel channel = null;
	private Device connectedDevice = null;
	
	public Device()
	{
		
	}
	
	protected void setDeviceType(DeviceType t){	type = t;	}
	public void setID(int nID){	id = nID;	}
	public void setOn(boolean on){	isOn = on;	}
	private void setChannelType(String c){	channelType = c;	}
	public void setChannel(Channel c){	if(c!=null){channel = c; updateChannelType();}	}
	protected void setConnectedDevice(Device d){	connectedDevice = d;	}
	public void setState(State nStat){	state = nStat; updateStatus();	}
	
	public DeviceType getType(){	return type;	}
	public boolean isOn(){	return isOn;	}
	public int getID(){	return id;	}
	public String getStatus(){	return status;	}
	public State getState(){	return state;	}
	public String getChannelType(){	return channelType;	}
	public Device getConnectedDevice(){	return connectedDevice;	}
	protected Channel getChannel(){	return channel;	}
	
	public void disconnectChannel(){	channel = null; updateChannelType();	}
	
	private void updateStatus()
	{
		String s = "";
		switch(state)
		{
			case OFF: s = STATUS_OFF; break;
			case INITIALIZING: s = STATUS_INIT; break;
			case WAITING: s = STATUS_WAIT; break;
			case CONNECTED: s = STATUS_CONN; break;
			case RUNNING: s = STATUS_RUNNING; break;
			case ERROR: s = STATUS_ERROR; break;
		}
		
		status = s;
	}
	
	private void updateChannelType()
	{
		if(channel == null) setChannelType("No Channel");
		else setChannelType("("+channel.getChannelNum()+")"+channel.getChannelType());
	}
	
	
	public String toString()
	{
		String s = (type == DeviceType.PHONE)? "Phone:" : (type == DeviceType.RADIO)? "Radio:" : "Unknown Device";
		s += ""+'\n'+'\t'+"ID: "+id;
		s += ""+'\n'+'\t'+"isOn:"+isOn;
		s += ""+'\n'+'\t'+"Status: "+status;
		s += ""+'\n'+'\t'+"Channel: "+channelType;
		
		return s;
		
	}
	
	public String toXML(String indent)
	{
		String dtype = (type == DeviceType.PHONE)? "Phone" : (type == DeviceType.RADIO)? "Radio" : "Device";
		
		String xml = indent+"<"+dtype+">"+'\n';
		
		xml += indent+'\t'+"<id>"+id+"</id>"+'\n';
		xml += indent+'\t'+"<isOn>"+isOn+"</isOn>"+'\n';
		xml += indent+'\t'+"<status>"+status+"</status>"+'\n';
		xml += indent+'\t'+"<channel>"+channelType+"</channel>"+'\n';
		
		xml += indent+"</"+dtype+">";
		
		return xml;
	}
}
