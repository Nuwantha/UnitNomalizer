import com.sun.istack.internal.logging.Logger;
import nl.wur.fbr.om.conversion.CoreInstanceFactory;
import nl.wur.fbr.om.exceptions.ConversionException;
import nl.wur.fbr.om.exceptions.UnitOrScaleCreationException;
import nl.wur.fbr.om.factory.InstanceFactory;
import nl.wur.fbr.om.model.UnitAndScaleSet;
import nl.wur.fbr.om.model.measures.Measure;
import nl.wur.fbr.om.model.units.Unit;
import nl.wur.fbr.om.om18.set.OM;
import org.eclipse.rdf4j.query.algebra.Str;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;

/**
 * Created by nuwantha on 8/7/17.
 */
public class UnitNormalizer {
    public static void main(String args[]){
        System.out.println("hai");
        try {

            org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
            UnitConverter unitConverter=new UnitConverter();
            Pattern pattern = Pattern.compile("[0-9]+");

            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("/home/nuwantha/FYP/MyImplementation/UnitNomalizer/UnitNormalizer/src/main/resources/word_prob.json"));
            System.out.println(jsonArray.size());
            JSONArray modifiedJsonArray = unitConverter.createdModifiedJsonArray(jsonArray);
            FileWriter file = new FileWriter( "/home/nuwantha/FYP/MyImplementation/UnitNomalizer/UnitNormalizer/src/main/resources/word_prob_modified.json",false);
            file.write(modifiedJsonArray.toJSONString());
            file.flush();



        }
        catch (FileNotFoundException e) {}
        catch (IOException ex) {}
        catch (UnitOrScaleCreationException ex) {}
        catch (ConversionException ex) {}
        catch (org.json.simple.parser.ParseException e) {}


    }

}
