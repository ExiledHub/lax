grammar LaxAntlrParser;

options{
	language = Java;
	k = 1;
}

@parser::header {
package co.edu.eafit.dis.st0270.lax.parser;
}

@lexer::header{
package co.edu.eafit.dis.st0270.lax.parser;
}

@lexer::rulecatch {
   catch (RecognitionException e) {
      throw e;
   }
}


program	:	block
	;
	
block	:	DECLARE declaration (COLON declaration)* BEGIN statement_list END
	;

statement_list
	:	(statement)(COLON statement)*
	;
	
statement
	:	label_definition* (expression | WHILE expression loop | FOR IDENTIFIER FROM expression TO expression loop  | jump)
	;
	
label_definition
	:	IDENTIFIERLABEL
	;

loop	:	DO statement_list END
	;

jump	:	GOTO IDENTIFIER
	;

declaration
	:	IDENTIFIER declaration2
	|	procedure_declaration
	|	type_declaration
	;
	
declaration2
	: 	DOUBLEDOT variable_declaration2 OF type_specification
	|	IS expression DOUBLEDOT type_specification
	;

variable_declaration
	:	IDENTIFIER DOUBLEDOT variable_declaration2 OF type_specification
	;

variable_declaration2
	:	type_specification
	|	ARRAY LBRACKET bound_pair (COMA bound_pair)* RBRACKET
	;

type_specification
	:	IDENTIFIER
	|	REF type_specification2
	|	PROCEDURE IDENTIFIER procedure
	;
	
type_specification2
	:	type_specification
	|	array_type
	;
	
bound_pair
	:	expression DOUBLEDOT expression
	;
	
array_type
	:	ARRAY LBRACKET COMA* RBRACKET OF type_specification
	;

procedure_type
	:	PROCEDURE (LPAREN type_specification (COLON type_specification)* RPAREN)? (result_type)?
	;
	
identity_declaration
	:	IDENTIFIER IS expression DOUBLEDOT type_specification
	;

procedure_declaration
	:	PROCEDURE IDENTIFIER procedure
	;

procedure
	:	(LPAREN parameter (COLON parameter)* RPAREN)? (result_type)? COLON expression
	;

parameter
	:	IDENTIFIER DOUBLEDOT type_specification
	;

result_type
	:	DOUBLEDOT type_specification
	;

type_declaration
	:	TYPE IDENTIFIER EQUAL record_type
	;

record_type
	:	RECORD field (COLON field)* END
	;

field	:	IDENTIFIER DOT type_specification
	;

type	:	IDENTIFIER
	|	REF type_specification2
	|	PROCEDURE IDENTIFIER procedure
	|	array_type
	;

expression
	:	IDENTIFIER name3 expression2
	|	NEW IDENTIFIER name3 expression2
	|	denotation expression3
	|	LPAREN expression RPAREN expression3
	|	block expression3
	|	clause expression3
	|	UNOP factor expression3
	;
	
expression2
	:	ASSIGN expression	
	|	expression3
	;
	
expression3
	:	(MULOP factor)* (ADDOP term)* (RELOP sum)? (EQOP relation)? 
		(AND comparison)* (OR conjunction)*
	;
	
assignment
	:	IDENTIFIER name3 ASSIGN expression 
	|	NEW IDENTIFIER name3 ASSIGN expression
	;

disjunction
	:	conjunction (OR conjunction)*
	;
	
conjunction
	:	comparison (AND comparison)*
	;

comparison
	:	relation (EQOP relation)?
	;

relation
	:	sum (RELOP sum)?
	;

sum	:	term(ADDOP term)*
	;

term	:	factor(MULOP factor)*
	;

factor 	:	denotation
	|	IDENTIFIER name3 
	| 	NEW IDENTIFIER name3
	|	LPAREN expression RPAREN
	|	block
	|	clause
	|	UNOP factor
	;
	
name	:	IDENTIFIER name3 | NEW IDENTIFIER name3
	;
	
name2	:	DOT IDENTIFIER name3
	| 	LBRACKET expression(COLON expression)* RBRACKET name3
	|	EXPONENT name3
	|	LPAREN argument(COMA argument)* RPAREN name
	;

name3	:	EPSILON
	|	name2
	;

argument:	expression
	;

clause	:	conditional_clause 
	|	case_clause
	;

conditional_clause
	:	IF expression THEN statement_list conditional_clause2
	;

conditional_clause2
	:	END | ELSE statement_list END
	;
	
case_clause
	:	CASE  expression OF(case_label DOUBLEDOT statement_list(DOUBLESLASH case_label DOUBLEDOT statement_list)*) ELSE statement_list END
	;

case_label
	:	INTEGER
	;
	
denotation
	:	INTEGER
	|	FLOATING_POINT
	;

EPSILON	:	
	;

DOT	:	'.'
	;
	
DOUBLEDOT
	:	':'
	;
	
COMA	:	','
	;
	
COLON	:	';'
	;
	
EXPONENT:	'^'
	;
	
DOUBLESLASH
	:	'//'
	;
	
LBRACKET:	'['
	;
	
RBRACKET:	']'
	;
	
LPAREN	:	'('
	;

RPAREN	:	')'
	;

IF	:	'if'
	;
	
IS	:	'is'
	;
	
DO	:	'do'
	;
	
TO	:	'to'
	;

FOR	:	'for'
	;
	
FROM	:	'from'
	;
	
WHILE	:	'while'
	;
	
DECLARE	:	'declare'
	;
	
BEGIN 	:	'begin'
	;
	
ARRAY	:	'array'
	;

REF	:	'ref'
	;
	
GOTO	:	'goto'
	;

PROCEDURE
	:	'procedure'
	;
	
EQUAL	:	'='
	;
	
TYPE	:	'type'
	;

RECORD	:	'record'
	;
	
THEN	:	'then'
	;

CASE	:	'case'
	;

OF	:	'of'
	;	
	
ELSE	:	'else'
	;
	
END	:	'end'
	;

NEW	:	'new'
	;
	
AND	:	'and'
	;
	
OR	:	'or'
	;
	
ASSIGN	:	':='
	;
	
INTEGER	:	'1'..'9'('0'..'9')* | '0'
	;
	
FLOATING_POINT
	:	('0'..'9')+ SCALE
	|	('0'..'9')* DOT ('0'..'9')+ (SCALE)?
	;

SCALE	:	'e' ('+'|'-')? INTEGER
	;
	
EQOP	:	'='
	|	'=='
	;
	
RELOP	:	'<'
	|	'>'
	;
	
ADDOP	:	'+'
	|	'-'
	;
	
MULOP	:	'*'
	|	'/'
	|	'div'
	|	'mod'
	;
		
UNOP	:	'+'
	|	'-'
	|	'not'
	;

IDENTIFIERLABEL
	:	('a'..'z')(('_')?('a'..'z' | INTEGER))* ':'
	;
	
IDENTIFIER
	:	('a'..'z')(('_')?('a'..'z' | INTEGER))*
	;
	
COMMENT	:	'(*' ( options {greedy=false;} : . )* '*)' {$channel=HIDDEN;}
	;
	
EOL 	: '\r' {$channel = HIDDEN;}
    | '\n' {$channel = HIDDEN;}
    | '\r\n' {$channel = HIDDEN;}
    ;

	
WS 	: ' '   {$channel=HIDDEN;}
    	| EOL   {$channel=HIDDEN;}
   	| '\t\n\f' {$channel=HIDDEN;}
    	;
