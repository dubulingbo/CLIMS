package cn.clims.service.dataDictionary;

import java.util.List;

import cn.clims.pojo.DataDictionary;

public interface DataDictionaryService {

	public List<DataDictionary> getDicListByTypeCode(DataDictionary dic) throws Exception;

}
