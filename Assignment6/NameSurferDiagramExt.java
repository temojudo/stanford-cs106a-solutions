import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.util.RandomGenerator;

public class NameSurferDiagramExt extends GCanvas implements NameSurferConstants, ComponentListener {

	private int diagramLength;
	private int diagramHeight;
	private int yearMargin;
	private double[] rankY = new double[NDECADES];
	private NameSurferEntry entry;
	private RandomGenerator rgen = RandomGenerator.getInstance();

	public NameSurferDiagramExt() {
		addComponentListener(this);
	}

	public void drawLastEntry(NameSurferEntry entry) {
		this.entry = entry;
		update();
	}

	public void update() {
		removeAll();
		addGraph();
		addLabels();
		if (entry != null) {
			addEntry();
		}

	}

	private void addGraph() {
		diagramLength = getWidth() - 2 * DIAGRAM_FROM_LEFT;
		diagramHeight = getHeight() - 2 * DIAGRAM_FROM_BOTTOM;

		add(new GLine(DIAGRAM_FROM_LEFT, getHeight() - DIAGRAM_FROM_BOTTOM, DIAGRAM_FROM_LEFT + diagramLength,
				getHeight() - DIAGRAM_FROM_BOTTOM));

		add(new GLine(DIAGRAM_FROM_LEFT, getHeight() - DIAGRAM_FROM_BOTTOM, DIAGRAM_FROM_LEFT,
				getHeight() - (DIAGRAM_FROM_BOTTOM + diagramHeight)));

	}

	private void addLabels() {
		yearMargin = diagramLength / NDECADES;
		for (int i = 0; i < NDECADES; i++) {
			int x = DIAGRAM_FROM_LEFT + yearMargin * i;
			int year = START_DECADE + i * 10;

			GLabel label = new GLabel("" + year);
			add(label, x + (yearMargin - label.getWidth()) / 2,
					getHeight() - DIAGRAM_FROM_BOTTOM + LABELS_FROM_DIAGRAM);

			add(new GLine(x, getHeight() - DIAGRAM_FROM_BOTTOM, x,
					getHeight() - DIAGRAM_FROM_BOTTOM + LABELS_FROM_DIAGRAM));
		}

	}

	private void addEntry() {
		addDiagram();
		addNames();

	}

	private void addDiagram() {
		for (int i = 0; i < NDECADES; i++) {
			int rank = entry.getRank(i);

			if (rank == 0)
				rankY[i] = getHeight() - DIAGRAM_FROM_BOTTOM;
			else
				rankY[i] = getHeight() - DIAGRAM_FROM_BOTTOM - diagramHeight
						+ ((double) rank / MAX_RANK) * diagramHeight;

			GRect rect = new GRect(yearMargin, getHeight() - DIAGRAM_FROM_BOTTOM - rankY[i]);
			rect.setFilled(true);
			rect.setColor(rgen.nextColor());
			add(rect, DIAGRAM_FROM_LEFT + i * yearMargin, rankY[i]);
		}

	}

	private void addNames() {
		String name = entry.getName();
		GLabel label;
		for (int i = 0; i < NDECADES; i++) {
			if (entry.getRank(i) == 0)
				label = new GLabel(name + "*");
			else
				label = new GLabel(name + entry.getRank(i));

			add(label, DIAGRAM_FROM_LEFT + i * yearMargin + (yearMargin - label.getWidth()) / 2,
					rankY[i] - NAME_FROM_DIAGRAM);
		}

	}

//-------------------------------------------------------
//-------------------------------------------------------

	public void componentHidden(ComponentEvent arg0) {
	}

	public void componentMoved(ComponentEvent arg0) {
	}

	public void componentResized(ComponentEvent arg0) {
		update();
	}

	public void componentShown(ComponentEvent arg0) {
	}

}
