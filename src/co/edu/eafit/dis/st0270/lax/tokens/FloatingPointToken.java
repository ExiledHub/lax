package co.edu.eafit.dis.st0270.lax.tokens;

public class FloatingPointToken extends DenotationToken {
    
    private String value;

    public FloatingPointToken(String value, int line, int column) {
	super(line, column);
	this.value = new String(value);
    }

    public String getValue(){
	return this.value;
    }

    public String toString(){
	return "Float: " + value + " " + super.toString();
    }
}
