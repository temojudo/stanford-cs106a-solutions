import java.awt.Color;
import java.util.Iterator;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

public class FacePamphletGraphExt extends GCanvas implements FacePamphletConstants {

	private static final double WIDTH = 900;
	private static final double HEIGHT = 470;
	private static final double LABEL_SEP = 10;

	public FacePamphletGraphExt() {
	}

//	create start page when you run a code or sign out from your profile	
	public void createStartPage() {
		removeAll();

		GLabel label1 = new GLabel("Welcome to FacePamphlet");
		label1.setFont("Helvetica-35");
		GLabel label2 = new GLabel("It's free and always will be");
		label2.setFont("Helvetica-30-Italic");
		GLabel label3 = new GLabel("Press " + "\"" + "Log In" + "\"" + " or " + "\"" + "Sign Up" + "\"");
		label3.setFont("Helvetica-35");

		double height = (HEIGHT - (3 * LABEL_SEP + label1.getAscent() + label2.getAscent() + label3.getAscent())) / 2;

		height += label1.getAscent();
		add(label1, (WIDTH - label1.getWidth()) / 2, height);

		height += label2.getAscent() + LABEL_SEP;
		add(label2, (WIDTH - label2.getWidth()) / 2, height);

		height += label3.getAscent() + LABEL_SEP;
		add(label3, (WIDTH - label3.getWidth()) / 2, height);
	}

	private GLabel message = new GLabel("");

//	prints message when something is changed in the profile	
	public void printMessage(String msg) {
		message.setLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, (getWidth() - message.getWidth()) / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

//	looking up profiles with key	
	public void lookupProfiles(Iterator<String> names) {
		if (names.hasNext()) {
			removeAll();
			drawPossibleProfileNames(names);
		}
	}

//	draw found profiles	
	private void drawPossibleProfileNames(Iterator<String> names) {
		GLabel tr = new GLabel("Try:");
		tr.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(tr, (getWidth() - tr.getWidth()) / 2, labelHeight);

		double height = labelHeight;
		while (names.hasNext()) {
			height += FRIEND_MARGIN;
			GLabel friend = new GLabel(names.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, (getWidth() - friend.getWidth()) / 2, height);
		}

	}

//	draw this for sign up first page	
	public void signUpFirstPage() {
		removeAll();

		GLabel name = new GLabel("First name: ");
		GLabel surname = new GLabel("Last name: ");

		add(name, getWidth() - name.getWidth(), 165);
		add(surname, getWidth() - surname.getWidth(), 215);
	}

//	that's for second	
	public void signUpLastPage() {
		removeAll();

		GLabel username = new GLabel("Username: ");
		GLabel password = new GLabel("Password: ");
		GLabel repeatPassword = new GLabel("Repeat password: ");

		add(username, getWidth() - username.getWidth(), 140);
		add(password, getWidth() - password.getWidth(), 190);
		add(repeatPassword, getWidth() - repeatPassword.getWidth(), 240);
	}

//	drow when you choose Log In button	
	public void logIn() {
		removeAll();

		GLabel username = new GLabel("Username: ");
		GLabel password = new GLabel("Password: ");

		add(username, getWidth() - username.getWidth(), 140);
		add(password, getWidth() - password.getWidth(), 190);
	}

//	add profile on window
	public void addProfile(FacePamphletProfileExt profile) {
		removeAll();
		if (profile != null) {
			GImage image = null;
			if (!profile.getImage().equals("noImage"))
				image = new GImage(profile.getImage());

			setProfileName(profile.getName());
			setPicture(image);
			setStatus(profile.getStatus());
			setFriends(profile.getFriends());
		}

	}

//	draw friends	
	private void setFriends(Iterator<String> friends) {
		GLabel fr = new GLabel("Friends:");
		fr.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(fr, getWidth() / 2, labelHeight);

		height = labelHeight;
		while (friends.hasNext()) {
			height += FRIEND_MARGIN;
			GLabel friend = new GLabel(friends.next());
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, getWidth() / 2, height);
		}

	}

//	draw profile status
	private void setStatus(String status) {
		if (!status.equals("noStatus")) {
			height += IMAGE_HEIGHT + STATUS_MARGIN;
			GLabel st = new GLabel(status);
			st.setFont(PROFILE_STATUS_FONT);
			add(st, LEFT_MARGIN, height + st.getHeight());
		}

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
