package frame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame implements ActionListener {
	
	JButton buttonMove, buttonList, buttonRun;
	JFrame frame;
	JButton[] functionButtons = new JButton[3];
	JPanel panel;
	
	public MyFrame() {
		
		frame = new JFrame("Pokemon");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLayout(null);
		
		buttonMove = new JButton("Move");
		buttonList = new JButton("List");
		buttonRun = new JButton("Run");
		
		buttonMove.setBounds(50, 430, 145, 50);
		buttonList.setBounds(100, 430, 145, 50);
		buttonRun.setBounds(205, 430, 145, 50);
		
		functionButtons[0] = buttonMove;
		functionButtons[1] = buttonList;
		functionButtons[2] = buttonRun;
		
		for (int i = 0; i < functionButtons.length; i++) {
			functionButtons[i].addActionListener(this);
			functionButtons[i].setFocusable(false);
		}
		
		panel = new JPanel();
		panel.setBounds(50, 100, 300, 300);
		panel.setLayout(new GridLayout(2,2, 10, 10));
		panel.setBackground(Color.LIGHT_GRAY);
		
		panel.add(functionButtons[0]);
		panel.add(functionButtons[2]);
		panel.add(functionButtons[1]);
		
		frame.add(panel);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == buttonMove) {
			System.out.println(String.valueOf(buttonMove));
		}
		else if (e.getSource() == buttonList) {
			System.out.println("Listing moves");
		}
		else if (e.getSource() == buttonRun) {
			System.out.println("Got away safely!");
		} 
		
	}
}
