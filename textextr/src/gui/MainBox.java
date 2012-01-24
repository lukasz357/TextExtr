package gui;

//import HyperlinkCellRenderer;
//import OscarCellRenderers;

import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JLabel;

import textextr.Advertisement;
import textextr.DataBase;
import textextr.HTMLView;
import textextr.URLDownloaderRunnable;
import net.miginfocom.swing.MigLayout;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.JTable;
import javax.swing.JTextField;

//import TableDemo.IMDBLinkAction;

import com.toedter.calendar.JDateChooser;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainBox {
	private static Log log = LogFactory.getLog(MainBox.class);
	private JFrame frmTextextr;
	JButton btnUst;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTable table;

	public MainBox getMainBox(){
		return this;
	}
	public JButton getBtnUst(){
		return btnUst;
	}

	public JFrame getFrmTextextr() {
		return frmTextextr;
	}

	public void setFrmTextextr(JFrame frmTextextr) {
		this.frmTextextr = frmTextextr;
	}

	/**
	 * Create the application.
	 */
	public MainBox() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTextextr = new JFrame();
		frmTextextr.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		frmTextextr.setMinimumSize(new Dimension(500, 200));
//		frmTextextr.setResizable(false);
		frmTextextr.setTitle("TextExtr");
		frmTextextr.setBounds(100, 100, 1101, 632);
		frmTextextr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		btnUst = new JButton("UST");
		btnUst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfigureBox cb = new ConfigureBox(getFrmTextextr(), getMainBox());
				cb.showConfigureBox();
//				btnUst.setEnabled(true);
			}
		});
		
		JPanel statusPanel = new JPanel();
		statusPanel.setMaximumSize(new Dimension(32767, 100));
		GroupLayout groupLayout = new GroupLayout(frmTextextr.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(statusPanel, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnUst, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(statusPanel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnUst, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		final JLabel lblInfo = new JLabel("");
		
		final JProgressBar progressBar = new JProgressBar();
		GroupLayout gl_statusPanel = new GroupLayout(statusPanel);
		gl_statusPanel.setHorizontalGroup(
			gl_statusPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_statusPanel.createSequentialGroup()
					.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))
		);
		gl_statusPanel.setVerticalGroup(
			gl_statusPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
				.addComponent(lblInfo, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
		);
		statusPanel.setLayout(gl_statusPanel);
		

		
		JPanel startPanel = new JPanel();
		tabbedPane.addTab("START", null, startPanel, null);
		startPanel.setLayout(new MigLayout("", "[][]", "[][][][]"));
		
		JLabel lblLiczbaOfertPracy = new JLabel("Liczba ofert pracy w bazie: ");
		startPanel.add(lblLiczbaOfertPracy, "cell 0 1");
		
		JLabel lblAktualizujBazOgloszen = new JLabel("Aktualizuj bazę ogłoszeń: ");
		startPanel.add(lblAktualizujBazOgloszen, "cell 0 3,aligny baseline");
		
		final JButton btnAktualizuj = new JButton("Aktualizuj");
		btnAktualizuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				progressBar.setIndeterminate(true);
				btnAktualizuj.setEnabled(false);
				 String str = "<html>" + "<font color=\"#008000\">" + "<b>" + 
						 "Trwa aktualizacja bazy ogłoszeń..." + "</b>" + "</font>" + "</html>";
				lblInfo.setText(str);
				try {
					Class.forName("org.sqlite.JDBC");

					DataBase db = new DataBase();
					Runnable r = new URLDownloaderRunnable("http://www.nauka.gov.pl/app/wyszukiwarka,24.html", "filed_0=&filed_1=&filed_2=&filed_3=&filed_4=", db, btnAktualizuj, progressBar, lblInfo);
					Thread t = new Thread(r);
					t.start();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		startPanel.add(btnAktualizuj, "cell 1 3");
		
		JPanel szukajPanel= new JPanel();
		tabbedPane.addTab("SZUKAJ", null, szukajPanel, null);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		GroupLayout gl_szukajPanel = new GroupLayout(szukajPanel);
		gl_szukajPanel.setHorizontalGroup(
			gl_szukajPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
		);
		gl_szukajPanel.setVerticalGroup(
			gl_szukajPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_szukajPanel.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
		);
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblStanowisko = new JLabel("Stanowisko");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		
		JLabel lblMiasto = new JLabel("Miasto");
		
		JLabel lblInstytucja = new JLabel("Instytucja");
		
		JLabel lblDyscNaukowa = new JLabel("Dysc. naukowa");
		
		JLabel lblSowoKluczowe = new JLabel("Słowo kluczowe");
		
		final JDateChooser dtChDataOgloszenia = new JDateChooser();
		
		JLabel lblDataOgoszenia = new JLabel("Data ogłoszenia");
		
		final JDateChooser dtChTerminSklOfert = new JDateChooser();
		
		JLabel lblTerminSklOfert = new JLabel("Termin skl. ofert");
		
		JButton btnSzukaj = new JButton("Szukaj");
		btnSzukaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Class.forName("org.sqlite.JDBC");

					DataBase datb = new DataBase();
					String stanowisko, miasto, instytucja, dyscyplina, slowo;
					Date dataOgl, termSklOf;
					Advertisement ad = new Advertisement();
					if((stanowisko = textField.getText() )!= null) {
						ad.setStanowisko(stanowisko);
					}
					if((miasto = textField_1.getText()) != null)
						ad.setMiasto(miasto);
					if((instytucja = textField_2.getText()) != null)
						ad.setInstytucja(instytucja);
					if((dyscyplina = textField_3.getText()) != null)
						ad.setDyscyplinaNaukowa(dyscyplina);
					if((slowo = textField_4.getText()) != null)
						ad.getSlowaklucz().add(slowo);
					if((dataOgl = dtChDataOgloszenia.getDate()) != null)
						ad.setDataOgloszenia(dataOgl);
					if((termSklOf = dtChTerminSklOfert.getDate())!= null)
						ad.setTerminSklOfert(termSklOf);
					System.out.println("aaaa");
					log.info("Klik Szukaj");
					
					ArrayList<Advertisement> ads = new ArrayList<Advertisement>(datb.getAdvertisements(ad).values());
					
					HTMLView view = new HTMLView(ads);
					
					view.generatePage();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStanowisko))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMiasto))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblInstytucja))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDyscNaukowa))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSowoKluczowe))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDataOgoszenia)
						.addComponent(dtChDataOgloszenia, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTerminSklOfert)
						.addComponent(dtChTerminSklOfert, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
					.addComponent(btnSzukaj)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblStanowisko)
								.addComponent(lblMiasto)
								.addComponent(lblInstytucja)
								.addComponent(lblDyscNaukowa)
								.addComponent(lblSowoKluczowe))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(19))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnSzukaj))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblDataOgoszenia)
								.addComponent(lblTerminSklOfert))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(dtChDataOgloszenia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(dtChTerminSklOfert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		panel_1.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Lp.", "Stanowisko", "Miasto", "Instytucja", "Dysc. naukowa", "S\u0142owa kluczowe", "Data og\u0142oszenia", "Termin skl. ofert"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
//		table.getColumnModel().getColumn(6).setResizable(false);
		table.setColumnModel(createColumnModel());
		table.setAutoCreateRowSorter(true);
		table.setRowHeight(26);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setIntercellSpacing(new Dimension(0, 0));
		panel_1.add(table, "cell 0 0,grow");
		szukajPanel.setLayout(gl_szukajPanel);
		
		JPanel infoPanel = new JPanel();
		tabbedPane.addTab("INFO", null, infoPanel, null);
		infoPanel.setLayout(new MigLayout("", "[][][][]", "[][][][][][]"));
		
		JLabel lblNazwaProgramu = new JLabel("Nazwa programu:");
		lblNazwaProgramu.setHorizontalAlignment(SwingConstants.RIGHT);
		infoPanel.add(lblNazwaProgramu, "cell 1 1");
		
		JLabel lblTextrextr = new JLabel("TextrExtr");
		infoPanel.add(lblTextrextr, "cell 3 1");
		
		JLabel lblWersja = new JLabel("Wersja:");
		lblWersja.setHorizontalAlignment(SwingConstants.RIGHT);
		infoPanel.add(lblWersja, "cell 1 2");
		
		JLabel label = new JLabel("1.0");
		infoPanel.add(label, "cell 3 2");
		
		JLabel lblAutor = new JLabel("Autor:");
		lblAutor.setHorizontalAlignment(SwingConstants.RIGHT);
		infoPanel.add(lblAutor, "cell 1 3");
		
		JLabel lblukaszKrok = new JLabel("Łukasz Krok");
		infoPanel.add(lblukaszKrok, "cell 3 3");
		frmTextextr.getContentPane().setLayout(groupLayout);
		
	}
    protected TableColumnModel createColumnModel() {
        DefaultTableColumnModel columnModel = new DefaultTableColumnModel();

//        TableCellRenderer cellRenderer = new OscarCellRenderers.RowRenderer(getTableRowColors());

        TableColumn column = new TableColumn();
        column.setModelIndex(1);
        column.setHeaderValue("Stanowisko");
        column.setPreferredWidth(26);
//        column.setCellRenderer(new OscarCellRenderers.YearRenderer(getTableRowColors()));
        columnModel.addColumn(column);

        column = new TableColumn();
        column.setModelIndex(2);
        column.setHeaderValue("Miasto");
        column.setPreferredWidth(100);
//        column.setCellRenderer(cellRenderer);
        columnModel.addColumn(column);

        column = new TableColumn();
        column.setModelIndex(3);
        column.setHeaderValue("Instytucja");
        column.setPreferredWidth(180);
//        HyperlinkCellRenderer hyperlinkRenderer =
//                new OscarCellRenderers.MovieRenderer(new IMDBLinkAction(),
//                        true, getTableRowColors());
//        hyperlinkRenderer.setRowColors(getTableRowColors());
//        column.setCellRenderer(hyperlinkRenderer);
        columnModel.addColumn(column);

        column = new TableColumn();
        column.setModelIndex(4);
        column.setHeaderValue("Dyscyplina naukowa");
        column.setPreferredWidth(120);
//        column.setCellRenderer(new OscarCellRenderers.NomineeRenderer(getTableRowColors()));
        columnModel.addColumn(column);

        return columnModel;
    }
}
