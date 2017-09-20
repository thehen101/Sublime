package com.thehen101.csgoexternal.swing;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.JLabel;

public class HyperlinkMouseAdapter extends MouseAdapter {
	private final JLabel parentLabel;
	private final URI targetURI;

	public HyperlinkMouseAdapter(JLabel parentLabel, URI targetURI) {
		this.parentLabel = parentLabel;
		this.targetURI = targetURI;
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {
		try {
			this.parentLabel.setForeground(Color.MAGENTA.darker().darker().darker());
			Desktop.getDesktop().browse(this.targetURI);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}