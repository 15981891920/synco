package com.synco.oa.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.synco.oa.pojo.User;
import com.synco.oa.service.UserMapperService;
import com.synco.oa.util.Config;
import com.synco.oa.util.HttpClientUtils;
import com.synco.oa.util.JsonUtil;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/lg")
public class LoginController {

	@Resource
	private UserMapperService userMapperService;

	/**
	 * 
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login")
	public String login(HttpSession session, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie2 : cookies) {
				System.out.println("cookie null:" + cookie2 == null);
				System.out.println(cookie2.getName());
				if ("usertoken".equals(cookie2.getName())) {
					String utoken = cookie2.getValue();
					User u = new User();
					u.setUser_token(utoken);
					List<User> us = userMapperService.selectUser2(u);
					if (us.size() > 0) {
						User u1 = us.get(0);
						String actoken = u1.getUser_token();
						String rtoken = u1.getUser_flush_token();
						// 是否过期
						String r = istokenouttime(actoken);
						if ("outtime".equals(r)) {
							System.out.println("过期令牌");
							String flush = u1.getUser_flush_token();
							JSONObject json = JSONObject.parseObject((Config.flushToken(flush)));
							// 刷新令牌若过期，此处应会报错
							actoken = json.getString("access_token");
							rtoken = json.getString("refresh_token");
							u1.setUser_token(actoken);
							u1.setUser_flush_token(rtoken);
							userMapperService.modifyUser(u1);
							Cookie c = new Cookie("usertoken", actoken);
							c.setMaxAge(7 * 24 * 60 * 60);
							response.addCookie(c);
						} else {
							System.out.println("未过期");
						}
						session.setAttribute("loginuser", u1);
						System.out.println("loginsuccess");
						return Config.loginOkurl + "?access_token=" + actoken;
					} else {
						System.out.println("数据库不存在此token,错误或过期");
						break;
					}
				}
			}
		}
		getAccessTokens(response);
		return "";
	}

	/**
	 * 获取URL(登陆授权)
	 * 
	 */
	@ApiOperation(value = "获取URL", notes = "获取URL,调用明道登陆接口")
	@RequestMapping(value = "/getAccessToken")
	public void getAccessTokens(HttpServletResponse response) throws Exception {
		String url = Config.getAuthorizeUrl();
		System.out.println(url);
		response.sendRedirect(url);
	}

	/**
	 * 获取令牌access_token
	 * 
	 * @param code
	 *            回掉地址返回的code,用来获取access_token
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAccessTokenUrl")
	public String getAccessTokenUrl(String code, HttpSession session, HttpServletResponse response) throws Exception {
		String re = Config.getAccessTokenByCode(code);
		System.out.println(re);
		JSONObject js = JSONObject.parseObject(re);
		String actoken = js.getString("access_token");
		String rtoken = js.getString("refresh_token");
		String url = "https://api.mingdao.com/v1/passport/get_passport_detail?access_token=" + actoken;
		String result = HttpClientUtils.get(url.toString(), "UTF-8");
		// 转化实体类
		User pojo = JSONObject.parseObject(JsonUtil.getJsonPojo(result), User.class);
		pojo.setUser_token(actoken);
		pojo.setUser_flush_token(rtoken);
		int num = userMapperService.selectUser(pojo);
		if (num > 0) {
			userMapperService.modifyUser(pojo);
		} else {
			pojo.setUser_integral(0);
			int nums = userMapperService.inserUserInfoId(pojo);
			if (nums >= 1) {
				result = "OK数据新增成功";
			}
		}
		session.setAttribute("loginuser", pojo);
		Cookie c = new Cookie("usertoken", actoken);
		c.setMaxAge(7 * 24 * 60 * 60);
		response.addCookie(c);
		System.out.println("firstloginsuccess");
		return Config.loginOkurl + "?access_token=" + actoken;
	}

	// long+
	/**
	 * 判断令牌是否过期
	 * 
	 * @throws Exception
	 */
	public String istokenouttime(String actoken) throws Exception {
		String url = "https://api.mingdao.com/v1/passport/get_passport_detail?access_token=" + actoken;
		String result = HttpClientUtils.get(url.toString(), "UTF-8");
		JSONObject jr = JSONObject.parseObject(result);
		System.out.println(jr);
		if (jr.get("error_code") == null) {
			return "success";
		}
		if (jr.get("error_code").toString().equals("10105")) {
			return "outtime";
		}
		return "此错误未在代码中定义";
	}

}
