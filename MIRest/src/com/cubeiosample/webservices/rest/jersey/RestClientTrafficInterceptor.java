package com.cubeiosample.webservices.rest.jersey;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

@Provider
public class RestClientTrafficInterceptor implements ClientRequestFilter {

  final static Logger LOGGER = Logger.getLogger(RestClientTrafficInterceptor.class);

  @Override
  public void filter(ClientRequestContext clientRequestContext) throws IOException {
      URI uri = clientRequestContext.getUri();
      String path = Optional.ofNullable(uri.getPath()).orElse("N/A");
      Integer port = clientRequestContext.getUri().getPort();
      String host = Optional.ofNullable(uri.getHost()).orElse("N/A");
      String query = Optional.ofNullable(uri.getQuery()).orElse("N/A");


      LOGGER.info(new ObjectMessage(Map.of("path" , path , "port" , port , "host" , host , "query" , query)));
      URIBuilder uriBuilder = new URIBuilder();
    try {
        URI normalizedNewUrl = uriBuilder.setScheme("http").setHost("cubeiocorp.io").setPort(8080).setPath("/cs/rr/" + path).build().normalize();
        LOGGER.info(new ObjectMessage(Map.of("New URI" , normalizedNewUrl.toString())));
        //clientRequestContext.setUri(normalizedNewUrl);
    } catch (URISyntaxException e) {
      LOGGER.error(e);
    }

    //clientRequestContext.setUri(URI.create("http://cubecorp.io:8080/cs/rr/"));
      //LOGGER.info(new ObjectMessage(Map.of("REQUEST :: " , clientRequestContext.getUri().toString())));
  }
}
