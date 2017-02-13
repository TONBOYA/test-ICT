package jp.ac.chitose.ui.practice;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.ParentPage;

public class PracticeDeleteConfirmPage extends ParentPage {
	private static final long serialVersionUID = -7075502984822054482L;
	private Model<PracticePageBean> contentModel;
	@Inject
	IHierarchyService hierarchyService;

	@Inject
	IAccountService accountService;

	@Inject
	IPracticeService practiceService;

	@Override
	public String getTitle() {
		return "実践削除確認ページ";
	}

	public PracticeDeleteConfirmPage(TreeBean parent, List<TreeBean> practiceList,String category){

		add(new Label("path",hierarchyService.makePracticeHierarchyPath(parent)));

		add(new PropertyListView<TreeBean>("practiceList",practiceList) {
			private static final long serialVersionUID = 3498315717152531055L;

			@Override
			protected void populateItem(ListItem<TreeBean> item) {
				item.add(new Label("name",item.getModel().getObject().getName()));
				item.add(new Label("editor",accountService.getPersonalBean(item.getModel().getObject().getAccountId()).getName()));
			}
		});


		add(new Link<Void>("returnManagePracticePage"){

			private static final long serialVersionUID = 4415277142028062206L;

			@Override
			public void onClick() {
				setResponsePage(new ManagePracticePage(parent,category));
			}
		});

		add(new Link<Void>("toDeletePracticeCompletedPage"){
			private static final long serialVersionUID = 8784143518919979868L;

			@Override
			public void onClick() {
				practiceList.stream().forEach(practice ->{
					practiceService.deletePractice(practice.getId());
					//practice = null;
					System.out.println(practice.getId());
				});
				practiceList.stream().forEach(nullplass ->{
					nullplass = null;
					//System.out.println(nullplass.getId());
				});
				setResponsePage(new PracticeDeleteCompletedPage(parent,category));
			}
		});
	}
}
