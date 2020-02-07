package com.gao.service.base;

import java.util.List;

import org.springframework.stereotype.Service;

@Service("baseParseService")
public class BaseParseService<T> extends BaseService<T> {

	// 获取日期
	@SuppressWarnings("rawtypes")
	public List reportDateJssj(String sql) {
		return this.getBaseDao().MonthJssj(sql);
	}

}
