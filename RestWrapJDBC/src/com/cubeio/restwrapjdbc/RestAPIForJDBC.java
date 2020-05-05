package com.cubeio.restwrapjdbc;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;
import org.json.JSONArray;
import org.json.JSONObject;

import io.cube.utils.ConnectionPool;
import io.md.constants.Constants;
import io.md.utils.CommonUtils;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;

@Path("/")
public class RestAPIForJDBC {

    final static Logger LOGGER;
    private static ConnectionPool jdbcPool = null;
    static Tracer tracer;

    static {
        LOGGER = Logger.getLogger(RestAPIForJDBC.class);

        /*tracer = CommonUtils.init("RestWrapJDBC");
        try {
            MDTracer.register(tracer);
        } catch (IllegalStateException e) {
            LOGGER.error(new ObjectMessage(Map.of(Constants.MESSAGE,
                "Trying to register a tracer when one is already registered")), e);
        }*/

        //tracer = Tracing.init("RestWrapJDBC");
        BasicConfigurator.configure();

        // mysql properties
        //String MYSQL_HOST = "sakila2.cnt3lftdrpew.us-west-2.rds.amazonaws.com";  // "localhost";
        //String MYSQL_PORT = "3306";
        //String MYSQL_DBNAME = "sakila";
        String MYSQL_USERNAME = "cube";
        //private static String MYSQL_PWD = "cubeio";  // local docker host pwd
        String MYSQL_PWD = "cubeio12";  // AWS RDS pwd

        String MYSQL_URI = "jdbc:mysql://sakila2.cnt3lftdrpew.us-west-2.rds.amazonaws.com:3306/sakila";

        initJdbc(MYSQL_URI, MYSQL_USERNAME, MYSQL_PWD);

    }


    static private void initJdbc(String uri, String username, String passwd) {
        try {
            jdbcPool = new ConnectionPool();
            jdbcPool.setUpPool(uri, username, passwd);
            LOGGER.info("mysql uri: " + uri);
            LOGGER.info(jdbcPool.getPoolStatus());
        } catch (Exception e) {
            LOGGER.error("connection pool creation failed; " + e.toString());
        }
    }


    @Path("/health")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response health() {
        return Response.ok().type(MediaType.APPLICATION_JSON)
            .entity("{\"status\": \"Rest wrapper for JDBC is healthy\"}").build();
    }


    @Path("/initialize")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response initialize(@QueryParam("username") String username,
        @QueryParam("password") String passwd,
        @QueryParam("uri") String uri,
        @Context HttpHeaders httpHeaders) {
        final Span span = CommonUtils.startClientSpan("initialize"
            , Map.of("initialize", uri + "; " + username + "; <pwd>"));
        try (Scope scope = CommonUtils.activateSpan(span)) {

            initJdbc(uri, username, passwd);
            return Response.ok().type(MediaType.APPLICATION_JSON)
                .entity("{\"status\": \"Connection pool created.\"}").build();
        } catch (Exception e) {
            LOGGER.error("connection pool creation failed; " + e.toString());
            return Response.ok().type(MediaType.APPLICATION_JSON)
                .entity("{\"result\": \"Initialization failed\"}").build();
        } finally {
            span.finish();
        }

    }


    @Path("/query")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response query(@QueryParam("querystring") String query,
        @QueryParam("params") String queryParams,
        @Context HttpHeaders httpHeaders) {
        JSONArray result = null;
        JSONArray params = new JSONArray(queryParams);
        final Span span = CommonUtils.startClientSpan("query",
            Map.of("query", query));
        try (Scope scope = CommonUtils.activateSpan(span)) {
            result = jdbcPool.executeQuery(query, params);
            LOGGER.info(jdbcPool.getPoolStatus());
            return Response.ok().type(MediaType.APPLICATION_JSON).entity(result.toString()).build();
        } catch (Exception e) {
            LOGGER.error("Query failed: " + query + "; " + queryParams + "; " + e.toString());
            return Response.ok().type(MediaType.APPLICATION_JSON)
                .entity("{[\"result\": \"Query execution failed\"]}").build();
        } finally {
            span.finish();
        }

    }


    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(String queryAndParamsStr,
        @Context HttpHeaders httpHeaders) {
        JSONObject result = null;
        final Span span = CommonUtils.startClientSpan( "update"
            , Map.of("update", queryAndParamsStr));
        try (Scope scope = CommonUtils.activateSpan(span)) {
            JSONObject queryAndParams = new JSONObject(queryAndParamsStr);
            JSONArray params = queryAndParams.getJSONArray("params");
            String query = queryAndParams.getString("query");
            LOGGER.debug("Update stmt: " + query + "; params: " + params.toString());
            result = jdbcPool.executeUpdate(query, params);
            LOGGER.info(jdbcPool.getPoolStatus());
            return Response.ok().type(MediaType.APPLICATION_JSON)
                .entity(result.toString()).build();
        } catch (Exception e) {
            result = new JSONObject();
            LOGGER.error("Update query failed: " + queryAndParamsStr + "; " + e.toString());
            result.put("num_updates", -10);
            result.put("exception", e.toString());
            return Response.serverError().type(MediaType.APPLICATION_JSON)
                .entity(result.toString()).build();
        } finally {
            span.finish();
        }

    }

}
