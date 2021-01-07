package io.cube.sampleapps.AddressService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;


import com.google.protobuf.util.JsonFormat;

public class AddressServiceUtils {
	private static final double COORD_FACTOR = 1e7;

	/**
	 * Gets the latitude for the given point.
	 */
	public static double getLatitude(Point location) {
		return location.getLatitude() / COORD_FACTOR;
	}

	/**
	 * Gets the longitude for the given point.
	 */
	public static double getLongitude(Point location) {
		return location.getLongitude() / COORD_FACTOR;
	}

	/**
	 * Gets the default features file from classpath.
	 */
	public static URL getDefaultFeaturesFile() {
		return AddressService.class.getResource(
			"route_guide_db.json");
	}

	/**
	 * Parses the JSON input file containing the list of features.
	 */
	public static List<Address> parseFeatures(URL file) throws IOException {
		InputStream input = file.openStream();
		try {
			Reader reader = new InputStreamReader(input, Charset.forName("UTF-8"));
			try {
				AddressDatabase.Builder database = AddressDatabase.newBuilder();
				JsonFormat.parser().merge(reader, database);
				return database.getAddressList();
			} finally {
				reader.close();
			}
		} finally {
			input.close();
		}
	}

//	/**
//	 * Indicates whether the given Address exists (i.e. has a valid name).
//	 */
//	public static boolean exists(Address Address) {
//		return Address != null && !Address.getName().isEmpty();
//	}
}
