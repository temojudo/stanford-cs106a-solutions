
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	GLabel message = new GLabel("");

	public void showMessage(String msg) {
		message.setLabel(msg);
		message.setFont(MESSAGE_FONT);
		double width = message.getWidth();
		add(message, (getWidth() - width) / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if (profile != null) {
			setProfileName(profile.getName());
			setPicture(profile.getImage());
			setStatus(profile.getStatus());
			setFriends(profile.getFriends());
		}

	}

//	draw friends		
	private void setFriends(Iterator<String> friends) {
		GLabel fr = new GLabel("Friends:");
		fr.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(fr, getWidth() / 2, labelHeight);

		while (friends.hasNext()) {
			labelHeight += FRIEND_MARGIN;
			GLabel friend = new GLabel(friends.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, getWidth() / 2, labelHeight);
		}

	}

//	draw profile status	
	private void setStatus(String status) {
		height += IMAGE_HEIGHT + STATUS_MARGIN;
		GLabel st = new GLabel(status);
		st.setFont(PROFILE_STATUS_FONT);
		add(st, LEFT_MARGIN, height + st.getHeight());
	}

//	draw profile picture	
	private void setPicture(GImage image) {
		height += IMAGE_MARGIN;
		labelHeight = height;

		if (image != null)
			addImage(image);
		else
			addMessage();

	}

	private void addImage(GImage image) {
		GImage picture = image;
		picture.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
		add(picture, LEFT_MARGIN, height);
	}

	private void addMessage() {
		GLabel text = new GLabel("No Image");
		text.setFont(PROFILE_IMAGE_FONT);
		text.setLocation(LEFT_MARGIN + (IMAGE_WIDTH - text.getWidth()) / 2,
				height + (IMAGE_WIDTH - text.getHeight()) / 2 + text.getHeight());
		add(text);
		add(new GRect(LEFT_MARGIN, height, IMAGE_WIDTH, IMAGE_HEIGHT));
	}

//	draw profile name	
	private void setProfileName(String Name) {
		GLabel name = new GLabel(Name);
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		height = name.getHeight() + TOP_MARGIN;
		add(name, LEFT_MARGIN, height);
	}

	private double height = 0;
	private double labelHeight = 0;

}
