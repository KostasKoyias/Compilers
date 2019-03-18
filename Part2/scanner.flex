import java_cup.runtime.*;

%%
/* ----------------- Options and Declarations Section----------------- */

//The name of the class JFlex will create will be Scanner. Will write the code to the file Scanner.java.
%class Scanner

//The current line number can be accessed with the variable yyline and the current column number with the variable yycolumn.
%line
%column

//Will switch to a CUP compatibility mode to interface with a CUP generated parser.
%cup
%unicode

// Declarations :Code between %{ and %}, both of which must be at the beginning of a line, will be copied verbatim into the lexer class source.
%{
    
    // The following two methods create java_cup.runtime.Symbol objects
    StringBuffer stringBuffer = new StringBuffer();
    private Symbol symbol(int type) {
       return new Symbol(type, yyline, yycolumn);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

/* Macro Declarations: Regular expressions that will be used latter in the Lexical Rules Section.
   In case of two or more match, the maximal much rule is applied. In case of same maximal length, 
   the one listed first will be choosed, so pay attention to the order of experessions in the following list */

// A line terminator is a \r (carriage return), \n (line feed), or \r\n. 
LineTerminator = \r|\n|\r\n

// White space is a line terminator, space, tab, or line feed. 
WhiteSpace     = {LineTerminator} | [ \t\f]

// A literal integer is a number beginning with a decimal base digit[0-9] followed by zero or more decimal based digits in [0-9] or just a 0.  */
keywords = if | else
identifier = [:jletter:] [:jletterdigit:]*  // or identifier = [_a-zA-Z] \w* where \w = [a-zA-Z_0-9] 

%state STRING

%%
/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> {
/* operators: in case of recognizing any of the following, the corresponding brucket's content define what will be returned to the parser*/
 "+"      {return symbol(sym.PLUS);}
 "prefix" {return symbol(sym.PREFIX);}
 "suffix" {return symbol(sym.SUFFIX);}
 "("      { return symbol(sym.LPAREN);}
 ")"      { return symbol(sym.RPAREN);}
 "{"      { return symbol(sym.LBRACK);}
 "}"      { return symbol(sym.RBRACK);}
 \"       { stringBuffer.setLength(0); yybegin(STRING); }
 ","      { return symbol(sym.COMMA);}
}

<STRING> {
      \"                             { yybegin(YYINITIAL);
                                       return symbol(sym.STRING_LITERAL, stringBuffer.toString()); }
      [^\n\r\"\\]+                   { stringBuffer.append( yytext() ); }
      \\t                            { stringBuffer.append('\t'); }
      \\n                            { stringBuffer.append('\n'); }

      \\r                            { stringBuffer.append('\r'); }
      \\\"                           { stringBuffer.append('\"'); }
      \\                             { stringBuffer.append('\\'); }
}

{WhiteSpace} { /* just skip what was found, do nothing */ }
{keywords} { return symbol(sym.KEYWORD);}
{identifier} { return symbol(sym.IDENTIFIER, new String(yytext())); }

// No token was found for the input so print out an Illegal character message with the illegal character that was found. 
[^] { throw new Error("Illegal character <"+yytext()+">"); }
