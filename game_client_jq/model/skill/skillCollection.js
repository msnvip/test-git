define(['jquery', 'underscore', 'backbone','model/skill/skillSimpleModel'],
       function ($, _, Backbone,SkillSimpleInfo){

        var SkillSimpleInfos=Backbone.Collection.extend({
          model:SkillSimpleInfo,

          fetch:function(category,page){
            console.log("i will fetch");
            var self=this;
            var tmpContact;
            
            makePost(
            	{
            		url: "skillInfo.do",
            		param: {
            			operate: "get_simple_list",
            			category: category,
            			page: page
            		},
            		succ: function(data){
	        			$.each(data, function(i,item){
	                        tmpContact=new SkillSimpleInfo({
	                        	id: item.id,
	                			userId: item.userId,
	                			title: item.title,
	                			haveSkill: item.haveSkill,
	                			hopeSkill: item.hopeSkill,
	                			nickName: item.nickName,
	                			createTime: item.createTime
	                        });
	                        self.add(tmpContact);
	                      });
	                    self.trigger("fetchCompleted:SkillSimpleList");
            		},
            		fail: function(data){
            			alert("getSkillListError:"+data);
            		}
            	}
            );
            /*var jqxhr = $.getJSON("data/data.json")
              .success(function(data, status, xhr) { 
                console.log("success" + data);
                $.each(data, function(i,item){
                  tmpContact=new SkillInfo({id:item.id,name:item.name});
                  self.add(tmpContact);
                });
                console.log("will trigger fetchComplete:Contact");
                self.trigger("fetchCompleted:Contacts");
              })
              .error(function() { alert("error"); })
              .complete(function() {
                    console.log("fetch complete + " + this);
              });*/
          }
  });

  return SkillSimpleInfos;
});