package jp.ac.chitose.ui.practice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;

import com.google.inject.Inject;

import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IPracticeService;

public class TagAllListPanel extends Panel {

	@Inject
	private IPracticeService practiceService;

	@Inject
	private IContentService contentService;

	List<String> tagAllList;

	public TagAllListPanel(String id, String category, String TYPE) {
		super(id);
		if (TYPE.equals("PRACTICE")) {
			tagAllList = practiceService.selectAllTagList(category);
		}else if(TYPE.equals("CONTENT")){
			tagAllList = contentService.selectAllTagList(category);
		}

		// 列を制御するリストビュー
		final IModel<List<List<String>>> rowListViewModel = new ListModel<List<String>>(split(tagAllList, 6));

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

						item.add(new Label("dataLabel", item.getModelObject()));
					}
				};
				item.add(columnListView);
			}
		};
		add(rowListView);
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
