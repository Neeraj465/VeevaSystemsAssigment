package automation.framework;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import com.opencsv.CSVWriter;

public class CommonHelper{

    List<Map<String,String>> yamlData = null;

    /**
     * This method reads data from yaml file using fileName
     * @param fileName - yaml file to read
     * @return - List of row and column data in form of Map
     */
    public List<Map<String,String>> readYamlfile(String fileName){
        Yaml yaml = new Yaml();
        String path = System.getProperty("user.dir") + "/src/main/resources/testdata/"+fileName+".yaml" ;
        try {
            InputStream file = new FileInputStream(path);
            yamlData = (List<Map<String,String>>) yaml.load(file);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return yamlData;
    }

    /**
     * Method to store data in csv file with given column name row data and fileName
     * @param headers - columns of csv file
     * @param data - rows of csv file
     * @param fileName - csv file name
     */
     void writeAndStoreDataInCsvFile(String[] headers, List<String[]> data, String fileName){

        String path = System.getProperty("user.dir")+"/src/test/resources/"+fileName+".csv";

         try {
             Files.deleteIfExists(Paths.get(path));
         } catch(Exception e){

         }
        File file = new File(path);
        try {
            // create FileWriter object with file as parameter
            Writer outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            writer.writeNext(headers);

            // adding data to csv
            writer.writeAll(data);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to read csv file from given fileName
     * @param fileName - csv file name to read
     * @return - returns row data as list of String
     */
    public List<String> readCsvFile(String fileName){
        String path = System.getProperty("user.dir")+"/src/test/resources/"+fileName+".csv";
        File file = new File(path);
        List<String> list = new ArrayList<>();
        try{
            FileReader filereader = new FileReader(file);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            // print Data
            for (String[] row : allData) {
                for (String cell : row) {
                    list.add(cell);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }
}
