package welch.subscriber;

public class RabbitEnvelopeToContentImageBean {

	public ContentImage process(RabbitEnvelope envelope) {
		return envelope.message;
	}
}
