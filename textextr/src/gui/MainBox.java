package gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MainBox {

	private JFrame frmTextextr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainBox window = new MainBox();
					window.frmTextextr.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		
		JPanel panel = new JPanel();
		
		JButton btnUst = new JButton("UST");
		GroupLayout groupLayout = new GroupLayout(frmTextextr.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnUst, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnUst, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JPanel startPanel = new JPanel();
		tabbedPane.addTab("START", null, startPanel, null);
		
		JPanel szukajPanel= new JPanel();
		tabbedPane.addTab("SZUKAJ", null, szukajPanel, null);
		
		JPanel przetwPanel = new JPanel();
		tabbedPane.addTab("PRZETW.", null, przetwPanel, null);
		
		JPanel infoPanel = new JPanel();
		tabbedPane.addTab("INFO", null, infoPanel, null);
		frmTextextr.getContentPane().setLayout(groupLayout);
	}
}
