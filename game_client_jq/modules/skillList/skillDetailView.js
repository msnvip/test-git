define(['jquery', 'underscore', 'backbone','text!modules/skillList/skillDetailView.html'],
       function ($, _, Backbone, detailViewTemplate) {
  'use strict';

  var detailView = Backbone.View.extend({
    
    template: _.template(detailViewTemplate),

    initialize: function(){
        this.model.bind('fetchCompleted:SkillInfoDetail',this.render,this);
    },
    
    render: function(){
      console.log("model=" + this.model.toJSON());
      $(this.el).append(this.template(this.model.toJSON()));
      this.trigger("renderCompleted:SkillInfoDetail",this,"test parameter");
      return this;
    }


  });
  
  return detailView;
});