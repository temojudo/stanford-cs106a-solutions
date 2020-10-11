
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	private JButton buttonGraph, buttonClear;
	private JTextField tf;
	private NameSurferGraph graph;
	private NameSurferDataBase file = new NameSurferDataBase(NAMES_DATA_FILE);

	public void init() {
		graph = new NameSurferGraph();
		add(graph);

		addInteractors();
		addActionListeners();
	}

//	add buttons text field and label	
	private void addInteractors() {
		add(new JLabel("Name"), SOUTH);

		tf = new JTextField(20);
		add(tf, SOUTH);
		tf.addActionListener(this);

		buttonGraph = new JButton("Graph");
		add(buttonGraph, SOUTH);

		buttonClear = new JButton("Clear");
		add(buttonClear, SOUTH);

	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */

	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() == tf || e.getSource() == buttonGraph) && file.findEntry(tf.getText()) != null)
			graph.addEntry(file.findEntry(tf.getText()));

		if (e.getSource() == buttonClear)
			graph.clear();

	}

}
