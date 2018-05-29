var _SERVERS_ = "http://synconize.com/syncoOA";

var api = {};
api.extend = function(b, a) {
	for ( var c in a) {
		b[c] = a[c];
	}
	return b;
};

/* ajax */
api.ajaxFlag = false; // 为了让后台接受到数组形式的数据
api.ajax = function(_url, data, cb) {
	var _traditional = false;
	if (api.ajaxFlag) {
		_traditional = true;
		api.ajaxFlag = false;
	}
	$.ajax({
		url : _SERVERS_ + _url, // 跳转到 action
		type : "post",
		cache : false,
		async : true /* true异步执行，false */,
		data : data,
		dataType : "json",
		timeout : 30000,
		traditional : _traditional,
		success : function(ret) {
			console.log(ret, data, _url);
			if (ret.error) {
				api.showPress({
					title : ret.error,
					duration : 2000
				});
			}
			typeof cb == "function" && cb(ret);
		},
		error : function(xmlHttpRequest, error) {
			api.hideLoad();
			if ("用户访问令牌失效，请重新登陆" == error) {
				sessionStorage.removeItem('userInfoState');
				window.location.href = _SERVERS_ + '/lg/login';
				return false;
			}
			if ("timeout" == error) {
				api.showPress({
					title : "请求超时，请稍后再试",
					duration : 2000
				});
				return false;
			}
			api.showPress({
				title : error,
				duration : 2000
			});
		}
	});
};

/**
 * 消息模块 *{ title:'',//提示内容 默认是执行中的动画 duration:0,//定时自动关闭；为0时不自动关闭；大于等于200时候为定时关闭
 * url:'',跳转url }
 */
api.showPress = function(_con) {
	var config = {
		title : "<i></i><i></i><i></i><i></i>",
		duration : 0,
		url : ""
	};
	_con && (config = api.extend(config, _con));
	if (!document.getElementById("side-mask")) {
		var d = document.createElement("div");
		d.id = "side-mask";
		d.className = "f-hide";
		document.body.appendChild(d);
	}
	var sidemask = document.getElementById("side-mask");
	sidemask.innerHTML = "<section>" + config.title + "</section>";
	sidemask.className = "";
	199 < config.duration
			&& setTimeout(function() {
				"" !== config.url ? (window.location.href = config.url) : api
						.hidePress();
			}, config.duration);
};
api.hidePress = function() {
	if (document.getElementById("side-mask")) {
		document.getElementById("side-mask").className = "f-hide";
	}
};
/* confirmfn */
api.confirmFn = function(title, endFn) {
	api
			.showPress({
				title : '<span class="api-title">'
						+ title
						+ '</span><a class="api-button confirm">确定</a><a class="api-button cancel">取消</a>'
			});
	$(".api-button.confirm").one("click", function() {
		api.hidePress();
		endFn && endFn();
	});
	$(".api-button.cancel").one("click", function() {
		api.hidePress();
	});
};

/* loading */

// 查找类名
api.findName = function(dom) {
	var demo = [];
	for (var i = 0; i < dom.length; i++) {
		if (-1 != dom[i].indexOf("#")) {
			// id
			demo.push(document.getElementById(dom[i].substr(1)));
		} else if (-1 != dom[i].indexOf(".")) {
			// 类
			demo.push(document.getElementsByClassName(dom[i].substr(1)));
		} else {
			// 标签
			demo.push(document.getElementsByTagName(dom[i]));
		}
	}
	return demo;
};

api.loading = function(dom) {
	if ("undefined" == typeof dom) {
		api.showPress({
			title : "请添加loadin父元素",
			duration : 2000
		});
	}
	var domArr = api.findName(dom);
	for (var i = 0; i < domArr.length; i++) {
		for (var j = 0; j < domArr[i].length; j++) {
			if (domArr[i][j].getElementsByClassName("side-mask")[0]) {
				domArr[i][j].getElementsByClassName("side-mask")[0].classList
						.remove("f-hide");
			}
			var d = document.createElement("div");
			d.className = "side-load";
			d.innerHTML = "<i></i>";
			domArr[i][j].appendChild(d);
		}
	}
};
api.hideLoad = function(dom) {
	if ("undefined" == typeof dom) {
		for (var i = 0; i < document.getElementsByClassName("side-load").length; i++) {
			document.getElementsByClassName("side-load")[i].classList
					.add("f-hide");
		}
		return false;
	}
	var domArr = api.findName(dom);
	for (var i = 0; i < domArr.length; i++) {
		for (var j = 0; j < domArr[i].length; j++) {
			domArr[i][j].getElementsByClassName("side-load")[0].classList
					.add("f-hide");
		}
	}
};
