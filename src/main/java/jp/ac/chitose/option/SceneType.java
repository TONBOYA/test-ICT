package jp.ac.chitose.option;

public enum SceneType {
	IMAGE,
	MOVIE,
	USER;
	private SceneType TYPE;
	
	public SceneType getTYPE(){
		return TYPE;
	}
	
	public void setTYPE(SceneType TYPE){
		this.TYPE = TYPE;
	}
	
	public static final SceneType of(String str){
		switch (str) {
		case "image":
			
			return SceneType.IMAGE;
		
		case "movie":
			
			return SceneType.MOVIE;
			
		default:
			
			return SceneType.USER;

		}
	}
}
