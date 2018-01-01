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

public class MainMenuGui {
	
	boolean location = false;
	boolean time = false;
	boolean id = false;

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
		System.out.println("Filter Time is " + time + ", Filter Location is " + location + ", Filter ID is " + id);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 782, 924);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JCheckBox chckbxTime = new JCheckBox("Time");
		chckbxTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxTime.isSelected())
				time = true;
				else time = false;
				filters();
			}
		});
		chckbxTime.setBackground(Color.LIGHT_GRAY);
		
		JCheckBox chckbxLocation = new JCheckBox("Location");
		chckbxLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxLocation.isSelected())
					location = true;
				else location = false;
				filters();
			}
		});
		chckbxLocation.setBackground(Color.LIGHT_GRAY);
		
		JCheckBox chckbxId = new JCheckBox("ID");
		chckbxId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxId.isSelected())
					id = true;
				else id = false;
				filters();
			}
		});
		chckbxId.setBackground(Color.LIGHT_GRAY);
		
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
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(336)
					.addComponent(btnClearAll)
					.addContainerGap(335, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(33)
							.addComponent(chckbxTime, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(chckbxLocation)
							.addGap(75)
							.addComponent(chckbxId)
							.addGap(361))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnLoadFile)
								.addComponent(btnSaveCombinedCsv))
							.addPreferredGap(ComponentPlacement.RELATED, 327, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnSaveKmlFile)
								.addComponent(btnLoadFolder))
							.addContainerGap(42, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(42, Short.MAX_VALUE)
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
						.addComponent(chckbxTime, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxLocation)
						.addComponent(chckbxId))
					.addGap(613))
		);
		frame.getContentPane().setLayout(groupLayout);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{chckbxTime, btnClearAll, btnSaveCombinedCsv, btnSaveKmlFile, btnLoadFile, btnLoadFolder, chckbxLocation, chckbxId}));
	}
}
