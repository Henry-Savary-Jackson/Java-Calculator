
package util;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class CalcTextField extends JTextField implements KeyListener{
    public JFrame frame;
    public static final String validKeys = "0123456789(),+-=/*";
    public int caretPos = 0;
    
    public CalcTextField(String init, JFrame f){
	super(init);
	frame= f;
	addKeyListener(this);
    }
    
    public void update(String s){
	
	int w = getFontMetrics(getFont()).stringWidth(getText() + s);
	if ( w >= getMinimumSize().width){

	    Dimension dim = new Dimension(w,getHeight());
	    setPreferredSize(dim);
	    frame.pack();
	}
    }
    
     public void InsertString (String insert){
	StringBuilder sb = new StringBuilder(getText());
	sb.insert(getCaretPosition(), insert);
	setText(sb.toString() + insert);
	update("");
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
	char c = ke.getKeyChar();
	boolean valid = (validKeys.indexOf(c) != -1) || (c == KeyEvent.VK_BACK_SPACE);
	if (!valid){
	    ke.consume(); 
	}else{
	    update(String.valueOf(c));
	}
    }

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {}
}
