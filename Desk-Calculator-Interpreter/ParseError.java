public class ParseError extends Exception {

    public String getMessage() {
	    return "\u001B[31m" + "\033[1mparse error\033[0m "+ "\u001B[0m"; // return parser error in RED, then reset colour
    }
}