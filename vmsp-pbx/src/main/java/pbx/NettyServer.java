/**
 * @author Scott Alexander
 * 
 * 
 */

package pbx;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import Netty.*;

/*
import org.exolab.castor.xml.XMLContext;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Marshaller;
*/

public class NettyServer implements ActionListener
{
	
	private PBX pbx = null;
	
	private static final String FILE_WRITE_SUFFIX = ".write";
	private static final String FILE_READ_SUFFIX = ".read";
	private static final String FILE_NAME = "data/xmlPBX";
	
	private static File writeFile = new File(FILE_NAME+FILE_WRITE_SUFFIX);
	private static File readFile = new File(FILE_NAME+FILE_READ_SUFFIX);
	
	public NettyServer(PBX npbx)
	{
		pbx = npbx;
		
		//create and start the netty server and pass it the read file
		//bind 0.0.0.0/0.0.0.0:8023
		BeaconServer server = new BeaconServer(readFile);
		new Thread(server).start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		pbx.updateTimeStamp();
		
		//System.out.println(pbx.toString());
		//System.out.println(""+'\n'+'\n'+'\n'+'\n');
		
		try
		{
			//write the pbx xml data to the write file
			FileWriter writer = new FileWriter(writeFile);
			//<?xml version="1.0" encoding="UTF-8"?>
			char q = 34;
			String cont = "<?xml version="+q+"1.0"+q+" encoding="+q+"UTF-8"+q+"?>"+'\n'+pbx.toXML();
			writer.write(cont);
			writer.flush();
			writer.close();
			
			//if the read file already exists, delete it
			if(readFile.exists()) readFile.delete();
			
			//rename the write file to read file
			writeFile.renameTo(readFile);
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
	}
}
