package me.astropvp.astrotournaments.struct;

import java.util.List;

import com.google.common.collect.Lists;

public class CommandReference {
	
	private List<String> references;
	
	public CommandReference() {
		this.references = Lists.newArrayList();
	}
	
	public CommandReference append(String text) {
		this.references.add(text);
		return this;
	}
	
	public boolean isReferenced(String text) {
		text = text.toLowerCase();
		for(String ref : references) {
			if(ref.equals(text)) {
				return true;
			}
			if(text.startsWith(ref)) {
				int index = text.lastIndexOf(" ");
				if(index == -1) {
					continue;
				}
				return text.substring(0, index).startsWith(ref);
			}
		}
		return false;
	}
}
