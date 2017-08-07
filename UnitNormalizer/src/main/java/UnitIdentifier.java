import nl.wur.fbr.om.model.units.Unit;
import nl.wur.fbr.om.om18.set.OM;

import java.util.Arrays;

/**
 * Created by nuwantha on 8/7/17.
 */
public class UnitIdentifier {

    String [] lengthUnit= {"m","dm","cm","km","in"};
    String [] timeUnit={"hour","second","minute", "day"};


    public Unit getBaseUnit(Unit unit){
        if(Arrays.asList(timeUnit).contains(unit.getName())){
            return OM.SecondTime;
        }else if(Arrays.asList(lengthUnit).contains(unit.getSymbol())){
            return OM.Metre;

        }



        return null;
    }

}
