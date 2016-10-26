package co.edu.eafit.dis.st0270.lax.tokens;

public class IntegerToken extends DenotationToken {
    
    private Integer value;

    public IntegerToken(String value, int line, int column){
	super(line,column);
	this.value = new Integer(value);
    }
    
    public int getValue(){
	return this.value;
    }
    
    public String toString(){
	return "Integer: " + value + " " + super.toString();
    }
}
