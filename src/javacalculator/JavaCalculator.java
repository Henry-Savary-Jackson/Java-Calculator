
package javacalculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import util.CalcButton;
import util.CalcTextField;
import util.Parser;

public class JavaCalculator extends JFrame{
    
    CalcTextField txtInput = new CalcTextField(this);
    
    JPanel pnlTrig = new JPanel();
    CalcButton[] trigBtns = new CalcButton[]{
	new CalcButton("cos",(txt)->txt.InsertString("cos("),txtInput),
	new CalcButton("sin",(txt)-> txt.InsertString("sin(") ,txtInput),
	new CalcButton("tan",(txt)-> txt.InsertString("tan(") ,txtInput),
	new CalcButton("acos",(txt)-> txt.InsertString("acos(") ,txtInput),
	new CalcButton("asin",(txt)-> txt.InsertString("asin(") ,txtInput),
	new CalcButton("atan",(txt)-> txt.InsertString("atan(") ,txtInput)
    };
    
    JPanel pnlButtons = new JPanel();
    CalcButton[] numBtns = new CalcButton[]{
	new CalcButton("CLEAR",(txt)-> {txt.setText(""); txt.caretPos = 0;} ,txtInput),
	new CalcButton("x\u221Ay",(txt)-> txt.InsertString("\u221A") ,txtInput) ,//\u221A for root operator
	new CalcButton("x^y",(txt)-> txt.InsertString( "^") ,txtInput),
	new CalcButton("/",(txt)-> txt.InsertString("/") ,txtInput),
	new CalcButton("7",(txt)-> txt.InsertString("7") ,txtInput),
	new CalcButton("8",(txt)-> txt.InsertString("8") ,txtInput),
	new CalcButton("9",(txt)-> txt.InsertString("9") ,txtInput),
	new CalcButton("*",(txt)-> txt.InsertString("*") ,txtInput),
	new CalcButton("4",(txt)-> txt.InsertString("4") ,txtInput),
	new CalcButton("5",(txt)-> txt.InsertString("5") ,txtInput),
	new CalcButton("6",(txt)-> txt.InsertString("6") ,txtInput),
	new CalcButton("-",(txt)-> txt.InsertString("-") ,txtInput),
	new CalcButton("3",(txt)-> txt.InsertString("3") ,txtInput),
	new CalcButton("2",(txt)-> txt.InsertString("2") ,txtInput),
	new CalcButton("1",(txt)-> txt.InsertString("1") ,txtInput),
	new CalcButton("+",(txt)-> txt.InsertString("+") ,txtInput),
	new CalcButton("abs",(txt)-> txt.InsertString("abs(") ,txtInput),
	new CalcButton("0",(txt)-> txt.InsertString("0") ,txtInput),
	new CalcButton(".",(txt)-> txt.InsertString(".") ,txtInput),
	new CalcButton("=",(txt)-> System.out.println("CALCULATE!") ,txtInput)
    };
    
    JPanel pnl = new JPanel();
    
    public JavaCalculator(){
	super("Java Caclulator");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	setLookAndFeel();
	
	setResizable(false);
	
	Dimension windowDim = Toolkit.getDefaultToolkit().getScreenSize();
	setLocation((int)(windowDim.width * 0.35f) , (int)(windowDim.height *0.35f));
	
	setLayout(new BorderLayout());
	
	pnl.setLayout(new BorderLayout());
	
	pnlTrig.setLayout(new GridLayout(2,3,10,5));
	pnlTrig.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	Arrays.asList(trigBtns).forEach((calcBtn)-> pnlTrig.add(calcBtn));
	
	pnl.add(pnlTrig, BorderLayout.NORTH);
	
	pnlButtons.setLayout(new GridLayout(5,4,10,5));
	pnlButtons.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	Arrays.asList(numBtns).forEach((calcBtn)-> pnlButtons.add(calcBtn));
	pnl.add(pnlButtons,BorderLayout.SOUTH);
	
	add(pnl,BorderLayout.CENTER);
	
	txtInput.setCaretPosition(0);
	add(txtInput, BorderLayout.NORTH);
	
	pack();
	setVisible(true);
    }

    public static void main(String[] args) {
	new JavaCalculator();
    }
    
    void setLookAndFeel(){
	try{
	    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
	    
	} catch (ClassNotFoundException|InstantiationException| IllegalAccessException| UnsupportedLookAndFeelException e) {
	    e.printStackTrace();
	}
    }
}
