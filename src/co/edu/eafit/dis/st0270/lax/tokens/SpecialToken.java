package co.edu.eafit.dis.st0270.lax.tokens;

public class SpecialToken extends DelimiterToken {
    
    private String symbol;

    public SpecialToken(String value, int line, int column){
	super(line,column);
	this.symbol = new String(value);
    }

    public String getValue(){
	return this.symbol;
    }

    public String toString(){
	return "Special: " + this.symbol + " " + super.toString();
    }
}
