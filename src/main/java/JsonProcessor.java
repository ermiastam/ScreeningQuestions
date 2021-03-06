import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.*;
import java.util.Iterator;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class JsonProcessor {

	 public static int totalNumber = 0;
	// http://localhost:8080/RESTfulExample/json/product/get
	public static void main(String[] args) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			//input = new FileInputStream("/resources/urlpath.properties");
			input = ClassLoader.getSystemResourceAsStream("urlpath.properties");

			// load a properties file
			prop.load(input);

			getKeys(prop.getProperty("url"));


		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


	}



	//# -1 getting the JSON data from a given URL

	public static BufferedReader getData(String uri) {
		BufferedReader br = null;
		HttpURLConnection conn = null;

		try {

			URL url = new URL(uri);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
			//System.out.println(br.readLine());

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}


		return br;

	}

	//# -2
	public static void getKeys(String uri) {

		BufferedReader br = getData(uri);

		String output;

		try {
			while ((output = br.readLine()) != null) {
				//System.out.println(output);

				JSONArray jArray = new JSONArray(output);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject object = jArray.optJSONObject(i);
					Iterator<String> iterator = object.keys();
					while (iterator.hasNext()) {
						String currentKey = iterator.next();

						System.out.println(currentKey);

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//#-3
	public static int addKeys(String uri) {

		BufferedReader br = getData(uri);
		int sum = 0;
		String output;

		try {
			while ((output = br.readLine()) != null) {


				JSONArray jArray = new JSONArray(output);
				for (int i = 0; i < jArray.length(); i++) {
					JSONObject object = jArray.optJSONObject(i);
					Iterator<String> iterator = object.keys();
					while (iterator.hasNext()) {
						String currentKey = iterator.next();
						if(currentKey.equalsIgnoreCase("numbers")) {
							sum = sum + Integer.parseInt(currentKey);
							totalNumber++;
						}

					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
return sum;

	}

	//#-4
public int getTotalNumber(String uri){

	addKeys(uri);
		return totalNumber;

}



}