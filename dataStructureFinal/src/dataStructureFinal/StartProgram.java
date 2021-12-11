package dataStructureFinal;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Colby Wemer - Cgwemer CIS171 12601 WH1 Dec 9, 2021
 */
public class StartProgram {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Panel panel = new Panel();
		frame.add(panel);
		frame.setSize(1800, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}
