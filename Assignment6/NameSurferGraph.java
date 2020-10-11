
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	private ArrayList<NameSurferEntry> entryList = new ArrayList<NameSurferEntry>();

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		entryList.clear();
		update();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		if (!entryList.contains(entry)) {
			entryList.add(entry);
			update();
		}
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		int diff = getWidth() / NDECADES;
		addVerticalLines(diff);
		addLabels(diff);
		addHorizontalLines();
		addGraph();

	}

//	add all entryList elements at graph	
	private void addGraph() {
		for (int i = 0; i < entryList.size(); i++) {
			Color color = getColor(i);
			addPeople(entryList.get(i), color);
		}

	}

//	take colour for each people	
	private Color getColor(int i) {
		if (i % 4 == 0)
			return Color.BLACK;
		if (i % 4 == 1)
			return Color.RED;
		if (i % 4 == 2)
			return Color.BLUE;

		return Color.YELLOW;

	}

//	add each people on graph with colour	
	private void addPeople(NameSurferEntry entry, Color color) {
		int diff = getWidth() / NDECADES;
		String name = entry.getName();
		Point[] point = new Point[NDECADES];

		for (int i = 0; i < NDECADES; i++) {
			if (entry.getRank(i) == 0) {
				int x = i * diff;
				int y = getHeight() - GRAPH_MARGIN_SIZE;
				drawNames(point, i, x, y, name + "*", color);

			} else {
				int x = i * diff;
				int y = GRAPH_MARGIN_SIZE + entry.getRank(i) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
				drawNames(point, i, x, y, name + entry.getRank(i), color);
			}

			if (i > 0)
				drawLines(point, i, color);
		}

	}

//	add names	
	private void drawNames(Point[] point, int i, int x, int y, String name, Color color) {
		point[i] = new Point(x, y);
		GLabel label = new GLabel(name, x, y);
		label.setColor(color);
		add(label);

	}

//	add lines	
	private void drawLines(Point[] point, int i, Color color) {
		GLine line = new GLine(point[i - 1].getX(), point[i - 1].getY(), point[i].getX(), point[i].getY());
		line.setColor(color);
		add(line);

	}

//	add years at south part of graph	
	private void addLabels(int diff) {
		for (int i = 0; i < NDECADES; i++) {
			int year = START_DECADE + i * 10;
			GLabel label = new GLabel("" + year);
			add(label, i * diff, getHeight() - (GRAPH_MARGIN_SIZE - label.getAscent()) / 2);
		}

	}

//	just add vertical lines	
	private void addVerticalLines(int diff) {
		for (int i = 0; i < NDECADES; i++)
			add(new GLine(i * diff, 0, i * diff, getHeight()));

	}

//	just add horizontal lines	
	private void addHorizontalLines() {
		add(new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE));
		add(new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE));
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}
}
