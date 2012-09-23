define(function(){

	var SkillInfo=Backbone.Model.extend({

		defaults:{
			id:"",
			userId:'',
			category: '',			
			title:'ds',
			tagStr: '',
			content: '',
			createTime: '',			
			haveSkill:'',
			hopeSkill:'',			
			nickName: '',
			selfSex: '',
			provinceId: '',
			cityId: '',
			areaId: '',
			age: '',
			hopeSex: '',
			email: '',
			qq: '',
			phone: '',
			tel: ''
			
		},

		fetch:function(userId,id){
            var self=this;
            var tmpContact;
            
            makePost(
            	{
            		url: "skillInfo.do",
            		param: {
            			operate: "get_skill_detail",
            			userId: userId,
            			id: id
            		},
            		succ: function(data){
            			self.set({
            				id: data.id,
            				userId: data.userId,
            				category: data.category,			
            				title: data.title,
            				tagStr: data.tagStr,
            				content: data.content,
            				createTime: data.createTime,			
            				haveSkill: data.haveSkill,
            				hopeSkill: data.hopeSkill,			
            				nickName: data.nickName,
            				selfSex: data.selfSex,
            				provinceId: data.provinceId,
            				cityId: data.cityId,
            				areaId: data.areaId,
            				age: data.age,
            				hopeSex: data.hopeSex,
            				email: data.email,
            				qq: data.qq,
            				phone: data.phone,
            				tel: data.tel
            			});	        			
	                    self.trigger("fetchCompleted:SkillInfoDetail");
            		},
            		fail: function(data){
            			alert("getSkillInfoDetailError:"+data);
            		}
            	}
            );           
          }
	});

	return SkillInfo;
});