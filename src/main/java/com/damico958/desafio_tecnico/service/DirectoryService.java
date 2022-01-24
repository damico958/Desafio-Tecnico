package com.damico958.desafio_tecnico.service;

import com.damico958.desafio_tecnico.database.DirectoryDatabase;
import com.google.common.hash.HashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DirectoryService {
    @Autowired
    private DirectoryDatabase directoryDatabase;

    private Map<String, String> cachedHashes;

    public DirectoryService() {
        this.cachedHashes = new HashMap<String, String>();
    }

    public void getHashes() {
        this.cachedHashes = directoryDatabase.getDirectoryDatabase();
    }

    public boolean checkIsNewHash(String path, HashCode sha256Hash) {
        if(cachedHashes.containsKey(path)){
            System.out.println("File already hashed in cache!");
            if(cachedHashes.get(path).equals(sha256Hash.toString())) {
                System.out.println("Same hash! Nothing changed in " + path);
                return false;
            }
            System.out.println("Different Hash! Let's update it in " + path);
        }
        System.out.println("New File! Saving path and hash");
        save(path, sha256Hash);
        return true;
    }

    public void save(String path, HashCode sha256Hash) {
        cachedHashes.put(path, sha256Hash.toString());
        directoryDatabase.saveDirectoryDatabase(cachedHashes);
    }
}
