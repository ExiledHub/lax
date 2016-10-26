package co.edu.eafit.dis.st0270.lax.tokens;

public abstract class Token {

    private int line;
    private int column;

    public Token(int line, int column) {
	this.line   = line + 1;
	this.column = column;
    }

    public void setLine(int line) {
	this.line = line;
    }

    public void setColumn(int column) {
	this.column = column;
    }

    public int getLine() {
	return this.line;
    }

    public int getColumn() {
	return this.column;
    }

    public String toString() {
	return "linea: " + this.line + " columna: " + this.column;
    }
}
