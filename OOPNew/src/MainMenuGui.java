import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;

public class MainMenuGui {

	boolean location = false;
	boolean time = false;
	boolean id = false;
	boolean and = false;
	boolean or = false;
	String filter1 = "";
	String filter2 = "";
	boolean notFilter1 = false;
	boolean notFilter2 = false;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuGui window = new MainMenuGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenuGui() {
		initialize();
	}

	public void filters() {
		if(and)
			if(notFilter1&&notFilter2)
				System.out.println("Filters (Not " + filter1 + ") and (Not " + filter2 + ")");
			else if(notFilter1)
				System.out.println("Filters (Not " + filter1 + ") and " + filter2);
			else if(notFilter2)
				System.out.println("Filters " + filter1 + " and (Not " + filter2 + ")");
			else System.out.println("Filters " + filter1 + " and " + filter2);
		else if(or)
			if(notFilter1&&notFilter2)
				System.out.println("Filters (Not " + filter1 + ") or (Not " + filter2 + ")");
			else if(notFilter1)
				System.out.println("Filters (Not " + filter1 + ") or " + filter2);
			else if(notFilter2)
				System.out.println("Filters " + filter1 + " or (Not " + filter2 + ")");
			else System.out.println("Filters " + filter1 + " or " + filter2);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 715, 627);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextPane txtpnStartTime = new JTextPane();
		txtpnStartTime.setText("Start time");
		txtpnStartTime.setVisible(time);

		JTextPane txtpnEndTime = new JTextPane();
		txtpnEndTime.setText("End time");
		txtpnEndTime.setVisible(time);

		JTextPane txtpnLat = new JTextPane();
		txtpnLat.setText("Lat");
		txtpnLat.setVisible(location);

		JTextPane txtpnLon = new JTextPane();
		txtpnLon.setText("Lon");
		txtpnLon.setVisible(location);

		JTextPane txtpnAlt = new JTextPane();
		txtpnAlt.setText("Alt");
		txtpnAlt.setVisible(location);

		JTextPane txtpnIdFilter = new JTextPane();
		txtpnIdFilter.setText("ID name");
		txtpnIdFilter.setVisible(id);


		JCheckBox chckbxNot1Filter = new JCheckBox("Not");
		chckbxNot1Filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxNot1Filter.isSelected())
					notFilter1 = true;
				else notFilter2 = false;
				filters();
			}
		});
		chckbxNot1Filter.setBackground(Color.LIGHT_GRAY);

		JCheckBox chckbxNot2Filter = new JCheckBox("Not");
		chckbxNot2Filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxNot2Filter.isSelected())
					notFilter2 = true;
				else notFilter2 = false;
				filters();
			}
		});
		chckbxNot2Filter.setBackground(Color.LIGHT_GRAY);

		JButton btnLoadFile = new JButton("Load Combined CSV File");
		btnLoadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle(null);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv", "csv");
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): " 
							+  chooser.getCurrentDirectory());
					System.out.println("getSelectedFile() : " 
							+  chooser.getSelectedFile());
				}
				else {
					System.out.println("No Selection ");
				}
			}
		});

		JButton btnLoadFolder = new JButton("Load Wigle folder");
		btnLoadFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle(null);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getCurrentDirectory(): " 
							+  chooser.getCurrentDirectory());
					System.out.println("getSelectedFile() : " 
							+  chooser.getSelectedFile());
				}
				else {
					System.out.println("No Selection ");
				}
			}
		});

		JButton btnClearAll = new JButton("Clear all");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JButton btnSaveCombinedCsv = new JButton("Save Combined CSV File");
		btnSaveCombinedCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton btnSaveKmlFile = new JButton("Save KML File");
		btnSaveKmlFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JComboBox comboBoxFilter1 = new JComboBox();
		comboBoxFilter1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filter1 = (String) comboBoxFilter1.getSelectedItem();
				filters();
			}
		});
		comboBoxFilter1.setModel(new DefaultComboBoxModel(new String[] {"", "Time", "Location", "ID"}));
		comboBoxFilter1.setMaximumRowCount(4);
		comboBoxFilter1.setToolTipText("AND\r\nNOT\r\nOR");

		JComboBox comboBoxFilter2 = new JComboBox();
		comboBoxFilter2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filter2 = (String)comboBoxFilter2.getSelectedItem();
				filters();
			}
		});
		comboBoxFilter2.setModel(new DefaultComboBoxModel(new String[] {"", "Time", "Location", "ID"}));
		comboBoxFilter2.setToolTipText("AND\r\nNOT\r\nOR");
		comboBoxFilter2.setMaximumRowCount(4);

		JRadioButton rdbtnAnd = new JRadioButton("AND");
		JRadioButton rdbtnOr = new JRadioButton("OR");
		rdbtnAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnAnd.isSelected()) {
					and = true;
					or = false;
					rdbtnOr.setSelected(false);
				}
			}
		});

		rdbtnOr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnOr.isSelected()) {
					or = true;
					and = false;
					rdbtnAnd.setSelected(false);
				}
			}
		});
		rdbtnOr.setBackground(Color.LIGHT_GRAY);
		rdbtnAnd.setBackground(Color.LIGHT_GRAY);


		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(27)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnLoadFile)
								.addComponent(btnSaveCombinedCsv)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(9)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(chckbxNot1Filter)
														.addGap(18)
														.addComponent(comboBoxFilter1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addComponent(txtpnStartTime, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtpnEndTime, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
										.addGap(20)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(rdbtnOr)
														.addGap(56)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(txtpnLat, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
																.addComponent(txtpnLon, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
																.addComponent(txtpnAlt, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)))
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(rdbtnAnd)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(chckbxNot2Filter)
														.addGap(18)
														.addComponent(comboBoxFilter2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup()
														.addGap(151)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(btnSaveKmlFile)
																.addComponent(btnLoadFolder)))
												.addGroup(groupLayout.createSequentialGroup()
														.addGap(18)
														.addComponent(txtpnIdFilter, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))))
								.addComponent(btnClearAll))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnLoadFile)
								.addComponent(btnLoadFolder))
						.addGap(26)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSaveCombinedCsv)
								.addComponent(btnSaveKmlFile))
						.addGap(18)
						.addComponent(btnClearAll)
						.addGap(22)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(chckbxNot1Filter, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBoxFilter1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(chckbxNot2Filter)
								.addComponent(comboBoxFilter2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnAnd))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(txtpnStartTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtpnLat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtpnIdFilter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(txtpnEndTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtpnLon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(txtpnAlt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(rdbtnOr))
						.addGap(225))
				);
		frame.getContentPane().setLayout(groupLayout);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{chckbxNot1Filter, btnClearAll, btnSaveCombinedCsv, btnSaveKmlFile, btnLoadFile, btnLoadFolder, chckbxNot2Filter}));
	}
}
