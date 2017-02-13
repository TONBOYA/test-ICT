package jp.ac.chitose.ui.content.manager;

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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.Definition;
import jp.ac.chitose.option.TreeType;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.ui.MySession;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

public class ManageContentPage extends ParentPage {
	private static final long serialVersionUID = -9140361420452129505L;

	@Inject
	private IContentHierarchyService cHierarchyService;

	@Inject
	private IContentService contentService;

	@Override
	public String getTitle() {
		return "コンテンツ管理ページ";
	}

	public ManageContentPage(TreeBean parent, String category) {
		System.out.println("選択教科は"+category+"です");

	//	ログインしたユーザーのAccountId
		int loginId = MySession.get().getAccountBean().getAccountId();

	//  ログインしたユーザーのAccountRole
		String loginRole = MySession.get().getAccountBean().getRole();

	//  エラー表示用Panel
		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = -6260187453027297208L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
			}
		};
		add(feedback);

	//  階層を表示する
		add(new Label("path", cHierarchyService.makeContentHierarchyPath(parent)));

	//  親の下についている子treeをListに格納する
		List<TreeBean> treeList = new ArrayList<TreeBean>();
		//	もし現階層がルートだったら・・・
		if (parent.getTreeType() == TreeType.ROOT)
		{
		//  初回のみrootなので通過 type = category : 教科
			treeList = cHierarchyService.searchChildrenCategory(parent);
		} else {
		//  二回目以降に通過 type = hierarchy : フォルダ
			treeList = cHierarchyService.searchChildren(parent);
		}
        //  チェックされたTreeBeanを保持するlist
		val checkDeleteList = new ArrayList<TreeBean>();
		//  チェック項目
		val checkDeleteGroup = new CheckGroup<TreeBean>("checkGroup", checkDeleteList);

		ListView<TreeBean> childList = new PropertyListView<TreeBean>("hierarchy", treeList) {
			private static final long serialVersionUID = 1987563454996738022L;

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

			//  削除依頼表示用変数
				String deleteRequest = "----";

			//	CONTENT時以外は単元名のためlinkPanelを生成
				Panel hierarchyPanel = new ManageContentLinkedNamePanel("name", item.getModel(), category);


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
					hierarchyPanel = new ManageContentLinkedNamePanel("name", item.getModel(), selectCategory);
				}
				else if (childTreeType == TreeType.CONTENT){

				//  CONTENT時はコンテンツ名のためnamePanelを生成
					hierarchyPanel = new ManageContentNamePanel("name", item.getModel());

				//  CONTENT時はServiceからNameをひっぱる
					authorName = accountService.getPersonalBean(authorId).getName();

					if(contentService.content(childTreeId).isDeleteRequest() == true){
					//  CONTENT && DeleteRequest == true

					//  trueで削除依頼あり、tableに表示
						deleteRequest = "削除依頼済み";
					}

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
				case CONTENT:
					item.add(new Label("type", "コンテンツ"));
					break;
				default:
					break;
				}


				// // // // // // //コンポーネント// // // // // // //

			//  (link or name) Panel
				item.add(hierarchyPanel);

			//  (管理者 or 作成者) Label
				item.add(new Label("authorBody",authorName));

			//  ("" or 依頼あり) Label
				item.add(new Label("deleteRequestBody", deleteRequest)
						.setVisibilityAllowed(parent.getTreeType() != TreeType.ROOT));

			//  コンテンツにのみ表示 ☑チェック
				item.add(new Check<>("check", item.getModel())
						.setVisibilityAllowed(childTreeType == TreeType.CONTENT
						&& (loginRole.equals("ADMIN") || loginId == authorId)));

			//  削除判断Label
				item.add(new Label("checkLabel",deleteType));

				// // // // // // // // //--編集ボタン--// // // // // // // // //
				item.add(new Link<Void>("edit") {
					private static final long serialVersionUID = -6715256280419213007L;

					@Override
					public void onClick() {
					//  edit用model
						IModel<ContentPageBean> contentEditModel = Model.of(
								contentService.selectContent(childTreeId));

						setResponsePage(new ContentEditPage(parent, contentEditModel, category));

					}//作成者コンテンツ欄にのみ編集ボタンを表示
				}.setVisibilityAllowed((authorId == loginId || loginRole.equals("ADMIN"))
						&& childTreeType.equals(TreeType.CONTENT)));
			// // // // // // // // //--------------// // // // // // // // //

			// // // // // // // //--階層変更ボタン--// // // // // // // //
				item.add(new Link<Void>("changeHierarchyBody") {
					private static final long serialVersionUID = 8098196965367397755L;

					@Override
					public void onClick() {
						setResponsePage(new ContentHierarchyChangePage(item.getModelObject(),
								cHierarchyService.searchSubRoot(), item.getModelObject().getName()));
					}
				}.setVisibilityAllowed(loginRole.equals("ADMIN")
						&& !(parent.getTreeType() == TreeType.ROOT
							|| parent.getTreeType() == TreeType.CONTENT)));
			// // // // // // // //-----------------// // // // // // // //


			}
		};
		checkDeleteGroup.add(new Label("changeHierarchyHead", "階層変更")
				.setVisibilityAllowed(loginRole.equals("ADMIN")
				&& !(parent.getTreeType() == TreeType.ROOT)));

		checkDeleteGroup.add(new Label("authorHead", "作成者"));

		checkDeleteGroup.add(new Label("deleteRequestHead","削除依頼")
				.setVisibilityAllowed(parent.getTreeType() != TreeType.ROOT));

		checkDeleteGroup.add(childList);

		val form = new Form<Void>("form");
		AjaxButton deleteContentComfirmButton = new AjaxButton("toDeleteContent", form) {
			private static final long serialVersionUID = 2295021786566597941L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setVisibilityAllowed(loginRole.equals("ADMIN"));
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				if (checkDeleteList.size() >= 1) {
				//  checkが一つ以上の時deleteできる(ADMINとコンテンツ登録者に表示)
					setResponsePage(new ContentDeleteConfirmPage(parent, checkDeleteList, category));
				} else {
					error(Definition.TO_DELETECONTENTPAGE_ADMIN_ERROR);
					target.add(feedback);
				}
			}
		};

		AjaxButton deleteRequestComfirmButton = new AjaxButton("toDeleteRequest", form) {
			private static final long serialVersionUID = 2295021786566597941L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setVisibilityAllowed(!(loginRole.equals("ADMIN")));
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				if (checkDeleteList.size() == 1) {
				//  checkが一つの時deleteRequestできる(ADMIN以外に表示)
					setResponsePage(new ContentDeleteRequestConfirmPage(parent, checkDeleteList, category));
				} else {
					error(Definition.TO_DELETECONTENTPAGE_USER_ERROR);
					target.add(feedback);
				}
			}
		};

		form.add(deleteContentComfirmButton);
		form.add(deleteRequestComfirmButton);

		form.add(checkDeleteGroup);

		form.add(new Link<Void>("toOneLevelUp") {
			private static final long serialVersionUID = 3198573063130837556L;

			@Override
			public void onClick() {
				setResponsePage(new ManageContentPage(parent.getParent(), category));
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(parent.getTreeType() != TreeType.ROOT);
			}
		});

		form.add(new Link<Void>("toUploadContentPage") {
			private static final long serialVersionUID = 5504384815476251562L;

			@Override
			public void onClick() {
				setResponsePage(new ContentRegisterPage(parent,
						Model.of(new ContentPageBean(loginId, parent.getId(),
								category))));
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(parent.getTreeType() != TreeType.ROOT);
			}
		});

		add(form);

	}
}
