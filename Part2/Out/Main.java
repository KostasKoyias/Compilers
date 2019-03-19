java -cp ../java-cup-11b-runtime.jar:. Main
public class Main{
	public static void main(String[] args){
		System.out.println(cond_repeat("n", "os"));
		System.out.println(cond_repeat("nos", "ir"));
		System.out.println(cond_repeat("yes", "Tarzan"));
		System.out.println(cond_repeat("fourth", "4times"));
	}
	public static String cond_repeat(String c, String x){
		if((repeat("no" + "sir") + c).startsWith(c + x))
			if((c).startsWith("no"))
				return repeat(x);
			else
				return x + "x in quotes";
		else
			if((x + "yessir").endsWith(if(("yessir").startsWith(c))
				return "yessir";
			else
				return "nosir"))
				return x;
			else
				return repeat(repeat(x));
	}
	public static String repeat(String x){
		return x + x;
	}

}
