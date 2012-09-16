package org.you.core.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.you.core.util.NumberUtil;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * 处理所有和逻辑相关的请求.主要是ajax
 * @author fenglei
 *
 */
public abstract class BaseAction extends Action{
	static Logger logger = Logger.getLogger(BaseAction.class);
	
	protected String operate = "";
	protected boolean isAjax = false;
	protected long userId = 0L;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		operate = getParamString("operate", request);		
		isAjax = Boolean.parseBoolean(request.getParameter("isAjax"));
		String sk = request.getHeader("sk");
		
		if(!checkSkValid(sk)){
			writeJsonFail(null, response);
			return null;
		}
		
		userId = parseUserId(sk);
		
		ActionForward result = null;
		try {
			result = customeExecute(mapping, form, request, response);			
		} catch (Exception e) {
			logger.error("error",e);
			writeJsonFail("error", response);
			return null;
		}finally{
			response.addHeader("sk", sk);
		}
		
		return result;
	}
	
	protected boolean checkSkValid(String sk){
		return true;
	}
	protected long parseUserId(String sk){
		String uidStr = sk.split("-")[1];
		return NumberUtil.getLong(uidStr);
	}
	
	protected abstract ActionForward customeExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 写出请求失败
	 * @param error
	 * @param resp
	 * @throws IOException
	 */
	protected void writeJsonFail(Object error, HttpServletResponse resp) throws IOException{
		writeJsonResp(error, 500, resp);
	}
	
	/**
	 * 简版写出请求成功
	 * @param resp
	 * @throws IOException
	 */
	protected void writeJsonSuccess(HttpServletResponse resp) throws IOException{
		writeJsonSuccess(null, resp);
	}
	/**
	 * 向客户端写出成功.
	 * @param result
	 * @param resp
	 * @throws IOException 
	 */
	protected void writeJsonSuccess(Object result,HttpServletResponse resp) throws IOException{
		writeJsonResp(result, 200, resp);
	}
	private void writeJsonResp(Object result, int code, HttpServletResponse resp) throws IOException{
		Gson gson = new Gson();
		String data = gson.toJson(result);
		
		JsonObject fullResult = new JsonObject();
		fullResult.addProperty("code", code);
		fullResult.addProperty("data", data);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("content-type", "application/json;charset=UTF-8");

		resp.getWriter().write(gson.toJson(fullResult));
		resp.getWriter().flush();
		resp.getWriter().close();
	}
	
	protected String getParamString(String paramName, HttpServletRequest req){
		return req.getParameter(paramName);
	}
	
	protected int getParamInt(String paramName, HttpServletRequest req){
		String vl = req.getParameter(paramName);
		return NumberUtil.getInt(vl);
	}
	
	
	
}
