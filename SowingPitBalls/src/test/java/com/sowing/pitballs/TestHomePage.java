package com.sowing.pitballs;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import com.sowing.pitballs.WelcomePage;
import com.sowing.pitballs.WicketApplication;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(WelcomePage.class);

		//assert rendered page class
		tester.assertRenderedPage(WelcomePage.class);
	}
}
