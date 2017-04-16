/*import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Lists;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class GoogleCalendarReader {
	private static final String APPLICATION_NAME = "SMART MIRROR";
	
	private static final java.io.File DATA_STORE_DIR =
		      new java.io.File(System.getProperty("user.home"), ".store/calendar");
	private static FileDataStoreFactory dataStoreFactory;
	private static HttpTransport httpTransport;
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static com.google.api.services.calendar.Calendar client;
	static final java.util.List<Calendar> addedCalendarsUsingBatch = Lists.newArrayList();
	
	private static Credential authorize() throws Exception {
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
	        new InputStreamReader(GoogleCalendarReader.class.getResourceAsStream("/client_secret.json")));
	    if (clientSecrets.getDetails().getClientId().startsWith("Enter")
	        || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
	      System.out.println(
	          "Enter Client ID and Secret");
	      System.exit(1);
	    }
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        httpTransport, JSON_FACTORY, clientSecrets,
	        Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
	        .build();
	    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}	
	  
	static void Init(){
	    try {
	        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	        dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
	        Credential credential = authorize();
	        client = new com.google.api.services.calendar.Calendar.Builder(
	            httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
	        showCalendars();
	      } catch (IOException e) {
	        System.err.println(e.getMessage());
	      } catch (Throwable t) {
	        t.printStackTrace();
	      }
	      System.exit(1);
	}
	
	
	private static void showEvents(Calendar calendar) throws IOException {
		System.out.printf("Show Events");
	    Events feed = client.events().list(calendar.getId()).execute();
	    System.out.printf(feed.toString());
	}
	
	private static void showCalendars() throws IOException {
		System.out.printf("Show Calendars");
	    CalendarList feed = client.calendarList().list().execute();
	    if (feed.getItems() != null) {
	    	//CalendarListEntry entry : feed.getItems()
	        for (int i = 0; i < 1; i++) {
	          System.out.println();
	          System.out.println("-----------------------------------------------");
	          System.out.println(feed.getItems().toString());
	        }
	      }
	}
}*/


import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class GoogleCalendarReader {
	private static String upEvents;
	
	/** Application name. */
    private static final String APPLICATION_NAME =
        "Google Calendar API Java Quickstart";
	
    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/calendar-java-quickstart");
	
	/** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    
    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();
    
    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;
    
    /** Global instance of the scopes required by this quickstart.
    *
    * If modifying these scopes, delete your previously saved credentials
    * at ~/.credentials/calendar-java-quickstart
    */
   private static final List<String> SCOPES =
       Arrays.asList(CalendarScopes.CALENDAR_READONLY);
   
   static {
       try {
           HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
           DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
       } catch (Throwable t) {
           t.printStackTrace();
           System.exit(1);
       }
   }
   
   /**
    * Creates an authorized Credential object.
    * @return an authorized Credential object.
 * @throws Throwable 
    */
   public static Credential authorize() throws Throwable {
       // Load client secrets.
       InputStream in =
    	GoogleCalendarReader.class.getResourceAsStream("/client_secret.json");
       GoogleClientSecrets clientSecrets =
           GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

       // Build flow and trigger user authorization request.
       GoogleAuthorizationCodeFlow flow =
               new GoogleAuthorizationCodeFlow.Builder(
                       HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
               .setDataStoreFactory(DATA_STORE_FACTORY)
               .setAccessType("offline")
               .build();
       Credential credential = new AuthorizationCodeInstalledApp(
           flow, new LocalServerReceiver()).authorize("user");
       System.out.println(
               "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
       return credential;
   }
   
   /**
    * Build and return an authorized Calendar client service.
    * @return an authorized Calendar client service
 * @throws Throwable 
    */
   public static com.google.api.services.calendar.Calendar
       getCalendarService() throws Throwable {
       Credential credential = authorize();
       return new com.google.api.services.calendar.Calendar.Builder(
               HTTP_TRANSPORT, JSON_FACTORY, credential)
               .setApplicationName(APPLICATION_NAME)
               .build();
   }
   
   static void Init() throws Throwable {
	   com.google.api.services.calendar.Calendar service =
	            getCalendarService();
	   
	   // List the next 10 events from the primary calendar.
       DateTime now = new DateTime(System.currentTimeMillis());
       Events events = service.events().list("primary")
           .setMaxResults(10)
           .setTimeMin(now)
           .setOrderBy("startTime")
           .setSingleEvents(true)
           .execute();
       List<Event> items = events.getItems();
       if (items.size() == 0) {
    	   setEvents("No upcoming events found.");
       } else {
    	   //setEvents("Upcoming events");
           for (Event event : items) {
               DateTime start = event.getStart().getDateTime();
               if (start == null) {
                   start = event.getStart().getDate();
               }
               setEvents(event.getSummary() + " " + start + "\n");
           }
       }
   }
   
	public static String getEvents() {
		return upEvents;
	}
	public static void setEvents(String upEvents) {
		GoogleCalendarReader.upEvents += upEvents;
	}
	public static void clearEvents(String upEvents) {
		GoogleCalendarReader.upEvents = "";
	}

}

