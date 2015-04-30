package pbx;

import java.util.Random;

import pbx.Device.DeviceType;
import pbx.Device.State;

/**
 * 
 * @author Scott Alexander
 *
 */
public class Radio extends Device implements Runnable
{
	private Thread th;
	private PBX pbx;
	private Random numgen = new Random();
	
	public Radio(PBX p)
	{
		pbx = p;
		
		setID(numgen.nextInt(PBX.MAX_ID));
		
		setDeviceType(DeviceType.RADIO);
		
	}
	
	public void run()
	{
		th = Thread.currentThread();
		
		try
		{
		//pause for 1-5 seconds
		sleep(1+numgen.nextInt(6));
		
		setOn(true);
		setState(State.INITIALIZING);
		
		//pause for 1-3 seconds
		sleep(1+numgen.nextInt(3));
		
		
		// connect to PBX
		// if connection fails, generate a new ID
		boolean connected = false;
		do
		{
			try
			{
				connected = pbx.connectToPBX(this);
				if(!connected) setID(numgen.nextInt(PBX.MAX_ID));
			}
			catch(InactiveDeviceException ide)
			{
				System.out.println(ide.getMessage());
				sleep(2);
			}
		} while(!connected);
		setState(State.WAITING);
		
		//pause 3-6 seconds
		sleep(3+numgen.nextInt(4));
		
		
		//connect to a channel
		Channel com = null;
		do
		{
			do
			{
				com = pbx.findChannel(this);
			} while(com==null);
		}while(!com.setRadio(this));
		this.setChannel(com);
		
		setState(State.CONNECTED);
		
		//pause 1-2 seconds
		sleep(1+numgen.nextInt(2));
		
		setState(State.RUNNING);
		
		//find the connected phone through the channel
		do
		{
			setConnectedDevice(getChannel().getPhone());
		}while(getConnectedDevice()==null);
		
		}
		catch(Exception e)
		{
			setState(State.ERROR);
			System.out.println(e.getMessage()+'\n'+e.getStackTrace());
		}
	}
	
	private void sleep(long seconds) throws InterruptedException
	{
		th.sleep(seconds*1000);
	}
}
