package gui;

import java.awt.Dimension;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;

import textextr.DataBase;
import textextr.URLDownloaderRunnable;
import net.miginfocom.swing.MigLayout;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import java.awt.Cursor;

public class MainBox {

	private JFrame frmTextextr;
	JButton btnUst;

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
		frmTextextr.setBounds(100, 100, 450, 300);
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
		
		JPanel przetwPanel = new JPanel();
		tabbedPane.addTab("PRZETW.", null, przetwPanel, null);
		
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
}
