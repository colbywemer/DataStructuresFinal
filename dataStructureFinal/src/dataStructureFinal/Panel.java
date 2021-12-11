package dataStructureFinal;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Colby Wemer - Cgwemer CIS171 12601 WH1 Dec 9, 2021
 */
public class Panel extends JPanel {
	double price[] = { 16.99, 6.69, 50.79, 99.99, 5.99, 17.99, 19.99, 10.99, 70.99, 15.99 };
	String items[] = { "item 1", "item 2", "item 3", "item 4", "item 5", "item 6", "item 7", "item 8", "item 9",
			"item 10" };
	Map<String, String> accounts = new HashMap<String, String>();
	private Queue<String>[] queues;
	private JLabel error = new JLabel();
	private JButton login = new JButton("Login");
	private JButton signUp = new JButton("Sign Up");
	private JTextField userNameText = new JTextField(25);
	private JTextField passwordText = new JTextField(25);
	private JLabel userName = new JLabel("Username");
	private JLabel password = new JLabel("Password");
	private JLabel account = new JLabel();
	private JButton logout = new JButton("Logout");
	private JButton low = new JButton("Low - High");
	private JButton high = new JButton("High - Low");
	private JLabel sort = new JLabel("Sort by");
	private JButton[] buttons = new JButton[items.length];
	private JButton[] shipButtons = new JButton[items.length];
	private JButton[] unsortedShipButtons = new JButton[items.length];

	public Panel() {
		queues = new Queue[items.length];
		for (int i = 0; i < items.length; i++)
			queues[i] = new LinkedList<>();
		loginPanel();
		LoginButtonListener loginListener = new LoginButtonListener();
		SignUpButtonListener sigupListener = new SignUpButtonListener();
		login.addActionListener(loginListener);
		signUp.addActionListener(sigupListener);
		LogoutButtonListener logoutListener = new LogoutButtonListener();
		logout.addActionListener(logoutListener);
		SortButtonListener sortListener = new SortButtonListener();
		low.addActionListener(sortListener);
		high.addActionListener(sortListener);
		createShipButtons();
	}

	class LoginButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String userName = userNameText.getText();
			String password = passwordText.getText();
			String value = accounts.get(userName);
			if (!userName.equals("") && !password.equals("")) {
				if (userName.equals("Owner") && password.equals("1234")) {
					error.setText("");
					shipPanel();
				} else {

					if (value != null) {
						if (value.equals(password)) {
							error.setText("");
							newPanel(userName);

						} else {
							error.setText("Incorect Username or Password");
							error.setForeground(Color.red);
						}

					} else {
						if (userName.equals("Owner")) {
							error.setText("Incorect Username or Password");
							error.setForeground(Color.red);
						} else {
							error.setText("Username does not exsist");
							error.setForeground(Color.red);
						}
					}

				}
			} else {
				error.setText("Username and Password cannot be empty");
				error.setForeground(Color.red);
			}
		}

	}

	class SignUpButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String userName = userNameText.getText();
			String password = passwordText.getText();
			if (!userName.equals("") && !password.equals("")) {
				if (accounts.get(userName) != null || userName.equals("Owner")) {
					error.setText("Username Already Exsists");
					error.setForeground(Color.red);
				} else {
					accounts.put(userName, password);
					error.setForeground(Color.black);
					error.setText("Account created! Please Login");
				}

			} else {
				error.setText("Username and Password cannot be empty");
				error.setForeground(Color.red);
			}
		}

	}

	class LogoutButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			loginPanel();

		}

	}

	class SortButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			removeAll();
			repaint();
			add(sort);
			add(low);
			add(high);
			add(account);
			add(logout);
			sort();
			BuyButtonListener buy = new BuyButtonListener();
			if (e.getSource() == low) {
				for (int i = 0; i < items.length; i++) {
					String buttonLabel = items[i] + " Price: $" + price[i];
					JLabel label = new JLabel(buttonLabel);
					add(label);
					JButton button = new JButton("Buy");
					buttons[i] = button;
					button.addActionListener(buy);
					add(button);
				}
				validate();
			} else {
				for (int l = items.length; l > 0; l--) {
					String buttonLabel = items[l - 1] + " Price: $" + price[l - 1];
					JLabel label = new JLabel(buttonLabel);
					add(label);
					JButton button = new JButton("Buy");
					buttons[l - 1] = button;
					button.addActionListener(buy);
					add(button);
				}
				validate();
			}

		}

	}

	class BuyButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String name = account.getText().substring(13);

			for (int i = 0; i < items.length; i++) {
				if (e.getSource() == buttons[i]) {
					queues[i].add(name);
					break;
				}
			}

		}

	}

	class ShipButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String shipTo = null;
			String item = null;

			for (int i = 0; i < items.length; i++) {
				if (e.getSource() == shipButtons[i]) {
					item = items[i];
					shipTo = queues[i].peek();
					if (shipTo != null) {
						queues[i].remove();
						error.setText("Shiped " + item + " to " + shipTo);
						break;
					} else {
						error.setText("No orders for " + item);
					}
				}
			}
		}

	}

	public void newPanel(String username) {
		removeAll();
		repaint();
		account.setText("Logged in as " + username);
		add(sort);
		add(low);
		add(high);
		add(account);
		add(logout);
		BuyButtonListener addToCart = new BuyButtonListener();
		for (int i = 0; i < items.length; i++) {
			String buttonLabel = items[i] + " Price: $" + price[i];
			JLabel label = new JLabel(buttonLabel);
			add(label);
			JButton button = new JButton("Buy");
			buttons[i] = button;
			button.addActionListener(addToCart);
			add(button);
		}
		validate();

	}

	public void loginPanel() {
		error.setText("");
		removeAll();
		repaint();
		add(userName);
		add(userNameText);
		add(password);
		add(passwordText);
		add(login);
		add(signUp);
		add(error);
	}

	public void sort() {
		int n = price.length;
		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n - i - 1; j++)
				if (price[j] > price[j + 1]) {
					double temp = price[j];
					String temp2 = items[j];
					Queue<String> temp3 = queues[j];
					JButton temp4 = shipButtons[j];
					price[j] = price[j + 1];
					items[j] = items[j + 1];
					queues[j] = queues[j + 1];
					shipButtons[j] = shipButtons[j + 1];
					price[j + 1] = temp;
					items[j + 1] = temp2;
					queues[j + 1] = temp3;
					shipButtons[j + 1] = temp4;
				}
	}

	public void createShipButtons() {
		ShipButtonListener ship = new ShipButtonListener();
		for (int i = 0; i < items.length; i++) {
			String buttonLabel = "Ship Item " + (i + 1);
			JButton button = new JButton(buttonLabel);
			shipButtons[i] = button;
			unsortedShipButtons[i] = shipButtons[i];
			shipButtons[i].addActionListener(ship);
		}
	}

	public void shipPanel() {
		removeAll();
		repaint();
		for (int i = 0; i < items.length; i++) {

			add(unsortedShipButtons[i]);
		}
		add(logout);
		add(error);
		error.setForeground(Color.black);
		validate();
	}

}
