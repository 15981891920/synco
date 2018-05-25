package com.synco.oa.service.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.synco.oa.dao.UserMapper;
import com.synco.oa.dao.UserTaskMapper;
import com.synco.oa.pojo.Members;
import com.synco.oa.pojo.User_task;
import com.synco.oa.pojo.tasks;
import com.synco.oa.service.UserTaskService;

@Service
public class UserTaskServiceImpl implements UserTaskService {

	@Resource
	UserTaskMapper userTaskMapper;

	@Resource
	UserMapper userMapper;

	@Override
	public Integer InsertUserTask(User_task userTask) {
		return userTaskMapper.InsertUserTask(userTask);
	}

	public Integer insertUsertasks(User_task userTask, tasks taskIn, String task_id) {
		userTask.setTask_id(task_id);
		if (taskIn.getMembers().size() > 1) {
			for (Members men : taskIn.getMembers()) {
				if (men.getAccount_id().equals(taskIn.getCharge_user().getAccount_id())
						|| men.getAccount_id() == null) {
					continue;
				}
				userTask.setUser_id(userMapper.findUserIdbyAidU(men.getAccount_id()));
				if (userTask.getUser_id() == null) {
					return 0;
				}
				if (userTaskMapper.countTaskUser(userTask) == 0) {
					if (InsertUserTask(userTask) > 0) {
						System.out.println("++++++OK++++++");
					}
				}
			}
		}
		return 100;
	}

	@Override
	public Integer findTaskInsertTime(User_task userTask, tasks taskIn) {
		SimpleDateFormat simdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date maxTime = userTaskMapper.findInsertTime();
		Integer a = 100;
		if (maxTime == null) {
			a = InsertUserTask(userTask);
			return a;
		}
		try {
			if (simdf.parse(simdf.format(maxTime)).getTime() < simdf.parse(taskIn.getCreate_time()).getTime()) {
				a = InsertUserTask(userTask);
				a = insertUsertasks(userTask, taskIn, userTask.getTask_id());
			} else if (simdf.parse(taskIn.getUpdate_time()).getTime() > simdf.parse(simdf.format(maxTime)).getTime()) {
				String taskId = findTaskId(userTask);
				if (!taskId.equals(userTask.getTask_id())) {
					a = InsertUserTask(userTask);
					a = insertUsertasks(userTask, taskIn, userTask.getTask_id());
				}
			} else {
				if (findTaskId(userTask) == "C") {
					a = InsertUserTask(userTask);
					a = insertUsertasks(userTask, taskIn, userTask.getTask_id());
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
