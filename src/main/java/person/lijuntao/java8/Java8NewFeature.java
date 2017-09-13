package person.lijuntao.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class Java8NewFeature {
	@Test
	public void test1(){
		List<String> list = new ArrayList<String>();
		list.add("asfaqfasf");
		list.add("drufgh");
		list.add("ghfghdrt");
		Collections.sort(list, (a, b) -> b.compareTo(a));
		
		print(list, (a, b) -> {return a+b;});
		System.out.println(Arrays.toString(list.toArray()));
	}
	
	public void print(List<String> list, IFunctionalStyleTest i){
		String s1 = "";
		int j = 0;
		for(String s : list){
			s1 = i.concat(s1, s);
			list.set(j++, s1);
		}
	}
	
	@FunctionalInterface
	public interface IFunctionalStyleTest {
		String concat(String a, String b);
	}
}
