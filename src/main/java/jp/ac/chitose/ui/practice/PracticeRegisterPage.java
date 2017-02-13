package jp.ac.chitose.ui.practice;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.ITextFormLimmiterService;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

public class PracticeRegisterPage extends ParentPage {

	private static final long serialVersionUID = 4979186376998016337L;
	private boolean inputError = false;
	private boolean visible = false;
	@Inject
	private IHierarchyService hierarchyService;

	@Inject
	ITextFormLimmiterService textFormLimmiterService;

	PageType TYPE = PageType.REGISTER;

	@Override
	public String getTitle() {
		return "実践登録ページ";
	}

	public PracticeRegisterPage(TreeBean parent, IModel<PracticeRegisterBean> practiceModel) {
		setDefaultModel(CompoundPropertyModel.of(practiceModel));
		System.out.println("選択されている教科は" + practiceModel.getObject().getCategory());
	//	int DEFAULT_UNFOLDING_SIZE = practiceModel.getObject().getUnfoldingList().size();

		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = 5700101250723122903L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setOutputMarkupId(true);
			}
		};
		add(feedback);

		//add(new Label("path", hierarchyService.makePracticeHierarchyPath(parent)));

		val form = new Form<Void>("form");

		// form.add(new PracticePreviewPanelにするとAjaxでtarget.add(form)としなければならない
		// 挙動がおかしくなるので先に生成するtarget.add(変数名)でおｋ
		PracticePreviewPanel practicePreviewPanel = new PracticePreviewPanel("preview", practiceModel);
		practicePreviewPanel.setOutputMarkupId(true);
		form.add(practicePreviewPanel);

		form.add(new PracticeFormPanel("formPanel", practiceModel) {
			private static final long serialVersionUID = -6160665426309617016L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
			}

			@Override
			public void setPracticeTarget(AjaxRequestTarget target) {
				target.add(practicePreviewPanel);
			}

			@Override
			public void setTarget(AjaxRequestTarget target) {
				target.add(form);
			}
		});

		/*form.add(new PracticeUnfoldingNumberChangePanel("linkName", practiceModel, parent, DEFAULT_UNFOLDING_SIZE,
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
		form.add(unfoForm4);*/

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

				if (textFormLimmiterService.measureCharactersBlank(practiceModel.getObject().getName())) {
					error("実践名 欄に全角スペースを入れないでください。");
					inputError = true;
					System.out.println("実践名判定" + inputError);
				}
				if (practiceModel.getObject().getTag2().equals(practiceModel.getObject().getTag1())
						|| practiceModel.getObject().getTag3().equals(practiceModel.getObject().getTag1())
						|| practiceModel.getObject().getTag3().equals(practiceModel.getObject().getTag2())) {
					error("検索用キーワード 欄に同じ言葉を入れないでください。");
					inputError = true;
					System.out.println("実践名判定" + inputError);
				}
				if (practiceModel.getObject().getTag1() == null || practiceModel.getObject().getTag2() == null
						|| practiceModel.getObject().getTag3() == null) {
					error("検索用キーワード 欄を埋めてください。");
					inputError = true;
					System.out.println("実践名判定" + inputError);
				}
				if (!(inputError)) {
					if (hierarchyService.searchSameName(parent, practiceModel.getObject())) {
						setResponsePage(new PracticeRegisterPage2(parent, practiceModel));
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
		form.add(button);
		add(form);
	}
}
