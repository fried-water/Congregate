package com.mhack.congregate.util;

import java.io.InputStream;
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
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;


public class DataTransfer {
	private final static int timeoutConnection = 15000;
	private final static int timeoutSocket = 60000;


	public static boolean verifyResultStatus(JSONObject json) {
		if (json != null) {
			if (json.optString("status").equals("0")) {
				return true;
			}
		}
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
			
			final String str = EntityUtils.toString(resEntity);

			if (resEntity != null) {
				final JSONObject obj = new JSONObject(str);
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