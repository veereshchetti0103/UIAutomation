package bdd.utility;

public class FileReaderManager {
	
	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static ConfigFileReader configFileReader;
	private static LocatorsReader locators;
	private static ExcelReader excelReader;
	
	
	private FileReaderManager() {}
	
	
	public static FileReaderManager getInstance() {
		return fileReaderManager;
	}
	
	public ConfigFileReader getConfigReader() {
		return (configFileReader==null) ? new ConfigFileReader():configFileReader;
	}

	public LocatorsReader getLocatorsReader() {
		return (locators==null) ? new LocatorsReader():locators;
	}
	public ExcelReader getExcelReader() {
		return (excelReader==null) ? new ExcelReader():excelReader;
	}
}
