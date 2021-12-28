package util;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class CalcTextField extends JTextField implements KeyListener , MouseListener{
    public JFrame frame;
    public static final String validKeys = "0123456789(),+-/*";
    public int caretPos = 0;
    
    public CalcTextField(JFrame f){
	super();
	frame= f;
	addKeyListener(this);
	addMouseListener(this);
    }
    
    public void updateWidth(){
	int w = getFontMetrics(getFont()).stringWidth(getText()+ "  ");
	if ( w >= getMinimumSize().width){
	    Dimension dim = new Dimension(w,getHeight());
	    setPreferredSize(dim);
	    frame.pack();
	}
    }
    
     public void InsertString (String insert){
	StringBuilder sb = new StringBuilder(getText());
	sb.insert(caretPos, insert);
	setText(sb.toString());
	caretPos += insert.length();
	updateWidth();
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
	char c = ke.getKeyChar();
	
	boolean valid = (validKeys.indexOf(c) != -1) || (c == KeyEvent.VK_BACK_SPACE);
	if (!valid)
	    ke.consume(); 
    }

    @Override
    public void keyPressed(KeyEvent ke) {
	updateWidth();
    }

    @Override
    public void keyReleased(KeyEvent ke) {
	char c = ke.getKeyChar();
	caretPos = getCaretPosition();
	
	boolean valid = (validKeys.indexOf(c) != -1) || (c == KeyEvent.VK_BACK_SPACE);
	if(valid)
	    updateWidth();
    }

    @Override
    public void mouseClicked(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {caretPos = getCaretPosition();}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
}
