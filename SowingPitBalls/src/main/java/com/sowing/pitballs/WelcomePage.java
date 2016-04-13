package com.sowing.pitballs;


import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class WelcomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	

	public WelcomePage(final PageParameters parameters) {
		super(parameters);
		
		add(new PanelGamingBoard("simplepanel"));
	}
}
