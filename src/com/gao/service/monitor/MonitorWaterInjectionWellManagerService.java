package com.gao.service.monitor;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gao.dao.BaseDao;

/**<p>描述：注入井处理服务类Service</p>
 * @author gao 2014-06-10
 *
 * @param <T>
 */
@Service("monitorWaterInjectionWellManagerService")
public class MonitorWaterInjectionWellManagerService<T> {

	private BaseDao dao;

	public Integer getTotalRows(Integer orgId) {
		String orgIds = this.dao.getUserOrgIds(orgId);
		String hql = " select v From MonitorWaterInjectionWell as v ,Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgIds + ")";
		return this.dao.getRecordCountRows(hql);
	}

	public List<T> loadmonitorWaterInjectionWell(int offset,
			int pageSize, Integer orgId) throws Exception {
		String orgIds = this.dao.getUserOrgIds(orgId);
		String hql = "select  v  From MonitorWaterInjectionWell  v, Org as o where 1=1 and v.dwbh=o.orgCode and o.orgId in("
				+ orgIds + ") order by v.jh";
		return dao.getListForPage(offset, pageSize, hql);
	}

	public BaseDao getDao() {
		return dao;
	}

	@Resource(name = "baseDao")
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
