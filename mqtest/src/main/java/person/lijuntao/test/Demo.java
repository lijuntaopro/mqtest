package person.lijuntao.test;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.Test;

public class Demo {
	@Test
	public void test(){
		DecimalFormat format = new DecimalFormat("################.#######");
		String string = format.format(new BigDecimal("11111111.100000"));
		System.out.println(new BigDecimal(string));
	}
	@Test
	public void test2(){
		ArrayList list = new ArrayList();
		list.add(new Object());
		list.remove(0);
		System.out.println(list.size());
		
	}
	@Test
	public <T> void test3(){
		A a = new A();
		B object = a.<B>get(null);
		/*A a = new A();
		a.setI(123);
		a.setName("131313");
		B b = new B();
		try {
			BeanUtils.copyProperties(b, a);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(a.getI());
		System.out.println(a.getName());
		System.out.println(b.getI());
		System.out.println(b.getName());
		b.print();*/
	}
	@Test
	public void test4(){
		A a = new A();
		a.i = 123;
		a.name = "131313aa";
		B b = new B();
		try {
			//BeanUtils.copyProperties(b, a);
			PropertyUtilsBean bean = new PropertyUtilsBean();
			bean.copyProperties(b, a);
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(a.i);
		System.out.println(a.name);
		System.out.println(b.i);
		System.out.println(b.name);
		//System.out.println(b.name == a.name);
	}
}
