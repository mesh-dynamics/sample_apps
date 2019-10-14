package com.cubeiosample.trafficdriver;

import java.io.File;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.json.*;
import org.openjdk.tools.sjavac.Log;


public class FindAndRentMovies {

    //WebTarget targetService;
    private static String token = "";
    private static final boolean useAuthToken = false;
    //Map<String, String> headers;

    String host;
    String[] pathSegments;
    private CloseableHttpClient httpClient;

    public FindAndRentMovies(String host, String[] pathSegments) {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(50);
        connManager.setDefaultMaxPerRoute(50);
        httpClient
                = HttpClients.custom().setConnectionManager(connManager).build();
        this.host = host;
        this.pathSegments = pathSegments;
    }


    static class CubeHttpClientConnThread extends Thread {
        private CloseableHttpClient httpClient;
        private HttpPost httpPost;
        private String postEntity;
        private File latencyFile;
        private long elapsedTime;

        private HttpClientContext context;
        String host;
        String[] pathSegments;
        String keyWord;

        public CubeHttpClientConnThread(CloseableHttpClient httpClient, String host, String[] pathSegments, String movie, File latencyFile) {
            this.context = HttpClientContext.create();
            this.httpClient = httpClient;
            this.host = host;
            this.pathSegments = pathSegments;
            this.keyWord = movie;
            this.latencyFile = latencyFile;
        }


        @Override
        public void run() {
            CloseableHttpResponse response = null;
            try {
                Instant start = Instant.now();

                URIBuilder uriBuilder = new URIBuilder();

                List<String> pathSegmentsList = Stream.concat(Arrays.asList(pathSegments).stream() , Stream.of("listmovies")).collect(Collectors.toList());
                HttpGet httpGet = new HttpGet(uriBuilder.setScheme("http").setHost(host).setPathSegments(pathSegmentsList)
                        .setParameter("filmName", keyWord).build().toString());
                System.out.println(httpGet.getRequestLine().toString());
                response = httpClient.execute(httpGet, context);
                String jsonResponse = EntityUtils.toString(response.getEntity());
                System.out.println(jsonResponse);
                JSONArray movies = new JSONArray(jsonResponse);
                response.close();
                if (movies.length() == 0) {
                    return;
                }
                int chosen_movie_indx = randGen.nextInt(movies.length());
                JSONObject movieObj = movies.getJSONObject(chosen_movie_indx);
                System.out.println(movieObj.toString());
                int movieId = movieObj.getInt("film_id");

                // find stores with movie
				pathSegmentsList = Stream.concat(Arrays.asList(pathSegments).stream() , Stream.of("liststores")).collect(Collectors.toList());
                httpGet = new HttpGet(uriBuilder.setScheme("http").setHost(host).setPathSegments(
                        pathSegmentsList).setParameter("filmId", String.valueOf(movieId)).build().toString());
                System.out.println(httpGet.getRequestLine().toString());
                response = httpClient.execute(
                        httpGet, context);
                JSONArray stores = new JSONArray(EntityUtils.toString(response.getEntity()));
                if (response == null || response.getStatusLine().getStatusCode() != 200) {
                    System.out.println("list stores wasn't successful");
                    response.close();
                    return;
                }
                response.close();
                if (stores.length() == 0) {
                    System.out.println("No store found for film " + movieId);
                    return;
                }
                JSONObject storeObj = stores.getJSONObject(randGen.nextInt(stores.length()));
                System.out.println(storeObj.toString());
                int storeId = storeObj.getInt("store_id");

                int userId = 1 + randGen.nextInt(maxCustomerId - 1);
                int staffId = 1 + randGen.nextInt(maxStaffId - 1);
                JSONObject rentalInfo = new JSONObject();
                rentalInfo.put("filmId", movieId);
                rentalInfo.put("storeId", storeId);
                rentalInfo.put("duration", 2);
                rentalInfo.put("customerId", userId);
                rentalInfo.put("staffId", staffId);
                System.out.println("client rentalInfo: " + rentalInfo.toString());

				pathSegmentsList = Stream.concat(Arrays.asList(pathSegments).stream() , Stream.of("rentmovie")).collect(Collectors.toList());
                HttpPost httpPost = new HttpPost(uriBuilder.setScheme("http").setHost(host)
                        .setPathSegments(pathSegmentsList).build().toString());
                httpPost.setEntity(new StringEntity(
                        rentalInfo.toString(),
                        ContentType.APPLICATION_JSON));
                response = httpClient.execute(httpPost, context);
                JSONObject rentalResult = new JSONObject(EntityUtils.toString(response.getEntity()));
                if (response.getStatusLine().getStatusCode() != 200) {
                    System.out.println("Rent movie failed or returned null response");
                    response.close();
                    return;
                }
                response.close();

                System.out.println("rentmovie result: " + rentalResult.toString());
                int inventoryId = rentalResult.getInt("inventory_id");
                if (inventoryId < 0) {
                    System.out.println("Couldn't rent film: " + movieId + " @" + storeId);
                    return;
                }

                int numUpdates = rentalResult.getInt("num_updates");
                if (numUpdates < 0) {
                    System.out.println("Couldn't rent film: " + movieId + " @" + storeId);
                }

                // return movie
                // int inventoryId, int customerId, int staffId, double rent
                JSONObject returnMovieInfo = new JSONObject();
                returnMovieInfo.put("inventoryId", inventoryId);
                returnMovieInfo.put("userId", userId);
                returnMovieInfo.put("staffId", staffId);
                returnMovieInfo.put("rent", rentalResult.getDouble("rent"));

				pathSegmentsList = Stream.concat(Arrays.asList(pathSegments).stream() , Stream.of("returnmovie")).collect(Collectors.toList());
                httpPost = new HttpPost(uriBuilder.setScheme("http").setHost(host)
                        .setPathSegments(pathSegmentsList).build().toString());
                httpPost.setEntity(new StringEntity(
                        returnMovieInfo.toString(),
                        ContentType.APPLICATION_JSON));
                response = httpClient.execute(httpPost, context);
                JSONObject returnMovieResult = new JSONObject(EntityUtils.toString(response.getEntity()));
                if (response.getStatusLine().getStatusCode() != 200) {
                    System.out.println(response.getStatusLine().getStatusCode());
                }
                response.close();
                System.out.println("return movie result: " + returnMovieResult.toString() + "\n\n");

                FileWriter latencyFileWriter = new FileWriter(latencyFile, true);
                Instant end = Instant.now();
                Duration elapsedTime = Duration.between(start, end);
                this.elapsedTime = elapsedTime.toMillis();

                synchronized (latencyFile) {
                  BufferedWriter bufferedWriter = new BufferedWriter(latencyFileWriter);
                  bufferedWriter.write(String.valueOf(this.elapsedTime));
                  bufferedWriter.newLine();
                  bufferedWriter.close();
                }

            } catch (IOException | URISyntaxException ex) {
                System.out.println("Error occurred :: " + ex.getMessage());
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        System.out.println("Error occurred while closing response ");
                    }
                }
            }

        }


    }

    // Generated using the following query against the sakila db:
    // SELECT language_id,  group_concat(concat('"', title, '"') separator ', ')
    // FROM film WHERE film_id %23 = 0 GROUP BY language_id;
    private static String[] movies = {"ANACONDA CONFESSIONS", "AUTUMN CROW", "BEVERLY OUTLAW", "BOWFINGER GABLES", "CAMPUS REMEMBER", "CHARIOTS CONSPIRACY", "CLUE GRAIL", "CORE SUIT", "DANGEROUS UPTOWN", "DIARY PANIC", "DRIFTER COMMANDMENTS", "ELEMENT FREDDY", "FACTORY DRAGON", "FLATLINERS KILLER", "GABLES METROPOLIS", "GONE TROUBLE", "HALF OUTFIELD", "HELLFIGHTERS SIERRA", "HOUSE DYNAMITE", "INNOCENT USUAL", "JERICHO MULAN", "LADY STAGE", "LONELY ELEPHANT", "MAJESTIC FLOATS", "MIDSUMMER GROUNDHOG", "MOSQUITO ARMAGEDDON", "NETWORK PEAK", "OSCAR GOLD", "PEACH INNOCENT", "POND SEATTLE", "RAINBOW SHOCK", "ROBBERY BRIGHT", "SALUTE APOLLO", "SHAKESPEARE SADDLE", "SLEEPLESS MONSOON", "SPIKING ELEMENT", "STRAIGHT HOURS", "TADPOLE PARK", "TORQUE BOUND", "UNBREAKABLE KARATE", "VILLAIN DESPERATE", "WEDDING APOLLO", "WORKING MICROCOSMOS"};
    // private static String[] movies = {"TADPOLE PARK"};

    //  Generating query: select language_id, group_concat(concat('"', substring_index(title, ' ', 1), '"') separator ', ') from film where film_id % 29 = 0 group by language_id
    private static String[] movie_keywords = {"ANTITRUST", "BEACH", "BOONDOCK", "CANDIDATE", "CHISUM", "CONFIDENTIAL", "DAISY", "DIRTY", "DUFFEL", "EVERYONE", "FISH", "GANDHI", "GREASE", "HAUNTING", "HOTEL", "INTENTIONS", "KANE", "LIFE", "MAIDEN", "MINE", "MUSCLE", "OPERATION", "PEACH", "PRIDE", "REQUIEM", "RUSHMORE", "SHANE", "SMOKING", "STAR", "SWARM", "TOWERS", "UPTOWN", "WAR", "WONKA"};

    private static int maxCustomerId = 599;
    private static int maxStaffId = 2;

    private static Random randGen = new Random();

    private void warmMovieCache() {
        for (String movie : movies) {
            int rand = randGen.nextInt(100);
            if (rand % 10 == 0) {
                //Response response = callWithRetries(targetService.path("listmovies").queryParam("filmName", movie).request(MediaType.APPLICATION_JSON), null, true, 1);
                //response.close();
            }
        }
    }

    private void waitForListenerDeploy() {
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        scanner.close();
    }

    public void getToken() throws Exception {
        Form form = new Form();
        form.param("username", "cube");
        form.param("password", "cubeio");
    
		/*Response response = callWithRetries(targetService.path("authenticate").request(MediaType.APPLICATION_JSON),
				Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), false, 1);
		System.out.println(response.getStatus());
	  
		System.out.println("header string: " + response.getHeaders());
		if (response.getHeaderString(HttpHeaders.AUTHORIZATION) == null) {
			throw new Exception();
		}
		token = response.getHeaderString(HttpHeaders.AUTHORIZATION);*/
    }


    public void driveTraffic(Optional<Integer> numMovies) throws Exception {
        if (useAuthToken) {
            getToken();
        }
        //warmMovieCache();
		/*
		 commenting this since listeners will already be deployed by the script
         also, need to use the traffic driver from the create collection script, without any
         manual intervention
        */
        //waitForListenerDeploy();

        int nm = numMovies.orElse(movies.length);

        File latencyFile = new File("./latency.txt");
        if (!latencyFile.exists()) {
          latencyFile.createNewFile();
        }

        long totalElapsedTime[] = {0};

        // play traffic for recording.
        List<CubeHttpClientConnThread> connThreads = new ArrayList<>();
        for (int i = 0; i < nm; i++) {
            String movie = movies[i % movies.length];
            CubeHttpClientConnThread clientConnThread = new CubeHttpClientConnThread(httpClient, host, pathSegments, movie, latencyFile);
            connThreads.add(clientConnThread);
            clientConnThread.start();
        }

        connThreads.forEach(thread -> {
            try {
                thread.join();
                totalElapsedTime[0] += thread.elapsedTime;
            } catch (InterruptedException e) {
                System.out.println("Thread interrupt exception :: " + e.getMessage());
            }
        });

      System.out.println("Total elapsed time in milliseconds: " + totalElapsedTime[0] + " "
          + "Avg. Elapsed time in milliseconds: " + totalElapsedTime[0]/nm);
    }
}
