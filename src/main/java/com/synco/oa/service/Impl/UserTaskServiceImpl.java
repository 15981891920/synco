package com.synco.oa.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	public int InsertUserTask(User_task userTask) {
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
				String taskId = findTaskId(task_id);
				if (!taskId.equals(task_id)) {
					a = InsertUserTask(userTask);
				}
			} else {
				if (findTaskId(task_id) == "C") {
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
	public int findUserIntegralByTask(String user_id, String task_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int editUserIntegralByTask(String user_id, String task_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int findUsertaskrole(String user_id, String task_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int editUsertaskrole(String user_id, String task_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String findTaskId(String task_id) {
		String s = userTaskMapper.findTaskId(task_id);
		if (s == null) {
			s = "C";
		}
		return s;
	}

}
