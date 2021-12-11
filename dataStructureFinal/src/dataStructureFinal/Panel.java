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
		loginPanel();//sends to login panel
		LoginButtonListener loginListener = new LoginButtonListener();//sets listeners for buttons
		SignUpButtonListener sigupListener = new SignUpButtonListener();
		login.addActionListener(loginListener);
		signUp.addActionListener(sigupListener);
		LogoutButtonListener logoutListener = new LogoutButtonListener();
		logout.addActionListener(logoutListener);
		SortButtonListener sortListener = new SortButtonListener();
		low.addActionListener(sortListener);
		high.addActionListener(sortListener);
		createShipButtons();//creates the ship buttons
	}
//listens for when login button is pressed
	class LoginButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String userName = userNameText.getText();//gets username
			String password = passwordText.getText();//gets password
			String value = accounts.get(userName);
			if (!userName.equals("") && !password.equals("")) {//checks if fields empty
				if (userName.equals("Owner") && password.equals("1234")) {
					error.setText("");
					shipPanel();//sends to ship panel
				} else {

					if (value != null) {
						if (value.equals(password)) {//checks if password correct
							error.setText("");
							newPanel(userName);

						} else {
							error.setText("Incorect Username or Password");//message user error message
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
//class that listens for when the sign up button is pressed
	class SignUpButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String userName = userNameText.getText();//gets username from username field
			String password = passwordText.getText();//gets password from password field
			if (!userName.equals("") && !password.equals("")) {//checks if username or password field is empty
				if (accounts.get(userName) != null || userName.equals("Owner")) {//checks if the username is Owner
					error.setText("Username Already Exsists");//informs user the user already exisists
					error.setForeground(Color.red);//sets error message to color to red
				} else {
					accounts.put(userName, password);
					error.setForeground(Color.black);//sets message color to black
					error.setText("Account created! Please Login");//informs user the account was succesfully created
				}

			} else {
				error.setText("Username and Password cannot be empty");//informs user the username and/or password field can not be empty
				error.setForeground(Color.red);//sets error message color to red
			}
		}

	}
//class that listens for when logout button is pressed
	class LogoutButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			loginPanel();//sends the user to login panel

		}

	}

	class SortButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			removeAll();//removes all components from panel
			repaint();
			add(sort);//readds all components to panel
			add(low);
			add(high);
			add(account);
			add(logout);
			sort();//sorts the items and price by price
			BuyButtonListener buy = new BuyButtonListener();
			if (e.getSource() == low) {//adds a buy button and labels for all the items based on price low to high
				for (int i = 0; i < items.length; i++) {
					String buttonLabel = items[i] + " Price: $" + price[i];
					JLabel label = new JLabel(buttonLabel);
					add(label);
					JButton button = new JButton("Buy");
					buttons[i] = button;//saves all the buy buttons in an array
					button.addActionListener(buy);
					add(button);//adds the buttons
				}
				validate();
			} else {//adds a buy button and label for all items based on price high to low
				for (int l = items.length; l > 0; l--) {
					String buttonLabel = items[l - 1] + " Price: $" + price[l - 1];
					JLabel label = new JLabel(buttonLabel);
					add(label);
					JButton button = new JButton("Buy");
					buttons[l - 1] = button;//saves all the buttons in an array
					button.addActionListener(buy);
					add(button);//adds the buttons
				}
				validate();
			}

		}

	}
//listens for when buy buttons are pressed
	class BuyButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			String name = account.getText().substring(13);

			for (int i = 0; i < items.length; i++) {//uses buy button array to determine which buy button was pressed
				if (e.getSource() == buttons[i]) {
					queues[i].add(name);//adds the user to the queue for item they selected
					break;
				}
			}

		}

	}
//listens for when ship buttons are pressed
	class ShipButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String shipTo = null;
			String item = null;

			for (int i = 0; i < items.length; i++) {
				if (e.getSource() == shipButtons[i]) {//uses shipButtons array to determine which item is to be shipped
					item = items[i];
					shipTo = queues[i].peek();//gets user at front of queue for item
					if (shipTo != null) {
						queues[i].remove();//removes user at front of queue
						error.setText("Shiped " + item + " to " + shipTo);
						break;
					} else {
						error.setText("No orders for " + item);
					}
				}
			}
		}

	}

	public void newPanel(String username) {//sends user to store panel
		removeAll();//removes all elements
		repaint();
		account.setText("Logged in as " + username);
		add(sort);//readds all elements
		add(low);
		add(high);
		add(account);
		add(logout);
		BuyButtonListener addToCart = new BuyButtonListener();
		for (int i = 0; i < items.length; i++) {
			String buttonLabel = items[i] + " Price: $" + price[i];//adds buy buttons to all items
			JLabel label = new JLabel(buttonLabel);
			add(label);
			JButton button = new JButton("Buy");
			buttons[i] = button;//adds buy buttons to array
			button.addActionListener(addToCart);
			add(button);//adds buy buttons to panel
		}
		validate();

	}

	public void loginPanel() {//sends user to login panel
		error.setText("");
		removeAll();//removes all components
		repaint();
		add(userName);//readd all components
		add(userNameText);
		add(password);
		add(passwordText);
		add(login);
		add(signUp);
		add(error);
	}

	public void sort() {//sorts the arrays using bubble sort by price low to high
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

	public void createShipButtons() {//creates buttons for ship panel
		ShipButtonListener ship = new ShipButtonListener();
		for (int i = 0; i < items.length; i++) {
			String buttonLabel = "Ship Item " + (i + 1);
			JButton button = new JButton(buttonLabel);
			shipButtons[i] = button;//saves buttons to array
			unsortedShipButtons[i] = shipButtons[i];
			shipButtons[i].addActionListener(ship);
		}
	}

	public void shipPanel() {
		removeAll();//removes all components
		repaint();
		for (int i = 0; i < items.length; i++) {//adds ship buttons

			add(unsortedShipButtons[i]);
		}
		add(logout);//adds logout button
		add(error);
		error.setForeground(Color.black);
		validate();
	}

}
