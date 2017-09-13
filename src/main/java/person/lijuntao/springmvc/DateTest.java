package person.lijuntao.springmvc;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class DateTest {
	 //{"pageNum":1,"pageSize":10,"total":5,"totalPage":1,"list":[{"completeTime":1498024098060,"id":"20170621111760000010348840867033","amount":0.450,"payType":1,"payerName":"","receiptAmount":0.450},{"completeTime":1497965148053,"id":"20170620111760000010348851198217","amount":1.000,"payType":1,"payerName":"","receiptAmount":1.000},{"completeTime":1497963081030,"id":"20170620111760000010348830706841","amount":5.550,"payType":1,"payerName":"六五付款人二十一","receiptAmount":5.550},{"completeTime":1497962988087,"id":"20170620111760000010348829763131","amount":1000.000,"payType":1,"payerName":"","receiptAmount":1000.000},{"completeTime":1497962406067,"id":"20170620111760000010348823927224","amount":1.000,"payType":1,"payerName":"","receiptAmount":1.000}]}
    @Test
    public void test22(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
    	System.out.println( "20170621111760000010348840867033:"  +  format.format(new Date(1498024098060L)));
    }
}
