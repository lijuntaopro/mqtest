<%@ page language="java" import="java.util.*,org.owasp.esapi.ESAPI" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'esapitest.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" language="JavaScript" src="<%=basePath %>/js/jquery.min.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=basePath %>/esapi4js/esapi.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=basePath %>/esapi4js/lib/log4js.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=basePath %>/esapi4js/resources/i18n/ESAPI_Standard_en_US.properties.js"></script>
	<script type="text/javascript" language="JavaScript" src="<%=basePath %>/esapi4js/resources/Base.esapi.properties.js"></script>
  	<script type="text/javascript" language="JavaScript">
	    // Set any custom configuration options here or in an external js file that gets sourced in above.
	    Base.esapi.properties.logging['ApplicationLogger'] = {
	        Level: org.owasp.esapi.Logger.ALL,
	        Appenders: [ new Log4js.ConsoleAppender() ],
	        LogUrl: true,
	        LogApplicationName: true,
	        EncodingRequired: true
	    };
	
	    Base.esapi.properties.application.Name = "My Application v1.0";
	
	    // Initialize the api
	    org.owasp.esapi.ESAPI.initialize();
	
	    // Using the logger
	    //$ESAPI.logger().getLogger('ApplicationLogger').info(org.owasp.esapi.Logger.EventType.EVENT_SUCCESS, 'This is a test message');
	
	    // Using the encoder
	    document.writeln( $ESAPI.encoder().encodeForHTML( "<a href=\"http://owasp-esapi-js.googlecode.com\">Check out esapi4js</a>" ) );
	
	    // Using the validator
	    var validateCreditCard = function() {
	        return $ESAPI.validator().isValidCreditCard( $('CreditCard').value );
	    }
	</script>
  </head>
  
  <body>
    </br>
    <%String searchBox = "<script>alert(“this is jiayzhan”)</script>";%>
    <%searchBox = "a');alert('hacked by jiayzhan";%>
  	<input type="button" name="letusgo" value="测试多重转义" onclick="alert('<%= ESAPI.encoder().encodeForHTML(searchBox)%>')" />
  	<input type="button" name="letusgo" value="测试多重转义2" onclick="alert('<%= ESAPI.encoder().encodeForHTML(ESAPI.encoder().encodeForJavaScript(searchBox))%>')" />
  	</br>
          输入<input type="text" id="shuru"/><button onclick="escape22()">转义</button><button onclick="notEscape()">不转义</button>
    <p id='show'>
    
    </p>
    <%String str = ESAPI.encoder().encodeForHTML("hhaa\" onfocus=\"javascript:alert('haer')\""); %>
    <input type="text" value="<%=str %>"/>
    <script type="text/javascript">
    	function notEscape(){
	    	$('#show').append($('#shuru').val() + "<br>");
    	}
    	function escape22(){
	    	$('#show').append($ESAPI.encoder().encodeForCSS($('#shuru').val()) + "<br>");
	    	$('#show').append($ESAPI.encoder().encodeForHTML($('#shuru').val()) + "<br>");
	    	$('#show').append($ESAPI.encoder().encodeForHTMLAttribute($('#shuru').val()) + "<br>");
	    	$('#show').append($ESAPI.encoder().encodeForJavaScript($('#shuru').val()) + "<br>");
	    	$('#show').append($ESAPI.encoder().encodeForURL($('#shuru').val()) + "<br>");
    	}
    	
    </script>
  </body>
  
</html>
