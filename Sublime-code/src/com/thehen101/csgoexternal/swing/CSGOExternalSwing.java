package com.thehen101.csgoexternal.swing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class CSGOExternalSwing {
	
	/*
	 * Designed with the assistance of Eclipse WindowBuilder
	 */
	public static void main(String[] args) {	
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame("Sublime");
		frame.setSize(458, 345);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 450, 276);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Main", null, panel_1, null);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		panel_1.add(Box.createHorizontalGlue());
		JLabel label = new JLabel("Sublime");
		label.setFont(new Font("Tahoma", Font.BOLD, 48));
		panel_1.add(label);
		panel_1.add(Box.createHorizontalGlue());
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Cheats", null, panel, null);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		
		JLabel lblNewLabel_4 = new JLabel("New label");
		
		JLabel lblNewLabel_5 = new JLabel("New label");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_4)
						.addComponent(lblNewLabel_5))
					.addContainerGap(389, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_4)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_5)
					.addContainerGap(146, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JLabel lblNewLabel = new JLabel("Sublime - A FOSS project available on");
		lblNewLabel.setBounds(4, 280, 184, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JHyperlinkLabel lblGithub = new JHyperlinkLabel("Github", "https://github.com/thehen101/Sublime");
		lblGithub.setBounds(187, 280, 31, 14);
		frame.getContentPane().add(lblGithub);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		frame.setVisible(true);
	}
}
