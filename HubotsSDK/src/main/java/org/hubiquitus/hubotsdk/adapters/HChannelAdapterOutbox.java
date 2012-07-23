package org.hubiquitus.hubotsdk.adapters;

import java.util.Map;

import org.hubiquitus.hapi.hStructures.HCommand;
import org.hubiquitus.hapi.hStructures.HMessage;
import org.hubiquitus.hubotsdk.AdapterOutbox;

public class HChannelAdapterOutbox extends AdapterOutbox {

	private String chid;
	
	public HChannelAdapterOutbox() { }

	@Override
	public void start() {
	}

	@Override
	public void stop() {
	}

	@Override
	public void sendCommand(HCommand command) {
		HMessage message = new HMessage();
		message.setChid(chid);
		message.setType("hcommand");
		message.setPayload(command);
		hclient.publish(message, this);
	}

	@Override
	public void sendMessage(HMessage message) {
		message.setChid(chid);
		hclient.publish(message, this);
	}
	@Override
	public void setProperties(Map<String,String> params) {	
		if(params.get("chid") != null) {
			setChid(params.get("chid"));
		}
	}

	/* Getters and Setters */
	public String getChid() {
		return chid;
	}


	public void setChid(String chid) {
		this.chid = chid;
	}


	@Override
	public String toString() {
		return "HubotAdapter [name=" + name + ", chid" + chid + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chid == null) ? 0 : chid.hashCode());
		result = prime * result + ((hclient == null) ? 0 : hclient.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HChannelAdapterOutbox other = (HChannelAdapterOutbox) obj;
		if (chid == null) {
			if (other.chid != null)
				return false;
		} else if (!chid.equals(other.chid))
			return false;
		if (hclient == null) {
			if (other.hclient != null)
				return false;
		} else if (!hclient.equals(other.hclient))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
