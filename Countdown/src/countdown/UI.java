package countdown;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author duukkis
 */
public class UI {
    Scanner scanner;
    
    public UI() {
        this.scanner = new Scanner(System.in);
    }
    
    public void askUser(FileUtils fileUtils, TimePeriod timePeriod) {
        System.out.println("------------------------------\nWelcome to "
                + "Countdown by Tihku!\n------------------------------");
        String command = null;
        
        while(true) {
            System.out.println("To view all counters, press [v] key and then "
                    + "[ENTER]\n" + 
                    "To add a countdown event, press [a] key and then [ENTER]"
                    + "\n" +
                    "To remove a countdown event, press [r] key and then "
                    + "[ENTER]\n" +
                    "To quit the program, press [q] key and then [ENTER]");
            System.out.print("> ");
            command = this.scanner.nextLine();
            
            switch(command) {
                case "v": HashMap<String, LocalDate> events = 
                        timePeriod.getEvents();
                        LocalDate currentDate = timePeriod.getCurrentDate();
                        System.out.println("Events:\n"
                                + "---------------------------------");
                        for (String eventName : events.keySet()) {
                            LocalDate eventDate = events.get(eventName);
                            showCountdown(Period.between(
                                    currentDate, eventDate), eventName);
                        }
                        System.out.println("---------------------------------"
                                + "\n");
                        break;
                case "a": String[] strEventData = addEvent();
                        String[] eventData = 
                                timePeriod.preprocessEvent(strEventData);
                        Period period = 
                                timePeriod.createPeriod(eventData);
                        System.out.println("Event added!");
                        break;
                case "r": String eventName = removeEvent();
                        boolean removed = 
                                timePeriod.removeFromEvents(eventName);
                        if(removed) {
                            fileUtils.removeFromFile(eventName);
                            System.out.println("Event removed!");
                        } else {
                            System.out.println("Event could not be found "
                                    + "and removed. Sorry!");
                        }
                        break;
                case "q": return;
            }
        }
    }
    
    public String[] addEvent() {
        String strName = askEventName();
        String strDate = askEventDate();
        
        String[] eventData = new String[2];
        eventData[0] = strName;
        eventData[1] = strDate;
        
        return eventData;
    }
    
    public String askEventName() {
        String strName = "";
        do {
            System.out.println("Give a name for the event and press [ENTER] key. "
                    + "(Do not use ; character)");
            System.out.print("> ");
            strName = this.scanner.nextLine();
            if(strName.contains(";")) {
                System.out.println("Please enter event name without character "
                        + ";");
            }
        } while(strName.contains(";") == true);
        
        return strName;
    }
    
    public String askEventDate() {
        String strDate = "";
        //Regex to match “dd/mm/yyyy” with required leading zeros
        String dateRegex = 
                "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher;
        
        do {
            System.out.println("Give a starting date for an event and press "
                    + "[ENTER] key.\nFormat dd/mm/yyyy");
            System.out.print("> ");
            strDate = this.scanner.nextLine();
            matcher = pattern.matcher(strDate);
            if(matcher.matches() == false) {
                System.out.println("Date format was incorrect!");
            }
        } while(matcher.matches() == false);
        
        return strDate;
    }
    
    public void showCountdown(Period period, String eventName) {
        System.out.println("\nCountdown for " + eventName);
        System.out.println(period.getYears() + " years " + period.getMonths() 
                + " months " + period.getDays() + " days\n");
    }
    
    public String removeEvent() {
        System.out.println("Give a name of the event you want to remove and "
                + "press [ENTER] key.");
        System.out.print("> ");
        String eventName = this.scanner.nextLine();
        
        return eventName;
    }
}