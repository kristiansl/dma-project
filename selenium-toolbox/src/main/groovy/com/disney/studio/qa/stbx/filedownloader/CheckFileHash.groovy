package com.disney.studio.qa.stbx.filedownloader

import groovy.util.logging.Slf4j
import org.apache.commons.codec.digest.DigestUtils

import java.nio.file.Files
import java.nio.file.Path

@Slf4j
class CheckFileHash {
    private HashType typeOfHash = null;
    private String expectedFileHash = null;
    private Path pathToFile;
    private String actualFileHash;
    private TargetFileSystem targetFileSystem;

    public CheckFileHash() {
        this(TargetFileSystem.FILE_SYSTEM)
    }

    public CheckFileHash(TargetFileSystem targetFileSystem) {
        this.targetFileSystem = targetFileSystem
    }

    /**
     * Retrieves the actual file hash obtained on the file
     * @return
     * the actual hash
     */
    public String actualFileHash() {
        return actualFileHash
    }

    /**
     * The File to perform a Hash check upon
     *
     * @param fileToCheck
     * @throws FileNotFoundException
     */
    public void registerFileToCheck(Path path) throws FileNotFoundException {
        if (!Files.exists(path)) throw new FileNotFoundException((path.toAbsolutePath().toString() + " does not exist!"));
        this.pathToFile = path;
    }

    /**
     * Hash details used to perform the Hash check
     *
     * @param hash
     * @param hashType
     */
    public void registerHashDetails(String hash, HashType hashType) {
        this.expectedFileHash = hash;
        this.typeOfHash = hashType;
    }

    /**
     * Verifies if the hash is valid
     *
     * @return
     * @throws IOException
     */
    public boolean isHashValid() throws IOException {
        if (pathToFile == null) throw new FileNotFoundException("File to check has not been set!");
        if (expectedFileHash == null || typeOfHash == null) throw new NullPointerException("Hash details have not been set!");

        boolean isHashValid = false
        InputStream inputStream

        switch (targetFileSystem) {
            case TargetFileSystem.FILE_SYSTEM:
                inputStream = new FileInputStream(pathToFile.toAbsolutePath().toString());
                break;
            case TargetFileSystem.IN_MEMORY_FILE_SYSTEM:
                inputStream = Files.newInputStream(pathToFile);
                break;
        }

        switch (typeOfHash) {
            case HashType.MD5:
                actualFileHash = DigestUtils.md5Hex(inputStream);
                if (expectedFileHash.equals(actualFileHash)) isHashValid = true;
                break;
            case HashType.SHA1:
                actualFileHash = DigestUtils.sha1Hex(inputStream);
                if (expectedFileHash.equals(actualFileHash)) isHashValid = true;
                break;
        }

        log.info("Filename = '{}'", pathToFile.getFileName());
        log.info("Expected Hash = '{}'", expectedFileHash);
        log.info("Actual Hash = '{}'", actualFileHash);

        return isHashValid;
    }
}
