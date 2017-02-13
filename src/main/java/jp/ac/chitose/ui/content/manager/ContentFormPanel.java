package jp.ac.chitose.ui.content.manager;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.ui.practice.TagAllListPanel;

public class ContentFormPanel extends Panel {
	private static final long serialVersionUID = -5282544145786122055L;


	String TYPE = "CONTENT";
	Panel panel;
	String text = "登録済みのキーワードを表示";
	private boolean visible = false;

	public ContentFormPanel(String id, IModel<ContentPageBean> contentModel) {
		super(id,contentModel);

		add(new TextField<String>("name"){
			private static final long serialVersionUID = 3121416917978862703L;
			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("コンテンツ名"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {
					private static final long serialVersionUID = 2669705985293115909L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setContentTarget(target);
					}
				});
			}
		});

		ArrayList<String> selectList = new ArrayList<String>();
		selectList.add("タブレット");
		selectList.add("電子黒板");
		selectList.add("授業支援ソフト");
		selectList.add("実物投影機");
		selectList.add("デジタル教科書");
		selectList.add("その他");

		add(new CheckBoxMultipleChoice<String>("keyword",selectList){
			private static final long serialVersionUID = -6054355490080609774L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("使用したICT機器"));
				add(new AjaxFormChoiceComponentUpdatingBehavior() {
					private static final long serialVersionUID = -5217721914854966765L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setContentTarget(target);
					}
				});
			}
		});

		add(new TextField<String>("tag1"){
			private static final long serialVersionUID = -7565972147478471027L;
			@Override protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("キーワード1"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {
					private static final long serialVersionUID = -8052924313497785373L;
					@Override protected void onUpdate(AjaxRequestTarget target) {
						setContentTarget(target);}});
				}});
		add(new TextField<String>("tag2"){
			private static final long serialVersionUID = -7565972147478471027L;
			@Override protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("キーワード2"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {
					private static final long serialVersionUID = -8052924313497785373L;
					@Override protected void onUpdate(AjaxRequestTarget target) {
						setContentTarget(target);}});
				}});
		add(new TextField<String>("tag3"){
			private static final long serialVersionUID = -7565972147478471027L;
			@Override protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("キーワード3"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {
					private static final long serialVersionUID = -8052924313497785373L;
					@Override protected void onUpdate(AjaxRequestTarget target) {
						setContentTarget(target);}});
				}});

		this.panel = new TagAllListPanel("conditionPanel", contentModel.getObject().getCategory(),TYPE);
		panel.setOutputMarkupId(true);
		panel.setOutputMarkupPlaceholderTag(true);
		panel.setVisibilityAllowed(false);
		add(panel);

		Label label = new Label("label", text);
		label.setOutputMarkupId(true);

		add(new AjaxLink("link") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				visible = visible ? false : true;
				panel.setVisibilityAllowed(visible);
				target.add(panel);
				if (visible == true) {
					text = "登録済みのキーワードを非表示";
				} else {
					text = "登録済みのキーワードを表示";
				}
				label.setDefaultModelObject(text);
				target.add(label);
			}
		}.add(label));

		add(new TextArea<String>("howToUse"){

			private static final long serialVersionUID = 3499547010587272164L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setLabel(Model.of("使い方"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {
					private static final long serialVersionUID = -343052637702089060L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setContentTarget(target);
					}
				});
			}
		});
	}

	public void setContentTarget(AjaxRequestTarget target){

	}

	public void getError(){

	}

}

