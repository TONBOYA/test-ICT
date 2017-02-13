package jp.ac.chitose.ui.practice;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.Definition;
import jp.ac.chitose.option.TreeType;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.MySession;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

public class ManagePracticePage extends ParentPage {
	private static final long serialVersionUID = -4895569954978401315L;

	private static final int DEFAULT_UNFOLDING_SIZE = 3;

	@Inject
	private IHierarchyService hierarchyService;

	@Inject
	private IPracticeService practiceService;

	public ManagePracticePage(TreeBean parent, String category) {
		System.out.println("選択教科は"+category+"です");

	//	ログインしたユーザーのAccountId
		int loginId = MySession.get().getAccountBean().getAccountId();

	//  ログインしたユーザーのAccountRole
		String loginRole = MySession.get().getAccountBean().getRole();

	//  エラー表示用Panel
		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = -340004831684278993L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
			}
		};
		add(feedback);

	//  階層を表示する
		add(new Label("path", hierarchyService.makePracticeHierarchyPath(parent)));

	//  親の下についている子treeをListに格納する
		List<TreeBean> treeList = new ArrayList<TreeBean>();

	//  rootの下の各教科を表示してる場合に、タイプをフォルダから教科に変更し
	//  その後に、教科名と単元名を区別し教科名を変数selectCategory(s)に代入し続けるための処理
		if (parent.getTreeType() == TreeType.ROOT)
		{
			//初回のみrootなので通過 type = category : 教科
			treeList = hierarchyService.searchChildrenCategory(parent);
		} else {
		//  二回目以降に通過 type = hierarchy : フォルダ
			treeList = hierarchyService.searchChildren(parent);
		}

		val checkList = new ArrayList<TreeBean>();
		val checkGroup = new CheckGroup<TreeBean>("checkGroup", checkList);

		ListView<TreeBean> childList = new PropertyListView<TreeBean>("hierarchy", treeList) {
			private static final long serialVersionUID = -4505699943937945442L;

			@Override
			protected void populateItem(ListItem<TreeBean> item) {

			    // // // // // // //--変数コンテナ--// // // // // // //

			//  子のtreeId
				int childTreeId = item.getModelObject().getId();

			//  子のtreeType
				TreeType childTreeType = item.getModelObject().getTreeType();

			//  コンテンツ作成者のAccountId
				int authorId = item.getModelObject().getAccountId();

			//  コンテンツ作成者のname
				String authorName = "管理者";

			//  検索用キーワード用変数(選択した教科名を拾う)
				String selectCategory = "";

				String deleteType = "削除できません";

			//	PRACTICE時以外は単元名のためlinkPanelを生成
				Panel hierarchyPanel = new ManagePracticeLinkedNamePanel("name", item.getModel(), category);
				// // // // // // //----------------// // // // // // //

				if(childTreeType == TreeType.HIERARCHY){

					selectCategory = category;
					System.out.println("HIERARCHY in " + selectCategory);

				}
				else if (childTreeType == TreeType.CATEGORY) {

				//  CATEGORY時は、教科名↓を拾う それ以外は受け渡し
					selectCategory = item.getModelObject().getName();
					System.out.println("CATEGORY in " + selectCategory);
					System.out.println(" = " + category);
					hierarchyPanel = new ManagePracticeLinkedNamePanel("name", item.getModel(), selectCategory);

				}
				else if (childTreeType == TreeType.PRACTICE){

				//  PRACTICE時はコンテンツ名のためnamePanelを生成
					hierarchyPanel = new ManagePracticeNamePanel("name", item.getModel());

				//  PRACTICE時はServiceからNameをひっぱる
					authorName = accountService.getPersonalBean(authorId).getName();

					if(loginId == authorId || loginRole.equals("ADMIN")){
						deleteType = "チェックする";

					}
				}




				switch (childTreeType) {
				case CATEGORY:
					item.add(new Label("type", "教科"));
					break;
				case HIERARCHY:
					item.add(new Label("type", "フォルダ"));
					break;
				case PRACTICE:
					item.add(new Label("type", "実践"));
					break;
				default:
					break;
				}
				item.add(new Link<Void>("edit") {
					static final long serialVersionUID = -5337799630668686803L;

					@Override
					public void onClick() {


						setResponsePage(new PracticeEditPage(parent,
								Model.of(new PracticeRegisterBean(
										practiceService.makePractice(childTreeId),
										practiceService.editUnfolding(childTreeId),
										practiceService
												.selectPracticeKeywordToString(childTreeId),
										practiceService.selectPracticeTagToString(childTreeId),
										category))));
					}
				}.setVisibilityAllowed(childTreeType == TreeType.PRACTICE));
				item.add(new Link<Void>("changeHierarchyBody") {
					private static final long serialVersionUID = -8913692606235576226L;

					@Override
					public void onClick() {
						setResponsePage(new PracticeHierarchyChangePage(item.getModelObject(),
								hierarchyService.searchSubRoot(), category));
					}
				}.setVisibilityAllowed(loginRole.equals("ADMIN")));


			//  (link or name) Panel
				item.add(hierarchyPanel);

//			//  (管理者 or 作成者) Label
				item.add(new Label("authorBody",authorName));

			//  実践にのみ表示 ☑チェック
				item.add(new Check<>("check", item.getModel())
						.setVisibilityAllowed(childTreeType == TreeType.PRACTICE
						&& (loginRole.equals("ADMIN") || loginId == authorId)));

			//  実践以外で表示 Label
				item.add(new Label("checkLabel",deleteType));
			}
		};

		checkGroup.add(new Label("changeHierarchyHead", "階層変更")
				.setVisibilityAllowed(loginRole.equals("ADMIN")));

		checkGroup.add(new Label("authorHead", "作成者"));

		checkGroup.add(childList);
		val form = new Form<Void>("form");

		AjaxButton toDeletePracticeComfirmPageButton = new AjaxButton("toDeletePracticeComfirmPage", form) {
			private static final long serialVersionUID = 2938774725279676253L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				if (checkList.size() >= 1) {
					setResponsePage(new PracticeDeleteConfirmPage(parent, checkList, category));
				} else {

					error(Definition.TO_DELETEPRACTICEPAGE_ADMIN_ERROR);
					target.add(feedback);
				}
			}
		};
		form.add(toDeletePracticeComfirmPageButton);

		form.add(checkGroup);

		form.add(new Link<Void>("toOneLevelUp") {
			private static final long serialVersionUID = -3109069479905894671L;

			@Override
			public void onClick() {
				setResponsePage(new ManagePracticePage(parent.getParent(), category));
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(parent.getTreeType() != TreeType.ROOT);
			}
		});

		form.add(new Link<Void>("toRegisterPracticePage") {
			private static final long serialVersionUID = 8733634736769487234L;

			@Override
			public void onClick() {
				setResponsePage(
						new PracticeRegisterPage(parent,
								Model.of(new PracticeRegisterBean(
										practiceService.registerUnfoldingList(DEFAULT_UNFOLDING_SIZE),
										loginId, parent.getId(), category))));
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(parent.getTreeType() != TreeType.ROOT);
			}
		});
		add(form);
	}

	@Override
	public String getTitle() {
		return "実践管理ページ";
	}
}
