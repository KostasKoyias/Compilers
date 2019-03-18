public class Main{
	public static void main(String[] args){
		System.out.println(findLangType("Typescript"));
		System.out.println(findLangType("Javascript"));
		System.out.println(findLangType("Java"));
	}
	public static String findLangType(String langName){
		if(langName.startsWith("Java"))
				if("Java".startsWith(langName))
				return "Static";
		else
				if(langName.endsWith("script"))
				return "Dynamic";
		else
				return "Unknown";
		else
				if(langName.endsWith("script"))
				return "Probably Dynamic";
		else
				return "Unknown" ;
	}

}
