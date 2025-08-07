package com.cosog.service.operationMaintenance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosog.service.base.BaseService;
import com.cosog.service.base.CommonDataService;

@Service("operationMaintenanceService")
public class OperationMaintenanceService<T> extends BaseService<T>  {
	@Autowired
	private CommonDataService service;
}
