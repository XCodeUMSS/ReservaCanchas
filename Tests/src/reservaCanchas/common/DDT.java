/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reservaCanchas.common;

import au.com.bytecode.opencsv.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @date 24/08/2015
 * @author Khrono
 */
public class DDT {
    private static CSVReader csvReader;
	
	private final static Logger logger = Logger.getRootLogger();

	public static Object[][] DDTReaderFull(String csvPath) {
        List<Object[]> resp = new ArrayList<>();
        try {
            FileReader fr = new FileReader(csvPath);
            csvReader = new CSVReader(fr);
            List<String[]> complete = csvReader.readAll();
            complete.remove(0);
            for( String[] row : complete){
                if(!row[0].startsWith("//"))
                    resp.add(row);
            }
            Object[][] arrayResp = new Object[resp.size()][];
            for(int i = 0; i < resp.size(); i++){
                arrayResp[i] = resp.get(i);
            }
            return arrayResp;
        } catch (FileNotFoundException ex) {
        	logger.error(ex);
        } catch (IOException ex) {
        	logger.error(ex);
        }
        return new Object[][]{};
    }
}
