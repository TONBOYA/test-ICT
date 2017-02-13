package jp.ac.chitose.ui.practice;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.service.Interface.ITextFormLimmiterService;

public class UnfoldingFormPanel extends Panel {
	private static final long serialVersionUID = -8248676251612414786L;

	@Inject
	private ITextFormLimmiterService textFormLimmiterService;

	public UnfoldingFormPanel(String id,IModel<UnfoldingFormBean> unfoldingModel,int unfoldingNumber) {
		super(id,unfoldingModel);

		int s = 0;
		List<Integer> requiredMinuteList = new ArrayList<>();
		for (int i = 0;i < 12; i++){
			requiredMinuteList.add(s);
			s += 5;
		}


		setDefaultModel(CompoundPropertyModel.of(unfoldingModel));

		add(new Label("ordinalForHeading",unfoldingModel.getObject().getOrdinal()));
//		//add(new Label("maxUnfoldingForHeading",unfoldingNumber));
//
//		add(new Label("ordinalForName",unfoldingModel.getObject().getOrdinal()));
//		add(new Label("maxUnfoldingForName",unfoldingNumber));
//
//		add(new Label("ordinalForTime",unfoldingModel.getObject().getOrdinal()));
//		add(new Label("maxUnfoldingForTime",unfoldingNumber));
//
//		add(new Label("ordinalForInstruction",unfoldingModel.getObject().getOrdinal()));
//		add(new Label("maxUnfoldingForInstruction",unfoldingNumber));
//
//		add(new Label("ordinalForSpecial",unfoldingModel.getObject().getOrdinal()));
//		add(new Label("maxUnfoldingForSpecial",unfoldingNumber));

		add(new TextField<String>("name"){

			private static final long serialVersionUID = -3364457466125643713L;
			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("展開名(" + unfoldingModel.getObject().getOrdinal() + ")"));

				add(new AjaxFormComponentUpdatingBehavior("onchange") {
					private static final long serialVersionUID = 2341644188342668312L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						if (textFormLimmiterService.measureCharactersBlank(unfoldingModel.getObject().getName())) {
							System.out.println("ここは地獄ぞ");
							error("名前 欄に全角スペースを入れないでください。");
						} else {
							System.out.println("ここは天国ぞ");
						setUnfoldingTarget(target);
						}
					}
				});
			}
		});

		add(new DropDownChoice<Integer>("requiredMinute",requiredMinuteList){
			private static final long serialVersionUID = 6028306615003844671L;
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new AjaxFormComponentUpdatingBehavior("onchange") {
					private static final long serialVersionUID = 4562430169147495373L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setUnfoldingTarget(target);
					}
				});
			}
		});

		add(new TextArea<String>("instruction"){

			private static final long serialVersionUID = 3958048111568139645L;
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					private static final long serialVersionUID = 362846015622335011L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setUnfoldingTarget(target);
					}
				});
			}
		});

		add(new TextArea<String>("specialInstruction"){

			private static final long serialVersionUID = 1881647581566042355L;
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new AjaxFormComponentUpdatingBehavior("onchange") {

					private static final long serialVersionUID = -8965539305545819153L;

					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						setUnfoldingTarget(target);
					}
				});
			}
		});




		add(new PracticeImageSubmitPanel("firstScenePanel", Model.ofList(unfoldingModel.getObject().getFirstImageUploader()), Model.of(unfoldingModel.getObject().getFirstImage()), unfoldingModel.getObject().getOrdinal(), 1){
			private static final long serialVersionUID = 4858549293872404252L;
			@Override
			protected void onInitialize() {
				setOutputMarkupId(true);
				setOutputMarkupPlaceholderTag(true);
				super.onInitialize();
			}
			@Override
			public void setTargetForPracticeImageSubmitPanel(AjaxRequestTarget target) {
				setUnfoldingTarget(target);
			}
		});
		add(new PracticeImageSubmitPanel("secondScenePanel", Model.ofList(unfoldingModel.getObject().getSecondImageUploader()), Model.of(unfoldingModel.getObject().getSecondImage()), unfoldingModel.getObject().getOrdinal(), 2){
			private static final long serialVersionUID = 8736361004772177136L;
			@Override
			protected void onInitialize() {
				setOutputMarkupId(true);
				setOutputMarkupPlaceholderTag(true);
				super.onInitialize();
			}
			@Override
			public void setTargetForPracticeImageSubmitPanel(AjaxRequestTarget target) {
				setUnfoldingTarget(target);
			}
		});


//		WebMarkupContainer firstSceneFileUploadContainer = new WebMarkupContainer("firstSceneFileUploadContainer"){
//
//			private static final long serialVersionUID = -1180916643148185492L;
//
//			@Override
//			protected void onInitialize() {
//				super.onInitialize();
//				setOutputMarkupId(true);
//				setOutputMarkupPlaceholderTag(true);
//			}
//
//			@Override
//			protected void onConfigure() {
//				super.onConfigure();
//				setVisibilityAllowed(StringUtils.isBlank(unfoldingModel.getObject().getFirstSceneFileName()));
//
//			}
//		};
//
//		add(firstSceneFileUploadContainer);
//
//		WebMarkupContainer firstSceneFileUploadCancelContainer = new WebMarkupContainer("firstSceneFileUploadCancelContainer"){
//
//			private static final long serialVersionUID = -1146322125567923570L;
//
//			@Override
//			protected void onInitialize() {
//				super.onInitialize();
//				setOutputMarkupId(true);
//				setOutputMarkupPlaceholderTag(true);
//			}
//
//			@Override
//			protected void onConfigure() {
//				super.onConfigure();
//				setVisibilityAllowed(!StringUtils.isBlank(unfoldingModel.getObject().getFirstSceneFileName()));
//			}
//
//		};
//
//		add(firstSceneFileUploadCancelContainer);
//
//		WebMarkupContainer secondSceneFileUploadContainer = new WebMarkupContainer("secondSceneFileUploadContainer"){
//
//			private static final long serialVersionUID = 7056886354067996511L;
//
//			@Override
//			protected void onInitialize() {
//				super.onInitialize();
//				setOutputMarkupId(true);
//				setOutputMarkupPlaceholderTag(true);
//			}
//
//			@Override
//			protected void onConfigure() {
//				super.onConfigure();
//				setVisibilityAllowed(StringUtils.isBlank(unfoldingModel.getObject().getSecondSceneFileName()));
//
//			}
//		};
//
//		add(secondSceneFileUploadContainer);
//
//
//		WebMarkupContainer secondSceneFileUploadCancelContainer = new WebMarkupContainer("secondSceneFileUploadCancelContainer"){
//
//			private static final long serialVersionUID = 2022066682791924678L;
//
//			@Override
//			protected void onInitialize() {
//				super.onInitialize();
//				setOutputMarkupId(true);
//				setOutputMarkupPlaceholderTag(true);
//			}
//
//			@Override
//			protected void onConfigure() {
//				super.onConfigure();
//				setVisibilityAllowed(!StringUtils.isBlank(unfoldingModel.getObject().getSecondSceneFileName()));
//			}
//
//		};
//
//		add(secondSceneFileUploadCancelContainer);
//
//
//		firstSceneFileUploadContainer.add(new FileUploadField("firstScene"){
//
//			private static final long serialVersionUID = -2844376602264071274L;
//
//			@Override
//			protected void onInitialize() {
//				super.onInitialize();
////				add(new AjaxEventBehavior("change") {
////
////					@Override
////					protected void onEvent(AjaxRequestTarget target) {
////						unfoldingModel.getObject().setFirstSceneFileName(sceneService.makeShelterSceneFile(MySession.get().getAccountBean().getAccountId(), unfoldingModel.getObject().getFirstScene(), unfoldingModel.getObject().getOrdinal(), 1));
////						setTarget(target);
////						firstSceneFileUploadContainer.setVisibilityAllowed(false);
////						firstSceneFileUploadCancelContainer.setVisibilityAllowed(true);
////						target.add(firstSceneFileUploadContainer);
////						target.add(firstSceneFileUploadCancelContainer);
////					}
////				});
//				add(new AjaxFormSubmitBehavior("change"){
//					private static final long serialVersionUID = -1663084516643079685L;
//
//					@Override
//					protected void onSubmit(AjaxRequestTarget target) {
//						unfoldingModel.getObject().setFirstSceneFileName(sceneService.makeShelterSceneFile(MySession.get().getAccountBean().getAccountId(), unfoldingModel.getObject().getFirstScene(), unfoldingModel.getObject().getOrdinal(), 1));
//						setTarget(target);
//						firstSceneFileUploadContainer.setVisibilityAllowed(false);
//						firstSceneFileUploadCancelContainer.setVisibilityAllowed(true);
//						target.add(firstSceneFileUploadContainer);
//						target.add(firstSceneFileUploadCancelContainer);
//					}
//				});
//			}
//
//		});
//
//		secondSceneFileUploadContainer.add(new FileUploadField("secondScene"){
//
//			private static final long serialVersionUID = -8028623963270454623L;
//
//			@Override
//			protected void onInitialize() {
//				super.onInitialize();
//				add(new AjaxFormSubmitBehavior("change"){
//					private static final long serialVersionUID = -2245946572815887637L;
//
//					@Override
//					protected void onSubmit(AjaxRequestTarget target) {
//						if(unfoldingModel.getObject().getSecondScene().get(0) != null){
//							unfoldingModel.getObject().setSecondSceneFileName(sceneService.makeShelterSceneFile(MySession.get().getAccountBean().getAccountId(), unfoldingModel.getObject().getSecondScene(), unfoldingModel.getObject().getOrdinal(), 2));
//						}
//						setTarget(target);
//						secondSceneFileUploadContainer.setVisibilityAllowed(false);
//						secondSceneFileUploadCancelContainer.setVisibilityAllowed(true);
//						target.add(secondSceneFileUploadContainer);
//						target.add(secondSceneFileUploadCancelContainer);
//					}
//				});
//			}
//		});
//
//		AjaxLink<Void> firstFileUploadCancelButton = new AjaxLink<Void>("firstFileUploadCancelButton") {
//
//			private static final long serialVersionUID = -9121810038125083814L;
//
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				unfoldingModel.getObject().setFirstSceneFileName(sceneService.deleteShelterSceneFile(MySession.get().getAccountBean().getAccountId(), unfoldingModel.getObject().getFirstSceneFileName()));
//				setTarget(target);
//				firstSceneFileUploadContainer.setVisibilityAllowed(true);
//				firstSceneFileUploadCancelContainer.setVisibilityAllowed(false);
//				target.add(firstSceneFileUploadContainer);
//				target.add(firstSceneFileUploadCancelContainer);
//
//			}
//		};
//		firstSceneFileUploadCancelContainer.add(firstFileUploadCancelButton);
//
//
//		AjaxLink<Void> secondFileUploadCancelButton = new AjaxLink<Void>("secondFileUploadCancelButton") {
//
//			private static final long serialVersionUID = -6544379642853182672L;
//
//			@Override
//			public void onClick(AjaxRequestTarget target) {
//				unfoldingModel.getObject().setSecondSceneFileName(sceneService.deleteShelterSceneFile(MySession.get().getAccountBean().getAccountId(), unfoldingModel.getObject().getSecondSceneFileName()));
//				setTarget(target);
//				secondSceneFileUploadContainer.setVisibilityAllowed(true);
//				secondSceneFileUploadCancelContainer.setVisibilityAllowed(false);
//				target.add(secondSceneFileUploadContainer);
//				target.add(secondSceneFileUploadCancelContainer);
//
//			}
//		};
//
//		secondSceneFileUploadCancelContainer.add(secondFileUploadCancelButton);

	}

	protected void setUnfoldingTarget(AjaxRequestTarget target){
	}

	public void getError2() {

	}

}
