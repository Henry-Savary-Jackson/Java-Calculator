
package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.JButton;

public class CalcButton  extends JButton implements ActionListener{

    public Consumer<CalcTextField> onClick;
    public CalcTextField output;
    
    public CalcButton(String n, Consumer <CalcTextField> consumer , CalcTextField text){
	super(n);
	onClick = consumer;
	output = text;
	addActionListener(this);
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
	if (ae.getSource() == this){
	    onClick.accept(output);
	}
    }
    
}
