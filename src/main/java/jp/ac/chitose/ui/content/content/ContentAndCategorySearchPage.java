package jp.ac.chitose.ui.content.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormChoiceComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.Definition;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.content.manager.ManageContentNamePanel;
import lombok.val;

public class ContentAndCategorySearchPage extends ParentPage{

	private static final long serialVersionUID = -8505834372421738435L;
	@Inject
	IContentService contentService;

	boolean checkBool;
	int checkNum = 0;

	@Override
	public String getTitle() {
		return "検索項目を指定するページ";
	}

	public ContentAndCategorySearchPage(TreeBean parent, List<TreeBean> parents) {

		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = -340004831684278993L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
			}
		};
		add(feedback);

		WebMarkupContainer topPanel = new WebMarkupContainer("top");
		topPanel.add(new Link<Void>("topLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				setResponsePage(new ContentPage());
			}
		});
		add(topPanel);
		topPanel.setVisible(true);

		add(new ContentTreePanel("contentTreePanel"));

		WebMarkupContainer searchPanel = new WebMarkupContainer("search");
		searchPanel.add(new Label("categoryChoiceLabel", "⦿現在 " + parent.getName() + " が選択されています"));
		searchPanel.add(new Label("categoryLabel", "カテゴリー選択: "));
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		ListView<TreeBean> categoryList = new PropertyListView<TreeBean>("category", parents) {
			private static final long serialVersionUID = -4505699943937945442L;

			@Override
			protected void populateItem(ListItem<TreeBean> item) {
				if (!item.getModel().getObject().getName().equals(parent.getName())) {
					item.add(new ContentLinkedNamePanel("categoryName", item.getModel(), parents));
				} else {
					item.add(new ManageContentNamePanel("categoryName", item.getModel()));
				}
			}
		};

		searchPanel.add(categoryList);
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		IModel<ArrayList<String>> searchModel = new Model<ArrayList<String>>();
		checkBool = searchModel.equals(null);

		ArrayList<String> selectList = new ArrayList<String>();
		selectList.add("タブレット");
		selectList.add("電子黒板");
		selectList.add("授業支援ソフト");
		selectList.add("実物投影機");
		selectList.add("デジタル教科書");
		selectList.add("その他");
		val form = new Form<Void>("form");
		form.add(new CheckBoxMultipleChoice<String>("keywordCheck", searchModel, selectList) {
			private static final long serialVersionUID = -656313995048207328L;

			@Override
			protected void onInitialize() {

				super.onInitialize();
				// setLabel(Model.of("キーワード"));
				add(new AjaxFormChoiceComponentUpdatingBehavior() {
					private static final long serialVersionUID = -4583478161680323186L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						//searchModel.getObject().add(getComponent().getDefaultModelObjectAsString());
						target.add();
					}
				});
			}
		});

		val tagList = contentService.selectAllTagList(parent.getName());
		val checkList = new ArrayList<String>();
		val checkGroup = new CheckGroup<>("checkGroup", checkList);
		// 列を制御するリストビュー
					final IModel<List<List<String>>> rowListViewModel = new ListModel<List<String>>(split(tagList, 6));

					// テーブルのカラム3〜7の値を決めるListView
					ListView<List<String>> rowListView = new ListView<List<String>>("rowListView", rowListViewModel) {
						@Override
						protected void populateItem(final ListItem<List<String>> item) {

							// カラムのデータを保持するModelを用意
							IModel<List<String>> columnListViewModel = new ListModel<String>(item.getModelObject());

							// カラムを表示するリストビュー
							ListView<String> columnListView = new ListView<String>("columnListView", columnListViewModel) {

								@Override
								protected void populateItem(ListItem<String> item) {
									item.add(new Label("tagLabel", item.getModel()));
									item.add(new Check<>("check", item.getModel()));
								}
							};
							item.add(columnListView);
						}
					};
					checkGroup.add(rowListView);



		AjaxButton keySearchResultPageLink = new AjaxButton("toKeySearchResultLinkPage", form) {
			private static final long serialVersionUID = 3492980341558347170L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				String itemCategory = "keyword";
				if (searchModel.getObject().size() > 0) {
					setResponsePage(new ContentAndCategorySearchResultPage(parent, parents, searchModel.getObject(), itemCategory));
			}else {
				error(Definition.TO_DELETEPRACTICEPAGE_NOT_KEYWORD_ADMIN_ERROR);
				target.add(feedback);
				}
			}
		};


		AjaxButton tagSearchResultPageLink = new AjaxButton("toTagSearchResultLinkPage", form) {
			private static final long serialVersionUID = -1264048168576145759L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				String itemCategory = "tag";
				if (checkList.size() > 0) {
					setResponsePage(new ContentAndCategorySearchResultPage(parent, parents, checkList, itemCategory));
				} else{
						System.out.println(checkList.size() + "こんなのおかしいよ" + searchModel.getObject().size());
						error(Definition.TO_PRACTICEPAGE_NOT_TAG_ADMIN_ERROR);
						target.add(feedback);

				}
			}
		};


//		AjaxButton searchResultPageLink = new AjaxButton("toSearchResultLinkPage", form) {
//			private static final long serialVersionUID = 2938774725279676253L;
//
//			@Override
//			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
//				super.onSubmit(target, form);
//				if (checkList.size() >= 1 && searchModel.getObject() != null) {
//
//					//setResponsePage(new PracticeAndCategorySearchResultPage(parent, searchModel.getObject(), checkList));
//				} else {
//					if (searchModel.getObject() == null) {
//						error(Definition.TODELETEPRACTICEPAGE_KEYWORD_ADMIN_ERROR);
//						target.add(feedback);
//					}
//					if (checkList.size() == 0) {
//						error(Definition.TODELETEPRACTICEPAGE_TAG_ADMIN_ERROR);
//						target.add(feedback);
//					}
//				}
//			}
//		};
		form.add(checkGroup);
		form.add(keySearchResultPageLink);
		form.add(tagSearchResultPageLink);
		//form.add(searchResultPageLink);


		searchPanel.add(form);
		add(searchPanel);

	}
	private static <T> List<List<T>> split(Collection<T> splitTarget, int size) {
		List<List<T>> result = new ArrayList<List<T>>();
		Iterator<T> it = splitTarget.iterator();
		while (it.hasNext()) {
			int session = 0;
			List<T> add = new ArrayList<>(size);
			while (it.hasNext() && session < size) {
				add.add(it.next());
				session++;
			}
			result.add(add);
		}
		return result;
	}
}

