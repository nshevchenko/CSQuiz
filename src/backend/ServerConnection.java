package backend;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.example.oopquiz.Managers.ProfileManager;

	public class ServerConnection extends AsyncTask<String, Integer, Double>{
		 
	@Override
	protected Double doInBackground(String... params) {
		// TODO Auto-generated method stub
		postData(params[0]);
		return null;
	}
 
	protected void onPostExecute(Double result){
		//pb.setVisibility(View.GONE);
		//temp.getButton().setText(value);
	}
	
	protected void onProgressUpdate(Integer... progress){
		//pb.setProgress(progress[0]);
	}
	
	public void postData(String valueIWantToSend) {
		//Create a new HttpClient and Post Header
		HttpParams params = new BasicHttpParams();
		HttpClient client = new DefaultHttpClient(params);
		HttpPost post = new HttpPost("http://lionsrace.altervista.org/serverCSQuiz.php");
		try
		{
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("name", ProfileManager.myProfile.getName());
			jsonObj.put("id", ProfileManager.myProfile.getId());
			jsonObj.put("score", ProfileManager.myProfile.getExp());

			String topcat = "";
			String[] topCategories = ProfileManager.myProfile.top3;
			for(String str : topCategories) 
				topcat += str + ",";
			jsonObj.put("topcat", topcat);
			
			JSONArray friendsID = new JSONArray();
			for (int i = 0 ; i < ProfileManager.profiles.size() ; i++)
			{
				jsonObj = new JSONObject();
				jsonObj.put("id", ProfileManager.getProfile(i).getId());
				friendsID.put(jsonObj);
			}   	    
			jsonObj.put("friends", friendsID);
			
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			postParams.add(new BasicNameValuePair("data", jsonObj.toString()));
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
			entity.setContentEncoding(HTTP.UTF_8);
			post.setEntity(entity);
			HttpResponse response = client.execute(post);
			String result = inputStreamToString(response.getEntity().getContent()).toString();
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
		
	private StringBuilder inputStreamToString(InputStream is) throws IOException {
		String line = "";
		StringBuilder total = new StringBuilder();
		    		    // Wrap a BufferedReader around the InputStream
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    // Read response until the end
	    while ((line = rd.readLine()) != null) { 
	        total.append(line); 
	    }
	   
	    // Return full string
	    return total;
	}
 
	private void decodeJson()
	{
		//decode 
		//assign info to my friends
		
	}
}

