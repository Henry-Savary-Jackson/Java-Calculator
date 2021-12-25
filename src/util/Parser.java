package util;

import java.util.ArrayList;

public class Parser {
    
    static String[] functions = new String[] {"acos", "asin", "atan","cos", "sin", "tan"};
    
    public static String findMultiplication(String input){
	String inp = input;
	for (int i = 0; i < 10; i++){
	    String num = String.valueOf(i);
	    
	    String test = ")" + num;
	    String test2 =num+ "(";
	    String replace = ")*" + num;
	    String replace2 = num + "*("; 
	    inp = inp.replaceAll(test, replace);
	    inp = inp.replaceAll(test2, replace2);
	}
	return inp;
    }
    
    public static float strToEqu(String input){
	String inp = findMultiplication(input);
	ArrayList<String> brackets = findOperator(inp, new String[]{"("}, new String[]{")"});
	if (!brackets.isEmpty()){
	    for (String b : brackets){
		float result = strToEqu(b);
		inp = inp.replaceFirst( b, String.valueOf(result));
	    }
	}
	for (String f : functions){
	    int index = inp.indexOf(f);
	    if (index != -1){
	    }
	}
	return 0f;
    }
    
    public static String findFunction(String input, String func){
	String inp = input;

	return inp;
    }
    
    static String funcResult(String input){
	return "";
    }
    
    public static ArrayList<String> findOperator(String inp, String[] beginS, String[] endS){
	int beginPos = 0;
	int endPos = 0;
	ArrayList<String> output = new ArrayList<>();
	
	while(beginPos <= inp.length()){
	    for (String b : beginS){
		beginPos = inp.indexOf(b, beginPos);
		if (beginPos != -1){
		    for (String e : endS){
			endPos = inp.indexOf(e, beginPos);
			if (endPos != -1){
			    output.add(inp.substring(beginPos+1, endPos));
			    beginPos = endPos;
			}
		    }
		}
	    }
	}
	return output;
    }
    
    
    
    
}
