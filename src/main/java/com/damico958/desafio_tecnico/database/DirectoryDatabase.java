package com.damico958.desafio_tecnico.database;

import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class DirectoryDatabase {
    private static final String JSON_FILES_FILE_PATH = "src/main/resources/Files.json";

    static Gson googleJson = new GsonBuilder().setPrettyPrinting().create();

    public boolean saveDirectoryDatabase(Map<String, String> hashesMap){
        try(BufferedWriter fileWriter = Files.newBufferedWriter(Paths.get(JSON_FILES_FILE_PATH))){
            fileWriter.write(googleJson.toJson(hashesMap));
        } catch (Exception e) {
            throw new InvalidBehaviorException("Fail! Could not save hashes Map as a json file!", e);
        }
        return true;
    }

    public Map<String, String> getDirectoryDatabase(){
        Map<String, String> directoryDatabase;
        try(Reader fileReader = Files.newBufferedReader(Paths.get(JSON_FILES_FILE_PATH))){
            Gson googleJson = new Gson();
            directoryDatabase = (googleJson.fromJson(fileReader, new TypeToken<HashMap<String, String>>(){}.getType()));
        } catch (Exception ex){
            throw new InvalidBehaviorException("Fail! Could not retrieve hashes Map from json file!", ex);
        }
        return directoryDatabase;
    }
}
