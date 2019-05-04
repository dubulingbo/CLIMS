package cn.clims.dao.dataDictionary;

import java.util.List;

import cn.clims.pojo.DataDictionary;

public interface DataDictionaryMapper {
	public List<DataDictionary> getDataDictionaryList(DataDictionary dataDictionary)throws Exception;
	
	
	
	
}
