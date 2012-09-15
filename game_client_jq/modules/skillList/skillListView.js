// Filename: store/vew/list.js
define(['jquery', 'underscore', 'backbone', 'text!modules/skillList/skillListView.html'],
       function ($, _, Backbone, listViewTemplate) {
  'use strict';

  var listView = Backbone.View.extend({
    
    template: _.template(listViewTemplate),

    initialize: function(){
        this.collection.bind('fetchCompleted:SkillList',this.render,this);
    },
    
    render: function(){
      $(this.el).append(this.template({data:this.collection.toJSON()}));
      this.trigger("renderCompleted:SkillList",this,"test parameter");
      return this;
    }
  });
  
  return listView;
});