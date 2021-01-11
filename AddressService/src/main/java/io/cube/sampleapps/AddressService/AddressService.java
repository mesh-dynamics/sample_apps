package io.cube.sampleapps.AddressService;

import static java.lang.Math.max;
import static java.lang.Math.min;

import io.cube.sampleapps.AddressService.Store.Builder;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;
import io.grpc.stub.StreamObserver;
//import io.grpc.examples.routeguide.GuiderGrpc.GuiderBlockingStub;
//import io.grpc.examples.routeguide.GuiderGrpc.GuiderStub;
//import io.grpc.examples.routeguide.RouteGuideGrpc.RouteGuideStub;
//import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * A sample gRPC server that serve the RouteGuide (see Address_Store.proto) service.
 */
public class AddressService {

	private static RestOverSql ros = null;
	private static final Logger LOGGER = Logger.getLogger(AddressService.class.getName());

	private final int port;
	private final Server server;


	static String STORE_BASE_URL = System.getenv("STORE_SERVICE_URL");
	static String StoreServerTarget = STORE_BASE_URL != null ? STORE_BASE_URL : "localhost:9000/store";
	static ManagedChannel storeServerChannel = ManagedChannelBuilder.forTarget(StoreServerTarget).usePlaintext().intercept().build();
	//	static GuiderBlockingStub guiderServerBlockingStub = GuiderGrpc.newBlockingStub(guiderServerChannel);
	// 	static GuiderStub guiderServerAsyncStub = GuiderGrpc.newStub(guiderServerChannel);


	static String RESTWRAPJDBC_BASE_URL = System.getenv("RESTWRAPJDBC_URI");
	static String RestWrapJDBCServerTarget = RESTWRAPJDBC_BASE_URL != null ? RESTWRAPJDBC_BASE_URL : "http://localhost:8086/RestWrapJDBC/restsql/";



	private static void info(String msg, Object... params) {
		LOGGER.log(Level.INFO, msg, params);
	}

	private static void warning(String msg, Object... params) {
		LOGGER.log(Level.WARNING, msg, params);
	}

	public AddressService(int port) throws IOException {
		this(port, AddressServiceUtils.getDefaultFeaturesFile());
	}

	/**
	 * Create a RouteGuide server listening on {@code port} using {@code featureFile} database.
	 */
	public AddressService(int port, URL featureFile) throws IOException {
		this(ServerBuilder.forPort(port), port, Collections.EMPTY_LIST);
	}

	/**
	 * Create a RouteGuide server using serverBuilder as a base and features as data.
	 */
	public AddressService(ServerBuilder<?> serverBuilder, int port,
		Collection<Address> features) {
		this.port = port;
		server = serverBuilder.addService(
			ServerInterceptors.intercept(new AddressServiceInternal(features)))
			.build();
		ros = new RestOverSql(RestWrapJDBCServerTarget);
	}

	/**
	 * Start serving requests.
	 */
	public void start() throws IOException {
		server.start();
		LOGGER.info("Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				try {
					AddressService.this.stop();
				} catch (InterruptedException e) {
					e.printStackTrace(System.err);
				}
				System.err.println("*** server shut down");
			}
		});
	}

	/**
	 * Stop serving requests and shutdown resources.
	 */
	public void stop() throws InterruptedException {
		if (server != null) {
			server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	/**
	 * Main method.  This comment makes the linter happy.
	 */
	public static void main(String[] args) throws Exception {
		AddressService server = new AddressService(8087);
		server.start();
		server.blockUntilShutdown();
	}


	private static class AddressServiceInternal extends io.cube.sampleapps.AddressService.AddressServiceGrpc.AddressServiceImplBase {

		private final Collection<Address> features;

		AddressServiceInternal(Collection<Address> features) {
			this.features = features;
		}

		/**
		 * Gets the {@link Address} at the requested {@link Point}. If no Address at that location
		 * exists, an unnamed Address is returned at the provided location.
		 *
		 * @param request          the requested location for the Address.
		 * @param responseObserver the observer that will receive the Address at the requested
		 *                         point.
		 */
		@Override
		public void getAddress(Point request, StreamObserver<Address> responseObserver) {

			int addressId = -1; // Default In case no address found in POSTGres table
			try {
				JSONArray rs = null;
				String addressQuery = "select address_id from address "
					+ " where latitude = ? and longitude = ? ";
				JSONArray params = new JSONArray();
				RestOverSql.addIntegerParam(params, request.getLatitude());
				RestOverSql.addIntegerParam(params, request.getLongitude());
				LOGGER.info("Inside getAddress method. Running query to fetch address");
				rs = ros.executeQuery(addressQuery, params);
				if (rs != null && rs.length() > 0) {
					addressId = rs.getJSONObject(0).getInt("address_id");
				}
			} catch (Exception sqlException) {
				LOGGER.severe("Couldn't execute get address stmt: " + sqlException.toString());
			}
			Address Address = io.cube.sampleapps.AddressService.Address.newBuilder().setAddressId(addressId).setLocation(request).build();
//			Address Address = io.cube.sampleapps.AddressService.Address.newBuilder().build();
			responseObserver.onNext(Address);
			responseObserver.onCompleted();
		}

		public static Optional<Integer> joGetIntField(JSONObject jo, String fieldName) {
			try {
				return Optional.of(jo.getInt(fieldName));
			} catch (Exception e)
			{
				return Optional.empty();
			}
		}

		public static Optional<String> joGetStrField(JSONObject jo, String fieldName) {
			try {
				return Optional.of(jo.getString(fieldName));
			} catch (Exception e)
			{
				return Optional.empty();
			}
		}

		@Override
		public void listStores(Rectangle request, StreamObserver<Store> responseObserver) {
			int left = min(request.getLo().getLongitude(), request.getHi().getLongitude());
			int right = max(request.getLo().getLongitude(), request.getHi().getLongitude());
			int top = max(request.getLo().getLatitude(), request.getHi().getLatitude());
			int bottom = min(request.getLo().getLatitude(), request.getHi().getLatitude());

			try {
				JSONArray rs = null;
				String addressQuery = "SELECT * from store, address, city, country "
					+ "WHERE "
					+ "address.latitude BETWEEN ? AND ? "
					+ "AND address.longitude BETWEEN ? AND ? "
					+ "AND store.address_id=address.address_id "
					+ "AND address.city_id = city.city_id "
					+ "AND city.country_id=country.country_id "
					+ "GROUP BY "
					+ "store_id";
				JSONArray params = new JSONArray();
				RestOverSql.addIntegerParam(params, bottom);
				RestOverSql.addIntegerParam(params, top);
				RestOverSql.addIntegerParam(params, left);
				RestOverSql.addIntegerParam(params, right);
				LOGGER.info("Inside listStore method. Running query to fetch stores");
				rs = ros.executeQuery(addressQuery, params);
				if (rs != null) {
					for (int i = 0; i < rs.length(); i++) {
						JSONObject jo = rs.getJSONObject(i);
						Builder storeBuilder = Store.newBuilder();
						joGetIntField(jo, "store_id").map(storeBuilder::setStoreId);
						joGetIntField(jo, "address_id").map(storeBuilder::setAddressId);
						joGetStrField(jo, "address").map(storeBuilder::setAddress);
						joGetStrField(jo, "district").map(storeBuilder::setDistrict);
						joGetStrField(jo, "city").map(storeBuilder::setCity);
						joGetStrField(jo, "country").map(storeBuilder::setCountry);
						joGetIntField(jo, "latitude").map(storeBuilder::setLatitude);
						joGetIntField(jo, "longitude").map(storeBuilder::setLongitude);
						joGetStrField(jo, "description").map(storeBuilder::setDescription);

						Store store = storeBuilder.build();


//						.setStoreId(jo.getInt("store_id"))
//							.setAddressId(jo.getInt("address_id"))
//							.setAddress(jo.getString("address"))
//							.setDistrict(jo.getString("district"))
//							.setCity(jo.getString("city"))
//							.setCountry(jo.getString("country"))
//							.setLatitude(jo.getInt("latitude"))
//							.setLongitude(jo.getInt("longitude"))
//							.setDescription(jo.getString("description"))
//							.build();
						responseObserver.onNext(store);
					}
				}
			} catch (Exception sqlException) {
				LOGGER.severe("Couldn't execute get store stmt. Message: " + sqlException.toString()
					+ "\nStack Trace:" + Arrays.toString(sqlException.getStackTrace()));
			} finally {
				responseObserver.onCompleted();
			}
		}
	}

}
