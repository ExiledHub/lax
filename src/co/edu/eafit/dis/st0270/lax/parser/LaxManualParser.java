package co.edu.eafit.dis.st0270.lax.parser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.Token;
import java.io.IOException;
import co.edu.eafit.dis.st0270.lax.lexer.LaxAntlrLexer;

public class LaxManualParser {
    
    private LaxAntlrLexer lexer = null;
    private Token t = null;
    private ANTLRFileStream afs = null;

    public LaxManualParser(String inputFile){
	//holi
	try{
	    afs = new ANTLRFileStream(inputFile);
	}catch (IOException ioe) {
	    System.err.println("Error: " + ioe);
	    System.exit(1);
	}
    }
    
    public void parser() 
	throws CompLaxParserException, IOException { //TODO
	
	try {
	    lexer = new LaxAntlrLexer(afs);
	    t = lexer.nextToken();
	    program();
	    if(t.getType() != lexer.getEOFToken().getType()){
		throw new Exception("Error: programa invalido.");//TODO
	    }
	} catch (Exception e) {
	    System.err.println(e);
	    System.exit(1);
	}
    }
    
    private void program()
	throws CompLaxParserException, IOException {
	
	block();
	return;
    }
    
    private void block()
	throws CompLaxParserException, IOException {
	
	if(t.getText().equals("declare")){
	    next();
	    declaration();
	    next();
	    while(t.getText().equals(";")){
		next();
		declaration();
		next();
	    }
	    if(t.getText().equals("begin")){
		next();
		statement_list();
		next();
		if(t.getText().equals("end")){
		    return;
		} else throw new CompLaxParserException
			   ("Se espera: end","block");
	    } else throw new CompLaxParserException
		      ("Se espera: begin","block");

	}else throw new CompLaxParserException
		  ("Se espera: declare","block");
    }
    
    private void statement_list()
	throws CompLaxParserException, IOException {
	statement();
	next();
	while(t.getText().equals(";")){
	    next();
	    statement();
	    next();
	} 
	return;
    }
    
    private void statement()
	throws CompLaxParserException, IOException {
	while(t.getType() == 13){
	    next();
	    label_definition();
	    next();
	}
	String str = t.getText();
	if(str.equals("while")){
	    next();
	    expression();
	    next();
	    loop();
	    return;
	} else if(str.equals("for")){
	    next();
	    if(t.getType() == 7){
		next();
		if(t.getText().equals("from")){
		    next();
		    expression();
		    next();
		    if(t.getText().equals("to")){
			next();
			expression();
			next();
			loop();
			return;
		    } else throw new CompLaxParserException
			       ("Se espera: to","statement");
		}else throw new CompLaxParserException
			  ("Se espera: from","statement");
	    }else throw new CompLaxParserException
                      ("Se espera: Identifier","statement");
	} else if(str.equals("goto")){
	    jump();
	    return;
	} if(str.equals("new")|str.equals("(")
	   |t.getType() == 7|str.equals("declare")
	   |t.getType() == 8|t.getType() == 6
	   |t.getText().equals("if")|t.getText().equals("case")
	   |t.getText().equals("+")|t.getText().equals("-")
	   |t.getText().equals("not")){
	    expression();
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: new, (, Identifier, Integer,"
		    + " Floating point, declare, if, case, +, -, not"
		    + " goto, for, while"
		    ,"statement");
    }
    
    private void label_definition()
	throws CompLaxParserException, IOException {
	if(t.getType() == 13){
	    return;
	} else throw new CompLaxParserException
		  ("Se espera: Identifier:","label_definition");
    }
    
    private void loop()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("do")){
	    next();
	    statement_list();
	    next();
	    if(t.getText().equals("end")){
		return;
	    } else throw new CompLaxParserException
                      ("Se espera: end","loop");
	} else throw new CompLaxParserException
		  ("Se espera: do","loop");
    }
    
    private void jump()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("goto")){
	    next();
	    if(t.getType() == 7){
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","jump");
	} else throw new CompLaxParserException
		   ("Se espera: goto","jump");
    }
    
    private void declaration()
	throws CompLaxParserException, IOException {
	if(t.getType() == 7){
	    next();
	    declaration2();
	    return;
	} else if(t.getText().equals("procedure")){
	    next();
	    if(t.getType() == 7){
		next();
		procedure();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","declaration");
	} else if(t.getText().equals("type")){
	    next();
	    if(t.getType() == 7){
		next();
		if(t.getText().equals("=")){
		    next();
		    record_type();
		    return;
		} else throw new CompLaxParserException
			   ("Se espera: =","declaration");
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","declaration");
	} else throw new CompLaxParserException
		   ("Se espera: type, procedure, Identifier"
		    ,"declaration");
    }
    
    private void declaration2()
	throws CompLaxParserException, IOException {
	if(t.getText().equals(":")){
	    next();
	    variable_declaration2();
	    next();
	    if(t.getText().equals("of")){
		next();
		type_specification();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: of","declaration2");
	} else if(t.getText().equals("is")){
	    next();
	    expression();
	    next();
	    if(t.getText().equals(":")){
		next();
		type_specification();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: :","declaration2");
	} else throw new CompLaxParserException
		   ("Se espera: is, :","declaration2");
    }
    
    //variable_declaration no es alcanzable.
    
    private void variable_declaration2()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("array")){
	    next();
	    if(t.getText().equals("[")){
		next();
		bound_pair();
		next();
		while(t.getText().equals(",")){
		    next();
		    bound_pair();
		    next();
		}
		if(t.getText().equals("]")){
		    return;
		} else throw new CompLaxParserException
			   ("Se espera: ]","variable_declaration2");
	    } else throw new CompLaxParserException
		       ("Se espera: [","variable_declaration2");
	} else if(t.getText().equals("ref")
	   |t.getText().equals("procedure")|t.getType() == 7){
	    type_specification();
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: array, ref, procedure, Identifier"
		    ,"variable_declaration2");
    }
    
    private void type_specification()
	throws CompLaxParserException, IOException {
	if(t.getType() == 7){
	    next(); //Fol de type_specification
	    return;
	} else if(t.getText().equals("ref")){
	    next();
	    type_specification2();
	    return;
	} else if(t.getText().equals("procedure")){
	    next();
	    if(t.getType() == 7){
		next();
		procedure();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","type_specification");
	} else throw new CompLaxParserException
		   ("Se espera: procedure, ref, Identifier"
		    ,"type_specification");
    }
    
    private void type_specification2()
	throws CompLaxParserException, IOException {
	if(t.getType() == 7 | t.getText().equals("ref")
	   |t.getText().equals("procedure")){
	    type_specification();
	    return;
	} else if(t.getText().equals("array")){
	    array_type();
	} else throw new CompLaxParserException
		   ("Se espera: array, Identifier, ref, procedure"
		    ,"type_specification2");
    }
    
    private void bound_pair()
	throws CompLaxParserException, IOException {
	String str = t.getText();
	if(str.equals("new")|str.equals("(")
	   |t.getType() == 7|str.equals("declare")
           |t.getType() == 8|t.getType() == 6
           |t.getText().equals("if")|t.getText().equals("case")
           |t.getText().equals("+")|t.getText().equals("-")
           |t.getText().equals("not")){
            expression();
	    next();
	    if(t.getText().equals(":")){
		next();
		expression();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: :","bound_pair");
	} else throw new CompLaxParserException
		   ("Se espera: (, Identifier, Integer, Floating Point"
		    +", new, declare, if, case, +, -, not"
		    ,"bound_pair");
    }
    
    private void array_type()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("array")){
	    next();
	    if(t.getText().equals("[")){
		next();
		while(t.getText().equals(",")){
		    next();
		}
		if(t.getText().equals("]")){
		    next();
		    if(t.getText().equals("of")){
			next();
			type_specification();
			return;
		    } else throw new CompLaxParserException
			       ("Se espera: of","array_type");
		} else throw new CompLaxParserException
			   ("Se espera: ]","array_type");
	    } else throw new CompLaxParserException
		       ("Se espera: [","array_type");
	} else throw new CompLaxParserException
		   ("Se espera: array","array_type");
    }
    
    private void procedure_declaration()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("procedure")){
	    next();
	    if(t.getType() == 7){
		next();
		procedure();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier",
			"procedure_declaration");
	} else throw new CompLaxParserException
		   ("Se espera: procedure","procedure_declaration");
    }
    
    private void procedure()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("(")){
	    next();
	    parameter();
	    next();
	    while(t.getText().equals(";")){
		next();
		parameter();
		next();
	    }
	    if(t.getText().equals(")")){
		// en realidad nada.
	    } else throw new CompLaxParserException
		       ("Se espera: )","procedure");
	}
	
	if(t.getText().equals(":")){
	    result_type();
	    next();
	}
	
	if(t.getText().equals(";")){
	    next();
	    expression();
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: ;","procedure");
    }
    
    private void parameter()
	throws CompLaxParserException, IOException {
	if(t.getType() == 7){
	    next();
	    if(t.getText().equals(":")){
		next();
		type_specification();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: :","parameter");
	} else throw new CompLaxParserException
		   ("Se espera: Identifier","parameter");
    }
    
    private void result_type()
	throws CompLaxParserException, IOException {
	if(t.getText().equals(":")){
	    next();
	    type_specification();
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: :","result_type");
    }
    
    private void type_declaration()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("type")){
	    next();
	    if(t.getType() == 7){
		next();
		if(t.getText().equals("=")){
		    next();
		    record_type();
		    return;
		} else throw new CompLaxParserException
			   ("Se espera: =","type_declaration");
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","type_declaration");
	} else throw new CompLaxParserException
		   ("Se espera: type","type_declaration");
    }
    
    private void record_type()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("record")){
	    next();
	    field();
	    next();
	    while(t.getText().equals(";")){
		next();
		field();
		next();
	    }
	    if(t.getText().equals("end")){
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: end","record_type");
	} else throw new CompLaxParserException
		   ("Se espera: record","record_type");
    }
    
    private void field()
	throws CompLaxParserException, IOException {
	if(t.getType() == 7){
	    next();
	    if(t.getText().equals(".")){
		next();
		type_specification();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: .","field");
	} else throw new CompLaxParserException
		   ("Se espera: Identifier","field");
    }
    
    private void expression()
	throws CompLaxParserException, IOException {
	if(t.getType() == 7){
	    next();
	    name3();
	    next();
	    expression2();
	    return;
	} else if(t.getText().equals("new")){
	    if(t.getType() == 7){
		next();
		name3();
		next();
		expression2();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","expression");
	} else if(t.getText().equals("(")){
	    next();
	    expression();
	    next();
	    if(t.getText().equals(")")){
		next();
		expression3();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: )","expression");
	} else if(t.getText().equals("+")
	   |t.getText().equals("-")
	   |t.getText().equals("not")){
	    next();
	    factor();
	    next();
	    expression3();
	    return;
	} else if(t.getText().equals("if")
	   |t.getText().equals("case")){
	    next();
	    expression3();
	    return;
	} else if(t.getText().equals("declare")){
	    next();
	    expression3();
	    return;
	} else if(t.getType() == 8 | t.getType() == 6){
	    next();
	    expression3();
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: Integer, Floating Point, Identifier"
		    +", new, (, declare, if, case, +, -, not"
		    ,"expression");
    }

    private void expression2()
	throws CompLaxParserException, IOException {
	String str = t.getText();
	if(t.getText().equals(":=")){
	    next();
	    expression();
	    return;
	} else if(str.equals("*")|str.equals("/")
	   |str.equals("div")|str.equals("mod")
	   |str.equals("+")|str.equals("-")
	   |str.equals("<")|str.equals(">")
	   |str.equals("=")|str.equals("==")
	   |str.equals("and")|str.equals("or")){
	    expression3();
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: *, /, div, mod, +, -, <, >, ="
		    + ", ==, and, or, :="
		    ,"expression2");
    }
    
    private void expression3()
	throws CompLaxParserException, IOException {	
	String str = t.getText();
	while(str.equals("*")|str.equals("/")
	      |str.equals("div")|str.equals("mod")){
	    next();
	    factor();
	}
	while(str.equals("+")|str.equals("-")){
	    next();
	    term();
	}
	if(str.equals("<")|str.equals(">")){
	    next();
	    sum();
	}
	if(str.equals("=")|str.equals("==")){
	    next();
	    relation();
	}
	while(str.equals("and")){
	    next();
	    comparison();
	}
	while(str.equals("or")){
	    next();
	    conjunction();
	}
	return;
    }

    private void assignment()
	throws CompLaxParserException, IOException {
	if(t.getType() == 7){
	    next();
	    name3();
	    next();
	    if(t.getText().equals(":=")){
		next();
		expression();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: :=","assignment");
	} else if(t.getText().equals("new")){
	    next();
	    if(t.getType() == 7){
		next();
		name3();
		next();
		if(t.getText().equals(":=")){
		    next();
		    expression();
		    return;
		} else throw new CompLaxParserException
			   ("Se espera: :=","assignment");
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","assignment");
	} else throw new CompLaxParserException
		   ("Se espera: new, Identifier","assignment");
    }

    private void conjunction()
	throws CompLaxParserException, IOException {
	comparison();
	next();
	while(t.getText().equals("and")){
	    next();
	    comparison();
	    next();
	}
	return;
    }

    private void comparison()
	throws CompLaxParserException, IOException {
	relation();
	next();
	if(t.getText().equals("=")|t.getText().equals("==")){
	    next();
	    relation(); //OPTIONAL
	    next();
	}
	return;
    }

    private void relation()
	throws CompLaxParserException, IOException {
	sum();
	next();
	if(t.getText().equals("<")|t.getText().equals(">")){
	    next();
	    sum();
	    next();
	}
	return;
    }

    private void sum()
	throws CompLaxParserException, IOException {
	term();
	next();
	while(t.getText().equals("+")|t.getText().equals("-")){
	    next();
	    term();
	    next();
	}
	return;
    }

    private void term()
	throws CompLaxParserException, IOException {
	factor();
	next();
	while(t.getText().equals("*")|t.getText().equals("/")
	      |t.getText().equals("div")|t.getText().equals("mod")){
	    next();
	    factor();
	    next();
	}
	return;
    }

    private void factor()
	throws CompLaxParserException, IOException {
	if(t.getType() == 8 | t.getType() == 6){
	    return;
	} else if(t.getType() == 7){
	    next();
	    name3();
	    return;
	} else if(t.getText().equals("new")){
	    next();
	    if(t.getType() == 7){
		next();
		name3();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","factor");
	} else if(t.getText().equals("(")){
	    next();
	    expression();
	    next();
	    if(t.getText().equals(")")){
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: )","factor");
	} else if(t.getText().equals("declare")){
	    block();
	    return;
	} else if(t.getText().equals("if")|t.getText().equals("case")){
	    clause();
	    return;
	} else if(t.getText().equals("+")|t.getText().equals("-")
	   |t.getText().equals("not")){
	    next();
	    factor();
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: +, -, not, if, case, declare"
		    + ", (, new, Identifier, Integer, Floating Point"
		    ,"factor");
    }

    private void name()
	throws CompLaxParserException, IOException {
	if(t.getType() == 7){
	    next();
	    name3();
	    return;
	} else if(t.getText().equals("new")){
	    next();
	    if(t.getType() == 7){
		next();
		name3();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","name");
	} else throw new CompLaxParserException
		   ("Se espera: new, Identifier","name");
    
    }

    private void name2()
	throws CompLaxParserException, IOException {
	String str = t.getText();
	if(str.equals(".")){
	    next();
	    if(t.getType() == 7){
		next();
		name3();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: Identifier","name2");
	} else if(str.equals("[")){
	    next();
	    expression();
	    next();
	    while(t.getText().equals(";")){
		next();
		expression();
		next();
	    }
	    if(t.getText().equals("]")){
		next();
		name3();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: ]","name2");
	} else if(str.equals("^")){
	    next();
	    name3();
	    return;
	} else if(str.equals("(")){
	    next();
	    argument();
	    next();
	    while(t.getText().equals(",")){
		next();
		argument();
		next();
	    }
	    if(t.getText().equals(")")){
		next();
		name();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: )","name2");
	} else throw new CompLaxParserException
		   ("Se espera: (, ^, [, .","name2");
    }

    private void name3()
	throws CompLaxParserException, IOException {
	if(t.getText().equals(".")|t.getText().equals("[")
	   |t.getText().equals("^")|t.getText().equals("(")){
	    name2();
	    return;
	} else if(t.getText().equals("")){
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: ., [, ^, (, ","name3");
    }

    private void argument()
	throws CompLaxParserException, IOException {
	expression();
	return;
    }

    private void clause()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("if")){
	    next();
	    expression();
	    next();
	    if(t.getText().equals("then")){
		next();
		statement_list();
		next();
		conditional_clause();
		return;
	    } else throw new CompLaxParserException
		       ("Se espera: then","clause");
	} else if(t.getText().equals("case")) {
	    next();
	    expression();
	    next();
	    if(t.getText().equals("of")){
		next();
		case_label();
		next();
		if(t.getText().equals(":")){
		    next();
		    statement_list();
		    next();
		    while(t.getText().equals("//")){
			next();
			case_label();
			next();
			if(t.getText().equals(":")){
			    next();
			    statement_list();
			    next();
			} else throw new CompLaxParserException
				   ("Se espera: :","clause");
		    }
		    if(t.getText().equals("else")){
			next();
			statement_list();
			next();
			if(t.getText().equals("end")){
			    return;
			} else throw new CompLaxParserException
				   ("Se espera: end","clause");
		    } else throw new CompLaxParserException
			       ("Se espera: else","clause");
		} else throw new CompLaxParserException
			   ("Se espera: :","clause");
	    } else throw new CompLaxParserException
		       ("Se espera: of","clause");
	} else throw new CompLaxParserException
		   ("Se espera: if,case","clause");
    }

    private void conditional_clause()
	throws CompLaxParserException, IOException {
	if(t.getText().equals("end")){
	    return;
	} else if(t.getText().equals("else")){
	    next();
	    statement_list();
	    next();
	    if(t.getText().equals("end")){
		return;
	    }
	} else throw new CompLaxParserException
		   ("Se espera: end, else","conditional_clause");
    }

    private void case_label()
	throws CompLaxParserException, IOException {
	if(t.getType() == 8){
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: Integer","case_label");
    }

    private void denotation()
	throws CompLaxParserException, IOException {
	if(t.getType() == 8 | t.getType() == 6){
	    return;
	} else throw new CompLaxParserException
		   ("Se espera: Integer, Floating Point","denotation");
    }
    
    private void next() {
	t = lexer.nextToken();
    }
}
