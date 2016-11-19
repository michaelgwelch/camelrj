package welch.subscriber;

import java.io.UnsupportedEncodingException;

public class BytesToStringBean {

	public String process(byte[] bytes) {
		String message = "hi";
		try {
			message = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
}
