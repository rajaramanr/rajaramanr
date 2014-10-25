import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {

	public void maskPassword(String msg){
		Pattern pattern = Pattern.compile("password=.*");
		Matcher	matcher = pattern.matcher(msg);
		StringBuffer sb = new StringBuffer();
		sb.append("password=.*");
		Matcher str = matcher.appendReplacement(sb, "password=\"raja\"");
		System.out.println(str);
	}
	
	
	public static void main(String[] args) {
		String s ="<distribution><STDID copyProtected=\"true\" password=\"hdhd\" passwordProtect=\"Password\" regenerateTokenCode=\"true\"/></distribution>";
		Test t = new Test();
		t.maskPassword(s);
	}

}
