package loginInfo;

public enum Language {
	KR("config_kr.xml"),
	EN("config_en.xml"),
	JP("config_jp.xml");
	private final String configFile;
	Language(String configFile){
		this.configFile = configFile;
	}
	public String getConfigFile() {
		return configFile;
	}
}
