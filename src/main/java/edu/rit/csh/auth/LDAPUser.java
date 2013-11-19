package edu.rit.csh.auth;

/**
 * A POJO model that contains information about a user pulled from LDAP or
 * custom constructed for testing and debugging. LDAPUser cannot modify the LDAP 
 * database, and only serves as an information container.
 * @author scott
 *
 */
public class LDAPUser {
	/**Username*/
	private String uid;
	/**First/nickname*/
	private String givenname;
	/**Whether a member is on floor*/
	private boolean onfloor;
	/**Whether a member is active*/
	private boolean active;
	/**Unique identifier number*/
	private String uidnumber;
	/**Current room number*/
	private String roomnumber;
	
	public LDAPUser(String uid, String givenname, boolean onfloor,
			boolean active, String uidnumber, String roomnmber) {
		this.uid = uid;
		this.givenname = givenname;
		this.onfloor = onfloor;
		this.active = active;
		this.uidnumber = uidnumber;
		this.roomnumber = roomnmber;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGivenname() {
		return givenname;
	}

	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}

	public boolean isOnfloor() {
		return onfloor;
	}

	public void setOnfloor(boolean onfloor) {
		this.onfloor = onfloor;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUidnumber() {
		return uidnumber;
	}

	public void setUidnumber(String uidnumber) {
		this.uidnumber = uidnumber;
	}

	public String getRoomnumber() {
		return roomnumber;
	}

	public void setRoomnumber(String roomnumber) {
		this.roomnumber = roomnumber;
	}

}