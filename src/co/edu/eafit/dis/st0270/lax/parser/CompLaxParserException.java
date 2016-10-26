package co.edu.eafit.dis.st0270.lax.parser;

public class CompLaxParserException 
    extends Exception {
    
    public CompLaxParserException(String cause, String token){
	super(cause + " en " + token);
    }
}
