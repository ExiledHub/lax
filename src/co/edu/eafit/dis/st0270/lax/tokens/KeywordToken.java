package co.edu.eafit.dis.st0270.lax.tokens;

public class KeywordToken extends DelimiterToken {
    
    private String keyword;

    public KeywordToken(String value, int line, int column){
	super(line,column);
	this.keyword = value;
    }
    
    public String getValue(){
	return this.keyword;
    }
    
    public String toString(){
	return "Keyword: " + this.keyword + " " + super.toString();
    }
}
