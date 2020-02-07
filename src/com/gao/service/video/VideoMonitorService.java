package com.gao.service.video;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gao.dao.BaseDao;
import com.gao.model.Video;

@Service("videoMonitorService")
public class VideoMonitorService<T> {
	private BaseDao dao;

	public BaseDao getDao() {
		return dao;
	}

	@Resource
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	public List<Video> queryVideos(String wellStation, String items,
			Integer orgId, int offset, int pageSize, Class<T> clazz) {

		List<Video> list = null;
		// String orgIds = this.dao.getUserOrgIds(orgId);
		String datas = "";
		String jc = "";
		String orgIds = this.dao.getUserOrgIds(orgId);
		if (!(wellStation.equals(""))) {
			jc = " and v.jc like '%" + wellStation + "%'";
		}
		if (!(items.equals(""))) {
			datas = " and v.jc in (" + items + ")";
		}
		String hql = "select v from Video as v,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgIds + ")" + jc + datas + " order by v.jlbh";
		try {
			list = this.dao.getListForPage(offset, pageSize, hql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Integer getTotalRows(String wellStation, String items, Integer orgId) {
		// String orgIds = this.dao.getUserOrgIds(orgId);
		String jc = "";
		String datas = "";
		String orgIds = this.dao.getUserOrgIds(orgId);
		if (!(wellStation.equals(""))) {
			jc = " and v.jc like '%" + wellStation + "%'";
		}
		if (!(items.equals(""))) {
			datas = " and v.jc in (" + items + ")";
		}
		String hql = "select v from Video as v,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgIds + ")" + jc + datas;
		return this.dao.getRecordCountRows(hql);
	}

	public List<T> loadWellSations(Class<T> clazz) {
		return dao.getAllObjects(clazz);
	}

}
