package com.damico958.desafio_tecnico.util;

import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FileSerializer {
    @Autowired
    private FileReportService fileReportService;

    public void writeFile(String outputPath) {
        File outputFile = new File(outputPath);
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile))) {
            String inputPath = outputPath.replace("/out/","/in/").replace(".done.", ".");
            bufferedWriter.write("Input path: " + inputPath);
            bufferedWriter.newLine();
            bufferedWriter.write(fileReportService.retrieveReportData().toString());
        } catch (FileNotFoundException exception) {
            throw new InvalidBehaviorException("Fail! Could not open file! Root Exception: ", exception);
        } catch (IOException exception) {
            throw new InvalidBehaviorException("Fail! Could not write on file! Root Exception: ", exception);
        }

    }
}
