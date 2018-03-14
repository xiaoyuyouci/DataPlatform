package com.winsafe.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.dao.DcQrCodeUsageRatioMapper;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.service.DcQrCodeUsageRatioService;

@Service
public class DcQrCodeUsageRatioServiceImpl implements DcQrCodeUsageRatioService {

	@Autowired 
	DcQrCodeUsageRatioMapper dcQrCodeUsageRatioMapper;

	@Override
	public List<Map<String, Object>> selectQrCodeUsageRate(Map<String, Object> filter) {
		return dcQrCodeUsageRatioMapper.selectQrCodeUsageRate(filter);
	}

	@Override
	@Transactional
	public int insertQrCodeUsageRate(Map<String, Object> filter) {
		return dcQrCodeUsageRatioMapper.insertQrCodeUsageRate(filter);
	}

	@Override
	@Transactional
	public int updateQrCodeUsageRate(Map<String, Object> filter) {
		return dcQrCodeUsageRatioMapper.updateQrCodeUsageRate(filter);
	}

	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getUploadInfo(Map<String, Object> filter, DataSourceName datasource) {
		return dcQrCodeUsageRatioMapper.getUploadInfo(filter);
	}

	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getOutInfo(Map<String, Object> filter, DataSourceName datasource) {
		return dcQrCodeUsageRatioMapper.getOutInfo(filter);
	}

	@Override
	@DataSourceAnnotation
	public List<Map<String, Object>> getFileInfo(Map<String, Object> filter, DataSourceName dsn) {
		return dcQrCodeUsageRatioMapper.getFileInfo(filter);
	}

}
