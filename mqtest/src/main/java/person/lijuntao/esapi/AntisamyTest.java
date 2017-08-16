package person.lijuntao.esapi;

import java.io.InputStream;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.junit.Test;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class AntisamyTest {
	private static Policy policy = null;
	public static Policy getPolicy() throws PolicyException{  
        if (policy == null) {  
            InputStream policyFile = AntisamyTest.class.getResourceAsStream("/antisamy.xml");  
            policy = Policy.getInstance(policyFile);  
        }  
        return policy;  
    }  
      
    @Test
    public void test1(){
    	print("<11�ٺ�>");
    	print("<11>22�ٺ�</11>");
    	print("<b>22�ٺ�</b>");
    	print("<script>22�ٺ�</b>");
    	print("<1script>22�ٺ�</b><>");
    	print("<1script>22�ٺ�</b><>;");
    	
    	//����Ӣ��  --ȫ������
    	print("1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM");
    	//Ӣ�������ַ� -- ��Ҫת�����<>&
    	print(",./;'\\[]`-=<>?:\"|{}~!@#$%^&*()_+");
    	//ȫ��Ӣ�ġ���&��
    	print("����������|{}~��@#��%��&*������+");
    	
    	print("<script>1");  //���ؿ��ַ�
    	print("script2");	//����ת�巵�ط���
    	print("<script3");  //���ؿ��ַ�
    	print("<3script3");  //����ת�巵�ط���
    	print("<3<script3");  //���� here:&lt;3
    	print("<3><script3");  //���� &lt;3&gt;
    	print("<3><scriptssag234");  //���� &lt;3&gt;
    	print("<3><scriptssag2>34");  //���� &lt;3&gt;34
    	print("<3><scriptssag2>34>");  //���� &lt;3&gt;34&gt;
    	print("script>4<bb");	//����script&gt;4
    	print("script>4<bb>245zs</bb>");	//����script>4<bb>245zs</bb>
    	print("script>4<b32b");	//����script&gt;4
    	print("script>4<332b");	//����script&gt;4
    	print("<b23423424<sdf5345<123423<2313");	//���ؿ��ַ�
    	/**
    	 * ��ǩ��<��һ��Ӣ����Ϊ����>Ϊֹ�������ȡ�ı�ǩ��������������˵ı�ǩ��Ѹñ�ǩ����
    	 * 
    	 * 
    	 * 
    	 */
    	
    	/**
    	 * ���ᱻ���˵ı�ǩ
    	 */
    	print("<div>1213</div>");	//������ǩ����
    	print("<div>1213<div>");	//��ȫ��ǩ����<div>1213</div>
    	print("<div>1213");	//��ȫ��ǩ����<div>1213</div>
    	print("<div>1213<sd");	//��ȫ��ǩ����,�ҹ�������<div>1213</div>
    	print("<div>1213<div>234");	//��ȫ�����ǩ����<div>1213<div>234</div></div>
    	print("<div");	//��
    	print("</div");	//��
    	print("<div>");	//��
    	print("<div name=''>");	//��
    	print("<div name=''>11");	//<div>11</div>
    	print("<div id=''>11");	//<div>11</div>
    	print("<div id='' name=\"sad\" title=\"12asd\" id=\"12ds3\"   >11</div>");
    	/**
    	 * �����˵ı�ǩ
    	 */
    	
    	print("<!-- div id=''>11");
    	
    	print("div id=''>11 -->");
    	
    	//javascript
    	print("javascript 235435");
    	print("script 235435");
    	print("noscript 235435");
    	print("vbscript 235435");
    	print("eval 235435");
    	print("eval( 235435");
    	print("eval235435");
    	print("evalas235435");
    	
    }
    
    public void print(String value){
    	String clean = AntisamyTest.antiSamyClean(value);
    	int j = 70;
    	StringBuilder builder = new StringBuilder();
    	if(StringUtils.isNotBlank(value)){
    		String input = String.format("����:��%s��", value);
    		String output = String.format("���:��%s��", clean);
    		builder.append(input);
    		for(int i=0;i<j - input.length();i++){
    			builder.append("-");
    		}
    		builder.append(output);
    		
    	}else{
    		builder.append("����Ϊ���ַ�");
    	}
    	System.out.println(builder.toString());
    }
    
    public static String cleanXSS(String value) {
		// log.info("=== beforce cleanXSS: " + value);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		//��Ҫ��<,>,',"���б��룬���ⳤ�ı���ʾ������
		// You'll need to remove the spaces from the html entities below
		/**
		 * ʹ��һ�� Whitelist �������� HTML �ĵ����й���.<br/>
		 * �ο���http://www.oschina.net/question/12_14127 .
		 */
		Whitelist whitelist = Whitelist.simpleText().addTags("br");
		//OutputSettings:falseԭ����������Ի��е������ַ�����
		String clean = Jsoup.clean(value, "", whitelist, new Document.OutputSettings().prettyPrint(false));
		// ֻ���� b, em, i, strong, u,br ��Щ��ǩ 
		String unescapeXml = StringEscapeUtils.unescapeXml(clean);
		// log.info("=== after cleanXSS: " + value);
		//return value;
		return unescapeXml;
	}
    
    public static String antiSamyClean(String value) {  
        if (StringUtils.isNotEmpty(value)) {  
            AntiSamy antiSamy = new AntiSamy();  
            try {  
                final CleanResults cr = antiSamy.scan(value, getPolicy());  
                //��ȫ��HTML���  
                value = cr.getCleanHTML();  
            } catch (ScanException e) {  
                e.printStackTrace();  
            } catch (PolicyException e) {  
                e.printStackTrace();  
            }  
        }  
        return value;  
    }
}
