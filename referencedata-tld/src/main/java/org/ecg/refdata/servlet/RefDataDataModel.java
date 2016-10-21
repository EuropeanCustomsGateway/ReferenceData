package org.ecg.refdata.servlet;

import com.cc.framework.ui.model.ListDataModel;
import org.ecg.refdata.query.QueryResult;

/**
 * Class implements <code>ListDataModel</code> interface from Common Controls.
 * It extends <code>QueryResult</code> class to be used in Common Controls
 * enviroment.
 * 
 */
public class RefDataDataModel implements ListDataModel {

	private QueryResult queryResult;
	
	/**
	 * Constructor gets just one simple parameter <code>QueryResult</code> object.
	 * 
	 * @param queryResult <code>QueryResult</code> instance.
	 */
	RefDataDataModel(QueryResult queryResult) {
		this.queryResult = queryResult;
	}
	
	/**
	 * Method returns total amount of items from <code>QueryResult</code> instance.
	 * 
	 * @see com.cc.framework.ui.model.ListDataModel#size()
	 */
	public int size() {
		return queryResult.getTotalCount();
	}
	
	/** 
	 * Returns unique key for each index.
	 * 
	 * @see com.cc.framework.ui.model.ListDataModel#getUniqueKey(int)
	 */
	public String getUniqueKey(int index) {
		return Integer.toString(index - queryResult.getItemsStart());
	}
	
	/** 
	 * Method returns required item form <code>QueryResult</code> instance.
	 * Gets just one parameter.
	 * 
	 * @see com.cc.framework.ui.model.ListDataModel#getElementAt(int)
	 * @param index the index of required item.
	 */
	public Object getElementAt(int index) {
		return queryResult.getItems().get(index-queryResult.getItemsStart());
	}

}
