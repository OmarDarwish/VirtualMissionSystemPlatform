/**
 * 
 * 
 * 
 */

package pbx;

import java.io.*;

public class InactiveDeviceException extends Exception
{
	private String device;
	
	public InactiveDeviceException(String s)
	{
		device = s;
	}
	
	public String getMessage()
	{
		return "The "+device+" is currently inactive";
	}
}
