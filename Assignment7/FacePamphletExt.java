import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import acm.graphics.GImage;
import acm.program.Program;
import acm.util.ErrorException;

public class FacePamphletExt extends Program implements FacePamphletConstants {

	public static final int APPLICATION_WIDTH = 1200;
	public static final int APPLICATION_HEIGHT = 600;

	private JButton logIn, signUp, backToProfile, signOut;
	private JTextField firstName, lastName, username, password, repeatPassword;
	private JTextField status, picture, friend, name;
	private JButton ChS, ChP, AddF, lookup;
	private JButton next, finish, enter;
	private FacePamphletGraphExt graph;
	private FacePamphletProfileExt myProfile, lookupProfile;
	private FacePamphletDataBaseExt dataBase = new FacePamphletDataBaseExt("profiles.txt");

	public void init() {
		graph = new FacePamphletGraphExt();
		add(graph);
		graph.createStartPage();

		addSouthInteractors();
		addNorthInteractors();
		addEastInteractors();
		addWestInteractors();

		addActionListeners();
	}

//	init north interactors	
	private void addNorthInteractors() {
		name = new JTextField(TEXT_FIELD_SIZE);
		add(name, NORTH);
		name.addActionListener(this);

		lookup = new JButton("lookup");
		add(lookup, NORTH);

		hideNorthInteractors();
	}

//	hide them	
	private void hideNorthInteractors() {
		name.setVisible(false);
		lookup.setVisible(false);
	}

// 	show them	
	private void showNorthInteractors() {
		name.setVisible(true);
		lookup.setVisible(true);
	}

//	init west interactors	
	private void addWestInteractors() {
		status = new JTextField(TEXT_FIELD_SIZE);
		add(status, WEST);
		status.addActionListener(this);

		ChS = new JButton("Change Status");
		add(ChS, WEST);

		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		picture = new JTextField(TEXT_FIELD_SIZE);
		add(picture, WEST);
		picture.addActionListener(this);

		ChP = new JButton("Change Picture");
		add(ChP, WEST);

		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		friend = new JTextField(TEXT_FIELD_SIZE);
		add(friend, WEST);
		friend.addActionListener(this);

		AddF = new JButton("Add friend");
		add(AddF, WEST);

		hideWestInteractors();
	}

//	hide them	
	private void hideWestInteractors() {
		status.setVisible(false);
		picture.setVisible(false);
		friend.setVisible(false);
		ChS.setVisible(false);
		ChP.setVisible(false);
		AddF.setVisible(false);
	}

//	show them	
	private void showWestInteractors() {
		status.setVisible(true);
		picture.setVisible(true);
		friend.setVisible(true);
		ChS.setVisible(true);
		ChP.setVisible(true);
		AddF.setVisible(true);
	}

//	init east interactors	
	private void addEastInteractors() {
		firstName = new JTextField(TEXT_FIELD_SIZE);
		lastName = new JTextField(TEXT_FIELD_SIZE);
		next = new JButton("next");
		username = new JTextField(TEXT_FIELD_SIZE);
		password = new JTextField(TEXT_FIELD_SIZE);
		repeatPassword = new JTextField(TEXT_FIELD_SIZE);
		finish = new JButton("finish");
		enter = new JButton("Log In");

		add(username, EAST);
		add(firstName, EAST);
		add(password, EAST);
		add(lastName, EAST);
		add(repeatPassword, EAST);
		add(next, EAST);
		add(enter, EAST);
		add(finish, EAST);

		hideEastInteractors();
	}

// 	hide south interactors	
	private void hideSouthInteractors() {
		logIn.setVisible(false);
		backToProfile.setVisible(false);
		signOut.setVisible(false);
		signUp.setVisible(false);
	}

// 	hide east interactors	
	private void hideEastInteractors() {
		username.setVisible(false);
		firstName.setVisible(false);
		password.setVisible(false);
		lastName.setVisible(false);
		repeatPassword.setVisible(false);
		next.setVisible(false);
		finish.setVisible(false);
		enter.setVisible(false);
	}

//	init south interactors	
	private void addSouthInteractors() {
		logIn = new JButton("Log In");
		add(logIn, SOUTH);

		backToProfile = new JButton("My Profile");
		add(backToProfile, SOUTH);

		signOut = new JButton("Sign Out");
		add(signOut, SOUTH);

		signUp = new JButton("Sign Up");
		add(signUp, SOUTH);

		hideSouthInteractors();
		signUp.setVisible(true);
		logIn.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		checkSouthInteractors(e);
		checkEastInteractors(e);
		checkNorthInteractors(e);
		checkWestInteractors(e);
	}

// 	check clicks on north interactors	
	private void checkNorthInteractors(ActionEvent e) {
		if ((e.getSource() == lookup || e.getSource() == name) && name.getText().length() > 0) {
			lookupProfile = dataBase.lookupProfile(name.getText());
			if (lookupProfile != null) {
				graph.addProfile(lookupProfile);
				graph.printMessage("Displaying " + name.getText());
				backToProfile.setVisible(true);
			} else {
				graph.lookupProfiles(dataBase.lookupProfilesWithKey(name.getText(), myProfile.getName()));
				graph.printMessage("A profile with the name " + "\"" + name.getText() + "\"" + " does not exist");
			}
		}

	}

// 	check clicks on west interactors	
	private void checkWestInteractors(ActionEvent e) {
		checkChangeStatus(e);
		checkChangePicture(e);
		checkAddFriend(e);
	}

// 	check clicks on change status	
	private void checkChangeStatus(ActionEvent e) {
		if ((e.getSource() == ChS || e.getSource() == status) && status.getText().length() > 0) {
			if (lookupProfile != null) {
				graph.printMessage("please back to your profile yet");
				return;
			}

			if (status.getText().contains("-")) {
				graph.printMessage("status mustn't contain symbol '-'");
				return;
			}

			if (myProfile != null) {
				myProfile.setStatus(status.getText());
				dataBase.update();
				graph.addProfile(myProfile);
				graph.printMessage("Status updated to " + status.getText());
			} else {
				graph.addProfile(myProfile);
				graph.printMessage("Please select a profile to change status");
			}

		}
	}

// 	check clicks on change picture	
	private void checkChangePicture(ActionEvent e) {
		if ((e.getSource() == ChP || e.getSource() == picture) && picture.getText().length() > 0) {
			if (lookupProfile != null) {
				graph.printMessage("please back to your profile yet");
				return;
			}

			if (myProfile != null) {
				GImage image = null;
				try {
					image = new GImage(picture.getText());
				} catch (ErrorException ex) {
				}

				if (image != null) {
					myProfile.setImage(picture.getText());
					dataBase.update();
					graph.addProfile(myProfile);
					graph.printMessage("Picture updated");
				} else {
					graph.printMessage("Unable to open image file: " + picture.getText());
				}
			} else {
				graph.printMessage("Please select a profile to change picture");
			}

		}
	}

// 	check clicks on add friend	
	private void checkAddFriend(ActionEvent e) {
		if ((e.getSource() == AddF || e.getSource() == friend) && friend.getText().length() > 0) {
			if (lookupProfile != null) {
				graph.printMessage("please back to your profile first");
				return;
			}

			if (myProfile != null) {
				if (myProfile.getName().equals(friend.getText())) {
					graph.printMessage("you can't add yourself as a friend");
					return;
				}

				if (dataBase.containsProfileName(friend.getText()) && myProfile.addFriend(friend.getText())) {
					graph.addProfile(myProfile);
					graph.printMessage(friend.getText() + " added as a friend");
					dataBase.lookupProfile(friend.getText()).addFriend(myProfile.getName());
					dataBase.update();
				} else {
					graph.lookupProfiles(dataBase.lookupProfilesWithKey(friend.getText(), myProfile.getName()));
					graph.printMessage("you have already " + "\"" + friend.getText() + "\"" + " as a friend or " + "\""
							+ friend.getText() + "\"" + " does not exist");
				}

			} else {
				graph.addProfile(myProfile);
				graph.printMessage(friend.getText() + " does not exist");
			}

		}
	}

// 	check clicks on east interactors	
	private void checkEastInteractors(ActionEvent e) {
		if (e.getSource() == next)
			if (firstName.getText().length() > 0 && lastName.getText().length() > 0
					&& !firstName.getText().contains("-") && !lastName.getText().contains("-")) {
				if (!dataBase.containsProfileName(firstName.getText() + " " + lastName.getText())) {
					addSignUpInteractors2();
					graph.signUpLastPage();
				} else {
					graph.printMessage("profile with this name already exist");
				}
			} else {
				graph.printMessage("first and last name's length must be positive and they mustn't contain symbol '-'");
			}

		if (e.getSource() == finish)
			if (checkUsermane() && checkPassword()) {
				saveProfile();
				hideEastInteractors();
				hideSouthInteractors();
				signOut.setVisible(true);
				backToProfile.setVisible(true);
				clearTextFields();
				showWestInteractors();
				showNorthInteractors();
			}

		if (e.getSource() == enter) {
			checkProfile();
		}

	}

//	clear all text fields	
	private void clearTextFields() {
		firstName.setText("");
		lastName.setText("");
		username.setText("");
		password.setText("");
		repeatPassword.setText("");
		status.setText("");
		picture.setText("");
		friend.setText("");
		name.setText("");
	}

//	check profile with username and password	
	private void checkProfile() {
		if (dataBase.findProfile(username.getText(), password.getText()) != null) {

			myProfile = dataBase.findProfile(username.getText(), password.getText());
			graph.addProfile(myProfile);
			clearTextFields();
			showWestInteractors();
			showNorthInteractors();
			hideEastInteractors();
			lookupProfile = null;
			hideSouthInteractors();
			signOut.setVisible(true);
			backToProfile.setVisible(true);
		} else
			graph.printMessage("wrong username or password");
	}

// 	save new profile	
	private void saveProfile() {
		myProfile = new FacePamphletProfileExt(firstName.getText() + " " + lastName.getText(), username.getText(),
				password.getText());

		lookupProfile = null;
		graph.addProfile(myProfile);
		dataBase.addProfile(myProfile);
		dataBase.update();
	}

// 	check clicks on south interactors	
	private void checkSouthInteractors(ActionEvent e) {
		if (e.getSource() == signUp) {
			addSignUpInteractors1();
			graph.signUpFirstPage();
			clearTextFields();
		}

		if (e.getSource() == logIn) {
			addLogInInteractors();
			graph.logIn();
			clearTextFields();
		}

		if (e.getSource() == backToProfile) {
			lookupProfile = null;
			graph.addProfile(myProfile);
			graph.printMessage("backed to your profile");
			clearTextFields();
		}

		if (e.getSource() == signOut) {
			graph.createStartPage();
			hideEastInteractors();
			hideNorthInteractors();
			hideSouthInteractors();
			hideWestInteractors();
			logIn.setVisible(true);
			signUp.setVisible(true);
			clearTextFields();
		}

	}

//	check password if it's not used yet	
	private boolean checkPassword() {
		if (!password.getText().equals(repeatPassword.getText())) {
			graph.printMessage("repeat password must be equal of password");
			return false;
		}

		if (password.getText().contains("-")) {
			graph.printMessage("username and password mustn't contain symbol '-'");
			return false;
		}

		if (dataBase.containsPassword(password.getText())) {
			graph.printMessage("profile with this password already exists");
			return false;
		}

		return true;
	}

//	check password if it's not used yet	
	private boolean checkUsermane() {
		if (username.getText().contains("-")) {
			graph.printMessage("username and password mustn't contain symbol '-'");
			return false;
		}

		if (dataBase.containsUsername(username.getText())) {
			graph.printMessage("profile with this username already exists");
			return false;
		}

		return true;
	}

	private void addSignUpInteractors2() {
		hideEastInteractors();
		hideWestInteractors();
		hideNorthInteractors();
		username.setVisible(true);
		password.setVisible(true);
		repeatPassword.setVisible(true);
		finish.setVisible(true);
	}

	private void addSignUpInteractors1() {
		hideEastInteractors();
		hideWestInteractors();
		hideNorthInteractors();
		firstName.setVisible(true);
		lastName.setVisible(true);
		next.setVisible(true);
	}

	private void addLogInInteractors() {
		hideEastInteractors();
		hideWestInteractors();
		hideNorthInteractors();
		username.setVisible(true);
		password.setVisible(true);
		enter.setVisible(true);
	}

}
