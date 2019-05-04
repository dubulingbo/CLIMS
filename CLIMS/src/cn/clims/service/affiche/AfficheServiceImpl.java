package cn.clims.service.affiche;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.clims.dao.affiche.AfficheMapper;
import cn.clims.pojo.Affiche;


@Service
public class AfficheServiceImpl implements AfficheService {
	@Resource
	private AfficheMapper afficheMapper;
	
	
	public List<Affiche> getList(Affiche affiche) throws Exception {
		// TODO Auto-generated method stub
		return afficheMapper.getList(affiche);
	}

	public Affiche getAffiche(Affiche affiche) throws Exception {
		// TODO Auto-generated method stub
		return afficheMapper.getAffiche(affiche);
	}

	public int addAffiche(Affiche affiche) throws Exception {
		// TODO Auto-generated method stub
		return afficheMapper.addAffiche(affiche);
	}

	public int modifyAffiche(Affiche affiche) throws Exception {
		// TODO Auto-generated method stub
		return afficheMapper.modifyAffiche(affiche);
	}

	public int deleteAffiche(Affiche affiche) throws Exception {
		// TODO Auto-generated method stub
		return afficheMapper.deleteAffiche(affiche);
	}

	public int count() throws Exception {
		// TODO Auto-generated method stub
		return afficheMapper.count();
	}

	public List<Affiche> getAfficheList(Affiche affiche) throws Exception {
		// TODO Auto-generated method stub
		return afficheMapper.getAfficheList(affiche);
	}

	public int portalCount() throws Exception {
		// TODO Auto-generated method stub
		return afficheMapper.portalCount();
	}

	public List<Affiche> getPortalAfficheList(Affiche affiche) throws Exception {
		// TODO Auto-generated method stub
		return afficheMapper.getPortalAfficheList(affiche);
	}

}
