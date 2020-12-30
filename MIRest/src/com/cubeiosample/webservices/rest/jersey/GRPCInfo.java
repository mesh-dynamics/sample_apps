package com.cubeiosample.webservices.rest.jersey;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.routeguide.Feature;
import io.grpc.examples.routeguide.Point;
import io.grpc.examples.routeguide.Rectangle;
import io.grpc.examples.routeguide.RouteGuideGrpc;
import io.grpc.examples.routeguide.RouteGuideGrpc.RouteGuideBlockingStub;
import io.grpc.examples.routeguide.RouteGuideGrpc.RouteGuideStub;
import io.opentracing.Tracer;
import java.util.Iterator;
import org.apache.log4j.Logger;

public class GRPCInfo {
  private static final int DEGREES_PER_METER = 8;
  private RouteGuideBlockingStub blockingStub;

  final static Logger LOGGER = Logger.getLogger(GRPCInfo.class);

  public GRPCInfo(Tracer tracer, Config config) {
    ManagedChannel channel = ManagedChannelBuilder.forTarget(config.GRPC_URI).usePlaintext().intercept().build();
    blockingStub = RouteGuideGrpc.newBlockingStub(channel);
  }

  public void getGRPCInfo(int latitude, int longitude, int length) {
    int lowLat = latitude - (length/2)*DEGREES_PER_METER;
    int lowLon = longitude - (length/2)*DEGREES_PER_METER;
    int hiLat = latitude + (length/2)*DEGREES_PER_METER;
    int hiLon = longitude + (length/2)*DEGREES_PER_METER;
    Rectangle request =
        Rectangle.newBuilder()
            .setLo(Point.newBuilder().setLatitude(lowLat).setLongitude(lowLon).build())
            .setHi(Point.newBuilder().setLatitude(hiLat).setLongitude(hiLon).build()).build();
    Iterator<Feature> features;
    try {
      features = blockingStub.listFeatures(request);
      for (int i = 1; features.hasNext(); i++) {
        Feature feature = features.next();
      }
    } catch (StatusRuntimeException e) {
      LOGGER.error(String.format("GRPC info failed for latitude: %s, longitude: %s, message:%s", latitude, longitude, e.toString()));
    }

  }
}
