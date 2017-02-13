package jp.ac.chitose.service.Interface;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.service.Class.TextFormLimmiterService;

@ImplementedBy(TextFormLimmiterService.class)
public interface ITextFormLimmiterService {

	boolean measureCharactersToTwoByte(String text);
	boolean measureCharactersBlank(String text);
	

}
