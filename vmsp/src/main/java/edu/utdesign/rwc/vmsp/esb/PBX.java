package edu.utdesign.rwc.vmsp.esb;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashSet;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;

public class PBX {
	private long lastUpdated;
	private boolean isOn;
	private HashSet<Phone> phones;
	private HashSet<Radio> radios;

	public PBX() {
		this.lastUpdated = System.currentTimeMillis() / 1000L;
		this.isOn = false;
		this.phones = new HashSet<Phone>();
		this.radios = new HashSet<Radio>();
	}

	public PBX(boolean isOn, HashSet<Phone> phones, HashSet<Radio> radios) {
		super();
		this.isOn = isOn;
		this.phones = phones;
		this.radios = radios;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
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

	public void setRadios(HashSet<Radio> radios) {
		this.radios = radios;
	}

}
