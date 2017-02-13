package jp.ac.chitose.ui.practice;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeImageBean;
import jp.ac.chitose.service.Interface.IImageService;
import jp.ac.chitose.ui.MySession;
import lombok.val;

public class PracticeImageSubmitPanel extends Panel {
	private static final long serialVersionUID = 3570604487144198453L;

	@Inject
	IImageService imageService;

	private boolean visiblebool = true;

	public PracticeImageSubmitPanel(String id){
		super(id);
	}

	public PracticeImageSubmitPanel(String id, IModel<List<FileUpload>> model,IModel<PracticeImageBean> practiceImage,int unfoldingOrdinal,int imageOrdinal) {
		super(id, model);

		int accountId = MySession.get().getAccountBean().getAccountId();
		String imageName = practiceImage.getObject().getFileName();

		val form = new Form<Void>("form");
		add(form);

		form.add(new Label("ordinal",imageOrdinal));
		WebMarkupContainer imageFileUploadContainer = new WebMarkupContainer("imageFileUploadContainer"){

			private static final long serialVersionUID = -1180916643148185492L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
				setOutputMarkupPlaceholderTag(true);
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(StringUtils.isBlank(practiceImage.getObject().getFileName()));
			}
		};

		form.add(imageFileUploadContainer);

		WebMarkupContainer imageFileUploadCancelContainer = new WebMarkupContainer("imageFileUploadCancelContainer"){

			private static final long serialVersionUID = -1146322125567923570L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
				setOutputMarkupPlaceholderTag(true);
			}

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(!StringUtils.isBlank(practiceImage.getObject().getFileName()));
			}

		};

		form.add(imageFileUploadCancelContainer);


		imageFileUploadContainer.add(new FileUploadField("image",model){

			private static final long serialVersionUID = -2844376602264071274L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new AjaxFormSubmitBehavior("change"){
					private static final long serialVersionUID = -1663084516643079685L;

					@Override
					protected void onSubmit(AjaxRequestTarget target) {
						visiblebool = visiblebool ? false : true;
						practiceImage.getObject().setFileName(imageService.makeShelterImageFile(accountId, model.getObject(),unfoldingOrdinal , imageOrdinal));
						setTargetForPracticeImageSubmitPanel(target);
						System.out.println(practiceImage.getObject().getFileName());
						imageFileUploadContainer.setVisibilityAllowed(visiblebool);
						imageFileUploadCancelContainer.setVisibilityAllowed(!visiblebool);
						target.add(imageFileUploadContainer);
						target.add(imageFileUploadCancelContainer);
					}
				});
			}
		});

		AjaxLink<Void> fileUploadCancelButton = new AjaxLink<Void>("fileUploadCancelButton") {

			private static final long serialVersionUID = -9121810038125083814L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				visiblebool = visiblebool ? false : true;
				practiceImage.getObject().setFileName(imageService.deleteShelterImageFile(accountId, imageName));
				setTargetForPracticeImageSubmitPanel(target);
				imageFileUploadContainer.setVisibilityAllowed(visiblebool);
				imageFileUploadCancelContainer.setVisibilityAllowed(!visiblebool);
				target.add(imageFileUploadContainer);
				target.add(imageFileUploadCancelContainer);

			}
		};

		imageFileUploadCancelContainer.add(fileUploadCancelButton);
	}



	public void setTargetForPracticeImageSubmitPanel(AjaxRequestTarget target){

	}

}
