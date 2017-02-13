package jp.ac.chitose.service.Class;

import jp.ac.chitose.service.Interface.ITextFormLimmiterService;

public class TextFormLimmiterService implements ITextFormLimmiterService{
	@Override
	public boolean measureCharactersToTwoByte(String text) {
		// TODO Auto-generated method stub
		char[] chars = text.toCharArray();
		boolean twoBytesCharacter = true;
		for (int i = 0; i < chars.length; i++) {
			if (String.valueOf(chars[i]).getBytes().length > 2) {
				twoBytesCharacter = false;
				break;
			}
		}
		return twoBytesCharacter;
	}

	@Override
	public boolean measureCharactersBlank(String text) {
		// TODO Auto-generated method stub
		boolean blankCharacter = false;
		try{
			char[] chars = text.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (String.valueOf(chars[i]).equals("　") || String.valueOf(chars[i]).equals(" ")) {
					blankCharacter = true;
					break;
				}
			}
		}catch(NullPointerException e){
			blankCharacter = true;
		}
		return blankCharacter;
	}

}
