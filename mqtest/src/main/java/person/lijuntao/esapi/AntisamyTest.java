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
    	print("<11嘿嘿>");
    	print("<11>22嘿嘿</11>");
    	print("<b>22嘿嘿</b>");
    	print("<script>22嘿嘿</b>");
    	print("<1script>22嘿嘿</b><>");
    	print("<1script>22嘿嘿</b><>;");
    	
    	//正常英文  --全部正常
    	print("1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM");
    	//英文特殊字符 -- 需要转义的是<>&
    	print(",./;'\\[]`-=<>?:\"|{}~!@#$%^&*()_+");
    	//全角英文“…&—
    	print("《》？：“|{}~！@#￥%…&*（）—+");
    	
    	print("<script>1");  //返回空字符
    	print("script2");	//正常转义返回返回
    	print("<script3");  //返回空字符
    	print("<3script3");  //正常转义返回返回
    	print("<3<script3");  //返回 here:&lt;3
    	print("<3><script3");  //返回 &lt;3&gt;
    	print("<3><scriptssag234");  //返回 &lt;3&gt;
    	print("<3><scriptssag2>34");  //返回 &lt;3&gt;34
    	print("<3><scriptssag2>34>");  //返回 &lt;3&gt;34&gt;
    	print("script>4<bb");	//返回script&gt;4
    	print("script>4<bb>245zs</bb>");	//返回script>4<bb>245zs</bb>
    	print("script>4<b32b");	//返回script&gt;4
    	print("script>4<332b");	//返回script&gt;4
    	print("<b23423424<sdf5345<123423<2313");	//返回空字符
    	/**
    	 * 标签以<和一个英文名为起，以>为止，如果获取的标签名不符合无需过滤的标签这把该标签过滤
    	 * 
    	 * 
    	 * 
    	 */
    	
    	/**
    	 * 不会被过滤的标签
    	 */
    	print("<div>1213</div>");	//正常标签返回
    	print("<div>1213<div>");	//补全标签返回<div>1213</div>
    	print("<div>1213");	//补全标签返回<div>1213</div>
    	print("<div>1213<sd");	//补全标签返回,且过滤数据<div>1213</div>
    	print("<div>1213<div>234");	//补全多个标签返回<div>1213<div>234</div></div>
    	print("<div");	//空
    	print("</div");	//空
    	print("<div>");	//空
    	print("<div name=''>");	//空
    	print("<div name=''>11");	//<div>11</div>
    	print("<div id=''>11");	//<div>11</div>
    	print("<div id='' name=\"sad\" title=\"12asd\" id=\"12ds3\"   >11</div>");
    	/**
    	 * 被过滤的标签
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
    		String input = String.format("输入:【%s】", value);
    		String output = String.format("输出:【%s】", clean);
    		builder.append(input);
    		for(int i=0;i<j - input.length();i++){
    			builder.append("-");
    		}
    		builder.append(output);
    		
    	}else{
    		builder.append("输入为空字符");
    	}
    	System.out.println(builder.toString());
    }
    
    public static String cleanXSS(String value) {
		// log.info("=== beforce cleanXSS: " + value);
		if (StringUtils.isBlank(value)) {
			return null;
		}
		//不要对<,>,',"进行编码，避免长文本显示出问题
		// You'll need to remove the spaces from the html entities below
		/**
		 * 使用一个 Whitelist 类用来对 HTML 文档进行过滤.<br/>
		 * 参考：http://www.oschina.net/question/12_14127 .
		 */
		Whitelist whitelist = Whitelist.simpleText().addTags("br");
		//OutputSettings:false原样输出，不对换行等特殊字符处理
		String clean = Jsoup.clean(value, "", whitelist, new Document.OutputSettings().prettyPrint(false));
		// 只允许 b, em, i, strong, u,br 这些标签 
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
                //安全的HTML输出  
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
