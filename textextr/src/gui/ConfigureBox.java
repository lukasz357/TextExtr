package gui;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ConfigureBox extends javax.swing.JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmTextextr;
	private JTextField textField;

	public JFrame getFrmTextextr() {
		return frmTextextr;
	}

	public void setFrmTextextr(JFrame frmTextextr) {
		this.frmTextextr = frmTextextr;
		frmTextextr.setMaximumSize(new Dimension(424, 182));
		frmTextextr.setMinimumSize(new Dimension(424, 182));
		frmTextextr.setPreferredSize(new Dimension(424, 182));
		frmTextextr.setSize(new Dimension(424, 182));
		frmTextextr.setAlwaysOnTop(true);
		frmTextextr.setTitle("Ustawienia");
	}
	
	public ConfigureBox(JFrame parent, MainBox mb) {
		super(parent);
		initialize(parent, mb);
		this.setLocationRelativeTo(parent);
	}

	/**
	 * @wbp.parser.entryPoint
	 */
	private void initialize(JFrame parent, final MainBox mb) {
		setFrmTextextr(new JFrame());
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnPrzegldaj = new JButton("Przeglądaj");
		btnPrzegldaj.setFont(new Font("Dialog", Font.BOLD, 10));
		GroupLayout groupLayout = new GroupLayout(frmTextextr.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(textField, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnPrzegldaj, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnPrzegldaj, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(120, Short.MAX_VALUE))
		);
		frmTextextr.getContentPane().setLayout(groupLayout);
		frmTextextr.setResizable(false);
		mb.getBtnUst().setEnabled(false);
		frmTextextr.getContentPane().setLayout(groupLayout);
		/*
		 * SUPER RZECZ - ustala co sie ma dziać przy okazji zamknięcia okna
		 */
		frmTextextr.addWindowListener(new WindowAdapter(){
			  public void windowClosing(WindowEvent we){
			  mb.getBtnUst().setEnabled(true);
			  }
			  });
	}
	public void showConfigureBox() {
		frmTextextr.setVisible(true);
	}
}
