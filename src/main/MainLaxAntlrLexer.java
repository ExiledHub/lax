package main;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.Token;
import java.io.IOException;
import co.edu.eafit.dis.st0270.lax.lexer.LaxAntlrLexer;


public class MainLaxAntlrLexer{
    
    public static void main(String args[]) {
	
	if (args.length != 1) {
	    System.err.println("Error: Uso JayCompilerAntlr File");
	    System.exit(1);
	}
	
	ANTLRFileStream afs = null;
	
	try {
	    afs = new ANTLRFileStream(args[0]);
	}
        catch (IOException ioe) {
	    System.err.println("Error: " + ioe);
	    System.exit(1);
	}
	
	LaxAntlrLexer lexer = new LaxAntlrLexer(afs);
	Token t = lexer.nextToken();
	Token eof = lexer.getEOFToken();
	while (t.getType() != eof.getType()) {
	    switch(t.getType()) {
	    case LaxAntlrLexer.COMMENT:
	    case LaxAntlrLexer.SCALE:
	    case LaxAntlrLexer.WS:
	    case LaxAntlrLexer.EOL:
		break;
	    default:
		System.out.println("Tipo: "+ getStrType(t.getType())+" Token: " + t.getText() + " fila: " + t.getLine() + " columna: " + t.getCharPositionInLine());
		break;
	    }
	    t = lexer.nextToken();
	}
    }

    public static String getStrType(int valToken){
	switch(valToken){
	case 6:
	    return "floating-point";
	case 7:
	    return "identifier";
	case 8: 
	    return "integer";
	case 9:
	    return "keyword";
	case 11:
	    return "special";
	default :
	    return " ";
	    
	}
    }
}
