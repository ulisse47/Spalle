package progetto.presentation.util;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;



public class MyTextField extends JTextField {


  public MyTextField() {
    this.addFocusListener( new MyListener() );
  }
}
class MyListener
    implements FocusListener /*, ItemListener */ {

  public void focusLost(FocusEvent e){
  }

 public void focusGained(FocusEvent e) {

    Class cl = e.getSource().getClass();
    JTextField source = (JTextField)e.getSource();

    source.selectAll();
    source.requestFocus();
  }
}


