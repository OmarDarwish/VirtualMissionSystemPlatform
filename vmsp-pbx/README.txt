README

Project: VMSP Project - PBX emulation
Version: 1.2.0-complete

Dependencies:
	JRE System Library
	Netty 4.0.27 library (netty-all-4.0.27.Final.jar)

Be sure to configure the project build path so that the included netty-all-4.0.27.Final.jar file is a dependency.

To start the simulation, run the main method of PBX.java in the pbx package.

The netty server will open on 0.0.0.0/0.0.0.0:8023.

once the client connects to the server, the PBX info will be sent in xml format as a string. see data/testxml.xml for example output.

to test the server, run PBX.java.  wait for the server to write to the console that it is active, and then run BeaconClient.java in the Netty package.
	BeaconClient will connect to the server and print everything it recieves to the console.