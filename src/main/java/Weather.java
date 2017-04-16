import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import org.json.JSONObject;

public class Weather {
	
	private static Double temp;
	private static String city;
	
	static void Init() throws IOException{
		try {
			//Find external IP address
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(
	                whatismyip.openStream()));
			String ip = in.readLine();
			in.close();
			//Find LatLong coords by IP
			LookupService cl = new LookupService("src/main/resources/GeoLiteCity.dat",
                    LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
			Location l1 = cl.getLocation(ip);
	        cl.close();
	        String owKey = "6b285fc3867b91ab43e467eed672cdbc";
	        String owURL = "http://api.openweathermap.org/data/2.5/weather?lat=" +
	        				l1.latitude + "&lon=" + l1.longitude + "&appid=" + owKey + "&units=metric";
	        HttpURLConnection urlConnection = null;
	        BufferedReader reader = null;
	        String forecastJsonStr = null;
	        URL url = new URL(owURL);
	        // Create the request to OpenWeatherMap, and open the connection
	        urlConnection = (HttpURLConnection) url.openConnection();
	        urlConnection.setRequestMethod("GET");
	        urlConnection.connect();
	        // Read the OpenWeather forecast JSON string
	        InputStream inputStream = urlConnection.getInputStream();
	        StringBuffer buffer = new StringBuffer();
	        reader = new BufferedReader(new InputStreamReader(inputStream));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            buffer.append(line);
	        }
	        forecastJsonStr = buffer.toString();
	        //Parse JSON string
	        JSONObject jsonObj = new JSONObject(forecastJsonStr);
	        setTemp(jsonObj.getJSONObject("main").getDouble("temp"));
	        setCity(jsonObj.getString("name"));	          
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public static Double getTemp() {
		return temp;
	}
	public static void setTemp(Double temp) {
		Weather.temp = temp;
	}
	public static String getCity() {
		return city;
	}
	public static void setCity(String city) {
		Weather.city = city;
	}
	
	

}
