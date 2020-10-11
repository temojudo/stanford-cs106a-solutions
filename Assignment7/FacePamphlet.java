
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import acmx.export.java.util.Iterator;

import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	private JButton Add, Delete, Lookup, ChS, ChP, AddF;
	private JTextField name, status, picture, friend;
	FacePamphletProfile s = null;
	FacePamphletDatabase base = new FacePamphletDatabase();
	private FacePamphletCanvas canvas;

	public void init() {
		canvas = new FacePamphletCanvas();
		add(canvas);
		addNorthInteractors();
		addWestInteractors();
		addActionListeners();

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

	}

//	init north interactors	
	private void addNorthInteractors() {
		add(new JLabel("name"), NORTH);

		name = new JTextField(TEXT_FIELD_SIZE);
		add(name, NORTH);

		Add = new JButton("Add");
		add(Add, NORTH);

		Delete = new JButton("Delete");
		add(Delete, NORTH);

		Lookup = new JButton("Lookup");
		add(Lookup, NORTH);

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */

	public void actionPerformed(ActionEvent e) {
		checkAdd(e);
		checkDelete(e);
		checkLookup(e);
		checkChangeStatus(e);
		checkChangePicture(e);
		checkAddFriend(e);

	}

//	check add profile	
	private void checkAdd(ActionEvent e) {
		if (e.getSource() == Add && name.getText().length() > 0) {

			if (base.containsProfile(name.getText())) {
				s = base.getProfile(name.getText());
				canvas.displayProfile(s);
				canvas.showMessage("A profile with the name " + name.getText() + " already exist");
			} else {
				s = new FacePamphletProfile(name.getText());
				base.addProfile(s);
				canvas.displayProfile(s);
				canvas.showMessage("New profile created");
			}

		}
	}

//	check delete profile	
	private void checkDelete(ActionEvent e) {
		if (e.getSource() == Delete && name.getText().length() > 0) {

			s = null;
			canvas.displayProfile(s);

			if (base.containsProfile(name.getText())) {
				base.deleteProfile(name.getText());
				canvas.showMessage("Profile of " + name.getText() + " deleted");
			} else
				canvas.showMessage("A profile with the name " + name.getText() + " does not exist");

		}
	}

//	check look up profile	
	private void checkLookup(ActionEvent e) {
		if (e.getSource() == Lookup && name.getText().length() > 0) {

			if (base.containsProfile(name.getText())) {
				s = base.getProfile(name.getText());
				canvas.displayProfile(s);
				canvas.showMessage("Displaying " + name.getText());
			} else {
				s = null;
				canvas.displayProfile(s);
				canvas.showMessage("A profile with the name " + name.getText() + " does not exist");
			}

		}
	}

//	check change status button and text field	
	private void checkChangeStatus(ActionEvent e) {
		if ((e.getSource() == ChS || e.getSource() == status) && status.getText().length() > 0) {

			if (s != null) {
				s.setStatus(status.getText());
				canvas.displayProfile(s);
				canvas.showMessage("Status updated to " + status.getText());
			} else {
				canvas.displayProfile(s);
				canvas.showMessage("Please select a profile to change status");
			}

		}
	}

//	check change picture button and text field	
	private void checkChangePicture(ActionEvent e) {
		if ((e.getSource() == ChP || e.getSource() == picture) && picture.getText().length() > 0) {

			if (s != null) {
				GImage image = null;
				try {
					image = new GImage(picture.getText());
				} catch (ErrorException ex) {
					// Code that is executed if the filename cannot be opened.
				}

				s.setImage(image);
				if (image != null) {
					canvas.displayProfile(s);
					canvas.showMessage("Picture updated");
				} else {
					canvas.displayProfile(s);
					canvas.showMessage("Unable to open image file: " + picture.getText());
				}
			} else {
				canvas.displayProfile(s);
				canvas.showMessage("Please select a profile to change picture");
			}

		}
	}

//	check add friend button and text field	
	private void checkAddFriend(ActionEvent e) {
		if ((e.getSource() == AddF || e.getSource() == friend) && friend.getText().length() > 0) {

			if (s != null) {
				if (s.getName().equals(friend.getText())) {
					canvas.showMessage("you can't add yourself as a friend");
					return;
				}

				if (s.addFriend(friend.getText()) && base.containsProfile(friend.getText())) {
					canvas.displayProfile(s);
					canvas.showMessage(friend.getText() + " added as a friend");
					base.getProfile(friend.getText()).addFriend(s.getName());

				} else {
					canvas.displayProfile(s);
					canvas.showMessage(s.getName() + " has already " + friend.getText() + " as a friend or "
							+ friend.getText() + " does not exist");
				}

			} else {
				canvas.displayProfile(s);
				canvas.showMessage(friend.getText() + " does not exist");
			}

		}
	}

}
