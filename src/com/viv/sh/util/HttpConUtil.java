package com.viv.sh.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpConUtil {

	
	public static String getHttpResponse(String str, int waitTimeInMillis){
		long sttime=System.currentTimeMillis();
		String strGetResponseBody = null;
		GetMethod getMethod = new GetMethod(str);
		getMethod.setFollowRedirects(true);
			try {
				HttpClient client = new HttpClient();
				client.getHttpConnectionManager().getParams().setConnectionTimeout(waitTimeInMillis);
				client.getHttpConnectionManager().getParams().setSoTimeout(waitTimeInMillis);
				getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
			    		new DefaultHttpMethodRetryHandler(1, true));
				int iGetResultCode = client.executeMethod(getMethod);
				strGetResponseBody = getMethod.getResponseBodyAsString();
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("API not responding in time "+str+"-> even after " +((System.currentTimeMillis()-sttime))+ "MS");
				strGetResponseBody=null;
			}
			finally{
				getMethod.releaseConnection();

			}
			// System.out.println(str+"->Time Taken="+((System.currentTimeMillis()-sttime)));
			return strGetResponseBody;
	}
	
	
	public static String postHttpResponse(String str, int waitTimeInMillis, HashMap<String, String> paramMap){
		long sttime=System.currentTimeMillis();
		String strGetResponseBody = null;
		PostMethod postMethod = new PostMethod(str);
//		postMethod.setFollowRedirects(true);
			try {
				HttpClient client = new HttpClient();
				postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				postMethod.setRequestHeader("accept","application/json");
				postMethod.setRequestHeader("accept-encoding","gzip, deflate");
				postMethod.setRequestHeader("accept-language","en-US,en;q=0.8");
				postMethod.setRequestHeader("user-agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
				client.getHttpConnectionManager().getParams().setConnectionTimeout(waitTimeInMillis);
				client.getHttpConnectionManager().getParams().setSoTimeout(waitTimeInMillis);
				postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
			    		new DefaultHttpMethodRetryHandler(1, true));
//				postMethod.getParams().setParameter("Content-Type","application/x-www-form-urlencoded");
				for(Map.Entry<String, String> entry : paramMap.entrySet()) {
				    String key = entry.getKey();
				    String value = entry.getValue();
				    postMethod.setParameter(key,value);
				    // do what you have to do here
				    // In your case, an other loop.
				}
				/*
				postMethod.setParameter("__EVENTTARGET","");
						postMethod.setParameter("__EVENTARGUMENT","");
								postMethod.setParameter("__VIEWSTATE","%2FwEPDwUKMTE3MjAwNjEyNw9kFgJmD2QWAgIDD2QWAgIBD2QWCgIdDxYCHgVzdHlsZQUJZGlzcGxheTo7ZAIjDxYCHwAFDWRpc3BsYXk6bm9uZTsWAgIDDxAPFgYeDURhdGFUZXh0RmllbGQFCXNjcmlwX2dycB4ORGF0YVZhbHVlRmllbGQFCXNjcmlwX2dycB4LXyFEYXRhQm91bmRnZBAVDgFBAUIBRQJJRgJJVAFNAk1UAVABVAJYQwJYRAJYVAFaAlpQFQ4BQQFCAUUCSUYCSVQBTQJNVAFQAVQCWEMCWEQCWFQBWgJaUBQrAw5nZ2dnZ2dnZ2dnZ2dnZ2RkAiUPFgIfAAUNZGlzcGxheTpub25lOxYCAgMPEGQQFSoOUyZQIEJTRSBTRU5TRVgLUyZQIEJTRSAxMDALUyZQIEJTRSAyMDALUyZQIEJTRSA1MDAOUyZQIEJTRSBBbGxDYXAMUyZQIEJTRSBBVVRPDlMmUCBCU0UgQkFOS0VYF1MmUCBCU0UgQmFzaWMgTWF0ZXJpYWxzFVMmUCBCU0UgQ0FQSVRBTCBHT09EUxBTJlAgQlNFIENBUkJPTkVYL1MmUCBCU0UgQ29uc3VtZXIgRGlzY3JldGlvbmFyeSBHb29kcyAmIFNlcnZpY2VzGVMmUCBCU0UgQ09OU1VNRVIgRFVSQUJMRVMMUyZQIEJTRSBDUFNFElMmUCBCU0UgRE9MTEVYIDEwMBJTJlAgQlNFIERPTExFWCAyMDARUyZQIEJTRSBET0xMRVggMzAOUyZQIEJTRSBFbmVyZ3kiUyZQIEJTRSBGYXN0IE1vdmluZyBDb25zdW1lciBHb29kcw9TJlAgQlNFIEZpbmFuY2UPUyZQIEJTRSBHUkVFTkVYElMmUCBCU0UgSGVhbHRoY2FyZSJTJlAgQlNFIEluZGlhIEluZnJhc3RydWN0dXJlIEluZGV4IVMmUCBCU0UgSW5kaWEgTWFudWZhY3R1cmluZyBJbmRleBNTJlAgQlNFIEluZHVzdHJpYWxzHlMmUCBCU0UgSW5mb3JtYXRpb24gVGVjaG5vbG9neQtTJlAgQlNFIElQTxBTJlAgQlNFIExhcmdlQ2FwDVMmUCBCU0UgTUVUQUwOUyZQIEJTRSBNaWRDYXAbUyZQIEJTRSBNaWRDYXAgU2VsZWN0IEluZGV4EVMmUCBCU0UgT0lMICYgR0FTDVMmUCBCU0UgUE9XRVILUyZQIEJTRSBQU1UOUyZQIEJTRSBSRUFMVFkRUyZQIEJTRSBTRU5TRVggNTAWUyZQIEJTRSBTRU5TRVggTmV4dCA1MBBTJlAgQlNFIFNtYWxsQ2FwHVMmUCBCU0UgU21hbGxDYXAgU2VsZWN0IEluZGV4D1MmUCBCU0UgU01FIElQTwxTJlAgQlNFIFRFQ0sPUyZQIEJTRSBUZWxlY29tEVMmUCBCU0UgVXRpbGl0aWVzFSoCMTYCMjICMjMCMTcCODcCNDICNTMCODgCMjUCNzcCODkCMjcCODACNjUCNDgCNDcCOTACODMCOTECNzUCODQCNzkCODYCOTICODUCNzICOTMCMzUCODECOTQCMzcCNjkCNDQCNjcCOTgCOTkCODICOTUCNzYCNDUCOTYCOTcUKwMqZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZGQCLw8WAh8ABQlkaXNwbGF5OjtkAjEPFgIfAAUNZGlzcGxheTpub25lO2QYAgUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgIFJWN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkaW1nRG93bmxvYWQFI2N0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkYnRuU3VibWl0BR5jdGwwMCRDb250ZW50UGxhY2VIb2xkZXIxJGdyZDEPPCsACgEIAmNkgYTdG8pgNPJV8GEa2dUcoeeYMaM%3D");
								postMethod.setParameter("__VIEWSTATEGENERATOR","31C5C149");
								postMethod.setParameter("__EVENTVALIDATION","%2FwEWTwLsj%2Fm4DQKYutHCDAKh5pvUAQLy68jZBALvmaf1CAKgsav2CQLq5L65BALqrYz6AQKvu6fXCAKKm9%2FaBAKLm9%2FaBAKOm9%2FaBAKym%2BfEBAKym6%2FEBAK2m9%2FaBAK2m6%2FEBAKlm9%2FaBAK5m9%2FaBAKtm%2BvEBAKtm%2B%2FEBAKtm6%2FEBAKjm9%2FaBAKjm5%2FEBAKTxNi8CQKSxOi8CQKSxNS8CQKTxMS8CQKExMS8CQKQxOi8CQKXxNS8CQKExIC%2FCQKSxNy8CQKVxMS8CQKExIy%2FCQKSxMS8CQKExOC8CQKWxNy8CQKQxIC%2FCQKQxMS8CQKLxOC8CQKExNS8CQKLxOy8CQKVxNy8CQKExNC8CQKVxIy%2FCQKExNi8CQKLxOi8CQKExNy8CQKVxOi8CQKLxNS8CQKRxNy8CQKExOy8CQKLxNC8CQKRxMS8CQKWxIy%2FCQKQxNC8CQKWxMS8CQKLxIC%2FCQKLxIy%2FCQKExOi8CQKLxNy8CQKVxNi8CQKQxNy8CQKLxNi8CQKLxMS8CQKH0768BwKJ07q8BwKJ0768BwL40JWiCgLR7%2FfzAwLR74vzAwLR75%2FzAwLR75PzAwLR74fzAwLR75vzAwLR76%2FzAwLR76PzAwK%2F5cfDAgLUzOW%2BB9Fax%2BngdiOO4v2r4Qj2l0CfaL%2BM");
								postMethod.setParameter("myDestination","%23");
								postMethod.setParameter("WINDOW_NAMER","1");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24hdfTy","AllMkt");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24hdfFL","All");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24hdfCOrder","NT");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24DDate","");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24imgDownload.x","10");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24imgDownload.y","6");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24ddlType","AllMkt");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24ddlGrp","A");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24ddlIndx","16");
								postMethod.setParameter("ctl00%24ContentPlaceHolder1%24ddlOrder","NT");
				*/
				int iGetResultCode = client.executeMethod(postMethod);
				strGetResponseBody = postMethod.getResponseBodyAsString();
//				System.out.println(strGetResponseBody);
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("API not responding in time "+str+"-> even after " +((System.currentTimeMillis()-sttime))+ "MS");
				strGetResponseBody=null;
			}
			finally{
				postMethod.releaseConnection();

			}
			// System.out.println(str+"->Time Taken="+((System.currentTimeMillis()-sttime)));
			return strGetResponseBody;
	}


}
