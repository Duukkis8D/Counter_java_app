package countdown;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author duukkis
 */
public class FileUtils {
    File file;
    
    public FileUtils() {
        this.file = new File("event_data.csv");
        try {
            this.file.createNewFile();
        } catch(IOException e) {
            System.out.println("IOException occurred!");
        }
    }
    
    public void readFromFile(TimePeriod timePeriod) {
        String line = null;
        try {
            BufferedReader bufferedReader = 
                    new BufferedReader(new FileReader(this.file));
            
            ArrayList<String> eventsTemp = new ArrayList<>();
            while( ( line = bufferedReader.readLine() ) != null ) {
                eventsTemp.add(line);
            }
            for (String eventData : eventsTemp) {
                String[] eventTemp = eventData.split("[;]");
                timePeriod.addToEvents(eventTemp[0], LocalDate.of(
                        Integer.parseInt(eventTemp[3]),
                        Integer.parseInt(eventTemp[2]), 
                        Integer.parseInt(eventTemp[1])));
            }
        } catch(FileNotFoundException e) {
            System.out.println("Event data file not found!");
        } catch(IOException e) {
            System.out.println("Input/output exception occurred!");
        }
    }
    
    public void writeToFile(String eventName, LocalDate eventDate) {
        try {
            BufferedWriter bufferedWriter = 
                    new BufferedWriter(new FileWriter(this.file, true));
            
            bufferedWriter.write(eventName + ";" + eventDate.getDayOfMonth() +
                    ";" + eventDate.getMonthValue() + ";" + eventDate.getYear() + 
                    "\n");
            bufferedWriter.close();
        } catch(IOException e) {
            System.out.println("Input/output exception occurred!");
        }
    }
    
    public void removeFromFile(String eventName) {
        File tempFile = new File("event_data_temp.csv");
        String line = null;
        try {
            BufferedReader bufferedReader = 
                    new BufferedReader(new FileReader(this.file));
            BufferedWriter bufferedWriter = 
                    new BufferedWriter(new FileWriter(tempFile, true));
            
            while( ( line = bufferedReader.readLine() ) != null ) {
                if(line.contains(eventName)) continue;
                bufferedWriter.write(line + "\n");
            }
            bufferedReader.close();
            bufferedWriter.close();
            boolean successfull = tempFile.renameTo(this.file);
            if(successfull = false) {
                System.out.println("Error occurred while removing event!");
            }
        } catch(FileNotFoundException e) {
            System.out.println("Event data file not found!");
        } catch(IOException e) {
            System.out.println("Input/output exception occurred!");
        }
    }
}