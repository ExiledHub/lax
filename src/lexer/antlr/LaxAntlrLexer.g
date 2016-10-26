lexer grammar LaxAntlrLexer;

@lexer::header{
package co.edu.eafit.dis.st0270.lax.lexer;
}

@lexer::rulecatch {
   catch (RecognitionException e) {
      throw e;
   }
}



SPECIAL	: '+'
    | '-'
    | '*'
    | '/'
    | '<'
    | '>'
    | '='
    | '^'
    | ':'
    | ';'
    | '.'
    | ','
    | '('
    | ')'
    | '['
    | ']'
    | '//'
    | ':='
    | '==' 
    ;	

KEYWORD :   'and'
    |   'array'
    |   'begin'
    |	'case'
    |	'declare'
    |	'div'
    |	'do'
    |	'else'
    |	'end'
    |	'for'
    |	'from'
    |	'goto'
    |	'if'
    |	'is'
    |	'mod'
    |	'new'
    |	'not'
    |	'of'
    |	'or'
    |	'procedure'
    |	'record'
    |	'ref'
    |	'then'
    |	'to'
    |	'type'
    |	'while' 
    ;
                  
INTEGER	:   ('0'..'9')('0'..'9')* 	
    ;

SCALE	: 'e'('+' |'-')?INTEGER	
    ;

FLOATING
    : (('0'..'9')+(SCALE))
    | ('0'..'9')*'.'('0'..'9')+(SCALE)?
    ;		

IDENTIFIER    
    : ('a'..'z')(('_')?('a'..'z' | INTEGER))*
    ;

COMMENT :    '(*' ( options {greedy=false;} : . )* '*)' {$channel=HIDDEN;}
    ;

EOL	: '\r'
    | '\n'
    | '\r\n'
    ;
	
WS  : ' '   {$channel=HIDDEN;}
    | EOL   {$channel=HIDDEN;}
    | '\t\n\f' {$channel=HIDDEN;}
    ;
    
ZIDENTIFIERLABEL
    : (IDENTIFIER)':'
    ;
