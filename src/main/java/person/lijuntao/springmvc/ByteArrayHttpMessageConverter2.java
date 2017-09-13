package person.lijuntao.springmvc;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class ByteArrayHttpMessageConverter2 extends ByteArrayHttpMessageConverter{

	@Override
	public byte[] readInternal(Class<? extends byte[]> clazz, HttpInputMessage inputMessage) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("ByteArrayHttpMessageConverter2"+clazz.getName());
		return super.readInternal(clazz, inputMessage);
	}



}
