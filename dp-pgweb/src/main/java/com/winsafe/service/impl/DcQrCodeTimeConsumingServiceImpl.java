package com.winsafe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.DcQrCodeTimeConsumingMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.DcQrCodeTimeConsumingService;

@Service
public class DcQrCodeTimeConsumingServiceImpl implements DcQrCodeTimeConsumingService {

	@Autowired DcQrCodeTimeConsumingMapper dcQrCodeTimeConsumingMapper;
	
	@Override
	@DataSourceAnnotation
	public Map<String, Object> getAppliedFileInfo(Map<String, Object> filter, DataSourceName datasource) {
		return dcQrCodeTimeConsumingMapper.getAppliedFileInfo(filter);
	}

	@Override
	@DataSourceAnnotation
	public Map<String, Object> getUploadProduceReportInfo(Map<String, Object> filter, DataSourceName datasource) {
		return dcQrCodeTimeConsumingMapper.getUploadProduceReportInfo(filter);
	}

	@Override
	@DataSourceAnnotation
	public Map<String, Object> getOutInfo(Map<String, Object> filter, DataSourceName datasource) {
		return dcQrCodeTimeConsumingMapper.getOutInfo(filter);
	}

	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getQrCodeUsageRate(Map<String, Object> filter, DataSourceName datasource) {
		return dcQrCodeTimeConsumingMapper.getQrCodeUsageRate(filter);
	}

}
