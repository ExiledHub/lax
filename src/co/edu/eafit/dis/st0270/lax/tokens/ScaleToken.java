package co.edu.eafit.dis.st0270.lax.tokens;

public class ScaleToken extends Token {
    
    private String value;
    
    public ScaleToken(String value, int line, int column){
	super(line, column);
	this.value = value;
    }

    public String getValue(){
	return this.value;
    }

    public String toString(){
	return "Scale: " + this.value + " " + super.toString();
    }
}
