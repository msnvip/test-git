
//window.SkillWebServer="http://yourua.gicp.net/skill_server/";
window.SkillWebServer="http://192.168.1.102/skill_server/";
window.SkillPageSize=10;
window.CurrentCity="北京";
window.CurrentCityId="010";



setTimeout(custome_init,1000);
//处理默认自定义初始化工作
function custome_init(){
	//初始化默认城市
	
	//拉取弹窗信息
	
	makeTrace("handled custome init");
}

function makeTrace(txt,obj){
	if (window.console) {
		window.console.log(txt);
		if (obj) {
			try {
				window.console.log(JSON.stringify(obj));
			} catch (e) {
				// TODO: handle exception
			}
		}
	}
}

function makePost(objs){
	var dfd = new jQuery.Deferred();
	var obj = {
		url : SkillWebServer+""+objs.url,
		param : objs.param,
		succ : objs.succ,
		fail : objs.fail,
		json : true
	};
	obj.param.isAjax = 'true';
	makeTrace('makePost.calling.', obj);
	
	$.post(obj.url, obj.param)
		.success(
			function(resp) {
				//makeTrace('makePost.retruned.', resp);
				if (obj.json) {
					if (obj.succ) {
						//var currentResp = JSON.parse(resp);
						if (resp.code == '200') {
							obj.succ( JSON.parse(resp.data) );
						} else {							
							obj.fail( JSON.parse(resp.data) );
						}
	
						dfd.resolve('finish');
					}
				}
			})
		.error(function(resp) {
			makeTrace('makeOdinPost.error', resp);
			if (obj.fail) {
				obj.fail(resp);
			}
			dfd.reject('error');
		});

	return dfd;
}