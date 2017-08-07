import nl.wur.fbr.om.conversion.CoreInstanceFactory;
import nl.wur.fbr.om.exceptions.ConversionException;
import nl.wur.fbr.om.exceptions.UnitOrScaleCreationException;
import nl.wur.fbr.om.factory.InstanceFactory;
import nl.wur.fbr.om.model.UnitAndScaleSet;
import nl.wur.fbr.om.model.measures.Measure;
import nl.wur.fbr.om.model.units.Unit;
import nl.wur.fbr.om.om18.set.OM;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by nuwantha on 8/7/17.
 */
public class UnitConverter {


    Pattern pattern = Pattern.compile("[0-9]+");


    public JSONArray createdModifiedJsonArray(JSONArray jsonArray) throws UnitOrScaleCreationException, ConversionException {

        JSONArray modifiedJsonArray = new JSONArray();
        for (Object jsonObject : jsonArray) {
            JSONObject modifiedJsonObject = createModifiedQuestion((JSONObject) jsonObject);
            modifiedJsonArray.add(modifiedJsonObject);
        }
        return modifiedJsonArray;
    }


    public  JSONObject createModifiedQuestion(JSONObject jsonObject) throws UnitOrScaleCreationException, ConversionException {
        InstanceFactory factory = new CoreInstanceFactory();
        UnitAndScaleSet addUnitAndScaleSet = factory.addUnitAndScaleSet(OM.class);
        UnitIdentifier unitIdentifier = new UnitIdentifier();
        String question = (String)jsonObject.get("question");
        System.out.println(question);
        String[] tokens = question.split(" ");
        String modifiedQuestion="";
        int i=0;

        Set<Unit> allUnits = addUnitAndScaleSet.getAllUnits();

        for (String token : tokens) {
            if( pattern.matcher(token).find()){
                String isUnit = tokens[i+1];
                Unit unit=null;
                Unit baseUnit = null;
                ArrayList<Unit> unitList = isUnit(isUnit, allUnits);
                if(!unitList.isEmpty()){
                    unit=unitList.get(0);
                    baseUnit = unitList.get(1);
                }
                if(unit != null){
                    System.out.println("location");
                    Measure m1= factory.createScalarMeasure(Double.parseDouble(token),unit);
                    Measure m2 = factory.convertToUnit(m1,baseUnit);
                    tokens[i]=String.valueOf(m2.getScalarValue());
                    System.out.println(tokens[i]);
                    tokens[i+1]=baseUnit.getName();
                    System.out.println("unit is found  : "+ unit.getName() +" "+ m2.getScalarValue());
                }

            }
            modifiedQuestion+=" "+tokens[i];
            i++;

        }

        System.out.println("modified question "+ modifiedQuestion);

        jsonObject.replace("question", modifiedQuestion);
        return jsonObject;

    }

    public ArrayList<Unit> isUnit(String token, Set<Unit> allUnits){
        Unit baseUnit=null;
        Unit foundUnit=null;
        ArrayList<Unit> unitList = new ArrayList<Unit>();

        boolean isFound=false;
        for (Unit unit : allUnits) {
            if(unit==null) continue;
            if(unit.getName().equals(token) || unit.getSymbol().equals(token)){
                isFound=true;
            }else{
                List<String> alternativeNames = unit.getAlternativeNames();
                for (String alternativeName : alternativeNames) {
                    if(alternativeName.equals(token)){
                        isFound=true;
                        break;
                    }
                }
                List<String> alternativeSymbols = unit.getAlternativeSymbols();
                for (String alternativeSymbol:alternativeSymbols) {
                    if(alternativeSymbol.equals(token)){
                        isFound=true;
                        break;
                    }
                }
            }
            if(isFound){
                foundUnit=unit;
                baseUnit = new UnitIdentifier().getBaseUnit(unit);
                unitList.add(foundUnit);
                unitList.add(baseUnit);
                System.out.println("unit is found");
                break;
            }

        }
        return unitList;
    }
}
