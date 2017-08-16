package person.lijuntao.test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Demo {
	@org.junit.Test
	public void Test(){
		DecimalFormat format = new DecimalFormat("################.#######");
		String string = format.format(new BigDecimal("11111111.100000"));
		System.out.println(new BigDecimal(string));
	}
}
