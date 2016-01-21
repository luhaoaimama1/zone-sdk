package and.utlis.rex;
import java.util.List;

import and.utlis.rex.Rex_Phone.PhoneEntity;


public class RexUtils {
	
	public static List<PhoneEntity>  byContextGetPhone(String str){
		return Rex_Phone.byContextGetPhone(str);
	}
}
