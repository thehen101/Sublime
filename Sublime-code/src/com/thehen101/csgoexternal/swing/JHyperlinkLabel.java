package com.thehen101.csgoexternal.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Insets;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;

public class JHyperlinkLabel extends JLabel {
	// Eclipse insisted upon having this
	private static final long serialVersionUID = -6924004972014492075L;
	private final HyperlinkMouseAdapter mouseAdapter;

	public JHyperlinkLabel(String displayText, String url) {
		super(displayText);
		URI targetURI = null;
		try {
			targetURI = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		this.mouseAdapter = new HyperlinkMouseAdapter(this, targetURI);
		this.addMouseListener(this.mouseAdapter);
		this.setForeground(Color.BLUE.darker());
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(this.getForeground());

		Insets insets = getInsets();
		int left = insets.left;
		if (getIcon() != null)
			left += getIcon().getIconWidth() + getIconTextGap();

		g.drawLine(left, getHeight() - 1 - insets.bottom, (int) getPreferredSize().getWidth() - insets.right,
				getHeight() - 1 - insets.bottom);
	}
}