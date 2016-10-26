package main;

import org.antlr.runtime.ANTLRFileStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import co.edu.eafit.dis.st0270.lax.parser.LaxManualParser;

public class MainLaxManualParser {
    
    private static void usage(int errcode){
	System.err.println("Usage java MainLaxManualParser <file>");
	System.exit(errcode);
    }
    
    public static void main(String args[]){
	
	if(args.length != 1){
	    usage(1);
	}
	
	try {
	    LaxManualParser parse = new LaxManualParser(args[0]);
	    parse.parser();
	} catch (Exception e) {
	    System.err.println(e);
	    System.exit(2);
	}
    }
}
