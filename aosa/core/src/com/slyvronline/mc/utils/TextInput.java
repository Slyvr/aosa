package com.slyvronline.mc.utils;

import com.badlogic.gdx.Input.TextInputListener;
import com.slyvronline.mc.objects.Ent;

public class TextInput implements TextInputListener {

	private String inputType;
	
	@Override
	public void input(String text) {
		
	}

	@Override
	public void canceled() {
		
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	
}
