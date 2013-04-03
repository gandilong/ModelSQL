package com.thang.processor;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;

public class ModelHandler<T> implements ResultSetHandler<T>{

	private static final ModelHandler<?> modelHandler=new ModelHandler();
	

    /**
     * The RowProcessor implementation to use when converting rows
     * into beans.
     */
    private final RowProcessor convert=ModelProcessor.getInstance();

    private ModelHandler(){}
    

    /**
     * Convert the first row of the <code>ResultSet</code> into a bean with the
     * <code>Class</code> given in the constructor.
     * @param rs <code>ResultSet</code> to process.
     * @return An initialized JavaBean or <code>null</code> if there were no
     * rows in the <code>ResultSet</code>.
     *
     * @throws SQLException if a database access error occurs
     * @see org.apache.commons.dbutils.ResultSetHandler#handle(java.sql.ResultSet)
     */
    @Override
    public T handle(ResultSet rs) throws SQLException {
        return rs.next() ? this.convert.toBean(rs, (Class<T>)null) : null;
    }

	@Override
	public T handle(ResultSet rs, Class<?> type) throws SQLException {
		return (T) (rs.next() ? this.convert.toBean(rs,type) : null);
	}
	
	public static ModelHandler<?> getInstance(){
		return modelHandler;
	}
	
}
