define(function(){

	var SkillSimpleInfo=Backbone.Model.extend({

		defaults:{
			id:"",
			userId:'',
			title:'ds',
			haveSkill:'',
			hopeSkill:'',
			nickName: '',
			createTime: ''
		}
	});

	return SkillSimpleInfo;
});