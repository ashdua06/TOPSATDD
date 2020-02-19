package com.optum.topsuat.reporting;


import cucumber.runtime.CucumberException;
import cucumber.runtime.model.CucumberFeature;

@SuppressWarnings("serial")
public class CucumberExceptions extends CucumberException
{

	public CucumberExceptions(String message) {
		super(message);		
	}
	
	

}
