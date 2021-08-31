package interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataStructures.json.Build;
import dataStructures.json.Commits;
import dataStructures.json.Repositories;

import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.codec.binary.Base64;

public class WebRequester {
	
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
	
	public static Repositories getBitbucketRepos(String apiKey) {
		HttpClient httpclient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder("https://zgamelogic.com:7990/rest/api/1.0/projects/BSPR/repos");
            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Authorization", "Basic " + encodedAuthorization(apiKey));
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
            	
        		ObjectMapper objectMapper = new ObjectMapper();
        		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return objectMapper.readValue(EntityUtils.toString(entity), Repositories.class);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		return null;
	}
	
	public static Commits getBitbucketRepoCommits(String apiKey, String repoSlug) {
		HttpClient httpclient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder("https://zgamelogic.com:7990/rest/api/1.0/projects/BSPR/repos/" + repoSlug + "/commits");
            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Authorization", "Basic " + encodedAuthorization(apiKey));
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
        		ObjectMapper objectMapper = new ObjectMapper();
        		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return objectMapper.readValue(EntityUtils.toString(entity), Commits.class);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		return null;
	}
	
	public static Build getBitbucketCommitBuildStatus(String apiKey, String commitID) {
		HttpClient httpclient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder("https://zgamelogic.com:7990/rest/build-status/1.0/commits/" + commitID);
            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Authorization", "Basic " + encodedAuthorization(apiKey));
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
        		ObjectMapper objectMapper = new ObjectMapper();
        		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        		return objectMapper.readValue(EntityUtils.toString(entity), Build.class);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	public static String getBitbucketRepoWebInfo(String repoSlug, String apiKey) {
		HttpClient httpclient = HttpClients.createDefault();
        try {
            URIBuilder builder = new URIBuilder("https://zgamelogic.com:7990/rest/api/1.0/projects/BSPR/repos/" + repoSlug + "/browse/website.info");
            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);
            request.setHeader("Authorization", "Basic " + encodedAuthorization(apiKey));
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
            	String info = "";
            	JSONObject result = new JSONObject(EntityUtils.toString(entity));
            	if(result.has("lines")) {
            		JSONArray jsonInfo = result.getJSONArray("lines");
            		
                	for(int i = 0; i < jsonInfo.length(); i++) {
                		info += jsonInfo.getJSONObject(i).getString("text") + "\n";
                	}
            	}else {
            		info = "website.info has not been added to this repository yet!";
            	}
            	
                return info;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		return null;
	}
	
	private static String encodedAuthorization(String apiKey) {
		String plainCreds = apiKey;
	    byte[] plainCredsBytes = plainCreds.getBytes();
	    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
	    String base64Creds = new String(base64CredsBytes);
		return base64Creds;
	}

}
