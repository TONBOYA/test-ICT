package jp.ac.chitose.ui.practice;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;

import com.google.inject.Inject;

import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.option.RegisterDefinition;
import jp.ac.chitose.service.Interface.IImageService;

public class ImagePanel extends Panel {
	private static final long serialVersionUID = -6277958542908655457L;

	@Inject
	private IImageService imageService;

	String firstFileName;
	String firstFilePath;
	String secondFileName;
	String secondFilePath;

	public ImagePanel(String id, IModel<UnfoldingBean> model, int practiceId) {
		super(id, model);
		setDefaultModel(CompoundPropertyModel.of(model));

		List<String> imageList = new ArrayList<String>();
		imageList.add("帳尻合わせ");

		firstFileName = model.getObject().getFirstImage().getFileName();
		firstFilePath = String.format(RegisterDefinition.PRACTICE_IMAGE_PATH_FORMAT, model.getObject().getPracticeId())
				+ model.getObject().getFirstImage().getFileName();
		secondFileName = model.getObject().getSecondImage().getFileName();
		secondFilePath = String.format(RegisterDefinition.PRACTICE_IMAGE_PATH_FORMAT, model.getObject().getPracticeId())
				+ model.getObject().getSecondImage().getFileName();

		Form<?> form = new Form<Object>("form");

		final ModalWindow window = new ModalWindow("modal");
		window.setTitle("拡大表示");
		// window.setInitialHeight(600);
		// window.setInitialWidth(1000);
		window.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			private static final long serialVersionUID = -4107587628060008698L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				window.close(target);
			}
		});

		ListView<String> imageContener = new PropertyListView<String>("imageContener", imageList) {
			private static final long serialVersionUID = -1032119053538395367L;

			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new AjaxButton("firstBtn") {
					private static final long serialVersionUID = -6159328682802611395L;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						try {
							System.out.println(imageService.imageRead(new File(firstFilePath)).getHeight() + ", "
									+ imageService.imageRead(new File(firstFilePath)).getWidth());
							window.setInitialHeight(imageService.imageRead(new File(firstFilePath)).getHeight());
							window.setInitialWidth(imageService.imageRead(new File(firstFilePath)).getWidth());
						} catch (IOException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						window.setContent(
								new ModalViewImagePanel(window.getContentId(), model, firstFileName, firstFilePath));
						window.show(target);
					}
				}.setVisibilityAllowed(!StringUtils.isBlank(firstFileName)));
				item.add(new AjaxButton("secondBtn") {
					private static final long serialVersionUID = -2327366593415777028L;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						try {
							System.out.println(imageService.imageRead(new File(secondFilePath)).getHeight() + ", "
									+ imageService.imageRead(new File(secondFilePath)).getWidth());
							window.setInitialHeight(imageService.imageRead(new File(secondFilePath)).getHeight());
							window.setInitialWidth(imageService.imageRead(new File(secondFilePath)).getWidth());
						} catch (IOException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						window.setContent(
								new ModalViewImagePanel(window.getContentId(), model, secondFileName, secondFilePath));
						window.show(target);
					}
				}.setVisibilityAllowed(!StringUtils.isBlank(secondFileName)));

				item.add(new NonCachingImage("firstScene") {
					private static final long serialVersionUID = 3434601431752754295L;

					@Override
					protected void onConfigure() {
						super.onConfigure();
						setVisibilityAllowed(!StringUtils.isBlank(firstFileName));
					}

					@Override
					protected IResource getImageResource() {
						return new DynamicImageResource() {
							private static final long serialVersionUID = 898501047008557601L;

							@Override
							protected byte[] getImageData(Attributes arg0) {
								if (!StringUtils.isBlank(firstFileName)) {
									return imageService.fileToByte(new File(firstFilePath));
								} else {
									return null;
								}
							}
						};
					}
				}.add(new AjaxEventBehavior("onclick") {
					private static final long serialVersionUID = 1385271971868840613L;

					@Override
					protected void onEvent(AjaxRequestTarget target) {
						try {
							System.out.println(imageService.imageRead(new File(firstFilePath)).getHeight() + ", "
									+ imageService.imageRead(new File(firstFilePath)).getWidth());
							window.setInitialHeight(imageService.imageRead(new File(firstFilePath)).getHeight());
							window.setInitialWidth(imageService.imageRead(new File(firstFilePath)).getWidth());
						} catch (IOException e) {
							// TODO 自動生成された catch ブロック
							e.printStackTrace();
						}
						window.setContent(
								new ModalViewImagePanel(window.getContentId(), model, firstFileName, firstFilePath));
						window.show(target);
					}

				}));
				item.add(new NonCachingImage("secondScene") {
					private static final long serialVersionUID = 649613278148206857L;

					@Override
					protected void onConfigure() {
						super.onConfigure();
						setVisibilityAllowed(!StringUtils.isBlank(secondFileName));
					}

					@Override
					protected IResource getImageResource() {
						return new DynamicImageResource() {
							private static final long serialVersionUID = -6225173469952356730L;

							@Override
							protected byte[] getImageData(Attributes arg0) {
								if (!StringUtils.isBlank(secondFileName)) {
									return imageService.fileToByte(new File(secondFilePath));
								} else {
									return null;
								}
							}
						};
					}
				});
			}
		};
		form.add(imageContener);
		form.add(window);
		add(form);
	}
}
