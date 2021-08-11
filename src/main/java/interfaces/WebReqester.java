package interfaces;

import org.json.JSONObject;

import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class WebReqester {
	
	public static JSONObject getHaloPlayerAppearance(String player, String apiKey) {
		HttpClient httpclient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder("https://www.haloapi.com/profile/h5/profiles/" + player + "/appearance");
            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", apiKey);
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return new JSONObject(EntityUtils.toString(entity));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		return null;
	}
	
	public static JSONObject getHaloCompany(String companyID, String apiKey) {
		HttpClient httpclient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder("https://www.haloapi.com/stats/h5/companies/" + companyID);
            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Ocp-Apim-Subscription-Key", apiKey);
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                return new JSONObject(EntityUtils.toString(entity));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		return null;
	}

}
