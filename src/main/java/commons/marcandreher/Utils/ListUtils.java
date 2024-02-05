package commons.marcandreher.Utils;

import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import commons.marcandreher.Commons.Flogger;
import commons.marcandreher.Commons.Flogger.Prefix;

public class ListUtils {

    /**
     * Prints the key-value pairs of a HashMap.
     * If the value is an instance of a Class, it is converted to a JSON string before printing.
     * 
     * @param hashMap the HashMap to print
     * @throws JsonProcessingException if there is an error while converting the value to JSON
     */
    public static void printHashMap(Map<?, ?> hashMap) throws JsonProcessingException {
        for (Map.Entry<?, ?> entry : hashMap.entrySet()) {
            if (entry.getValue().getClass() instanceof Class) {
                ObjectMapper ob = new ObjectMapper();
                String mapValue = ob.writeValueAsString(entry.getValue());
                System.out.println(entry.getKey() + ": " + mapValue);
            } else {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    /**
     * Prints the elements of an ArrayList.
     * The ArrayList is converted to a JSON string before printing.
     * 
     * @param arrayList the ArrayList to print
     * @throws JsonProcessingException if there is an error while converting the ArrayList to JSON
     */
    public static void printArrayList(ArrayList<?> arrayList) throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        String listValue = ob.writeValueAsString(arrayList);
        System.out.println(listValue);
    }

    /**
     * Prints the key-value pairs of a HashMap using a logger.
     * If the value is an instance of a Class, it is converted to a JSON string before printing.
     * 
     * @param hashMap the HashMap to print
     * @param logger the logger to use for printing
     * @throws JsonProcessingException if there is an error while converting the value to JSON
     */
    public static void printHashMap(Map<?, ?> hashMap, Flogger logger) throws JsonProcessingException {
        for (Map.Entry<?, ?> entry : hashMap.entrySet()) {
            if (entry.getValue().getClass() instanceof Class) {
                ObjectMapper ob = new ObjectMapper();
                String mapValue = ob.writeValueAsString(entry.getValue());
                logger.log(Prefix.INFO, entry.getKey() + ": " + mapValue, 1);
            } else {
                logger.log(Prefix.INFO, entry.getKey() + ": " + entry.getValue(), 1);
            }
        }
    }

    /**
     * Prints the elements of an ArrayList using a logger.
     * The ArrayList is converted to a JSON string before printing.
     * 
     * @param arrayList the ArrayList to print
     * @param logger the logger to use for printing
     * @throws JsonProcessingException if there is an error while converting the ArrayList to JSON
     */
    public static void printArrayList(ArrayList<?> arrayList, Flogger logger) throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        String listValue = ob.writeValueAsString(arrayList);
        logger.log(Prefix.INFO, listValue, 1);
    }

}
