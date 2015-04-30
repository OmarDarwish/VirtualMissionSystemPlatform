
package pbx;

import java.io.*;

public class DeviceNotConnectedException extends Exception 
{
	private int id;
	
	public DeviceNotConnectedException(int did)
	{
		id = did;
	}
	
	public String getMessage()
	{
		return "Device "+id+" is not connected to the PBX";
	}
}
