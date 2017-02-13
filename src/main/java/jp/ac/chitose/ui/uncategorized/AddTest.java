package jp.ac.chitose.ui.uncategorized;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
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
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.service.Interface.ITextFormLimmiterService;
import jp.ac.chitose.ui.MySession;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

public class AddTest extends ParentPage {
	private static final long serialVersionUID = -5898707705336747767L;

	@Override
	public String getTitle() {
		// TODO 自動生成されたメソッド・スタブ
		return "ああああ";
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

	public AddTest() {

		// ログインしたユーザーのAccountId
		int loginId = MySession.get().getAccountBean().getAccountId();

		// ログインしたユーザーのAccountRole
		String loginRole = MySession.get().getAccountBean().getRole();
		TreeBean parent = hierarchyService.searchRoot();
		String category = "";

		IModel<PracticeRegisterBean> practiceModel = Model.of(new PracticeRegisterBean(
				practiceService.registerUnfoldingList(DEFAULT_UNFOLDING_SIZE), loginId, parent.getId(), category));
		setDefaultModel(CompoundPropertyModel.of(practiceModel));


		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = 5700101250723122903L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setOutputMarkupId(true);
			}
		};
		add(feedback);

		add(new Label("path", hierarchyService.makePracticeHierarchyPath(parent)));

		val form = new Form<Void>("form");

		form.add(new AddTestPanel("formPanel", practiceModel) {
			private static final long serialVersionUID = -6160665426309617016L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
			}
		});


		AjaxButton button = new AjaxButton("submit", form) {
			private static final long serialVersionUID = 8339738348901132440L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				List<UnfoldingFormBean> list = practiceModel.getObject().getUnfoldingList();


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
						setResponsePage(new AddTest2(parent, practiceModel));
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
