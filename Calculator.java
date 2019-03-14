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

		// consume the token that was just parsed and 'load' the next one to lookaheadToken 
    private void consume(int symbol) throws IOException, ParseError {
			if (lookaheadToken != symbol)
				throw new ParseError();
			lookaheadToken = in.read();
    }

    private int value(int digit){
			return digit - '0';
    }

    private int exp() throws IOException, ParseError {
			System.out.println("exp");
			if((lookaheadToken < '0' || lookaheadToken > '9') && (lookaheadToken != '('))
				throw new ParseError();
			exp2();
			tail();
			return 1;
		}
		
		private int exp2() throws IOException, ParseError {
			System.out.println("exp2");
			if((lookaheadToken < '0' || lookaheadToken > '9') && (lookaheadToken != '('))
				throw new ParseError();
			last();
			tail2();
			return 1;
		}

		private int tail() throws IOException, ParseError{
			System.out.println("tail");
			if(lookaheadToken == '^'){
				System.out.println("tail: consumed: " + (char)lookaheadToken);
				consume('^');
				exp2();
				tail();
			}
			else if(lookaheadToken != '\n' && lookaheadToken != -1 && lookaheadToken != ')')
				throw new ParseError();
			return 1;
		}

		private int tail2() throws IOException, ParseError{
			System.out.println("tail2");
			if(lookaheadToken == '&'){
				System.out.println("tail2: consumed: " + (char)lookaheadToken);
				consume('&');
				last();
				tail2();
			}
			// else if it is NOT a digit[0-9] throw an exception else do nothing
			else if(lookaheadToken != '^' && lookaheadToken != ')' && lookaheadToken != '\n' && lookaheadToken != -1){
				System.out.println(lookaheadToken);
				throw new ParseError();}
			return 1;
		}

		private int last() throws IOException, ParseError{
			System.out.println("last");
			if(lookaheadToken == '('){
				System.out.println("last: consumed: " + (char)lookaheadToken);
				consume('(');
				exp();
				System.out.println("last: consumed: " + (char)lookaheadToken);
				consume(')');			
			}
			else if(lookaheadToken >= '0' && lookaheadToken <= '9'){
				System.out.println("last: consumed: " + (char)lookaheadToken);
				consume(lookaheadToken);
			}
			else
				throw new ParseError();
			return 1;
		}

    public int eval() throws IOException, ParseError {
			int rv = exp();
			if (lookaheadToken != '\n' && lookaheadToken != -1)
				throw new ParseError();
			return rv;
    }

    public static void main(String[] args) {
		try {
			Calculator evaluate = new Calculator(System.in);
			System.out.println(evaluate.eval());
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
		catch(ParseError err){
			//System.err.println(err.getMessage());
			err.printStackTrace();
		}
    }
}