package jp.ac.chitose.bean;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.tree.AbstractTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.model.IDetachable;
import org.apache.wicket.model.IModel;

public abstract class StyleCustomTreeContent<T> implements IDetachable {
	private static final long serialVersionUID = 6788325987452129827L;

	public abstract void onLeafClick(AjaxRequestTarget target, IModel<T> model);
	public abstract String getClosedFolderClass();
	public abstract String getOpenFolderClass();
	public abstract String getLeafClass(T t);

	@Override
	public void detach(){

	}

	public Component newContentComponent(String id, final AbstractTree<T> tree, IModel<T> model){
		return new Folder<T>(id,tree,model){
			private static final long serialVersionUID = 1019137068229782562L;

			@Override
			protected MarkupContainer newLinkComponent(String _id,final IModel<T> _model){
				T t = _model.getObject();
				if(tree.getProvider().hasChildren(t)){
					return super.newLinkComponent(_id,_model);
				}
				return new AjaxLink<T>(_id,_model){
					private static final long serialVersionUID = -4658448375034999927L;

					@Override
					public void onClick(AjaxRequestTarget target){
						onLeafClick(target,_model);
					}
				};
			}
			@Override
			protected String getClosedStyleClass(){
				return getClosedFolderClass();
			}

			@Override
			protected String getOpenStyleClass(){
				return getOpenFolderClass();
			}

			@Override
			protected String getOtherStyleClass(T t){
				return getLeafClass(t);
			}
		};
	}
}
