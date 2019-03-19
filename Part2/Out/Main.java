public class Main{
	public static void main(String[] args){
		System.out.println(cond_repeat("n", "os"));
		System.out.println(cond_repeat("nos", "ir"));
		System.out.println(cond_repeat("yes", "Tarzan"));
		System.out.println(cond_repeat("fourth", "4times"));
	}
	public static String cond_repeat(String c, String x){
		return (repeat("no" + "sir") + c).startsWith(c + x)?(c).startsWith("no")?repeat(x):x + "x in quotes":(x + "yessir").endsWith(("yessir").startsWith(c)?"yessir":"nosir")?x:repeat(repeat(x));
	}
	public static String repeat(String x){
		return x + x;
	}

}
