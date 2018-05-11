package com.synco.oa.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.synco.oa.dao.UserTaskMapper;
import com.synco.oa.pojo.User_task;
import com.synco.oa.service.UserTaskService;

@Service
public class UserTaskServiceImpl implements UserTaskService {

	@Resource
	UserTaskMapper userTaskMapper;

	@Override
	public Integer InsertUserTask(User_task userTask) {
		return userTaskMapper.InsertUserTask(userTask);
	}

	@Override
	public Integer findTaskInsertTime(User_task userTask, String createdTime, String updateTime, String task_id) {
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date maxTime = userTaskMapper.findInsertTime();
		Integer a = null;
		try {
			if (simdf.parse(simdf.format(maxTime)).getTime() < simdf.parse(createdTime).getTime()) {
				a = InsertUserTask(userTask);
			} else if (simdf.parse(updateTime).getTime() > simdf.parse(simdf.format(maxTime)).getTime()) {
				String taskId = findTaskId(userTask);
				if (!taskId.equals(task_id)) {
					a = InsertUserTask(userTask);
				}
			} else {
				if (findTaskId(userTask) == "C") {
					a = InsertUserTask(userTask);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("=============" + a);
		return a;
	}

	@Override
	public Integer findUserIntegralByTask(User_task usertask) {
		return userTaskMapper.findUserIntegralByTask(usertask);
	}

	@Override
	public Integer editUserIntegralByTask(User_task usertask) {
		return userTaskMapper.editUserIntegralByTask(usertask);
	}

	@Override
	public Integer findUsertaskrole(String user_id, String task_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer editUsertaskrole(String user_id, String task_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String findTaskId(User_task usertask) {
		String s = userTaskMapper.findTaskId(usertask);
		if (s == null) {
			s = "C";
		}
		return s;
	}

	@Override
	public List<User_task> findTaskIdByUserId(String user_id) {
		return userTaskMapper.findTaskIdByUserId(user_id);
	}

}
