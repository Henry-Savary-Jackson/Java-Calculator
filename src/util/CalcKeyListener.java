
package util;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class CalcKeyListener implements KeyListener{
    
    public JTextField txtInput;
    public JFrame frame;
    public static final String validKeys = "0123456789(),+-=/*";
    
    public CalcKeyListener(JTextField txt, JFrame f){
	txtInput = txt;
	frame= f;
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
	char c = ke.getKeyChar();
	boolean valid = (validKeys.indexOf(c) != -1) || (c == KeyEvent.VK_BACK_SPACE);
	if (!valid){
	    ke.consume(); 
	}else {
	    int w = txtInput.getFontMetrics(txtInput.getFont()).stringWidth(txtInput.getText() + c);
	    if ( w >= txtInput.getMinimumSize().width){

		Dimension dim = new Dimension(w,txtInput.getHeight());
		txtInput.setPreferredSize(dim);
		frame.pack();
	    }
	}
    }

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {}
}
