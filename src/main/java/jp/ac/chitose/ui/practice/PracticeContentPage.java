package jp.ac.chitose.ui.practice;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.Definition;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.option.TreeType;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.MySession;
import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.content.manager.ContentRegisterPage;
import jp.ac.chitose.ui.content.manager.ManageContentNamePanel;
import lombok.val;

public class PracticeContentPage extends ParentPage {
	private static final long serialVersionUID = 989180259202873078L;

	@Inject
	IAccountService accountService;

	@Inject
	private IContentHierarchyService hierarchyService;

	@Inject
	private IContentService contentService;

	@Inject
	private IPracticeService practiceService;

	@Override
	public String getTitle() {
		return "コンテンツ関連付けページ";
	}

	public PracticeContentPage(TreeBean parent, IModel<PracticeRegisterBean> practiceModel, PageType TYPE) {
		setDefaultModel(CompoundPropertyModel.of(practiceModel));

		// ログインしたユーザーのAccountId
		int loginId = MySession.get().getAccountBean().getAccountId();

		// ログインしたユーザーのAccountRole
		String loginRole = MySession.get().getAccountBean().getRole();

		// treeの現在地を表示するためのラベル
		add(new Label("path", hierarchyService.makeContentHierarchyPath(parent)));

		//////////////////// ------feedback------////////////////////
		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = -6260187453027297208L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
			}
		};
		add(feedback);
		//////////////////// ------feedback------////////////////////

		//////////////////// ------tree構造(テーブル表示)------////////////////////

		// チェックした分だけ格納するlist -> checkList
		val checkList = new ArrayList<TreeBean>();

		// チェックボタンのコンポーネント checkListを持つ -> checkGroup
		val checkGroup = new CheckGroup<TreeBean>("checkGroup", checkList);

		// 親の下についている子treeを格納するlist -> treelist
		List<TreeBean> treeList = new ArrayList<TreeBean>();

		// 各教科と各単元を区別するための処理
		if (parent.getTreeType() == TreeType.ROOT) {
			// 初回のみrootなので通過 type = category : ルートの下は教科名
			treeList = hierarchyService.searchChildrenCategory(parent);
		} else {
			// 二回目以降に通過 type = hierarchy : 教科名の下はフォルダ
			treeList = hierarchyService.searchChildren(parent);
		}

		// treeListの要素を一覧表示にするlistView. treeListを持つ -> childListView
		ListView<TreeBean> childListView = new PropertyListView<TreeBean>("hierarchy", treeList) {
			private static final long serialVersionUID = 1987563454996738022L;

			@Override
			protected void populateItem(ListItem<TreeBean> item) {

				// // // // // // //--変数コンテナ--// // // // // // //

				// 子のtreeId
				int childTreeId = item.getModelObject().getId();

				// 子のtreeType
				TreeType childTreeType = item.getModelObject().getTreeType();

				// コンテンツ作成者のAccountId
				int authorId = item.getModelObject().getAccountId();

				// コンテンツ作成者のname
				String authorName = "管理者";

				// コンテンツ名
				String fileName = "登録されていません";

				String deleteType = "関連付けられません";

				// 削除依頼表示用変数
				String deleteRequest = "----";

				Panel hierarchyPanel = new ManageContentNamePanel("itemName", item.getModel());

				// // // // // // //----------------// // // // // // //

				if (childTreeType == TreeType.CONTENT) {

					// CONTENT時はServiceからNameをひっぱる
					authorName = accountService.getPersonalBean(authorId).getName();

					fileName = contentService.content(item.getModel().getObject().getId()).getFileName();
					if (fileName.length() >= 10) {
						fileName = fileName.substring(0, 10) + "……" + StringUtils.substringAfterLast(fileName, ".");
					}

					if (contentService.content(childTreeId).isDeleteRequest() == true) {
						// CONTENT && DeleteRequest == true

						// trueで削除依頼あり、tableに表示
						deleteRequest = "削除依頼済み";
					}

					deleteType = "チェックする";

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

				// (link or name) Panel
				item.add(hierarchyPanel);

				// (管理者 or 作成者) Label
				item.add(new Label("authorName", authorName));

				// (登録なし or file名) Label
				item.add(new Label("fileName", fileName));

				// ("---" or 依頼あり) Label
				item.add(new Label("deleteRequestBody", deleteRequest)
						.setVisibilityAllowed(parent.getTreeType() != TreeType.ROOT));

				// コンテンツにのみ表示 ☑チェック
				item.add(new Check<>("check", item.getModel()).setVisibilityAllowed(
						childTreeType == TreeType.CONTENT));

				// 削除判断Label
				item.add(new Label("checkLabel", deleteType));

			}

		};
		checkGroup.add(childListView);

		checkGroup.add(new Label("deleteRequestHead","削除依頼")
				.setVisibilityAllowed(parent.getTreeType() != TreeType.ROOT));

		//////////////////// ------tree構造(テーブル表示)------////////////////////


		////////////////////------form------////////////////////
		val form = new Form<Void>("form");

		AjaxButton toPracticeContentConfirmButton = new AjaxButton("toPracticeContentConfirm", form) {
			private static final long serialVersionUID = 2295021786566597941L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				if (checkList.size() == 1) {
					TreeBean bean = checkList.get(0);
					System.out.println(bean.getAccountId() + "," + bean.getName() + " = "
							+ checkList.get(0).getAccountId() + "," + checkList.get(0).getName());

					setResponsePage(new PracticeContentConfirmPage(parent, checkList, practiceModel, TYPE));
				} else {
					error(Definition.TO_RACTICECONTENTPAGE_ADMIN_ERROR);
					target.add(feedback);
				}
			}
		};

		AjaxButton toPracticeContentCancelConfirmButton = new AjaxButton("toPracticeContentCancelConfirm", form) {
			private static final long serialVersionUID = 2295021786566597941L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				try{
					int contentId = practiceService.selectContentId(practiceModel.getObject().getPracticeId());
					setResponsePage(new PracticeContentCancelConfirmPage(parent, practiceModel,contentId, TYPE));
				}catch(NullPointerException e){
					error(Definition.TO_RACTICECONTENTPAGE_CANCEL_ERROR);
					target.add(feedback);
				}
			}
		};

		form.add(new Link<Void>("toUploadContentPage") {
			private static final long serialVersionUID = 5504384815476251562L;

			@Override
			public void onClick() {
				setResponsePage(new ContentRegisterPage(parent,
						Model.of(new ContentPageBean(MySession.get().getAccountBean().getAccountId(), parent.getId(),
								practiceModel.getObject().getCategory())),
						practiceModel, TYPE));
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(parent.getTreeType() != TreeType.ROOT);
			}
		});
		form.add(toPracticeContentConfirmButton);
		form.add(toPracticeContentCancelConfirmButton);
		form.add(checkGroup);
		add(form);
	}




}
