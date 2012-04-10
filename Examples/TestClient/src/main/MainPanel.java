
/*
 * Copyright (c) Novedia Group 2012.
 *
 *     This file is part of Hubiquitus.
 *
 *     Hubiquitus is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Hubiquitus is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Hubiquitus.  If not, see <http://www.gnu.org/licenses/>.
 */

package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.hubiquitus.hapi.client.HCallback;
import org.hubiquitus.hapi.client.HOption;
import org.hubiquitus.hapi.client.impl.HClient;

/**
 * 
 * @author speed
 * @version 0.3
 * The panel for this example
 */

public class MainPanel extends JPanel {
	private HClient client;
	
	private HOption option = new HOption();
	private CallbackExample callback = new CallbackExample(this);

	private JTextField usernameField = new JTextField("");
	private JTextField passwordField = new JTextField("");
	private JTextField portField = new JTextField("5222");

	private JButton connectButton = new JButton("Connection");
	private JButton disconnectButton = new JButton("Disconnection");
	
	private JRadioButton xmppRadioButton = new JRadioButton("XMPP");
	private JRadioButton socketRadioButton = new JRadioButton("Socket IO");
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JTextArea logArea = new JTextArea(10,50);

	public MainPanel() {
		super();
		initComponents();
		initListeners();
	}

	/**
	 * Initialization of all the component
	 */
	public void initComponents() {
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);
		
		
		buttonGroup.add(xmppRadioButton);
		buttonGroup.add(socketRadioButton);
		xmppRadioButton.setSelected(true);

		//Initialization of Labels,TextFields and RadioButtons
		JPanel paramsPanel = new JPanel();
		GridLayout paramsLayout = new GridLayout(4, 2);
		paramsPanel.setLayout(paramsLayout);
		paramsPanel.add(new JLabel("username"));
		paramsPanel.add(usernameField);
		paramsPanel.add(new JLabel("password"));
		paramsPanel.add(passwordField);
		paramsPanel.add(new JLabel("host"));
		paramsPanel.add(portField);
		paramsPanel.add(xmppRadioButton);
		paramsPanel.add(socketRadioButton);
		
		
		//Initialization of Buttons
		JPanel controlsPanel = new JPanel();
		GridLayout controlsLayout = new GridLayout(1, 2);
		controlsPanel.setLayout(controlsLayout);
		controlsPanel.add(connectButton);
		controlsPanel.add(disconnectButton);
		
		//Initialization of the TextArea
		JPanel consolePanel = new JPanel();
		logArea.setEditable(false);
		consolePanel.add(logArea);
		JScrollPane txtScrol=new JScrollPane(logArea);
		txtScrol.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		consolePanel.add(txtScrol);

		//Add all in the layout
		this.add(paramsPanel, BorderLayout.NORTH);
		this.add(controlsPanel, BorderLayout.CENTER);
		this.add(consolePanel, BorderLayout.SOUTH);
	}

	/**
	 * Initialization of the listeners 
	 */
	public void initListeners() {
		connectButton.addMouseListener(new ConnectionButtonListener());
		disconnectButton.addMouseListener(new DisconnectionButtonListener());
	}
	
	/**
	 * Add a text to the TextArea
	 * @param text
	 */
	public void addTextArea(String text){
		String tempTxt = this.logArea.getText() + "\n" + text;
		this.logArea.setText(tempTxt);
	}
	
	// Listener of button connection
	class ConnectionButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			
			String txt = portField.getText();
			if (txt == null || txt.equalsIgnoreCase(""))
				option.setServerPort("5222");
			else
				option.setServerPort(portField.getText());
			
			if(xmppRadioButton.isSelected())
				option.setTransport("XMPP");
			else {
				System.out.println("Pas encore pris en charge");
				addTextArea("Pas encore pris en charge");
			}
			client.connect(usernameField.getText(), passwordField.getText(), callback, option);
		}
	}

	// Listener of button disconnection
	class DisconnectionButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			client.disconnect();
		}
	}
	
	/* Getters & Setters */
	public HClient getClient() {
		return client;
	}

	public void setClient(HClient client) {
		this.client = client;
	}
	
	public void setTextArea(String text){
		this.logArea.setText(text);
	}
	
	
}
