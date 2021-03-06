package jp.ac.chitose.ui.practice;

import java.util.ArrayList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.TreeType;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

public class PracticeHierarchyChangePage extends ParentPage{

	/**
	 *
	 */
	private static final long serialVersionUID = 6893654248243524524L;

	@Inject
	private IHierarchyService hierarchyService;

	private int i = 1;

	@Override
	public String getTitle() {
		return "階層変更ページ";
	}

	public PracticeHierarchyChangePage(TreeBean beforeHierarchy, TempTreeBean afterHierarchy,String category){
		//beforeHierarchy.setId(afterHierarchy.getId());
		System.out.println(afterHierarchy.getOrdinal());
		FeedbackPanel feedback = new FeedbackPanel("feedback"){
			private static final long serialVersionUID = -6260187453027297208L;
			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
			}
		};

		add(feedback);

		val list = hierarchyService.searchSubChildren(afterHierarchy);
		val checkList = new ArrayList<TreeBean>();
		val checkGroup = new CheckGroup<TreeBean>("checkGroup",checkList);

		ListView<TempTreeBean> childList = new PropertyListView<TempTreeBean>("hierarchy", list) {
			private static final long serialVersionUID = 1987563454996738022L;

			@Override
			protected void populateItem(ListItem<TempTreeBean> item) {

				switch (item.getModel().getObject().getTreeType()) {
				case HIERARCHY:
					i++;
					System.out.println("aaaaaaaaaaaaaaaaaaaaaaは"+i);
					System.out.println(item.getModel().getObject().getOrdinal());
					item.add(new Label("type", "フォルダ"));
					break;
				case PRACTICE:
					item.add(new Label("type", "実践"));
					break;
				default:
					i--;
					System.out.println("aaaaaaaaaaaaaaaaaaaaaaは"+i);

					break;
				}

				if(item.getModel().getObject().getTreeType() != TreeType.PRACTICE){
					item.add(new ManagePracticeLinkedNamePanel("name", beforeHierarchy, item.getModel(), category));

				}else{
					item.add(new Label("name",item.getModel()));
				}
			}
		};
		checkGroup.add(new Link<Void>("changeHierarchy") {
			private static final long serialVersionUID = -6715256280419213007L;

			@Override
			public void onClick() {
				System.out.println(beforeHierarchy.getName() + ", " + afterHierarchy.getName());
				setResponsePage(new PracticeHierarchyChangeConfirmPage(beforeHierarchy,afterHierarchy,i, category));
			}
		});
		checkGroup.add(new Label("hierarchyName", afterHierarchy.getName()));
		checkGroup.add(childList);

		val form = new Form<Void>("form");

		form.add(checkGroup);

		form.add(new Link<Void>("toOneLevelUp") {
			private static final long serialVersionUID = 3198573063130837556L;
			@Override
			public void onClick() {
				setResponsePage(new PracticeHierarchyChangePage(beforeHierarchy, afterHierarchy.getParent(), category));
			}
			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(afterHierarchy.getTreeType() != TreeType.ROOT);
			}
		});

		add(form);

	}
}
