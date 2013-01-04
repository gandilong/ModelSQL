package org.com.thang.processor.common;


import org.com.thang.processor.FieldColumnTypeProcessor;
import org.com.thang.utils.ModelUtils;

/**'
 * 从field到column的类型处理。
 * @author Gandilong at 2012-12-29下午01:27:58
 *
 */
public class FCTProcessor implements FieldColumnTypeProcessor {

	private static final FieldColumnTypeProcessor instance=new FCTProcessor();
	
	@Override
	public String valueFormat(Class<?> model, String fieldName,String fieldValue) {
		String result="";
		if(ModelUtils.isString(model, fieldName)){
			result+="'"+fieldValue+"'";
		}else{
			result+=fieldValue;
		}
		return result;
	}

	public static FieldColumnTypeProcessor getInstance(){
		return FCTProcessor.instance;
	}

}
