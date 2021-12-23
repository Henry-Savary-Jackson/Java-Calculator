
package javacalculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import util.CalcButton;
import util.CalcKeyListener;

public class JavaCalculator extends JFrame{
    
    JPanel pnlText = new JPanel();
    JTextField txtInput = new JTextField("0");
    
    JPanel pnlTrig = new JPanel();
    CalcButton[] trigBtns = new CalcButton[]{
	new CalcButton("cos",(txt)-> CalcButton.InsertString(txt, "cos(") ,txtInput),
	new CalcButton("sin",(txt)-> CalcButton.InsertString(txt, "sin(") ,txtInput),
	new CalcButton("tan",(txt)-> CalcButton.InsertString(txt, "tan(") ,txtInput),
	new CalcButton("acos",(txt)-> CalcButton.InsertString(txt, "acos(") ,txtInput),
	new CalcButton("asin",(txt)-> CalcButton.InsertString(txt, "asin(") ,txtInput),
	new CalcButton("atan",(txt)-> CalcButton.InsertString(txt, "atan(") ,txtInput)
    };
    
    JPanel pnlButtons = new JPanel();
    CalcButton[] numBtns = new CalcButton[]{
	new CalcButton("CLEAR",(txt)-> {txt.setText("0"); txt.setCaretPosition(0);} ,txtInput),
	new CalcButton("x\u221Ay",(txt)-> CalcButton.InsertString(txt, "\u221A") ,txtInput) ,//\u221A
	new CalcButton("x^y",(txt)-> CalcButton.InsertString(txt, "^") ,txtInput),
	new CalcButton("/",(txt)-> CalcButton.InsertString(txt, "/") ,txtInput),
	new CalcButton("7",(txt)-> CalcButton.InsertString(txt, "7") ,txtInput),
	new CalcButton("8",(txt)-> CalcButton.InsertString(txt, "8") ,txtInput),
	new CalcButton("9",(txt)-> CalcButton.InsertString(txt, "9") ,txtInput),
	new CalcButton("*",(txt)-> CalcButton.InsertString(txt, "*") ,txtInput),
	new CalcButton("4",(txt)-> CalcButton.InsertString(txt, "4") ,txtInput),
	new CalcButton("5",(txt)-> CalcButton.InsertString(txt, "5") ,txtInput),
	new CalcButton("6",(txt)-> CalcButton.InsertString(txt, "6") ,txtInput),
	new CalcButton("-",(txt)-> CalcButton.InsertString(txt, "-") ,txtInput),
	new CalcButton("3",(txt)-> CalcButton.InsertString(txt, "3") ,txtInput),
	new CalcButton("2",(txt)-> CalcButton.InsertString(txt, "2") ,txtInput),
	new CalcButton("1",(txt)-> CalcButton.InsertString(txt, "1") ,txtInput),
	new CalcButton("+",(txt)-> CalcButton.InsertString(txt, "+") ,txtInput),
	new CalcButton("abs",(txt)-> CalcButton.InsertString(txt, "abs(") ,txtInput),
	new CalcButton("0",(txt)-> CalcButton.InsertString(txt, "0") ,txtInput),
	new CalcButton(".",(txt)-> CalcButton.InsertString(txt, ".") ,txtInput),
	new CalcButton("=",(txt)->{System.out.println("CALCULATE!");} ,txtInput)
    };
    
    JPanel pnl = new JPanel();
    
    public JavaCalculator(){
	super("Java Caclulator");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	Dimension windowDim = Toolkit.getDefaultToolkit().getScreenSize();
	setLocation((int)(windowDim.width * 0.5f) , (int)(windowDim.height *0.5f));
	
	setLayout(new BorderLayout());
	
	pnl.setLayout(new GridLayout(2,1,0,15));
	
	pnlTrig.setLayout(new GridLayout(2,3,10,10));
	pnlTrig.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	Arrays.asList(trigBtns).forEach((calcBtn)-> pnlTrig.add(calcBtn));
	
	pnl.add(pnlTrig);
	
	pnlButtons.setLayout(new GridLayout(5,4,10,10));
	pnlButtons.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	Arrays.asList(numBtns).forEach((calcBtn)-> pnlButtons.add(calcBtn));
	pnl.add(pnlButtons);
	
	add(pnl,BorderLayout.CENTER);
	
	txtInput.setCaretPosition(0);
	add(txtInput, BorderLayout.NORTH);
	txtInput.setMinimumSize(new Dimension(150,25));
	txtInput.setPreferredSize(txtInput.getMinimumSize());
	txtInput.addKeyListener( new CalcKeyListener(txtInput,this));
	
	pack();
	
	setVisible(true);
    }

    public static void main(String[] args) {
	new JavaCalculator();
    }
    
}
