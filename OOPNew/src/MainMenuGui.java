import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import java.awt.Dimension;
import java.awt.Button;
import javax.swing.ImageIcon;

public class MainMenuGui {

	Hashtable<String, Networks> strongPoints;
	watcher p;
	boolean location = false;
	boolean Date = false;
	boolean id = false;
	boolean and = true;
	boolean or = false;
	String filter1 = "";
	String filter2 = "";
	boolean notFilter1 = false;
	boolean notFilter2 = false;
	Filter filter11;
	Filter filter22;
	Filter orAndfilter;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		strongPoints = new Hashtable<>();
		history a = new history(strongPoints);
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setBounds(100, 100, 710, 615);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTextField txtpnStartDate = new JTextField();
		txtpnStartDate.setText("Start Date");
		txtpnStartDate.setVisible(Date);

		JTextField txtpnEndDate = new JTextField();
		txtpnEndDate.setText("End Date");
		txtpnEndDate.setVisible(Date);

		JTextField txtpnLat = new JTextField();
		txtpnLat.setText("MinLat");
		txtpnLat.setVisible(location);

		JTextField txtpnMaxlat = new JTextField();
		txtpnMaxlat.setText("MaxLat");
		txtpnMaxlat.setVisible(location);

		JTextField txtpnMaxlon = new JTextField();
		txtpnMaxlon.setText("MaxLon");
		txtpnMaxlon.setVisible(location);

		JTextField txtpnMaxalt = new JTextField();
		txtpnMaxalt.setText("MaxAlt");
		txtpnMaxalt.setVisible(location);

		JTextField txtpnAlt = new JTextField();
		txtpnAlt.setText("MinAlt");
		txtpnAlt.setVisible(location);

		JTextField txtpnLon = new JTextField();
		txtpnLon.setText("MinLon");
		txtpnLon.setVisible(location);

		JTextField txtpnIdFilter = new JTextField();
		txtpnIdFilter.setText("ID name");
		txtpnIdFilter.setVisible(id);

		JTextArea txtrInfo = new JTextArea();
		txtrInfo.setEditable(false);
		txtrInfo.setText("Empty filter");

		JTextArea txtrEmptyDatabase = new JTextArea();
		txtrEmptyDatabase.setText("Empty Database");
		txtrEmptyDatabase.setEditable(false);

		JTextArea txtrMacsAddresses = new JTextArea();
		txtrMacsAddresses.setText("0 MAC's Addresses");
		txtrMacsAddresses.setEditable(false);

		JRadioButton rdbtnAnd = new JRadioButton("AND");
		rdbtnAnd.setSelected(true);

		JRadioButton rdbtnOr = new JRadioButton("OR");

		JComboBox comboBoxFilter1 = new JComboBox();
		comboBoxFilter1.setModel(new DefaultComboBoxModel(new String[] {"", "Date", "Location", "ID"}));
		comboBoxFilter1.setToolTipText("filter1");
		comboBoxFilter1.setMaximumRowCount(4);

		JComboBox comboBoxFilter2 = new JComboBox();
		comboBoxFilter2.setModel(new DefaultComboBoxModel(new String[] {"", "Date", "Location", "ID"}));
		comboBoxFilter2.setToolTipText("filter2");
		comboBoxFilter2.setMaximumRowCount(4);

		JCheckBox chckbxNot1Filter = new JCheckBox("Not");
		chckbxNot1Filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxNot1Filter.isSelected())
					notFilter1 = true;
				else notFilter1 = false;
			}
		});
		chckbxNot1Filter.setBackground(Color.LIGHT_GRAY);

		JCheckBox chckbxNot2Filter = new JCheckBox("Not");
		chckbxNot2Filter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxNot2Filter.isSelected())
					notFilter2 = true;
				else notFilter2 = false;
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
				FileNameExtensionFilter filter = new FileNameExtensionFilter("csv file", "csv");
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getSelectedFile() : " 
							+  chooser.getSelectedFile());
					a.updateHistoryCSV(ImportCSV.mergeHash(strongPoints, ImportCombinedCSV.filterCSV(chooser.getSelectedFile().getAbsolutePath(), "")));

					txtrEmptyDatabase.setText("Database size: " + a.getPoints().size());
					txtrMacsAddresses.setText(a.diffMAC() + " MAC's Addresses");
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

					p = new watcher(chooser.getSelectedFile().getAbsolutePath()+"\\",a);
					p.start();

					Runnable updater = new Runnable(){
						public void run(){
							while(watcher.holdsLock(a)) {
							}
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							txtrEmptyDatabase.setText("Database size: " + a.getPoints().size());
							txtrMacsAddresses.setText(a.diffMAC() + " MAC's Addresses");
						}
					};
					Thread t2 = new Thread(updater);
					t2.start();

					System.out.println("getSelectedFolder : " 
							+  chooser.getSelectedFile().getAbsolutePath());
					btnLoadFolder.setEnabled(false);					
				}
				else {
					System.out.println("No Selection ");
				}
				txtrMacsAddresses.setText(a.diffMAC() + " MAC's Addresses");
				txtrEmptyDatabase.setText("Database size: " + a.getPoints().size());

			}
		});

		JButton btnClearAll = new JButton("Clear all");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.clear();
				btnLoadFolder.setEnabled(true);
				txtrInfo.setText("Empty filter");
				txtrEmptyDatabase.setText("Empty Database");
				txtrMacsAddresses.setText("0 MAC's Addresses");
			}
		});

		JButton btnSaveCombinedCsv = new JButton("Save Combined CSV File");
		btnSaveCombinedCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				int retrival = chooser.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					System.out.println("Saving " + a.getPoints().size() + " Points..");
					Runnable myRunnable = new Runnable(){
						public void run(){
							ExportCSV.writeCsvFile(a.getPoints(), chooser.getSelectedFile()+".csv",1);
							System.out.println("Finished writing CSV");
						}
					};
					Thread t1 = new Thread(myRunnable);
					t1.start();
				}

			}
		});

		JButton btnSaveKmlFile = new JButton("Save KML File");
		btnSaveKmlFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				int retrival = chooser.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					System.out.println("Saving " + a.getPoints().size() + " Points..");
					Runnable myRunnable = new Runnable(){
						public void run(){
							ExportKML.writeKMLFile(a.getPoints(), chooser.getSelectedFile()+".kml",2);
							System.out.println("Finished writing KML");
						}
					};
					Thread t1 = new Thread(myRunnable);
					t1.start();

				}
			}
		});


		comboBoxFilter1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filter1 = (String)comboBoxFilter1.getSelectedItem();
				txtpnStartDate.setVisible(filter1.equals("Date")||filter2.equals("Date"));
				txtpnEndDate.setVisible(filter1.equals("Date")||filter2.equals("Date"));
				txtpnLat.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnLon.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnAlt.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnIdFilter.setVisible(filter1.equals("ID")||filter2.equals("ID"));
				txtpnMaxlat.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnMaxlon.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnMaxalt.setVisible(filter1.equals("Location")||filter2.equals("Location"));
			}
		});

		comboBoxFilter2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				filter2 = (String)comboBoxFilter2.getSelectedItem();
				txtpnStartDate.setVisible(filter1.equals("Date")||filter2.equals("Date"));
				txtpnEndDate.setVisible(filter1.equals("Date")||filter2.equals("Date"));
				txtpnLat.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnLon.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnAlt.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnIdFilter.setVisible(filter1.equals("ID")||filter2.equals("ID"));
				txtpnMaxlat.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnMaxlon.setVisible(filter1.equals("Location")||filter2.equals("Location"));
				txtpnMaxalt.setVisible(filter1.equals("Location")||filter2.equals("Location"));

			}
		});

		rdbtnAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(and) {
					rdbtnAnd.setSelected(and);
				}
				else if(rdbtnAnd.isSelected()) {
					and = true;
					or = false;
					rdbtnOr.setSelected(false);
				}
			}
		});

		rdbtnOr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(or) {
					rdbtnOr.setSelected(or);
				}
				else if(rdbtnOr.isSelected()) {
					or = true;
					and = false;
					rdbtnAnd.setSelected(false);
				}
			}
		});
		rdbtnOr.setBackground(Color.LIGHT_GRAY);
		rdbtnAnd.setBackground(Color.LIGHT_GRAY);

		JButton btnUpdateFilter = new JButton("Update Filter");
		btnUpdateFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String lastFilter1 = "";
				String lastFilter2 = "";
				if(((String)comboBoxFilter1.getSelectedItem()).equals("Date"))
					lastFilter1 = (String)comboBoxFilter1.getSelectedItem() + "="
							+ txtpnStartDate.getText() + "," + txtpnEndDate.getText();
				if(((String)comboBoxFilter1.getSelectedItem()).equals("Location"))
					lastFilter1 = (String)comboBoxFilter1.getSelectedItem() + "=" +
							txtpnLat.getText() + "," + txtpnMaxlat.getText() +
							"," + txtpnLon.getText() + "," + txtpnMaxlon.getText()
							+ "," + txtpnAlt.getText() + "," + txtpnMaxalt.getText();
				if(((String)comboBoxFilter1.getSelectedItem()).equals("ID"))
					lastFilter1 = (String)comboBoxFilter1.getSelectedItem() + "=" + txtpnIdFilter.getText();

				if(((String)comboBoxFilter2.getSelectedItem()).equals("Date"))
					lastFilter2 = (String)comboBoxFilter2.getSelectedItem() + "="
							+ txtpnStartDate.getText() + "," + txtpnEndDate.getText();
				if(((String)comboBoxFilter2.getSelectedItem()).equals("Location"))
					lastFilter2 = (String)comboBoxFilter2.getSelectedItem() + "=" +
							txtpnLat.getText() + "," + txtpnMaxlat.getText() +
							"," + txtpnLon.getText() + "," + txtpnMaxlon.getText()
							+ "," + txtpnAlt.getText() + "," + txtpnMaxalt.getText();
				if(((String)comboBoxFilter2.getSelectedItem()).equals("ID"))
					lastFilter2 = (String)comboBoxFilter2.getSelectedItem() + "=" + txtpnIdFilter.getText();

				filter11 = ImportCombinedCSV.filter(lastFilter1,notFilter1);
				filter22 = ImportCombinedCSV.filter(lastFilter2,notFilter2);
				System.out.println(lastFilter1);
				System.out.println(lastFilter2);
				if(or) {
					orAndfilter = ImportCombinedCSV.filter(filter11,filter22,"OR");
					a.filter(orAndfilter);
				}
				else if(and) {
					orAndfilter = ImportCombinedCSV.filter(filter11,filter22,"AND");
					a.filter(orAndfilter);
				}
				txtrInfo.setText(orAndfilter.toString());
				txtrEmptyDatabase.setText("Database size: " + a.getPoints().size());
				txtrMacsAddresses.setText(a.diffMAC() + " MAC's Addresses");
			}
		});


		JButton btnLoadFilter = new JButton("Load Filter");
		btnLoadFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle(null);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Filter file", "ser");
				chooser.setFileFilter(filter);
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					System.out.println("getSelectedFile() : " 
							+  chooser.getSelectedFile());
					try {
						readFilter(chooser.getSelectedFile().getAbsolutePath());
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(a.getPoints().size());
				}
				else {
					System.out.println("No Selection ");
				}
				System.out.println("Loaded filter: " + orAndfilter.toString());

				a.filter(orAndfilter);
				txtrInfo.setText(orAndfilter.toString());
				txtrEmptyDatabase.setText("Database size: " + a.getPoints().size());
				txtrMacsAddresses.setText(a.diffMAC() + " MAC's Addresses");
			}
		});

		JButton btnSaveFilter = new JButton("Save Filter");
		btnSaveFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				int retrival = chooser.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					try {
						writeFilter(chooser.getSelectedFile()+".ser");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println("Saved filter: " + orAndfilter.toString());
			}
		});

		JButton btnClearFilter = new JButton("Clear Filter");
		btnClearFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.filteredStrongPoints.clear();
				txtrInfo.setText("Empty Filter");
				txtrEmptyDatabase.setText("Database size: " + a.getPoints().size());
				txtrMacsAddresses.setText(a.diffMAC() + " MAC's Addresses");
			}
		});

		frame.getContentPane().setLayout(new MigLayout("", "[75.00px][6px][75px][16px][75px][6px][9px][6px][25px][6px][41px][82px,grow][12px][3px][6px][126px][107px]", "[28px][29px][34px][24px][24px][28px][28px][][][][][23.00,grow][][][][grow]"));
		frame.getContentPane().add(chckbxNot1Filter, "cell 0 1");
		frame.getContentPane().add(comboBoxFilter1, "cell 2 1 3 1");
		frame.getContentPane().add(btnUpdateFilter, "cell 0 2 3 1");
		frame.getContentPane().add(rdbtnOr, "cell 6 1 3 1");
		frame.getContentPane().add(chckbxNot2Filter, "cell 10 1");
		frame.getContentPane().add(comboBoxFilter2, "cell 11 1 3 1");
		frame.getContentPane().add(rdbtnAnd, "cell 6 2 5 1");
		frame.getContentPane().add(btnSaveFilter, "cell 15 2");
		frame.getContentPane().add(btnLoadFilter, "cell 15 1");
		frame.getContentPane().add(btnLoadFile, "cell 0 0 7 1");
		frame.getContentPane().add(btnSaveCombinedCsv, "cell 8 0 6 1");
		frame.getContentPane().add(btnLoadFolder, "cell 15 0");
		frame.getContentPane().add(btnClearAll, "cell 16 1");
		frame.getContentPane().add(btnSaveKmlFile, "cell 16 0");
		frame.getContentPane().add(btnClearFilter, "cell 16 2");
		frame.getContentPane().add(txtpnIdFilter, "cell 4 3 7 1");
		frame.getContentPane().add(txtpnEndDate, "cell 0 4 3 1");
		frame.getContentPane().add(txtpnStartDate, "cell 0 3 3 1");
		frame.getContentPane().add(txtpnMaxalt, "cell 2 5 2 1");
		frame.getContentPane().add(txtpnMaxlon, "cell 4 5 4 1");
		
		JButton btnFindApLocationalgo = new JButton("Find AP Location(Algo 1)");
		btnFindApLocationalgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = (String)JOptionPane.showInputDialog(
	                    frame,
	                    "Enter MAC Address to find AP location:\n",
	                    "Algo 1",
	                    JOptionPane.PLAIN_MESSAGE,
	                    null, null,
	                    "a4:2b:b0:ad:2d:34");
				System.out.println(s);
				System.out.println(Arrays.toString(a.findAPloc(s)));
			}
		});
		
		btnFindApLocationalgo.setIcon(new ImageIcon(MainMenuGui.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		frame.getContentPane().add(btnFindApLocationalgo, "cell 15 5 1 2,grow");
		frame.getContentPane().add(txtpnAlt, "cell 2 6 2 1");
		frame.getContentPane().add(txtpnLat, "cell 0 6 2 1");
		frame.getContentPane().add(txtpnMaxlat, "cell 0 5 2 1");
		frame.getContentPane().add(txtpnLon, "cell 4 6 4 1");
		frame.getContentPane().add(txtrInfo, "cell 0 8 17 1");
		frame.getContentPane().add(txtrEmptyDatabase, "cell 0 9 17 1");
		frame.getContentPane().add(txtrMacsAddresses, "cell 0 10 17 1");
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{chckbxNot1Filter, btnClearAll, btnSaveCombinedCsv, btnSaveKmlFile, btnLoadFile, btnLoadFolder, chckbxNot2Filter}));

	}

	protected void writeFilter(String fileName) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(orAndfilter);
		oos.close();
	}

	protected void readFilter(String fileName) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		FileInputStream fis = new FileInputStream(fileName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		orAndfilter = (Filter) ois.readObject();
		ois.close();
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
	}
}
