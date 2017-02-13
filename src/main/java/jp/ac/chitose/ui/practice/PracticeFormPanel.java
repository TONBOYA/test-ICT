package jp.ac.chitose.ui.practice;

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

import jp.ac.chitose.bean.PracticeRegisterBean;

/**
 * 実践を入力するためのフォームを生成、表示するためのパネル
 *
 * @author kazuki
 *
 */
public class PracticeFormPanel extends Panel {

	/**
	 *
	 */
	private static final long serialVersionUID = 8002347617513448435L;
	private String TYPE = "PRACTICE";
	private Panel panel;
	private String text = "登録済みのキーワードを表示";
	private boolean visible = false;
	private boolean scriptbool = false;

	public PracticeFormPanel(String id, IModel<PracticeRegisterBean> practiceModel) {
		super(id, practiceModel);

		add(new TextField<String>("name") {

			/**
			 *
			 */
			private static final long serialVersionUID = -6519281288392158114L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("実践名"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					/**
					 *
					 */
					private static final long serialVersionUID = -8434039613689360891L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setPracticeTarget(target);
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
		add(new CheckBoxMultipleChoice<String>("keyword", selectList) {

			/**
			 *
			 */
			private static final long serialVersionUID = -5793443763340049849L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("ICT機器"));
				add(new AjaxFormChoiceComponentUpdatingBehavior() {

					/**
					 *
					 */
					private static final long serialVersionUID = -5138333174436938578L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setPracticeTarget(target);
					}
				});
			}
		});

		add(new TextField<String>("tag1") {

			/**
			 *
			 */
			private static final long serialVersionUID = -6062159503005389348L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("キーワード1"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					/**
					 *
					 */
					private static final long serialVersionUID = 1082353531100945100L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setPracticeTarget(target);
					}
				});
			}
		});

		add(new TextField<String>("tag2") {

			/**
			 *
			 */
			private static final long serialVersionUID = 6381227298461107590L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				// setModelValue(new String[]{ "ああ" });
				setRequired(true);
				setLabel(Model.of("キーワード2"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setPracticeTarget(target);
					}
				});
			}
		});
		add(new TextField<String>("tag3") {

			/**
			 *
			 */
			private static final long serialVersionUID = -7498344106760500742L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("キーワード3"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					/**
					 *
					 */
					private static final long serialVersionUID = 3770667604900828989L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setPracticeTarget(target);
					}
				});
			}
		});

		this.panel = new TagAllListPanel("conditionPanel", practiceModel.getObject().getCategory(), TYPE);
		panel.setOutputMarkupId(true);
		panel.setOutputMarkupPlaceholderTag(true);
		panel.setVisibilityAllowed(false);
		add(panel);

		Label label = new Label("label", text);
		label.setOutputMarkupId(true);

		add(new AjaxLink("link") {

			/**
			 *
			 */
			private static final long serialVersionUID = -8699356445569523841L;

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

		// DateTextField startOn = new DateTextField("startOn") {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = -638894997400627873L;
		//
		// @Override
		// protected void onInitialize() {
		// super.onInitialize();
		// setRequired(true);
		// setLabel(Model.of("実践日"));
		// add(DateValidator.maximum(new Date()));
		// add(new AjaxFormComponentUpdatingBehavior("onchange") {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = 2913473459510375618L;
		//
		// @Override
		// protected void onUpdate(AjaxRequestTarget target) {
		// setPracticeTarget(target);
		// }
		// });
		// }
		//
		// };
		//
		// startOn.add(new DatePicker());
		//
		// add(startOn);

//		Label scriptBoolLabel = new Label("scriptBool", text);
//		scriptBoolLabel.setOutputMarkupId(true);
//
//
//		add(new Button("javascriptButton"){
//
//
//			@Override
//			public void onSubmit() {
//				scriptbool = scriptbool ? false : true;
//				if (scriptbool == true) {
//					text = "登録済みのキーワードを非表示";
//				} else {
//					text = "登録済みのキーワードを表示";
//				}
//				scriptBoolLabel.setDefaultModelObject(text);
//				target.add(label);
//			}
//		}.add(label));

		add(new TextArea<String>("summary") {

			/**
			 *
			 */
			private static final long serialVersionUID = 3481741425393669270L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setLabel(Model.of("本時のICT活用について"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setPracticeTarget(target);
					}
				});
			}
		});

		// add(new TextArea<String>("aim") {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = 7586454571956538037L;
		//
		// @Override
		// protected void onInitialize() {
		// super.onInitialize();
		// setLabel(Model.of("本時のめあて"));
		// add(new AjaxFormComponentUpdatingBehavior("onchange") {
		//
		// /**
		// *
		// */
		// private static final long serialVersionUID = -3988654986160300564L;
		//
		// @Override
		// protected void onUpdate(AjaxRequestTarget target) {
		// setPracticeTarget(target);
		// }
		// });
		// }
		// });

		add(new TextArea<String>("challenge") {

			/**
			 *
			 */
			private static final long serialVersionUID = 1903845910096175402L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					/**
					 *
					 */
					private static final long serialVersionUID = 6185172446848961266L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setPracticeTarget(target);
					}
				});
			}
		});

		add(new TextArea<String>("reaction") {

			/**
			 *
			 */
			private static final long serialVersionUID = -380041436413490346L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					/**
					 *
					 */
					private static final long serialVersionUID = -6497134792679348120L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setPracticeTarget(target);
					}
				});
			}
		});

	}

	public void setPracticeTarget(AjaxRequestTarget target) {
	}

	public void setTarget(AjaxRequestTarget target) {
	}

	public void setTextTarget(AjaxRequestTarget target, String text) {
	}

	public void getError() {
	}
}
