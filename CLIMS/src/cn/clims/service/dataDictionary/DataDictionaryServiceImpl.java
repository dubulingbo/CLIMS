package cn.clims.service.dataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.dataDictionary.DataDictionaryMapper;
import cn.clims.pojo.DataDictionary;

@Service
public class DataDictionaryServiceImpl implements DataDictionaryService {
	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	
	@Override
	public List<DataDictionary> getDicListByTypeCode(DataDictionary dic) throws Exception {
		return dataDictionaryMapper.getDataDictionaryList(dic);
	}

}
