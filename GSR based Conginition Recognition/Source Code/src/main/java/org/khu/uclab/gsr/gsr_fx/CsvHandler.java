/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.khu.uclab.gsr.gsr_fx;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Fahad Ahmed Satti
 */
public class CsvHandler {
    private final static Logger LOG =  LoggerFactory.getLogger(GsrService.class); 
    private File targetDataFile;
    CSVParser parser;
    private boolean isFirstLine;
    CSVWriter writer;

    public CsvHandler(String fileName) {
        try {
            Path fp = Paths.get(fileName);
            if(fp.getParent()!=null){
                Files.createDirectories(fp.getParent());
            }
            fp = Files.createFile(fp);
            LOG.debug("CSV File Path:"+fp.toAbsolutePath());
            targetDataFile = fp.toFile();
            parser = new CSVParserBuilder().build();
            writer = new CSVWriter(new FileWriter(targetDataFile, true));
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }
    }

    /**
     * stores a list of String[] as a comma separated Header line in the target
     * file.
     *
     * @param headerDataArray
     */
    public void writeHeaderData(String[] headerDataArray) {
        if (isFirstLine) {
            //org.springframework.util.StringUtils.arrayToCommaDelimitedString(String[] array)
            writer.writeNext(headerDataArray);
            isFirstLine = false;
        } else {
            LOG.debug("Header already present. Skipping this line.");
        }
    }

    /**
     * stores a list of String[] as a comma separated line in the target file.
     *
     * @param data
     */
    public void writeData(String[] data) {
        if (isFirstLine) {
            writeHeaderData(data);
        }else{
            writer.writeNext(data);
        }

    }

    public File getTargetDataFile() {
        return targetDataFile;
    }

    public void setTargetDataFile(File targetDataFile) {
        this.targetDataFile = targetDataFile;
    }
    
    public void closeWriter(){
        try {
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }
    }

}
