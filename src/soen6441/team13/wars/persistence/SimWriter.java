package soen6441.team13.wars.persistence;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SerializationUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
/**
 * This class provides the capability to persist war simulations to disk
 * in a binary format, in XML format and JSON format.
 *
 */
public class SimWriter {
	/**
	 * Serialise world and all transitive objects in a file.
	 * @param <T> type of object to serialise.
	 * @param fileName where to save
	 * @return
	 */
	public static void toBinary(Serializable obj, String fileName){
		byte[] data = SerializationUtils.serialize(obj);
		try {
			FileUtils.writeByteArrayToFile(new File(fileName), data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Serialise world and all transitive objects in a file.
	 * @param <T> type of object to serialise.
	 * @param fileName where to save
	 * @return
	 */
	public static void toXML(Object bean, String fileName){
		try {
			XStream xstream = new XStream();
			String xml = xstream.toXML(bean);
			FileUtils.writeStringToFile(new File(fileName), xml);	
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Serialise world and all transitive objects in a file.
	 * @param <T> type of object to serialise.
	 * @param fileName where to save
	 * @return
	 */

	public static void toJSON(Object bean, String fileName){
		try {
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			xstream.setMode(XStream.NO_REFERENCES);
			String xml = xstream.toXML(bean);
			FileUtils.writeStringToFile(new File(fileName), xml);	
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
