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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JProgressBar;
import java.awt.GridBagLayout;

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
		frmTextextr.setMinimumSize(new Dimension(500, 200));
		frmTextextr.setResizable(false);
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
		GroupLayout groupLayout = new GroupLayout(frmTextextr.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(statusPanel, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
							.addComponent(btnUst, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(statusPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
		
		JButton btnAktualizuj = new JButton("Aktualizuj");
		btnAktualizuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				progressBar.setIndeterminate(true);
				 String str = "<html>" + "<font color=\"#008000\">" + "<b>" + 
						 "Trwa aktualizacja bazy ogłoszeń..." + "</b>" + "</font>" + "</html>";
				lblInfo.setText(str);
				try {
					Class.forName("org.sqlite.JDBC");

					DataBase db = new DataBase();
					ArrayList<String> urls = db.getUrls();
					Runnable r = new URLDownloaderRunnable("http://www.nauka.gov.pl/app/wyszukiwarka,24.html", "filed_0=&filed_1=&filed_2=&filed_3=&filed_4=", urls, db, progressBar, lblInfo);
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
		frmTextextr.getContentPane().setLayout(groupLayout);
		
	}
}
