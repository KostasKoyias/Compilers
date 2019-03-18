public class Main{
	public static void main(String[] args){
		System.out.println(fullname(name(), " ", surname()));
		System.out.println(name());
	}
	public static String fullname(String first_name, String sep, String last_name){
		return foo(name()) + "got the name" + sep + first_name + " from his dad" ;
	}
	public static String foo(String some_name){
		return some_name ;
	}
	public static String surname(){
		return "Doe" ;
	}
	public static String name(){
		return "John" ;
	}

}
