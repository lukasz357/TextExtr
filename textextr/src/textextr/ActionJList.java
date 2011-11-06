package textextr;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

public class ActionJList extends JList {
 /**
	 * Obsługa podwójnego klinięcia lub zaznacz + enter w JLiscie
	 * Autor: Réal Gagnon
	 */
	private static final long serialVersionUID = 1L;

 ActionListener al;

 public ActionJList(){
  super();

 addMouseListener(new MouseAdapter() {
  public void mouseClicked(MouseEvent me) {
   if (al == null) return;
   Object ob[] = getSelectedValues();
   if (ob.length > 1) return;
   if (me.getClickCount() == 2) {
     System.out.println("Sending ACTION_PERFORMED to ActionListener");
     al.actionPerformed(new ActionEvent(this,
        ActionEvent.ACTION_PERFORMED,
        ob[0].toString()));
     me.consume();
     }
   }
  });

  addKeyListener(new KeyAdapter() {
   public void keyReleased(KeyEvent ke) {
    if (al == null) return;
    Object ob[] = getSelectedValues();
    if (ob.length > 1) return;
      if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
        System.out.println("Sending ACTION_PERFORMED to ActionListener");
        al.actionPerformed(new ActionEvent(this,
        ActionEvent.ACTION_PERFORMED,
        ob[0].toString()));
        ke.consume();
        } 
    }
   });
   this.setSelectedIndex(0); 
  }

  public void addActionListener(ActionListener al){
   this.al = al;
   }
}