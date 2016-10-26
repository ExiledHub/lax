package co.edu.eafit.dis.st0270.lax.tokens;

public class IdentifierToken extends BasicSymbolToken {
    
    private String identifier;

    public IdentifierToken(String value, int line, int column){
	super(line, column);
	this.identifier = new String(value);
    }
    
    public String getIdentifier(){
	return this.identifier;
    }

    public String toString(){
	return "Identifier: " + this.identifier + " " + super.toString();
    }
}
