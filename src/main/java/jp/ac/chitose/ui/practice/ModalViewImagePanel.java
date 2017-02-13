package jp.ac.chitose.ui.practice;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;

import com.google.inject.Inject;

import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.service.Interface.IImageService;

public class ModalViewImagePanel extends Panel {
	private static final long serialVersionUID = 2789254774497153691L;

	@Inject
	IImageService imageService;

	ModalViewImagePanel(String id, IModel<UnfoldingBean> model, String fileName, String filePath) {
		super(id, model);

		FeedbackPanel feedback = new FeedbackPanel("feedback");
		Form<?> form = new Form<Object>("form");

		form.add(new NonCachingImage("image") {
			private static final long serialVersionUID = 3434601431752754295L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(!StringUtils.isBlank(fileName));
			}
			@Override
			protected IResource getImageResource() {
				return new DynamicImageResource() {
					private static final long serialVersionUID = 898501047008557601L;
					@Override
					protected byte[] getImageData(Attributes arg0) {
						if (!StringUtils.isBlank(fileName)) {
							return imageService.fileToByte(new File(filePath));
						} else {
							return null;
						}
					}
				};
			}
		});
		add(feedback);
		add(form);
	}
}
