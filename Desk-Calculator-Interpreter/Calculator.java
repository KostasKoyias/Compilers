/************************************************************\
 This is a calculator, implementing ^ and & operations,
 based on a simple context-free grammar that looks as follows
 exp -> exp2 tail
 tail -> ^ exp2 tail | ""
 exp2 -> last tail2	
 tail2 -> & last tail2 | ""
 last -> (exp) | 0 | 1 | ... | 9
 For each non-terminal a function is defined, these are
 calling each other based on the lookahead token           
 \***********************************************************/

import java.io.InputStream;
import java.awt.SystemTray;
import java.io.IOException;

class Calculator{
		/* Calculator's Fields*/
    private int lookaheadToken;
    private InputStream in;

		/*Calculator's member methods*/

		// initialize a calculator setting it's input stream and set the "next token to parse" to the "1st token of the stream"  
    public Calculator(InputStream in) throws IOException {
			this.in = in;
			lookaheadToken = in.read();
    }

		// consume the token that was just parsed and 'load' the next one to $lookaheadToken 
    private void consume(int symbol) throws IOException, ParseError {
			if (lookaheadToken != symbol)
				throw new ParseError();
			
			// get the next character, ignoring tabs ands spaces
			do{
				lookaheadToken = in.read();
			}while(lookaheadToken == ' ' || lookaheadToken == '\t');
    }

		// convert a character representing an integer to the integer 
    private int value(int digit){
			return digit - '0';
		}

		// non-terminal $exp having a single profuction rule: exp -> exp2 tail
    private int exp() throws IOException, ParseError {
			if((lookaheadToken < '0' || lookaheadToken > '9') && (lookaheadToken != '('))
				throw new ParseError();
			// $exp2's result will be passed to $tail as the 1st operand of the XOR operation
			return tail(exp2());
		}
		
		// non-terminal $exp2 is pretty similar to exp, it's only rule is: exp2 -> last tail2
		private int exp2() throws IOException, ParseError {
			if((lookaheadToken < '0' || lookaheadToken > '9') && (lookaheadToken != '('))
				throw new ParseError();
			// $last's result will be passed to $tail2 as the 1st operand of the BITWISE_AND operation
			return tail2(last());
		}

		// non-terminal $tail can produce the following: tail -> ^ exp2 tail | ""
		private int tail(int fst) throws IOException, ParseError{
			// in case the 1st rule was applied: tail -> ^ exp2 tail
			if(lookaheadToken == '^'){
				consume('^');
				int snd = tail(exp2());
				return (fst ^ snd);
			}
			// in case the 2nd rule was applied: tail -> ""(empty string), check whether lookaheadToken can FOLLOW $tail else throw an exception
			else if(lookaheadToken == '\n' || lookaheadToken == -1 || lookaheadToken == ')')
				return fst;
			else
				throw new ParseError();
		}

		// non-terminal $tail2 can produce the following: & last tail2 | "", pretty similar to $tail
		private int tail2(int fst) throws IOException, ParseError{
			// in case of the 1st rule: tail2 -> & last tail2
			if(lookaheadToken == '&'){
				consume('&');
				int snd = tail2(last());
				return (fst & snd);
			}
			// else in case of the 2nd rule: tail2 -> ""(empty string), check whether lookaheadToken can FOLLOW $tail2 else throw an exception
			else if(lookaheadToken == '^' || lookaheadToken == ')' || lookaheadToken == '\n' || lookaheadToken == -1)
				return fst;
			else
				throw new ParseError();
		}

		// non-terminal $last can produce stuff as follows: last -> (exp) | 0 | 1 | ... | 9
		private int last() throws IOException, ParseError{
			// in case the 1st rule has been applied: last -> (exp)
			if(lookaheadToken == '('){
				consume('(');
				int ret_val = exp();
				consume(')');	
				return ret_val;		
			}
			// else if the 2nd rule is applied: last -> [0-9]
			else if(lookaheadToken >= '0' && lookaheadToken <= '9'){
				int ret_val = value(lookaheadToken);
				consume(lookaheadToken);
				return ret_val;
			}
			// else throw an excepiton because FIRST+(last) = { (, 0, 1, ..., 9}
			else
				throw new ParseError();
		}

		// evaluate an expression using the grammar defined above, having exp as it's starting non-terminal symbol
    public int eval() throws IOException, ParseError {
			if(lookaheadToken == 'q')
				return -1;
			int rv = exp();
			if (lookaheadToken != '\n' && lookaheadToken != -1)
				throw new ParseError();
			return rv;
    }

		// main function creates a Calculator, that gets a string representing an operation from input parses on it 
		// and executes the operation until the character 'q' is given from input 
    public static void main(String[] args) throws IOException{
			System.out.println("\033[1mCalculator 0.1\n--------------\033[0m");
			// unitl 'q' is read
			while(true){
				// prompt user to enter a string representing an operation
				System.out.print('>');
				try {
					Calculator evaluate = new Calculator(System.in);
					int rv = evaluate.eval();
					if(rv == -1)
						break;
					else
						System.out.println("\033[1mResult: \033[0m " + rv);
					evaluate = null;
				}
				catch (IOException e) {
					System.err.println(e.getMessage());
				}
				catch(ParseError err){
					System.err.println(err.getMessage());

					// clear stdin by reading until newline or EOF
					int ch;
					try{
						while((ch = System.in.read()) != '\n' && ch != -1);
					}
					catch (IOException e) {
						System.err.println(e.getMessage());
					}
				}
			}
			System.out.println("\033[1mCalculator\033[0m: exiting now...");
    }
}