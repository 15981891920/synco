$(function() {
    //全局变量
    var main = {};

    // 全局事件
    main.eventLoad = function() {
      // 点击按钮显示弹窗
        
    };
  
    // 全局方法
    main.method = {
        getData:function(){
            var userInfoState = JSON.parse(sessionStorage.getItem('userInfoState'));
            api.ajax('/Test/getUserIntegral',{'access_token':userInfoState.access_token},function(ret){
                var myChart = echarts.init(document.getElementById('main-canvas'));
                myChart.setOption({
                    series: {
                        type: 'pie',
                        data: [{
                                name: '总积分：'+ret.userIntegral,
                                value:ret.userIntegral
                            }
                        ]
                    }
                });
            });
        }
    };
  
    // 初始化
    main.init = function() {
        main.method.getData();
    };
  
    // 调用
    main.init();
  });
  