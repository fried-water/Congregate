/**
 * 
 * Copyright (c) 2009-2011 Envision Mobile Ltd. All rights reserved.
 *
 * Other software and company names mentioned herein or used for developing 
 * and/or running the Envision Mobile Ltd's software may be trademarks or trade 
 * names of their respective owners.
 *
 * Everything in the source code herein is owned by Envision Mobile Ltd. 
 * The recipient of this source code hereby acknowledges and agrees that such 
 * information is proprietary to Envision Mobile Ltd. and shall not be used, 
 * disclosed, duplicated, and/or reversed engineered except in accordance 
 * with the express written authorization of Envision Mobile Ltd. 
 *
 * Module: DataTransfer.java
 * Project: Videonama
 * 
 * Description: 
 *
 *
 * Developer:	Enoch Cheng
 * Date:	May 4, 2012
 * Version:	
 *       
 *
 */
package com.mhack.congregate.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 * @author Enoch Cheng
 * 
 */
public class DataTransfer {
	private final static int timeoutConnection = 15000;
	private final static int timeoutSocket = 60000;

	/**
	 * 
	 * @param httpPost
	 */
	public static void setHTTPHeaderPost(HttpPost httpPost) {
		httpPost.setHeader("platform", "Android");
		httpPost.setHeader("make", Build.MANUFACTURER);
		httpPost.setHeader("model", Build.MODEL);
		httpPost.setHeader("os_version", android.os.Build.VERSION.SDK);
		httpPost.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");
	}

	public static void setHTTPHeaderGet(HttpGet httpGet) {
		httpGet.setHeader("platform", "Android");
		httpGet.setHeader("make", Build.MANUFACTURER);
		httpGet.setHeader("model", Build.MODEL);
		httpGet.setHeader("os_version", android.os.Build.VERSION.SDK);
	}

	public static boolean verifyEMPStatus(JSONObject json) {
		if (json != null) {
			if (json.optString("status").equals("0")) {
				Log.d("", "EMP success");
				return true;
			}
		}
		Log.d("", "EMP failure");
		return false;
	}

	public static JSONObject getResponseData(JSONObject json) {
		return json.optJSONObject("data");
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject getJSONResult(Context ctx, String url) {
		if (!Utility.isNetworkAvailable(ctx))
			return null;

		try {	
			final HttpClient client = new DefaultHttpClient();
			final HttpGet get = new HttpGet(url);
			final HttpResponse responsePost = client.execute(get);
			final HttpEntity resEntity = responsePost.getEntity();	
			
			boolean DEBUG = true;
			if (DEBUG) {
				Log.d("", "[JSON-ENV] url:  " + url);
			}

			final String str = EntityUtils.toString(resEntity);
			Log.d("CAA", "response str: " + str);

			if (resEntity != null) {
				final JSONObject obj = new JSONObject(str);
				Log.d("CAA", "JSON RESPONSE IS " + obj);
				return obj;

			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Receive response and parse JSON string as UTF_8 format, for French
	 * characters
	 * 
	 * @param url
	 * @return
	 */
	public static JSONObject getDecodedJSONResponse(Context ctx, String url) {
		if (!Utility.isNetworkAvailable(ctx))
			return null;

		// initialize
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		// http post
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			Log.d("", "*___ url is " + url);

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		// convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, HTTP.UTF_8), 16);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
			Log.d("", "*___json convrsion results are " + sb);

		} catch (Exception e) {
			Log.e("", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {

			jArray = new JSONObject(result);
		} catch (JSONException e) {
			Log.e("", "Error parsing data " + e.toString());
			return null;
		}

		return jArray;
	}
	
	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static JSONObject postJSONResult(Context ctx, final String url,
			final ArrayList<NameValuePair> params) {

		if (!Utility.isNetworkAvailable(ctx))
			return null;

		final HttpClient client = new DefaultHttpClient();
		final HttpPost httpPost = new HttpPost(url);

		final HttpResponse responsePost;
		final HttpEntity resEntity;

		try {

			Log.d("CAA", "REQUEST URL in connection " + params);

			boolean DEBUG = true;
			if (DEBUG) {
				final StringBuffer buffer = new StringBuffer();
				buffer.append(url + "?");

				for (int i = 0; i < params.size(); i++) {
					if (i != 0) {
						buffer.append("&");
					}
					buffer.append(params.get(i).getName() + "="
							+ params.get(i).getValue());
				}
				Log.d("", "[JSON-ENV] url:  " + buffer.toString());
			}

			HttpConnectionParams.setConnectionTimeout(httpPost.getParams(),
					timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.

			HttpConnectionParams.setSoTimeout(httpPost.getParams(),
					timeoutSocket);
			final UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,
					HTTP.UTF_8);
			httpPost.setEntity(ent);

			DataTransfer.setHTTPHeaderPost(httpPost);

			responsePost = client.execute(httpPost);
			resEntity = responsePost.getEntity();

			if (resEntity != null) {
				InputStream instream = resEntity.getContent();
				String result = Utility.readStream(instream);
				instream.close();
				JSONObject jsonObject = new JSONObject(result);
				return jsonObject;
			}
			return null;
		} catch (Exception e) {
			// return null;
			e.printStackTrace();
			return null;
		}
	}
}