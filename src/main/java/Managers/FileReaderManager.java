package Managers;

import DataProviders.ConfigFileReader;
import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import org.apache.commons.lang3.SystemProperties;

import java.util.Properties;

public class FileReaderManager {

    private static final FileReaderManager fileReaderManager = new FileReaderManager();
    private static ConfigFileReader configFileReader;

    private FileReaderManager() {}

    public static FileReaderManager getInstance() {
        return fileReaderManager;
    }

    public ConfigFileReader getConfigFileReader() {
        return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
    }

}
