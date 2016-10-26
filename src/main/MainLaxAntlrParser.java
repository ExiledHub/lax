package main;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import java.io.IOException;
import co.edu.eafit.dis.st0270.lax.parser.LaxAntlrParserParser;
import co.edu.eafit.dis.st0270.lax.parser.LaxAntlrParserLexer;

public class MainLaxAntlrParser {
    
    public static void main(String args[]){
	
	if(args.length != 1){
	    System.err.println("Error: Uso MainLaxAntlrParser File");
	    System.exit(1);
	}
	
	ANTLRFileStream afs = null;
	
	try {
	    afs = new ANTLRFileStream(args[0]);
	} catch (IOException ioe) {
	    System.err.println("Error: " + ioe);
	    System.exit(1);
	}
	
	LaxAntlrParserLexer lexer = new LaxAntlrParserLexer(afs);
	CommonTokenStream tokens = new CommonTokenStream(lexer);
	LaxAntlrParserParser parser = new LaxAntlrParserParser(tokens);
	
	try {
	    parser.program();
	} catch(RecognitionException re) {
	    System.err.println("Exception: " + re);
	    System.exit(1);
	}
    }
}
