package welch.subscriber;

public class ContentImage {
	public String imageUrl;
	public String caption;
	public String identifier;
	public String system;
	public String productNum;
	
	@Override
	public String toString() {
		return String.format("{'%s', '%s', '%s', '%s', '%s'}", productNum,
				imageUrl, caption, identifier, system);
	}
}
/*
public class ContentImage
{
	public string ImageUrl { get; set; }
	public string Caption { get; set; }
	public string System { get; set; }
	public string Identifier { get; set; }
}
*/