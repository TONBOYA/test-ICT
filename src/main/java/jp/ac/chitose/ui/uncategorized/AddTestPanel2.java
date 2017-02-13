package jp.ac.chitose.ui.uncategorized;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import jp.ac.chitose.bean.PracticeRegisterBean;

public class AddTestPanel2 extends Panel{

	public AddTestPanel2(String id, IModel<PracticeRegisterBean> model) {
		super(id, model);

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
					}
				});
			}
		});

		add(new TextArea<String>("aim") {

			/**
			 *
			 */
			private static final long serialVersionUID = 7586454571956538037L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setLabel(Model.of("本時のめあて"));
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					/**
					 *
					 */
					private static final long serialVersionUID = -3988654986160300564L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
					}
				});
			}
		});

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
					}
				});
			}
		});
	}

}
