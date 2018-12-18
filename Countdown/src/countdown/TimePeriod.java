package countdown;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;

/**
 *
 * @author duukkis
 */
public class TimePeriod {
    UI ui;
    FileUtils fileUtils;
    HashMap<String, LocalDate> events;
    
    public TimePeriod() {
        this.ui = new UI();
        this.fileUtils = new FileUtils();
        this.events = new HashMap<>();
    }
    
    public void start() {
        this.fileUtils.readFromFile(this);
        this.ui.askUser(fileUtils, this);
    }
    
    public String[] preprocessEvent(String[] eventData) {
        String[] strEventData = new String[4];
        String[] strDateTemp = eventData[1].split("[/]");
        strEventData[0] = eventData[0];
        strEventData[1] = strDateTemp[0];
        strEventData[2] = strDateTemp[1];
        strEventData[3] = strDateTemp[2];
        
        return strEventData;
    }
    
    public Period createPeriod(String[] strArray) {
        int[] startingTime = new int[3];
        for(int i = 0; i <= 2; i++) {
            startingTime[i] = Integer.parseInt(strArray[i + 1]);
        }
        
        LocalDate currentDate = LocalDate.now();
        LocalDate upcomingDate = LocalDate.of(startingTime[2], startingTime[1], 
                startingTime[0]);
        
        addToEvents(strArray[0], upcomingDate);
        this.fileUtils.writeToFile(strArray[0], upcomingDate);
        Period period = Period.between(currentDate, upcomingDate);
        
        return period;
    }
    
    public void addToEvents(String eventName, LocalDate eventDate) {
        this.events.put(eventName, eventDate);
    }
    
    public boolean removeFromEvents(String eventName) {
        if(this.events.containsKey(eventName)) {
            this.events.remove(eventName);
            return true;
        } else {
            return false;
        }
    }
    
    public HashMap<String, LocalDate> getEvents() {
        return events;
    }
    
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
