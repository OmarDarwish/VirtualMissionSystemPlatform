package edu.utdesign.rwc.vmsp.esb;

public class Phone {
	private int id;
	private boolean isOn;
	private String state;
	
	public Phone(){
		this.id = -1;
		this.isOn = false;
		this.state = "";
	}

	public Phone(int id, boolean isOn, String state) {
		this.id = id;
		this.isOn = isOn;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		return id;
	}
	

}
