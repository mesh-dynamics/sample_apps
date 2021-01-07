package io.cube.sampleapps.AddressService;


import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.uri.UriComponent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;


public class RestOverSql {

	public static String MYSQL_HOST = "sakila2.cnt3lftdrpew.us-west-2.rds.amazonaws.com";  // "localhost";
	public static String MYSQL_PORT = "3306";
	public static String MYSQL_DBNAME = "sakila";
	public static String MYSQL_USERNAME = "cube";
	public static String MYSQL_PWD = "cubeio12";  // AWS RDS pwd

	public String baseDbUri() {
		return "jdbc:mysql://" + MYSQL_HOST + ":" + MYSQL_PORT + "/" + MYSQL_DBNAME;
	}

	private Client restClient = null;
	private WebTarget restJDBCService = null;

	final static Logger LOGGER = Logger.getLogger(RestOverSql.class);


	Properties properties;

	public RestOverSql(String RESTWRAPJDBC_URI) {
		ClientConfig clientConfig = new ClientConfig()
			.property(ClientProperties.READ_TIMEOUT, 100000)
			.property(ClientProperties.CONNECT_TIMEOUT, 10000);
		restClient = ClientBuilder.newClient(clientConfig);

		LOGGER.debug("RESTWRAPJDBC_URI is " + RESTWRAPJDBC_URI);
		restJDBCService = restClient.target(RESTWRAPJDBC_URI);

		initializeJDBCService();
	}

	private void initializeJDBCService() {
		String username = MYSQL_USERNAME;
		String pwd = MYSQL_PWD;
		String uri = baseDbUri();
		LOGGER.debug("init jdbc service tracer: ");
		Response response = callWithRetries(restJDBCService.path("initialize").queryParam("username", username).queryParam("password", pwd).queryParam("uri", uri).request(MediaType.APPLICATION_JSON), null, "GET", 3, false);
		LOGGER.debug("intialized jdbc service " + uri + "; " + username + "; " + response.getStatus() + "; "+ response.readEntity(String.class));
		response.close();
	}


	public static Response callWithRetries(Builder req, JSONObject body, String requestType, int numRetries, boolean addHeaders) {
		int numAttempts = 0;
		while (numAttempts < numRetries) {
			try {
				if (requestType.equalsIgnoreCase("GET")) {
					return req.get();
				}
				// assuming body is not null.
				return req.post(Entity.entity(body.toString(), MediaType.APPLICATION_JSON));
			} catch (Exception e) {
				LOGGER.error("request attempt " + numAttempts + ": " + req.toString() + "; exception: " + e.toString());
				++numAttempts;
			}
		}
		LOGGER.debug("call with retries failed: " + req.toString());
		return null;
	}



	public String getHealth() {
		Response response = callWithRetries(restJDBCService.path("health").request(MediaType.APPLICATION_JSON), null, "GET", 3, false);
		String result = response.readEntity(String.class);
		response.close();
		return result;
	}


	public JSONArray executeQuery(String query, JSONArray params) {
		Response response = null;
		try {
			response = callWithRetries(
				restJDBCService.path("query").queryParam("querystring", query).queryParam("params", UriComponent.encode(params.toString(), UriComponent.Type.QUERY_PARAM_SPACE_ENCODED)).request(MediaType.APPLICATION_JSON),
				null, "GET", 3, false);
			JSONArray result = new JSONArray(response.readEntity(String.class));
			if (result != null) {
				LOGGER.debug("Query: " + query + "; " + params.toString() + "; NumRows=" + result.length());
			}
			response.close();
			return result;
		} catch (Exception e) {
			LOGGER.error(String.format("Execute query failed: %s, params: %s; %s", query, params, e.toString()));
			if (response != null) {
				LOGGER.error(response.toString());
			}
		}
		// empty array.
		return new JSONArray("[]");
	}


	public JSONObject executeUpdate(String query, JSONArray params) {
		Response response = null;
		try {
			JSONObject body = new JSONObject();
			body.put("query", query);
			body.put("params", params);
			response = callWithRetries(restJDBCService.path("update").request(), body, "POST", 3, false);

			// TODO: figure out the best way of extracting json array from the entity
			JSONObject result = new JSONObject(response.readEntity(String.class));
			LOGGER.debug("Update: " + query + "; " + params.toString() + "; " + result.toString());
			response.close();
			return result;
		} catch (Exception e) {
			LOGGER.error(String.format("Update failed: %s, params: %s; %s", query, params, e.toString()));
			if (response != null) {
				LOGGER.error(response.toString());
			}
		}
		return new JSONObject("{\"num_updates\": \"-1\"}");
	}

	// parameter binding methods
	public static void addStringParam(JSONArray params, String value) {
		try {
			JSONObject param = new JSONObject();
			param.put("index", params.length() + 1);
			param.put("type", "string");
			param.put("value", value);
			params.put(param);
		} catch (Exception e) {
			LOGGER.error(String.format("addStringParam failed: params: %s, value: %s", params.toString(), value));
		}
	}

	public static void addIntegerParam(JSONArray params, Integer value) {
		try {
			JSONObject param = new JSONObject();
			param.put("index", params.length() + 1);
			param.put("type", "integer");
			param.put("value", value);
			params.put(param);
		} catch (Exception e) {
			LOGGER.error(String.format("addStringParam failed: params: %s, value: %d", params.toString(), value));
		}
	}

	public static void addDoubleParam(JSONArray params, Double value) throws JSONException {
		try {
			JSONObject param = new JSONObject();
			param.put("index", params.length() + 1);
			param.put("type", "double");
			param.put("value", value);
			params.put(param);
		} catch (Exception e) {
			LOGGER.error(String.format("addStringParam failed: params: %s, value: %f", params.toString(), value));
		}
	}

}
