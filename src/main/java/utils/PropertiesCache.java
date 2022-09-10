package utils;

import java.io.*;
import java.util.Properties;
import java.util.Set;

public class PropertiesCache {

    private final Properties configProp = new Properties();
    private String file_name;

    public PropertiesCache(String file_name){
        this.file_name = file_name;
        //Private constructor to restrict new instances
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file_name + ".properties");
        System.out.println("Here is current file properties: " + file_name + ".properties");
        try {
            configProp.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PropertiesCache()
    {
        //Private constructor to restrict new instances
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file_name + ".properties");
        System.out.println("Here is current file properties: " + file_name + ".properties");
        try {
            configProp.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Bill Pugh Solution for singleton pattern
    private static class LazyHolder
    {
        private static final PropertiesCache INSTANCE = new PropertiesCache();
    }

    public static PropertiesCache getInstance()
    {
        return LazyHolder.INSTANCE;
    }

    // Read value from properties file by key
    public String getProperty(String key){
        return configProp.getProperty(key);

    }

    // Read all values from properties file by key
    public Set<String> getAllPropertyNames(){
        return configProp.stringPropertyNames();
    }

    //  Check value is existed or not
    public boolean containsKey(String key){
        return configProp.containsKey(key);
    }


    // Write data to properties file
    public void setProperty(String key, String value){
        configProp.setProperty(key, value);
    }

    public void flush(String file_name) throws FileNotFoundException, IOException {
        try (final OutputStream outputstream
                     = new FileOutputStream(file_name + ".properties");) {
            configProp.store(outputstream,"File Updated");
            outputstream.close();
        }
    }

    // Set file name to
    public void setFileName(String file_name){
        this.file_name=file_name;
    }

    public String getFileName(){
        return file_name;
    }
}
