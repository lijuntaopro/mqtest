package person.lijuntao.springmvc;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class MappingJackson2HttpMessageConverterEscapeXss extends MappingJackson2HttpMessageConverter{


	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		System.out.println(type.toString() + type.getClass() + type.getTypeName() + contextClass);
		return super.read(type, contextClass, inputMessage);
	}



}
