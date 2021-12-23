
package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JTextField;

public class CalcButton  extends JButton implements ActionListener{

    public Consumer<JTextField> onClick;
    public JTextField output;
    
    public CalcButton(String n, Consumer<JTextField> consumer , JTextField text){
	super(n);
	onClick = consumer;
	output = text;
	addActionListener(this);
    }
    
    public static void InsertString(JTextField txt, String insert){
	StringBuilder sb = new StringBuilder(txt.getText());
	sb.insert(txt.getCaretPosition(), insert);
	txt.setText(sb.toString());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
	if (ae.getSource() == this){
	    onClick.accept(output);
	}
    }
    
}
