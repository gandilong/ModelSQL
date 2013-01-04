package org.com.thang.processor.common;

import org.com.thang.model.mate.Table;
import org.com.thang.processor.ModelTableProcessor;
import org.com.thang.utils.StringUtils;

/**
 * 从实体类到表的处理。
 * @author Gandilong at 2012-12-29下午01:29:08
 *
 */
public class MTProcessor implements ModelTableProcessor {

	private static final ModelTableProcessor instance=new MTProcessor();
	
	@Override
	public String modelToTable(Class<?> model) {
		if(null!=model){
			if(model.isAnnotationPresent(org.com.thang.model.mate.Table.class)){
				return model.getAnnotation(org.com.thang.model.mate.Table.class).value();
			}
			return StringUtils.addUnderline(model.getSimpleName());
		}
		return null;
	}

	@Override
	public String tableToModel(Class<?> model) {
		if(null!=model){
			return model.getSimpleName();
		}
		return null;
	}
	
	public static ModelTableProcessor getInstance(){
		return MTProcessor.instance;
	}

}
