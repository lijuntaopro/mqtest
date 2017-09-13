package person.lijuntao.esapi;

import java.util.regex.Pattern;

import org.junit.Test;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.errors.EncodingException;
import org.owasp.esapi.errors.IntrusionException;
import org.owasp.esapi.errors.ValidationException;

public class EsapiTest {
	@Test
	public void test(){
		String oo = "0\" onmouseover=alert(123)  bad=\"";
		String bb = "&gt;<script>alert(\"asdad\")</script>";
		String string = ESAPI.encoder().encodeForCSS(oo);
		String string2 = ESAPI.encoder().encodeForHTML(oo);
		String string3 = ESAPI.encoder().encodeForHTMLAttribute(oo);
		String string4 = ESAPI.encoder().encodeForJavaScript(oo);
		//�ύǰ���ݿ��ܾ������룬�Ȱ� �����һ�������ַ��� ��������빥��
		System.out.println(ESAPI.encoder().canonicalize(bb, true));
		String string5 = null;
		try {
			string5 = ESAPI.encoder().encodeForURL(oo);
		} catch (EncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\n\n\n");
		System.out.println(string);
		System.out.println(string2);
		System.out.println(string3);
		System.out.println(string4);
		System.out.println(string5);
	}
	
	@Test
	public void test2(){
		//String stripXSS = stripXSS("<script type=\"\"> 4234234adl</script>");
		String stripXSS = stripXSS("<script> 4234234adl</script>");
		System.out.println(stripXSS);
	}
	@Test
	public void test3(){
		System.setProperty("org.owasp.esapi.resources", "src/main/resources");
		String stripXSS = "<script> 4234234adl</script>";
		stripXSS = "<b4234234adl>>>11���";
		stripXSS = "<b>>>11���";
		String validSafeHTML = null;
		try {
			//תΪ��ʹ�õı�ǩ
			validSafeHTML = ESAPI.validator().getValidSafeHTML("", stripXSS, 1000000000, true);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrusionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("here:" + validSafeHTML);
	}
	
	public String stripXSS(String value) {  
        if (value != null) {  
            // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to  
            // avoid encoded attacks.  
            value = ESAPI.encoder().canonicalize(value);//ע�⣺��ǰ��ʹ��get��ʽ�ύ����encodeURI�����ģ��˴�������  
  
            // Avoid null characters  
            value = value.replaceAll("", "");  
  
            // Avoid anything between script tags  
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);  
            value = scriptPattern.matcher(value).replaceAll("");  
  
            // Avoid anything in a src='...' type of expression  
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
            value = scriptPattern.matcher(value).replaceAll("");  
  
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
            value = scriptPattern.matcher(value).replaceAll("");  
  
            // Remove any lonesome </script> tag  
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);  
            value = scriptPattern.matcher(value).replaceAll("");  
  
            // Remove any lonesome <script ...> tag  
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
            value = scriptPattern.matcher(value).replaceAll("");  
  
            // Avoid eval(...) expressions  
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
            value = scriptPattern.matcher(value).replaceAll("");  
  
            // Avoid expression(...) expressions  
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
            value = scriptPattern.matcher(value).replaceAll("");  
  
            // Avoid javascript:... expressions  
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);  
            value = scriptPattern.matcher(value).replaceAll("");  
  
            // Avoid vbscript:... expressions  
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);  
            value = scriptPattern.matcher(value).replaceAll("");  
  
            // Avoid onload= expressions  
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);  
            value = scriptPattern.matcher(value).replaceAll("");  
        }  
        return value;  
    }  
}
