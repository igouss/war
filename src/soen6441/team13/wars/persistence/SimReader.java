package soen6441.team13.wars.persistence;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SerializationUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
/**
 * This class is responsible for reading a simulation that has been persisted 
 * to disk it supports a binary format, and XML format and a JSON format.
 * 
 *
 */
public class SimReader {
	
	/**
	 * Deserialize world from a binary file.
	 * @param <T> Type of the serialized object in a file.
	 * @param fileName with persisted world.
	 * @return World
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromBinary(String fileName){
		T result = null;
		try {
			byte[] bytes = FileUtils.readFileToByteArray(new File(fileName) );
			result = (T)SerializationUtils.deserialize(bytes);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	/**
	 * Deserialize world from a XML formatted file.
	 * @param <T> Type of the serialized object in a file.
	 * @param fileName with persisted world.
	 * @return World
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXML(String fileName){
		T result = null;
		try {
			XStream xstream = new XStream();
			String xml = FileUtils.readFileToString(new File(fileName));
			result = (T)xstream.fromXML(xml);
		} catch (IOException e) {
			// ignore, return null
		}
		return result;
	}
	/**
	 * Deserialize world from a JSON formatted file.
	 * @param <T> Type of the serialized object in a file.
	 * @param fileName with persisted world.
	 * @return World
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJSON(String fileName){
		T result = null;
		try {
			XStream xstream = new XStream(new JettisonMappedXmlDriver());
			String json = FileUtils.readFileToString(new File(fileName));
			result = (T)xstream.fromXML(json);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return result;
	}	
}