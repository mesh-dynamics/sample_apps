package com.cubeiosample.webservices.rest.jersey;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.cube.sampleapps.AddressService.AddressServiceGrpc;
import io.cube.sampleapps.AddressService.AddressServiceGrpc.AddressServiceBlockingStub;
import io.cube.sampleapps.AddressService.Point;
import io.cube.sampleapps.AddressService.Rectangle;
import io.cube.sampleapps.AddressService.Store;
import io.cube.utils.RequestBuilderCarrier;
import io.cube.utils.RestUtils;
import io.cube.utils.Tracing;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.MetadataUtils;
import io.jaegertracing.internal.JaegerSpanContext;
import io.opentracing.Tracer;

import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.json.JSONArray;
import org.json.JSONObject;

public class GRPCInfo {
  /**
   * 1Km = 0.009 degrees
   * as we are not keeping lat and long in decimal(6 digits in decimal)
   * degrees per km  .009*1000000 = 9000
   * we have length* degreesPerMeter/2
   * value = 4500
   */
  private static final int DEGREES_PER_KILO_METER = 4500;
  private final Tracer tracer;
  private final Config config;
  private AddressServiceBlockingStub blockingStub;

  final static Logger LOGGER = Logger.getLogger(GRPCInfo.class);

  public GRPCInfo(Tracer tracer, Config config) {
    this.tracer = tracer;
    this.config = config;
    ManagedChannel channel = ManagedChannelBuilder.forTarget(config.GRPC_URI).usePlaintext().intercept().build();
    blockingStub = AddressServiceGrpc.newBlockingStub(channel);
  }

  public JSONArray getNearByStores(int latitude, int longitude, int length) {
    int lowLat = latitude - length*DEGREES_PER_KILO_METER;
    int lowLon = longitude - length*DEGREES_PER_KILO_METER;
    int hiLat = latitude + length*DEGREES_PER_KILO_METER;
    int hiLon = longitude + length*DEGREES_PER_KILO_METER;
    Rectangle request =
        Rectangle.newBuilder()
            .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
            .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
    Iterator<Store> stores;
    JSONArray response = new JSONArray();
    try {

      JaegerSpanContext activeSpanContext = (JaegerSpanContext) tracer.activeSpan().context();



      Metadata header=new Metadata();
      Metadata.Key<String> traceIdKey =
          Metadata.Key.of("X-B3-TraceId", Metadata.ASCII_STRING_MARSHALLER);
      header.put(traceIdKey, activeSpanContext.getTraceId());

      Metadata.Key<String> spanIdKey =
          Metadata.Key.of("X-B3-SpanId", Metadata.ASCII_STRING_MARSHALLER);
      header.put(spanIdKey, String.valueOf(activeSpanContext.getSpanId()));

      Metadata.Key<String> sampledKey =
          Metadata.Key.of("X-B3-Sampled", Metadata.ASCII_STRING_MARSHALLER);
      header.put(sampledKey, String.valueOf(activeSpanContext.isSampled() ? 1 : 0));

      AddressServiceBlockingStub stub = MetadataUtils.attachHeaders(blockingStub, header);


      stores = stub.listStores(request);
      for (int i = 1; stores.hasNext(); i++) {
        Store store = stores.next();
        String jsonString = JsonFormat.printer()
            .print(store);
        JSONObject jsonObject = new JSONObject(jsonString);
        response.put(jsonObject);
      }
    } catch (InvalidProtocolBufferException e) {
      LOGGER.error(String.format("Error deserializing protobuf to json for latitude: %s, longitude: %s, message:%s", latitude, longitude, e.toString()));
    }
    catch (StatusRuntimeException e) {
      LOGGER.error(String.format("GRPC get nearby stores failed for latitude: %s, longitude: %s, message:%s", latitude, longitude, e.toString()));
    }
    return response;

  }
}
