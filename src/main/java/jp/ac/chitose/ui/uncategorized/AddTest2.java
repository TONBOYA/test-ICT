package jp.ac.chitose.ui.uncategorized;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.service.Interface.ITextFormLimmiterService;
import jp.ac.chitose.ui.MySession;
import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.practice.PracticeRegisterConfirmPage;
import jp.ac.chitose.ui.practice.PracticeUnfoldingNumberChangePanel;
import jp.ac.chitose.ui.practice.UnfoldingFormPanel;
import lombok.val;

public class AddTest2 extends ParentPage {

	@Override
	public String getTitle() {
		return null;
	}

	@Inject
	private IPracticeService practiceService;

	@Inject
	private IHierarchyService hierarchyService;

	@Inject
	ITextFormLimmiterService textFormLimmiterService;

	private static final int DEFAULT_UNFOLDING_SIZE = 3;

	private static final PageType TYPE = PageType.REGISTER;

	private boolean inputError = false;


	public AddTest2(TreeBean parent, IModel<PracticeRegisterBean> practiceModel) {
		setDefaultModel(CompoundPropertyModel.of(practiceModel));

		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = 5700101250723122903L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setOutputMarkupId(true);
			}
		};
		val form = new Form<Void>("form");
		practiceModel.getObject().setAccountId(MySession.get().getAccountBean().getAccountId());
		practiceModel.getObject().setPracticeHierarchyId(0);


		form.add(new AddTestPanel2("formPanel", practiceModel) {
			private static final long serialVersionUID = -6160665426309617016L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
			}
		});

		form.add(new PracticeUnfoldingNumberChangePanel("linkName", practiceModel, parent, DEFAULT_UNFOLDING_SIZE,
				TYPE));

		form.add(new PropertyListView<UnfoldingFormBean>("unfoldingList") {
			private static final long serialVersionUID = 6561257675695957444L;

			@Override
			protected void populateItem(ListItem<UnfoldingFormBean> item) {

				item.add(new UnfoldingFormPanel("formPanel", item.getModel(),
						practiceModel.getObject().getUnfoldingList().size()) {
					private static final long serialVersionUID = 7781312691388618391L;

				});
			}
		});


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
		form.add(button);
		add(form);
	}

}
