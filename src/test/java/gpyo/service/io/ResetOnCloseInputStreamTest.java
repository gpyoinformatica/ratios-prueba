package gpyo.service.io;

import org.hibernate.PropertyValueException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

import java.sql.Timestamp;
import java.util.*;

public class ResetOnCloseInputStreamTest {

/*	
	@Test
	static void closeAfterInputStreamIsConsumed(InputStream is)
	        throws IOException {
	    int r;
	
	    while ((r = is.read()) != -1) {
	        System.out.println(r);
	    }
	
	    is.close();
	    System.out.println("=========");
	
	}
	@Test
	public static void main(String[] args) throws IOException {
	    InputStream is = new ByteArrayInputStream("sample".getBytes());
	    ResetOnCloseInputStream decoratedIs = new ResetOnCloseInputStream(is);
	    closeAfterInputStreamIsConsumed(decoratedIs);
	    closeAfterInputStreamIsConsumed(decoratedIs);
	    closeAfterInputStreamIsConsumed(is);
	}
*/
}