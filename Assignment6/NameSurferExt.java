
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.GCanvas;
import acm.program.*;
import javafx.scene.control.RadioButton;

import java.awt.Color;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;

public class NameSurferExt extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	private JButton buttonGraph, buttonClear, buttonRemove;
	private JTextField tf;
	private JRadioButton yellow, black, red, blue, graphic, dgram;
	private NameSurferGraphExt graph;
	private NameSurferDiagramExt diagram;
	private HashMap<NameSurferEntry, Color> entries = new HashMap<>();
	private NameSurferEntry lastEntry;
	private NameSurferDataBase file = new NameSurferDataBase(NAMES_DATA_FILE);

	public void init() {
		diagram = new NameSurferDiagramExt();
		graph = new NameSurferGraphExt();

		addInteractors();
		addActionListeners();
	}

//	add buttons text field and label	
	private void addInteractors() {
		add(new JLabel("Name"), SOUTH);

		tf = new JTextField(10);
		add(tf, SOUTH);
		tf.addActionListener(this);

		initButtons();
		initSouthRadioButtons();
		initNorthRadioButtons();
	}

	private void initNorthRadioButtons() {
		add(new JLabel("Choose view"), NORTH);

		graphic = new JRadioButton("Graph");
		graphic.addActionListener(this);
		add(graphic, NORTH);

		dgram = new JRadioButton("Diagram");
		dgram.addActionListener(this);
		add(dgram, NORTH);

		ButtonGroup group = new ButtonGroup();
		group.add(graphic);
		group.add(dgram);

	}

	private void initButtons() {
		buttonGraph = new JButton("Graph");
		add(buttonGraph, SOUTH);

		buttonRemove = new JButton("Remove");
		add(buttonRemove, SOUTH);

		buttonClear = new JButton("Clear");
		add(buttonClear, SOUTH);

	}

	private void initSouthRadioButtons() {
		add(new JLabel("   Choose colour"), SOUTH);

		black = new JRadioButton("black");
		black.setSelected(true);
		add(black, SOUTH);

		red = new JRadioButton("red");
		add(red, SOUTH);

		blue = new JRadioButton("blue");
		add(blue, SOUTH);

		yellow = new JRadioButton("yellow");
		add(yellow, SOUTH);

		ButtonGroup group = new ButtonGroup();
		group.add(black);
		group.add(red);
		group.add(blue);
		group.add(yellow);

	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */

	public void actionPerformed(ActionEvent e) {
		checkButtons(e);
		checkRadioButtons(e);
	}

	private void checkButtons(ActionEvent e) {
		if ((e.getSource() == tf || e.getSource() == buttonGraph) && file.findEntry(tf.getText()) != null) {
			lastEntry = file.findEntry(tf.getText());
			if (!entries.containsKey(lastEntry)) {
				entries.put(lastEntry, chooseColor());
				graph.update(entries);
				diagram.drawLastEntry(lastEntry);
			}

		}

		if (e.getSource() == buttonRemove && file.findEntry(tf.getText()) != null) {
			entries.remove(file.findEntry(tf.getText()));
			graph.update(entries);
		}

		if (e.getSource() == buttonClear) {
			entries.clear();
			graph.update(entries);
		}

	}

	private void checkRadioButtons(ActionEvent e) {
		if (e.getSource() == graphic) {
			removeAll();
			graph = new NameSurferGraphExt();
			add(graph);
			graph.update(entries);
			validate();
		}

		if (e.getSource() == dgram) {
			removeAll();
			diagram = new NameSurferDiagramExt();
			add(diagram);
			diagram.drawLastEntry(lastEntry);
			validate();
		}

	}

	private Color chooseColor() {
		if (black.isSelected())
			return Color.BLACK;

		if (red.isSelected())
			return Color.RED;

		if (blue.isSelected())
			return Color.BLUE;

		return Color.YELLOW;
	}

}
