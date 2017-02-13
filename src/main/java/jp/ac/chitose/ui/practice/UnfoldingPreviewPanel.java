package jp.ac.chitose.ui.practice;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.DynamicImageResource;
import org.apache.wicket.request.resource.IResource;

import com.google.inject.Inject;

import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.option.RegisterDefinition;
import jp.ac.chitose.service.Interface.IImageService;
import jp.ac.chitose.ui.CustomMultiLineLabel;

public class UnfoldingPreviewPanel extends Panel {
	private static final long serialVersionUID = -6071053012525228579L;
	@Inject
	private IImageService imageService;

	public UnfoldingPreviewPanel(String id, IModel<UnfoldingFormBean> model, int accountId) {
		super(id, model);
		setDefaultModel(CompoundPropertyModel.of(model));

	try {
			imageService.TranceferToShelter(model.getObject().getPracticeId(), model.getObject().getFirstImage().getFileName());
			imageService.TranceferToShelter(model.getObject().getPracticeId(), model.getObject().getSecondImage().getFileName());

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		add(new Label("name"));
		add(new Label("requiredMinute"));
		add(new CustomMultiLineLabel("instruction"));
		add(new CustomMultiLineLabel("specialInstruction"));

		add(new NonCachingImage("firstScene") {
			private static final long serialVersionUID = -3125651350998325201L;

			@Override
			protected void onConfigure() {
				super.onConfigure();

				setVisibilityAllowed(!model.getObject().getFirstImage().getFileName().equals(""));
			}

			@Override
			protected IResource getImageResource() {
				return new DynamicImageResource() {
					private static final long serialVersionUID = 7240555724902322781L;

					@Override
					protected byte[] getImageData(Attributes arg0) {

						if (!StringUtils.isBlank(model.getObject().getFirstImage().getFileName())) {
							return imageService.fileToByte(
									new File(String.format(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT, accountId)
											+ model.getObject().getFirstImage().getFileName()));
						} else {
							return null;
						}
					}
				};
			}
		});

		add(new NonCachingImage("secondScene") {
			private static final long serialVersionUID = -291916600124186726L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(!model.getObject().getSecondImage().getFileName().equals(""));
			}

			@Override
			protected IResource getImageResource() {
				return new DynamicImageResource() {
					private static final long serialVersionUID = -6539799935430278269L;

					@Override
					protected byte[] getImageData(Attributes arg0) {
						if (!StringUtils.isBlank(model.getObject().getSecondImage().getFileName())) {
							return imageService.fileToByte(
									new File(String.format(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT, accountId)
											+ model.getObject().getSecondImage().getFileName()));
						} else {
							return null;
						}
					}
				};
			}
		});
	}

	public UnfoldingPreviewPanel(String id, IModel<UnfoldingFormBean> model, int accountId, PageType TYPE) {
		super(id, model);
		setDefaultModel(CompoundPropertyModel.of(model));

		add(new Label("name"));
		add(new Label("requiredMinute"));
		add(new CustomMultiLineLabel("instruction"));
		add(new CustomMultiLineLabel("specialInstruction"));

		add(new NonCachingImage("firstScene") {
			private static final long serialVersionUID = -3125651350998325201L;

			@Override
			protected void onConfigure() {
				super.onConfigure();

				setVisibilityAllowed(!model.getObject().getFirstImage().getFileName().equals(""));
			}

			@Override
			protected IResource getImageResource() {
				return new DynamicImageResource() {
					private static final long serialVersionUID = 7240555724902322781L;

					@Override
					protected byte[] getImageData(Attributes arg0) {
						System.out.println("3.ファイルネームは == " + model.getObject().getFirstImage().getFileName());
						if (!StringUtils.isBlank(model.getObject().getFirstImage().getFileName())) {
							return imageService.fileToByte(
									new File(String.format(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT, accountId)
											+ model.getObject().getFirstImage().getFileName()));
						} else {
							return null;
						}
					}
				};
			}
		});

		add(new NonCachingImage("secondScene") {
			private static final long serialVersionUID = -291916600124186726L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(!model.getObject().getSecondImage().getFileName().equals(""));
			}

			@Override
			protected IResource getImageResource() {
				return new DynamicImageResource() {
					private static final long serialVersionUID = -6539799935430278269L;

					@Override
					protected byte[] getImageData(Attributes arg0) {
						if (!StringUtils.isBlank(model.getObject().getSecondImage().getFileName())) {
							return imageService.fileToByte(
									new File(String.format(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT, accountId)
											+ model.getObject().getSecondImage().getFileName()));
						} else {
							return null;
						}
					}
				};
			}
		});
	}

}
