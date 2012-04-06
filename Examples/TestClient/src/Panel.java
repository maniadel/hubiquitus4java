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


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hubiquitus.hapi.client.HOption;
import org.hubiquitus.hapi.client.impl.HClient;

/**
 * 
 * @author speed
 * @version 0.3
 * The panel for this example
 */
public class Panel extends JPanel {
	private HClient client;
	public HOption option = new HOption();

	private JTextField usernameField = new JTextField("j.desousa@hub.novediagroup.com");
	private JTextField passwordField = new JTextField("Vaan79000.");
	private JTextField portField = new JTextField("5222");

	private JButton connectButton = new JButton("Connection");
	private JButton disconnectButton = new JButton("Disconnection");

	public Panel() {
		super();
		initComponents();
		initListeners();
	}

	public void initComponents() {
		BorderLayout layout = new BorderLayout();
		this.setLayout(layout);

		JPanel paramsPanel = new JPanel();
		GridLayout paramsLayout = new GridLayout(3, 2);
		paramsPanel.setLayout(paramsLayout);
		paramsPanel.add(new JLabel("username"));
		paramsPanel.add(usernameField);
		paramsPanel.add(new JLabel("password"));
		paramsPanel.add(passwordField);
		paramsPanel.add(new JLabel("host"));
		paramsPanel.add(portField);

		JPanel controlsPanel = new JPanel();
		GridLayout controlsLayout = new GridLayout(1, 2);
		controlsPanel.setLayout(controlsLayout);
		controlsPanel.add(connectButton);
		controlsPanel.add(disconnectButton);

		this.add(paramsPanel, BorderLayout.CENTER);
		this.add(controlsPanel, BorderLayout.SOUTH);
	}

	public void initListeners() {
		connectButton.addMouseListener(new ConnectionButtonListener());
		disconnectButton.addMouseListener(new DisconnectionButtonListener());
	}

	public HClient getClient() {
		return client;
	}

	public void setClient(HClient client) {
		this.client = client;
	}

	class ConnectionButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {

			if (portField.getText() == null)
				option.setServerPort("5222");
			else
				option.setServerPort(portField.getText());
			client.connect(usernameField.getText(), passwordField.getText(), null, option);
		}
	}

	class DisconnectionButtonListener extends MouseAdapter {
		public void mouseClicked(MouseEvent event) {
			client.disconnect();
		}
	}
}
