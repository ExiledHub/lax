/* Analizador lexico para el lenguaje LAX */
package co.edu.eafit.dis.st0270.lax.lexer;

import co.edu.eafit.dis.st0270.lax.tokens.Token;
import co.edu.eafit.dis.st0270.lax.tokens.BasicSymbolToken;
import co.edu.eafit.dis.st0270.lax.tokens.DelimiterToken;
import co.edu.eafit.dis.st0270.lax.tokens.DenotationToken;
import co.edu.eafit.dis.st0270.lax.tokens.FloatingPointToken;
import co.edu.eafit.dis.st0270.lax.tokens.IdentifierToken;
import co.edu.eafit.dis.st0270.lax.tokens.IntegerToken;
import co.edu.eafit.dis.st0270.lax.tokens.KeywordToken;
import co.edu.eafit.dis.st0270.lax.tokens.ScaleToken;
import co.edu.eafit.dis.st0270.lax.tokens.SpecialToken;

%%
%class LaxJFlexLexer
%unicode
%line
%column
%function getNextToken
%type Token
%public

WhiteSpace          = {EOL} | [ \t\f]
EOL                 = \r|\n|\r\n

Comment             = "(*" [^*] ~"*)" | "(*" "*"+ ")"

DecimalInteger      = (0-9)[0-9]*

FloatingPoint       = (0-9)[0-9]*{Scale} | [0-9]*"."[0-9][0-9]*[Scale]?
Scale               = "e"[+|-]?{DecimalInteger}

Keyword             = "and" | "array" | "begin" | "case"
		    | "declare" | "div" | "do" | "else"
		    | "end" | "for" | "from" | "goto"
		    | "if" | "is" | "mod" | "new" | "not"
		    | "of" | "or" | "procedure" | "record"
		    | "ref" | "then" | "to" | "type" | "while"

Special             = "+" | "-" | "*" | "/" | "<" | ">" | "="
		    | "^" | ":" | ";" | "." | "," | "(" | ")"
		    | "[" | "]" | "//" | ":=" | "=="

Identifier          = (a-z|A-Z)(["_"]?(0-9))*

%%

<YYINITIAL> {
   {Keyword}               { return new KeywordToken(yytext(), yyline, yycolumn); }
   {Comment}               { /* Ignore */ }
   {WhiteSpace}            { /* Ignore */ }
   {Special}               { return new SpecialToken(yytext(), yyline, yycolumn); }
   {DecimalInteger}        { return new IntegerToken(yytext(), yyline, yycolumn); }
   {FloatingPoint}         { return new FloatingPointToken(yytext(), yyline, yycolumn); }
   {Identifier}            { return new IdentifierToken(yytext(), yyline, yycolumn); }
}

.|\n                             { throw new Error("Illegal character <" + yytext() + "> at line: " + (yyline + 1) + " column: " + yycolumn); }