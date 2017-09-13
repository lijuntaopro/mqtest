package mqtest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class Test3 {
	public static void main(String[] args) {
		Integer i = null;
		System.out.println(i == new Integer(2));
		List<?> list = null;
		for(Object o : list){
			System.out.println("sss");
		}
	}
	
	@Test
	public void test(){
		System.out.println(System.currentTimeMillis());
		System.out.println(new Date().getTime());
	}
	
	@Test
	public void test2(){
		try {
			System.out.println(new SimpleDateFormat("HH:mm:ss").parse("08:00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
