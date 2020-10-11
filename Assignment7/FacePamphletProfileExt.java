import java.util.ArrayList;
import java.util.Iterator;

import acm.graphics.GImage;

public class FacePamphletProfileExt {

	private String name, username, password;
	private String image = "noImage";
	private String status = "noStatus";

	public FacePamphletProfileExt(String name, String username, String password) {
		this.name = name;
		this.username = username;
		this.password = password;
	}

//	get name of profile	
	public String getName() {
		return name;
	}

//	get username of profile	
	public String getUsername() {
		return username;
	}

//	get password of profile	
	public String getPassword() {
		return password;
	}

//	get image on profile	
	public String getImage() {
		return image;
	}

//	set image on profile
	public void setImage(String image) {
		this.image = image;
	}

//	get status on profile	
	public String getStatus() {
		return status;
	}

//	set status on profile	
	public void setStatus(String status) {
		this.status = status;
	}

	private ArrayList<String> friends = new ArrayList<String>();

//	add friend from list	
	public boolean addFriend(String friend) {
		if (friends.contains(friend))
			return false;

		friends.add(friend);
		return true;
	}

//	removes friend from list	
	public boolean removeFriend(String friend) {
		if (friends.contains(friend)) {
			friends.remove(friend);
			return true;
		}

		return false;
	}

//	return friend's iterator of profile	
	public Iterator<String> getFriends() {
		return friends.iterator();
	}

}
