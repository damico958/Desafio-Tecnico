package com.damico958.desafio_tecnico.util;

import com.damico958.desafio_tecnico.exceptions.InvalidBehaviorException;
import com.damico958.desafio_tecnico.service.DirectoryService;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.damico958.desafio_tecnico.util.FileDeserializer.*;
import static java.nio.file.StandardWatchEventKinds.*;

@Component
public class DirectoryWalker {
    private final FileDeserializer fileDeserializer;
    private final FileSerializer fileSerializer;
    private final DirectoryService directoryService;

    public DirectoryWalker(FileDeserializer fileDeserializer, FileSerializer fileSerializer, DirectoryService directoryService) {
        this.fileDeserializer = fileDeserializer;
        this.fileSerializer = fileSerializer;
        this.directoryService = directoryService;
    }

    public void walkAllFiles(String inputDirectoryPath, String outputDirectoryPath) {
        try(Stream<Path> paths = Files.walk(Paths.get(inputDirectoryPath))) {
            List<String> result = paths
                    .filter(Files::isRegularFile)
                    .filter(path -> !Files.isDirectory(path))
                    .map(path -> path.toString().toLowerCase())
                    .filter(file -> file.endsWith(".dat"))
                    .collect(Collectors.toList());

            directoryService.getHashes();

            System.out.println(ANSI_YELLOW_BOLD + "\nReading all .dat files...\n" + ANSI_RESET);

            for (String pathFound : result) {
                System.out.println(ANSI_YELLOW_BOLD + pathFound + ANSI_RESET);
            }

            for (String path : result) {
                System.out.println(ANSI_RED_BOLD + "\nPath received: " + path);
                File datFile = new File(path);
                try {
                    HashCode hashCode = HashCode.fromBytes(com.google.common.io.Files.asByteSource(datFile).hash(Hashing.sha256()).asBytes());
                    System.out.println("Hash generated from file: " + hashCode);
                    if (directoryService.checkIsNewHash(path, hashCode)) {
                        fileDeserializer.deserialize(datFile);
                        String newPath = path.replace(".dat", ".done.dat").replace(inputDirectoryPath, outputDirectoryPath);
                        System.out.println("Path given: " + newPath + ANSI_RESET);
                        fileSerializer.writeFile(newPath);
                    }
                } catch (IOException exception) {
                    throw new InvalidBehaviorException("Fail! Could not generate hash from this file! Cause of exception: ", exception);
                }
            }

        } catch (IOException exception) {
            throw new InvalidBehaviorException("Fail! Could not manage all files in the directory! Root Exception: ", exception);
        }

    }

    public void runServiceWatcher(String inputDirectoryPath, String outputDirectoryPath) {
        System.out.println(ANSI_YELLOW_BOLD + "\nChanging to Event Watcher...");
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            Path path = Paths.get(inputDirectoryPath);

            path.register(
                    watchService,
                    ENTRY_CREATE,
                    ENTRY_DELETE,
                    ENTRY_MODIFY);

            WatchKey watchKey;
            while ((watchKey = watchService.take()) != null) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    String contextString = event.context().toString();
                    if(!contextString.contains(".goutputstream")) {
                        System.out.println(ANSI_YELLOW_BOLD + "Event kind: " + event.kind() +
                                ". File: " + event.context() + ".");
                        walkAllFiles(inputDirectoryPath, outputDirectoryPath);
                        System.out.println(ANSI_YELLOW_BOLD + "\nBack to Event Watcher...");
                    }
                }
                watchKey.reset();
            }
        } catch (IOException exception) {
            throw new InvalidBehaviorException("Fail! Could not read manage all files in the directory! Root Exception: ", exception);
        } catch (InterruptedException exception) {
            throw new InvalidBehaviorException("Fail! An interruption occurred while waiting! Root Exception: ", exception);
        }
    }

}
