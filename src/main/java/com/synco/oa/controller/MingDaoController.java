/**
 * 
 */
package com.synco.oa.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.synco.oa.pojo.Task;
import com.synco.oa.pojo.User;
import com.synco.oa.pojo.tasks;
import com.synco.oa.service.UserMapperService;
import com.synco.oa.util.Config;
import com.synco.oa.util.HttpClientUtils;
import com.synco.oa.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author LiQian
 *
 */
@Api("测试明道API")
@Controller
@RequestMapping("/Test")
public class MingDaoController {

	@Resource
	private UserMapperService userMapperService;

	/**
	 * 获取URL(登陆授权)
	 * 
	 */
	@ApiOperation(value = "获取URL", notes = "获取URL,调用明道登陆接口")
	@RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
	public void getAccessTokens(HttpServletResponse response) throws Exception {
		String url = Config.getAuthorizeUrl();
		response.sendRedirect(url);
	}

	/**
	 * 获取令牌access_token
	 * 
	 * @param code
	 *            回掉地址返回的code,用来获取access_token
	 */
	@RequestMapping(value = "/getAccessTokenUrl", method = RequestMethod.GET)
	@ResponseBody
	public String getAccessTokenUrl(String code) {
		String TokenUrl = Config.getAccessTokenByCode(code);
		return TokenUrl;
	}

	/**
	 * 获取当前登录用户的基本信息
	 * 
	 * @param access_token
	 * @return
	 */
	@RequestMapping("/getUserInfo")
	@ResponseBody
	public String getUserInfo(String access_token) throws Exception {
		String url = "https://api.mingdao.com/v1/passport/get_passport_detail?access_token=" + access_token;
		String result = HttpClientUtils.get(url.toString(), "UTF-8");
		// 转化实体类
		User pojo = JSONObject.parseObject(JsonUtil.getJsonPojo(result), User.class);
		System.out.println(pojo.getUser_id());
		System.out.println(pojo.getAccount_id());
		int nums = userMapperService.selectUser(pojo);
		if (nums == 1) {
			return result;
		} else {
			pojo.setUser_integral(0.0f);
			nums = userMapperService.inserUserInfoId(pojo);
			if (nums >= 1) {
				result = "OK数据新增成功";
			}
		}
		return result;
	}

	/**
	 * 获取项目下成员任务甘特图用列表（全部任务）
	 * 
	 * @param access_token
	 * @param folder_id
	 * @return
	 */
	@RequestMapping("/getFoldersGantt")
	@ResponseBody
	public String getFoldersGantt(String access_token, String folder_id) throws Exception {
		String url = "https://api.mingdao.com/v2/task/get_folder_task_gantt?access_token=" + access_token
				+ "&folder_id=" + folder_id;
		String result = HttpClientUtils.get(url.toString(), "UTF-8");
		System.out.println(url.toString());
		return result;
	}

	/**
	 * 获取当前登录账户的任务列表
	 * 
	 * @param access_token
	 * @api https://api.mingdao.com/v2/task/get_task_list?
	 *      structure=0&access_token=fe89ed3a44954ecca05ba6c764b04b21
	 *      &project_id=all&page_index=1&page_size=20&filter_type=6&
	 *      status=0&tag_ids=&without_tag=false&sort=10&is_star=false&
	 *      time_format=true&format=json&account=true
	 * @return
	 */
	@RequestMapping("/getTasks")
	@ResponseBody
	public String getTasks(String access_token) throws Exception {
		// 拼接url
		String urls = "https://api.mingdao.com/v2/task/get_task_list?access_token=" + access_token
				+ "&project_id=all&page_index=1&page_size=20&filter_type=6&status=0&tag_ids=&without_tag=false&sort=10&is_star=false&time_format=true&format=json&account=true";
		String result = HttpClientUtils.get(urls.toString(), "UTF-8");
		System.out.println("网址链接" + urls.toString());

		List<Task> tt = JSONObject.parseArray(JsonUtil.getJsonPojo(result), Task.class);
		for (Task task : tt) {
			for (tasks tasks : task.getTasks()) {
				System.out.println(tasks.getTask_id());
			}
		}
		return result;
	}

	/**
	 * 获取任务详情（主要是获取当前任务所在阶段）
	 * 
	 * @param access_token
	 * @param folder_id
	 * @return
	 */
	@RequestMapping("/getTaskInfo")
	@ResponseBody
	public String getTaskInfo(String access_token, String task_id) throws Exception {
		// 拼接url
		String url = "https://api.mingdao.com/v2/task/get_task_detail?access_token=" + access_token + "&task_id="
				+ task_id;
		String result = HttpClientUtils.get(url.toString(), "UTF-8");
		System.out.println(url.toString());
		return result;
	}
}
