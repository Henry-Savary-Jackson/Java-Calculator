package util;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {
    
    static String[] functions = new String[] {"acos(", "asin(", "atan(","cos(", "sin(", "tan(", "abs("};
    static String basicOperators = "^" + "\u221A" + "*/+-" ;
    
    static String findMultiplication(String input){
	String inp = input;
	for (int i = 0; i < 10; i++){
	    String num = String.valueOf(i);
	    
	    String test = ")" + num;
	    String test2 =num+ "(";
	    String replace = ")*" + num;
	    String replace2 = num + "*("; 
	    inp = inp.replace(test, replace);
	    inp = inp.replace(test2, replace2);
	}
	return inp;
    }
    
    public static double strToEqu(String input){
	
	String inp = findMultiplication(input);
	ArrayList<String> brackets = findBracketOperator(inp, "(", ")");
	System.out.println( "parentheses:"+ Arrays.toString(brackets.toArray()));
	double answer = 0;
	
	if (brackets == null){
	    return Double.NaN;
	}
	if (!brackets.isEmpty()){
	    for (String b : brackets){
		String expr = b.substring(1,b.length());
		double result = strToEqu(expr);
		inp = inp.replaceFirst(expr, String.format("%.7f", result));
	    }
	}
	for (String f : functions){
	    brackets = findBracketOperator(inp, f, ")");
	    System.out.println( "functions:"+ Arrays.toString(brackets.toArray()));
	    if (brackets == null){
		return Double.NaN;
	    }
	    if (!brackets.isEmpty()){
		for (String s : brackets){
		    String funcIn = s.substring(s.indexOf("(")+1, s.indexOf(")"));
		    String result = funcValue(f,funcIn);
		    if (result == null){
			return Double.NaN;
		    }
		    inp = inp.replaceFirst(s, "("+ result +")");
		}
	    }
	}
	for (char op : basicOperators.toCharArray()){
	    ArrayList<String> expressions = findBasicOperator(inp, op);
	    //see what is going on in the expressions arraylist
	    //this doesnt seem to be following order of operation
	    if (expressions != null && !expressions.isEmpty()){
		for (String e : expressions){
		    //problem here 
		    String arg1 = e.substring(0, e.indexOf(op)).replace("(","").replace(")", "");
		    if (arg1.isEmpty()){
			arg1 = (op == '+' || op =='-')? "0": "";
		    }
		    String arg2 = e.substring(e.indexOf(op)+1).replace("(","").replace(")", "");
		    try{
			String result = operatorValue(op, Double.parseDouble(arg1), Double.parseDouble(arg2));
			inp = inp.replaceFirst(e, result);
		    } catch( NumberFormatException nfe){
			return Double.NaN;
		    }
		}
	    }
	}
	
	answer = Double.parseDouble(inp);
	
	return answer;
    }
    
    static String operatorValue(char op, double arg1, double arg2){
	double result = 0;
	switch (op){
	    case '^':
		result = Math.pow(arg1, arg2);
		break;
	    case '\u221A':
		result = Math.pow(arg2, 1/arg1);
		break;
	    case '*':
		result = arg1 * arg2;
		break;
	    case '/':
	        result = arg1/arg2;
		break;
	    case '+':
		result = arg1 + arg2;
		break;
	    case '-':
		result = arg1 -arg2;
		break;
	}
	return String.format("%.7f", result);
    }
    
    static ArrayList<String> findBasicOperator(String inp,char op){
	int beginPos = 0;
	int endPos = 0;
	int opPos = 0;
	ArrayList<String> output = new ArrayList<>();
	
	while (opPos <= inp.length() -1){
	    opPos = inp.indexOf(op, endPos);
	    if(opPos != -1){
		if (opPos != inp.length()-1){
		    if(inp.toCharArray()[opPos+1] != '('){
			for (int i = opPos+1; i < inp.length(); i++){
			    if (i == inp.length()-1){
				endPos = inp.length();
			    }else if (basicOperators.indexOf(inp.toCharArray()[i]) != -1){
				System.out.println(i);
				endPos = i ;
				break;
			    }
			}
		    } else {
			int testPos = inp.indexOf(")", opPos);
			if (testPos != -1){
			    endPos = testPos;
			} else {
			    return null;
			}
		    }
		} else{
		    return null;
		}
		
		if(opPos != 0){
		    if (inp.toCharArray()[opPos-1] != ')'){
			for (int i = opPos-1; i >= 0; i--){
			    if (basicOperators.indexOf(inp.toCharArray()[i]) != -1){
				beginPos = i+1;
				break;
			    }
			}
		    } else {
			String sBefore = new StringBuilder(inp.substring(0, opPos)).reverse().toString();
			int testPos = sBefore.indexOf("(");
			if (testPos != -1){
			    beginPos = testPos;
			}else {
			    return null;
			}
		    }
		} else {
		    beginPos = opPos;
		}
		System.out.println("begin:"+ String.valueOf(beginPos)+ ", end:" + String.valueOf(endPos));
		System.out.println(inp.substring(beginPos, endPos));
		output.add(inp.substring(beginPos, endPos));
	    } else {
		break;
	    }
	}
	return output;
    }
    
    static ArrayList<String> findBracketOperator(String inp, String beginS, String endS){
	int beginPos = 0;
	int endPos = 0;
	ArrayList<String> output = new ArrayList<>();
	while(beginPos <= inp.length()-1){
	    beginPos = inp.indexOf(beginS, beginPos);
	    if (beginPos != -1){
		endPos = inp.indexOf(endS, beginPos);
		if (endPos != -1){
		    output.add(inp.substring(beginPos, endPos));
		    beginPos = endPos;
		} else {
		    return null;
		}
	    } else {
		break;
	    }
	}
	return output;
    }
    
    static String funcValue(String func, String inp){
	double num = 0;
	try {
	    num = Double.parseDouble(inp);
	} catch (NumberFormatException nfe){
	    nfe.printStackTrace();
	    return null;
	}
	double result = 0;
	switch (func){
	    case "acos":
		result = Math.acos(num);
		break;
	    case "asin":
		result = Math.asin(num);
		break;
	    case "atan":
		result = Math.atan(num);
		break;
	    case "cos":
		result = Math.cos(num);
		break;
	    case "sin":
		result = Math.sin(num);
		break;
	    case "tan":
		result = Math.tan(num);
		break;
	    case "abs":
		result = Math.abs(num);
		break;
	}
	return String.format("%.7f", result);
    }
}
