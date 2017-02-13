package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.Session;
//treeBeanSet
public class TreeExpansion implements Set<TreeBean> ,Serializable {

	private static final long serialVersionUID = 6863377258225261648L;

	private static MetaDataKey<TreeExpansion> KEY = new MetaDataKey<TreeExpansion>() {

		private static final long serialVersionUID = 1571183864574181423L;
	};

	private Set<String> names = new HashSet<String>();

	private boolean inverse = false;

	public void expandAll(){
		names.clear();

		inverse = true;
	}

	public void conllapseAll(){
		names.clear();

		inverse = false;
	}


	@Override
	public int size() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		TreeBean tree = (TreeBean)o;

		if(inverse){
			return !names.contains(tree.getName());
		}else{
			return names.contains(tree.getName());
		}
	}

	@Override
	public Iterator<TreeBean> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(TreeBean e) {
		if(inverse){
			return names.remove(e.getName());
		}else{
			return names.add(e.getName());
		}
	}

	@Override
	public boolean remove(Object o) {
		TreeBean tree = (TreeBean)o;

		if(inverse){
			return names.add(tree.getName());
		}else{
			return names.remove(tree.getName());
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends TreeBean> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

	public static TreeExpansion get(){
		TreeExpansion expansion = Session.get().getMetaData(KEY);
		if(expansion == null){
			expansion = new TreeExpansion();
			Session.get().setMetaData(KEY, expansion);
		}

		return expansion;

	}


}
