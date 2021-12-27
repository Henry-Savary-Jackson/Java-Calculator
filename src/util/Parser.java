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
	ArrayList<String> brackets = findBracketOperator(inp, "(");
	
	double answer = 0;
	if (brackets == null){
	    return Double.NaN;
	}
	System.out.println( "parentheses:"+ Arrays.toString(brackets.toArray()));
	if (!brackets.isEmpty()){
	    
	    for (String b : brackets){
		String expr = b.substring(1,b.length()-1);
		double result = strToEqu(expr);
		inp = inp.replace(expr, String.format("%.7f", result));
	    }
	}
	for (String f : functions){
	    brackets = findBracketOperator(inp, f);
	    
	    if (brackets == null){
		return Double.NaN;
	    }
	    System.out.println( "functions " +f+ " :"+ Arrays.toString(brackets.toArray()));
	    if (!brackets.isEmpty()){
		for (String s : brackets){
		    //System.out.println(String.format("begin:%d   end:%d",s.indexOf("(")+1, s.indexOf(")")));
		    String funcIn = s.substring(s.indexOf("(")+1, s.indexOf(")"));
		    //System.out.println(funcIn);
		    String result = funcValue(f,funcIn);
		    if (result == null){
			return Double.NaN;
		    }
		    inp = inp.replace(s, result);
		}
	    }
	}
	for (char op : basicOperators.toCharArray()){
	    String expressions = findBasicOperator(inp, op);
	    if (expressions != null){
		inp = expressions;
		
	    } else {
		return Double.NaN;
	    }
	}
	inp = inp.replace("(", "").replace(")", "");
	try{
	    answer = Double.parseDouble(inp);
	    return answer;
	} catch (NumberFormatException nfe) {System.out.println(inp);return Double.NaN;}
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
    
    static String findBasicOperator(String inp,char op){
	int beginPos = 0;
	int endPos = 0;
	int opPos = 0;
	String output = inp;
	
	while (opPos <= output.length() -1){
	    opPos = output.indexOf(op, endPos);
	    if(opPos != -1){
		if (opPos != output.length()-1){
		    if(output.toCharArray()[opPos+1] != '('){
			for (int i = opPos+1; i < output.length(); i++){
			    if (i == output.length()-1){
				endPos = output.length();
			    }else if (basicOperators.indexOf(output.toCharArray()[i]) != -1){
				endPos = i ;
				break;
			    }
			}
		    } else {
			int testPos = output.indexOf(")", opPos);
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
		    if (output.toCharArray()[opPos-1] != ')'){
			for (int i = opPos-1; i >= 0; i--){
			    if (basicOperators.indexOf(output.toCharArray()[i]) != -1){
				beginPos = i+1;
				break;
			    }
			}
		    } else {
			String sBefore = new StringBuilder(output.substring(0, opPos)).reverse().toString();
			System.out.println(String.format("sBefore %s", sBefore));
			int testPos = sBefore.indexOf("(");
			if (testPos != -1){
			    beginPos = sBefore.length() - testPos;
			}else {
			    return null;
			}
		    }
		} else {
		    beginPos = opPos;
		}
		//System.out.println("begin:"+ String.valueOf(beginPos)+ ", end:" + String.valueOf(endPos));
		//System.out.println(output.substring(beginPos, endPos));
		String expr = output.substring(beginPos, endPos);
		String arg1 = expr.substring(0, expr.indexOf(op)).replace("(","").replace(")", "");
		if (arg1.isEmpty()){
			arg1 = (op == '+' || op =='-')? "0": "";
		}
		String arg2 = expr.substring(expr.indexOf(op)+1).replace("(","").replace(")", "");
		try{
		    //System.out.println("arg1: "+ arg1 + ", arg2: " + arg2);
		    String result = operatorValue(op, Double.parseDouble(arg1), Double.parseDouble(arg2));
		    //System.out.println(String.format("index of expr:%s",output.indexOf(expr)));
		    //System.out.println(result + " replaces " + expr);

		    output = output.replace(expr, result);
		    //System.out.println("new string: " + output);
		    } catch( NumberFormatException nfe){
			return null;
		    }
	    } else {
		break;
	    }
	}
	return output;
    }
    
    static ArrayList<String> findBracketOperator(String inp, String beginS){
	int beginPos = 0;
	ArrayList<String> output = new ArrayList<>();
	while(beginPos <= inp.length()-1){
	    beginPos = inp.indexOf(beginS, beginPos);
	    if (beginPos != -1){
		String bracket = findFullBracket(beginPos,inp, beginS);
		if (bracket != null){
		    output.add(bracket);
		    beginPos += bracket.length()-1;
		}else {
		    return null;
		}
	    } else {
		break;
	    }
	}
	return output;
    }
    
    static String findFullBracket(int beginPos, String input, String beginS){
	int endPos = 0;
	char[] inp = input.toCharArray();
	int innerCount = 0;
	System.out.println("wtf"+ input);
	for(int i = beginPos+ beginS.length(); i < inp.length; i++){
	    if(inp[i] == '('){
		innerCount ++;
	    } else if (inp[i] == ')'){
		if(innerCount == 0){
		    
		    endPos = i+1;
		    break;
		} else {
		    innerCount --;
		}
	    }
	    if (i == inp.length -1){
		return null;
	    }
	}
	return input.substring(beginPos, endPos);
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
	    case "acos(":
		result = Math.acos(Math.toRadians(num));
		break;
	    case "asin(":
		result = Math.asin(Math.toRadians(num));
		break;
	    case "atan(":
		result = Math.atan(Math.toRadians(num));
		break;
	    case "cos(":
		result = Math.cos(Math.toRadians(num));
		break;
	    case "sin(":
		result = Math.sin(Math.toRadians(num));
		break;
	    case "tan(":
		result = Math.tan(Math.toRadians(num));
		break;
	    case "abs(":
		result = Math.abs(num);
		break;
	}
	return String.format("%.7f", result);
    }
}
