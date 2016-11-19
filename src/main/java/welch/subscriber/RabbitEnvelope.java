package welch.subscriber;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RabbitEnvelope {
	public ContentImage message;
	
	@Override
	public String toString() {
		return " message: " + message.toString();
	}
}
/*
{
"destinationAddress": "rabbitmq://localhost/images/BusDepotTest2:ContentImage",
"headers": {},
"message": {
  "imageUrl": "myurl",
  "caption": "my caption",
  "system": "mysystem",
  "identifier": "3"
},
"messageType": [
  "urn:message:BusDepotTest2:ContentImage"
],
"sourceAddress": "rabbitmq://localhost/images/completed_images"
}
*/