package bdd.utility;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelReader extends GeneralUtils{

	private Fillo fillo;
	private Recordset data;
	private Connection connection;
	private String testdatapath=System.getProperty("user.dir")+"\\BDD\\src\\main\\resources\\TestData\\";
	private String testdatapath1="\\TestData.xlsx";
	
	public ExcelReader() {
		fillo = new Fillo();
	}
	
	
	public Recordset getExcelData(String airline ,String sheet,String value) {
		try {
			connection=fillo.getConnection(testdatapath+airline+testdatapath1);
			data=connection.executeQuery("Select * from "+sheet+"where TD='"+value+"'");
			data.moveFirst();
			return data;
		}catch(Exception e) {
			printlog("Excel not found at :: "+testdatapath+airline,false,false,true);
		}
		return null;
		
	}
}
