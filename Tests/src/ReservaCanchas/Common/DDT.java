/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ReservaCanchas.common;

import au.com.bytecode.opencsv.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @date 9/2/2013
 * @author Walter Ramirez
 */
/*
 * 9/20/2013 - Added support to use more than 1 filter from the configuration file
 */
public class DDT {
    private static CSVReader csvReader;
	/*
	 * Initializing a logger instance using the root logger
	 */
	private final static Logger logger = Logger.getRootLogger();
    /**
     * Reads a specific CSV file and return just the lines that meets with the filter
     * @param csvPath Path of the CSV file that you need to read
     * @param filter A filter String defined in the first column of the csv File
     * @return
     */
	public static Object[][] DDTReader(String csvPath, String filter) {
        List<Object[]> resp = new ArrayList<>();
        try {
            FileReader fr = new FileReader(csvPath);
            csvReader = new CSVReader(fr);
            List<String[]> complete = csvReader.readAll();
            complete.remove(0);
            for( String[] row : complete){
            	String[] filterVal = filter.split(",");
            	for (String val : filterVal){
            		if(row[0].equals(val)){
                    	resp.add(row);
                    }            		
            	}
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

    /**
     * Reads a specific CSV file and return just the lines that meets with the filter
     * @param csvPath Path of the CSV file that you need to read
     * @param filterA A filter String defined in the first column of the csv File
     * @param filterB A filter String defined in the second column of the csv File
     * @return
     */
	public static Object[][] DDTReader(String csvPath, String filterA, String filterB) {
        List<Object[]> resp = new ArrayList<>();
        try {
            FileReader fr = new FileReader(csvPath);
            csvReader = new CSVReader(fr);
            List<String[]> complete = csvReader.readAll();
            complete.remove(0);
            for( String[] row : complete){
            	String[] filterAVal = filterA.split(",");
            	for (String valA : filterAVal){
            		if(row[0].equals(valA)){
                    	String[] filterBVal = filterB.split(",");
                    	for (String valB : filterBVal){
                    		if(row[1].equals(valB)){
                            	resp.add(row);
                            }
                    	}
                    }
            	}
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

    /**
     * Reads a specific CSV file and return just the lines that meets with the filter
     * @param csvPath Path of the CSV file that you need to read
     * @param filterA A filter String defined in the first column of the csv File
     * @param filterB A filter String defined in the second column of the csv File
     * @param filterC A filter String defined in the third column of the csv File
     * @return
     */
	public static Object[][] DDTReader(String csvPath, String filterA, String filterB, String filterC) {
        List<Object[]> resp = new ArrayList<>();
        try {
            FileReader fr = new FileReader(csvPath);
            csvReader = new CSVReader(fr);
            List<String[]> complete = csvReader.readAll();
            complete.remove(0);
            for( String[] row : complete){
            	String[] filterAVal = filterA.split(",");
            	for (String valA :filterAVal){
            		if(row[0].equals(valA)){
                    	String[] filterBVal = filterB.split(",");
                    	for (String valB : filterBVal){
                    		if(row[1].equals(valB)){
                    			String[] filterCVal = filterC.split(",");
                            	for (String valC : filterCVal){
                            		if(row[2].equals(valC)){
                                    	resp.add(row);
                                    }
                            	}
                            }
                    	}
                    }
            	}
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

	/**
	 * Return all the lines content in a CSV File
	 * @param csvPath Path of the CSV file that you need read
	 * @return
	 */
	public static Object[][] DDTReaderFull(String csvPath) {
        List<Object[]> resp = new ArrayList<>();
        try {
            FileReader fr = new FileReader(csvPath);
            csvReader = new CSVReader(fr);
            List<String[]> complete = csvReader.readAll();
            complete.remove(0);
            for( String[] row : complete){
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
