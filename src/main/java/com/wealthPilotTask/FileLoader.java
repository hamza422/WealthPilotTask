package com.wealthPilotTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

public class FileLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileLoader.class);
    private  String fileName;
    private File jsonFile;
    private ResourceLoader resourceLoader;

    FileLoader(String fileName, ResourceLoader resourceLoader){
        this.fileName=fileName;
        this.resourceLoader=resourceLoader;
        init();
    }

     void init() {
        try {
            LOGGER.info("FileLoader: Trying to load Json File");

            Resource resource = resourceLoader.getResource(fileName);
            jsonFile = resource.getFile();

            LOGGER.info("FileLoader: Json file loaded successfully.");

        } catch (IOException | NullPointerException e) {
            LOGGER.info("json file not open "+ e.getMessage());
        }
    }

    public WealthPilotLeague loadJsonFile(){
        if (jsonFile ==null){
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        WealthPilotLeague wealthPilotLeague=  null;


        try {
            wealthPilotLeague = mapper.readValue(jsonFile, WealthPilotLeague.class);
        } catch (IOException e) {
            LOGGER.info("Object not converted ");
        }

        return wealthPilotLeague;
    }

}
