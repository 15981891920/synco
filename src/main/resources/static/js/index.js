$(function() {
  //全局变量
  var main = {};

  var lead_data; //所有任务
  var task_data; //点击按钮后的数据
  var list_this; //获得列表中的当前 this

  // 全局事件
  main.eventLoad = function() {
    // 点击按钮显示弹窗
    $(document).on("click", "button.active-state", main.method.btnActive);

    //确定按钮
    $(document).on("click", "button.task-btn", main.method.btnStart);

  };

  // 全局方法
  main.method = {
    //显示弹窗
    btnActive: function() {
      list_this = $(this);
      var _task = $(this).data("task"); //任务id
      task_data = main.method.findTask(_task); //获取当前任务数据
      var _html;
      var modal_data = {
        data: task_data,
        type: $(this).data("type")
      };
      if( 3 == $(this).data('type') ){
        //如果是立即完成，直接弹确定窗口
        main.method.btnStart($(this).data('type'));
      }
      _html = template("task-modal-alt", modal_data);
      $("#task-modal").html(_html);
    },
    //查找ID对应数据
    findTask: function(task) {
      for (var i = 0; i < lead_data.length; i++) {
        if (lead_data[i].task_id == task) {
          return lead_data[i];
        }
      }
    },
    //弹窗确认
    btnStart: function(btn_type) {
      var _this = $(this),
        _data = [], //参与人数数据
        _json,
        task_url, //接口地址
        _flag = false, //阻止弹窗消失
        reg = /^[1-9]\d*$/,
        task_id = ( 3 == btn_type ) ? task_data.task_id : _this.data("task"),    //项目id
        task_type = ( 3 == btn_type ) ? btn_type : _this.data("type"),  //项目状态
        task_intgarl = ( 3 == btn_type ) ? task_data.taskIntgarl : 0;        //项目总积分
        state_text = {
          state: "",
          btnText: "",
          btnState: false
        };                      //项目需要插入的html

      //push人员数据
      if(3 == btn_type){
        for( var i = 0; i < task_data.members.length; i++ ){
          _json =
            "{'task_id':'" +
            task_id +
            "','user_id':'" +
            task_data.members[i].account_id +
            "'}";
          _data.push(_json);
        }
      }else{
        $(".task-start-input").each(function() {
          if (!reg.test($(this).val())) {
            _flag = true;
            api.showPress({ title: "输入异常", duration: 1800 });
            return false;
          }
          task_intgarl += Number($(this).val());
          _json =
            "{'task_id':'" +
            task_id +
            "','user_id':'" +
            $(this).data("user") +
            "','userIntegral':'" +
            $(this).val() +
            "'}";
          _data.push(_json);
        });
      }
      if (_flag) {
        return false;
      }
      switch (task_type) {
        case 1:
          //立即开始
          task_url = "/Tast/taskStart";
          state_text.state = "进行中";
          state_text.btnText = "立即结算";
          break;
        case 2:
          //立即审核
          task_url = "/Tast/taskSettlement";
          state_text.state = "结算中";
          state_text.btnText = "立即完成";
          break;
        default:
          //立即完成
          task_url = "/Tast/taskComplete";
          state_text.state = "已完成";
          state_text.btnText = "已完成";
          state_text.btnState = true;
      }
      api.confirmFn("是否确认", function() {
    	  if( 2 == list_this.data('type')){
    		  console.log('点击立即确定');
    		  list_this.attr('data-toggle','');
    	  }
        api.showPress({title:'请稍等...'});
        api.ajaxFlag = true;
        api.ajax(task_url, { data: _data }, function(ret) {
          api.hidePress();
          if ("success" == ret.ifSuccess) {
            var proItem = list_this.parents('.pro-item');
            $("#exampleModalCenter").modal("hide");

            (3 == btn_type) ? '' : proItem.find('.intgarl').html('项目总积分：'+task_intgarl+'分');
            
            
            list_this.data("type", task_type + 1);
            proItem.removeClass('card-state-'+task_type).addClass('border-show card-state-'+(task_type+1));
            proItem.find(".state").html(state_text.state);
            list_this.text(state_text.btnText).attr("disabled", state_text.btnState);
            setTimeout(function(){
              proItem.removeClass('border-show');
            },3000)
          } else {
            api.showPress({ title: "发生错误", duration: 2000 });
          }
        });
      });
    },
    // 获取任务信息数据
    // liGetData:function(){
    //   var type = $(this).data('type');
      
    // },
    getData:function(fen){
      api.loading(['.pro-box']);
      var url = main.method.getRequest();  // access_token || false
      var userInfoState = JSON.parse(sessionStorage.getItem('userInfoState'));
      if( !url && userInfoState && userInfoState.access_token){
	    // 如果url为空 有缓存时 使用缓存
        var url = userInfoState;
      }else if('' == url.access_token && '' == userInfoState.access_token ){
    	//如果都为空  送他去登陆
        api.showPress({'title':'access_token为空',duration:2000,url:_SERVERS_ + '/lg/login'});
        return false;
      }
      sessionStorage.setItem('userInfoState',JSON.stringify(url));
      var dataObj = {
        access_token: url.access_token, //令牌
        taskfen: fen || ''
      };
      api.ajax("/Test/getTasks", dataObj, function(ret) {
        if( "用户访问令牌失效，请重新登陆" == ret.error){
          sessionStorage.removeItem('userInfoState');
          window.location.href =  _SERVERS_ + '/lg/login';
          return false;
        }
        api.hideLoad();
        lead_data = ret;
        var data = {
          data: ret
        };
        if( 2 == dataObj.taskfen ){
          var lead = template("list-lead", data);
          $("#lead-pro").html(lead);
        }else if( 3 == dataObj.taskfen ){
          var join = template("list-join", data);
          $("#join-pro").html(join);
        }else{
          var lead = template("list-lead", data);
          var join = template("list-join", data);
          $("#lead-pro").html(lead);
          $("#join-pro").html(join);
        }
      });
    },
    //获取url参数
    getRequest: function() {
      var url = window.location.search;
      if(!url){
    	  return false;
      }
      var theRequest = {};
      if (-1 != url.indexOf("?")) {
        var str = url.substr(1);
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
          theRequest[strs[i].split("=")[0]] = strs[i].split("=")[1];
        }
        return theRequest;
      }else{
        return theRequest;
      }
    }
  };



  // 初始化
  main.init = function() {
    main.eventLoad();
    main.method.getData();
  };

  // 调用
  main.init();
});
