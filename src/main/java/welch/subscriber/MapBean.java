package welch.subscriber;

@FunctionalInterface
public interface MapBean<TIn, TOut> {
	public TOut process(TIn in);
}
