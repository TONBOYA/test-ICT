package jp.ac.chitose.ui.practice;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.ITextFormLimmiterService;
import jp.ac.chitose.ui.MySession;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

public class PracticeRegisterPage2  extends ParentPage {

	private boolean inputError = false;
	private boolean visible = false;

	@Inject
	private IHierarchyService hierarchyService;

	@Inject
	ITextFormLimmiterService textFormLimmiterService;

	PageType TYPE = PageType.REGISTER;

	public PracticeRegisterPage2(TreeBean parent, IModel<PracticeRegisterBean> practiceModel) {
		setDefaultModel(CompoundPropertyModel.of(practiceModel));
		System.out.println("選択されている教科は" + practiceModel.getObject().getCategory());
		int DEFAULT_UNFOLDING_SIZE = practiceModel.getObject().getUnfoldingList().size();

		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = 5700101250723122903L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setOutputMarkupId(true);
			}
		};
		add(feedback);

		val form = new Form<Void>("form");
		practiceModel.getObject().setAccountId(MySession.get().getAccountBean().getAccountId());
		practiceModel.getObject().setPracticeHierarchyId(parent.getId());




		form.add(new PracticeUnfoldingNumberChangePanel("linkName", practiceModel, parent, DEFAULT_UNFOLDING_SIZE,
				TYPE));
		UnfoldingPreviewPanel unfoPanel1 = new UnfoldingPreviewPanel("unfoPanel1", Model.of(practiceModel.getObject().getUnfoldingList().get(0)),
				practiceModel.getObject().getAccountId());
		unfoPanel1.setOutputMarkupId(true);
		UnfoldingPreviewPanel unfoPanel2 = new UnfoldingPreviewPanel("unfoPanel2", Model.of(practiceModel.getObject().getUnfoldingList().get(1)),
				practiceModel.getObject().getAccountId());
		unfoPanel2.setOutputMarkupId(true);
		UnfoldingPreviewPanel unfoPanel3 = new UnfoldingPreviewPanel("unfoPanel3", Model.of(practiceModel.getObject().getUnfoldingList().get(2)),
				practiceModel.getObject().getAccountId());
		unfoPanel3.setOutputMarkupId(true);

		UnfoldingFormPanel unfoForm1 = new UnfoldingFormPanel("unfoForm1",
				Model.of(practiceModel.getObject().getUnfoldingList().get(0)), 1) {
			@Override
			protected void setUnfoldingTarget(AjaxRequestTarget target) {
				target.add(unfoPanel1);
			}
		};
		UnfoldingFormPanel unfoForm2 = new UnfoldingFormPanel("unfoForm2",
				Model.of(practiceModel.getObject().getUnfoldingList().get(1)), 2) {
			@Override
			protected void setUnfoldingTarget(AjaxRequestTarget target) {
				target.add(unfoPanel2);
			}
		};
		UnfoldingFormPanel unfoForm3 = new UnfoldingFormPanel("unfoForm3",
				Model.of(practiceModel.getObject().getUnfoldingList().get(2)), 3) {
			@Override
			protected void setUnfoldingTarget(AjaxRequestTarget target) {
				target.add(unfoPanel3);
			}
		};
		UnfoldingFormBean unfoBean4 = new UnfoldingFormBean();
		try {
			visible = true;
			unfoBean4 = practiceModel.getObject().getUnfoldingList().get(3);
		} catch (IndexOutOfBoundsException e) {
			visible = false;
			unfoBean4 =  new UnfoldingFormBean();
		}

		form.add(new Label("navi01","展開 01"));
		form.add(new Label("navi02","展開 02"));
		form.add(new Label("navi03","展開 03"));
		form.add(new Label("navi04","展開 04").setVisibilityAllowed(visible));

		UnfoldingPreviewPanel unfoPanel4 =
				new UnfoldingPreviewPanel("unfoPanel4", Model.of(unfoBean4),
				practiceModel.getObject().getAccountId());
		unfoPanel4.setOutputMarkupId(true);

		UnfoldingFormPanel unfoForm4 = new UnfoldingFormPanel("unfoForm4", Model.of(unfoBean4), 4) {
			@Override
			protected void setUnfoldingTarget(AjaxRequestTarget target) {
				target.add(unfoPanel4);
			}
		};
		unfoForm4.setVisibilityAllowed(visible);
		unfoPanel4.setVisibilityAllowed(visible);

		form.add(unfoPanel1);
		form.add(unfoPanel2);
		form.add(unfoPanel3);
		form.add(unfoPanel4);
		form.add(unfoForm1);
		form.add(unfoForm2);
		form.add(unfoForm3);
		form.add(unfoForm4);

		// form.add(new PropertyListView<UnfoldingFormBean>("unfoldingList") {
		// private static final long serialVersionUID = 6561257675695957444L;
		//
		// @Override
		// protected void populateItem(ListItem<UnfoldingFormBean> item) {
		//
		// item.add(new UnfoldingPreviewPanel("viewPanel", item.getModel(),
		// practiceModel.getObject().getAccountId()) {
		// private static final long serialVersionUID = -2711944578599262667L;
		//
		// @Override
		// protected void onInitialize() {
		// super.onInitialize();
		// setOutputMarkupId(true);
		// }
		// });
		// item.add(new UnfoldingFormPanel("formPanel", item.getModel(),
		// practiceModel.getObject().getUnfoldingList().size()) {
		// private static final long serialVersionUID = 7781312691388618391L;
		//
		// @Override
		// protected void setUnfoldingTarget(AjaxRequestTarget target) {
		// target.add(form);
		// }
		// });
		// }
		// });

		AjaxButton button = new AjaxButton("submit", form) {
			private static final long serialVersionUID = 8339738348901132440L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				List<UnfoldingFormBean> list = practiceModel.getObject().getUnfoldingList();

				list.stream().forEach(unfolding -> {
					if (textFormLimmiterService.measureCharactersBlank(unfolding.getName())) {
						error("展開名 欄に全角スペースを入れないでください。");
						inputError = true;
						System.out.println("展開名判定" + inputError);
					}
				});
				if (!(inputError)) {
					if (hierarchyService.searchSameName(parent, practiceModel.getObject())) {
						setResponsePage(new PracticeRegisterConfirmPage(parent, practiceModel));
					} else {
						target.add(feedback);
						error("同じ名前の実践が同じ階層にあります。実践名を変更してください。");
						inputError = false;
					}
				} else {
					inputError = false;
					System.out.println("インプットエラーの判定" + inputError);
				}

			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				target.add(feedback);
			}
		};

		form.add(new Link<Void>("returnRegisterPracticePage"){
			private static final long serialVersionUID = -3119920173245911285L;

			@Override
			public void onClick() {
				setResponsePage(new PracticeRegisterPage(parent, practiceModel));
			};
		});
		form.add(button);
		add(form);
	}

	@Override
	public String getTitle() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
