package music_test;
//: c14:FileChooserTest.java
//Demonstration of File dialog boxes.
//From 'Thinking in Java, 3rd ed.' (c) Bruce Eckel 2002
//www.BruceEckel.com. See copyright notice in CopyRight.txt.

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileChooserTest extends JFrame {
	private JTextField filename = new JTextField(), dir = new JTextField();
	private JButton open = new JButton("Autotune");

	private String[] keys = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};

	private JTextField textfield1 = new JTextField
			("Key to tune to: ");

	private JTextField textfield2 = new JTextField(15);
	private JComboBox comboBox = new JComboBox();

	private int count = 0;
	private String key = "C";

	public FileChooserTest() {

		for(int i = 0; i < keys.length; i++)
			comboBox.addItem(keys[count++]);
		textfield1.setEditable(false);

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				key = (String)((JComboBox)e.getSource()).getSelectedItem();
				textfield2.setText("You Selected : " +     
						((JComboBox)e.getSource()).getSelectedItem());
			}
		});

		setLayout(new FlowLayout());
		add(textfield1);
		add(textfield2);
		add(comboBox);   

		JPanel p = new JPanel();
		open.addActionListener(new OpenL());
		p.add(open);
		Container cp = getContentPane();
		cp.add(p, BorderLayout.SOUTH);
		dir.setEditable(false);
		filename.setEditable(false);
		p = new JPanel();
		p.setLayout(new GridLayout(2, 1));
		p.add(filename);
		p.add(dir);
		filename.setVisible(false);
		dir.setVisible(false);
		cp.add(p, BorderLayout.NORTH);
	}

	class OpenL implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser c = new JFileChooser();
			// Demonstrate "Open" dialog:
			int rVal = c.showOpenDialog(FileChooserTest.this);

			if (rVal == JFileChooser.APPROVE_OPTION) {

				filename.setText("Select file to autotune");
				dir.setText("May take some time...");

				filename.setVisible(true);
				dir.setVisible(true);

				String filenameString = c.getSelectedFile().getName();
				String dirString = c.getCurrentDirectory().toString();

				//filename.setText("Autotune in progress...");
				//dir.setText("Output will be "+dirString+"/"+"changed-"+filenameString);

				testing2 block = new testing2();
				try {
					block.main(dirString+"/"+filenameString,dirString+"/"+"changed-"+filenameString, key);
				}
				catch (Exception q) {
					System.out.println(q);
				}
				filename.setText("Autotune completed.");
				dir.setText("Outuput is "+dirString+"/"+"changed-"+filenameString);
				textfield2.setText("Success!");



			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText("You pressed cancel");
				dir.setText("");
			}
		}
	}

	public static void main(String[] args) {
		FileChooserTest GUI = new FileChooserTest();
		run(GUI, 600, 150);
		//System.out.println(GUI.dir);
	}

	public static void run(JFrame frame, int width, int height) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setVisible(true);
	}
}