package com.viv.sh.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipFile;

import org.springframework.web.multipart.MultipartFile;

import com.viv.sh.dao.DataEntryDao;
import com.viv.sh.dto.PriceDto;
import com.viv.sh.util.Constants;
import com.viv.sh.util.HttpConUtil;

public class DataEntryService {
	private DataEntryDao dataEntryDao;

	public void setDataEntryDao(DataEntryDao dataEntryDao) {
		this.dataEntryDao = dataEntryDao;
	}

	public void insertSampleData(String name) {
		dataEntryDao.insertSampleData(name);
	}

	public List<Object[]> getQuoteAndTableName(String tableName) {
		return dataEntryDao.getQuoteAndTableName(tableName);
	}

	public void manufactureData(String tableName) {
		dataEntryDao.manufactureData(tableName);
	}

	public void delDataComp() {
		dataEntryDao.delDataComp();
	}

	// Calculate the percentage change
	public void updateDataComp() {
		dataEntryDao.updateDataComp();
	}

	public void insertStockData(PriceDto dto) {
		dataEntryDao.insertStockData(dto);
	}

	public PriceDto getCurrentPriceObject(int numericCode) {
		PriceDto dto = null;
		String currentURLResp = HttpConUtil.getHttpResponse(Constants.curDataURL + numericCode, 10000);
		if (currentURLResp != null) {
			String[] splitAr = currentURLResp.split(",");
			if (splitAr.length == 5) {
				dto = new PriceDto();
				dto.setCurrentPrice(Double.parseDouble(splitAr[4]));
				dto.setOpenPrice(Double.parseDouble(splitAr[1]));
				System.out.println(splitAr[1]);
			}
		}
		return dto;
	}

	public boolean insertHistoryData(File file) {
		boolean retval = true;
		String fileName = file.getName();
		try {
			dataEntryDao.insertHistoryData(file);

			Files.move(Paths.get(Constants.bhavCopyPath + "un-processed\\" + fileName),
					Paths.get(Constants.bhavCopyPath + "processed\\" + fileName));
		} catch (Exception e) {
			retval = false;
			e.printStackTrace();
		}

		return retval;
	}

	public boolean insertNiftyData(Object file, String fileName, boolean isScript) {
		boolean retval = true;
//		ZipFile fis=new ZIpFil
		BufferedReader br=null;
		try {
		if(file instanceof File){
			 br = new BufferedReader(new FileReader((File)file));
		}else if(file instanceof MultipartFile){
			br=new BufferedReader(new InputStreamReader(((MultipartFile) file).getInputStream()));
		}

		
			System.out.println(fileName.substring(0, 1));
			if (fileName.substring(0, 2).equalsIgnoreCase("cm")) {
				dataEntryDao.insertNIftEquityData(br,fileName);
			} else if (fileName.substring(0, 2).equalsIgnoreCase("fo")) {
				dataEntryDao.insertNIftFutureData(br,fileName);
			} else if (fileName.substring(0, 2).equalsIgnoreCase("op")) {
				dataEntryDao.insertNIftOptionData(br,fileName);
			}else if(fileName.substring(0, 2).equalsIgnoreCase("pd")){
				dataEntryDao.insertNIftySecurityData(br,fileName);
			}
			
			if (isScript) {
				
				Files.move(Paths.get(Constants.niftBhavCopy + "un-processed\\" + fileName), Paths.get(Constants.niftBhavCopy + "processed\\" + fileName));
			}
		} catch (Exception e) {
			retval = false;
			e.printStackTrace();
		}

		return retval;
	}

}
