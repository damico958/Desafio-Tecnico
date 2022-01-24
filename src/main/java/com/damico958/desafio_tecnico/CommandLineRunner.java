package com.damico958.desafio_tecnico;

import com.damico958.desafio_tecnico.util.DirectoryWalker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
    @Value("${input.directory.path}")
    private String INPUT_DIRECTORY_PATH;

    @Value("${output.directory.path}")
    private String OUTPUT_DIRECTORY_PATH;

    private final DirectoryWalker directoryWalker;

    public CommandLineRunner(DirectoryWalker directoryWalker) {
        this.directoryWalker = directoryWalker;
    }

    @Override
    public void run(String... args) {
        directoryWalker.walkAllFiles(INPUT_DIRECTORY_PATH, OUTPUT_DIRECTORY_PATH);
        directoryWalker.runServiceWatcher(INPUT_DIRECTORY_PATH, OUTPUT_DIRECTORY_PATH);
    }
}
