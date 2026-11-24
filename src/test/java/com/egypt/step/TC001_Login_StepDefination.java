package com.egypt.step;

import org.testng.Assert;

import com.egypt.page.LoginPage;
import com.egypt.utills.BaseClass;

import io.cucumber.java.en.*;
import io.cucumber.java.en.When;

public class TC001_Login_StepDefination extends BaseClass {
	
	LoginPage loginPage ;
	
	@Given("User launches the application login page")
	public void user_launches_the_application_login_page() {
	browserLaunch();
	enterApplnUrl("http://158.101.230.16:8484/ehb-QA/login");
	    
	}

//	@When("User selects Staff Provider tab")
//	public void user_selects_staff_provider_tab() {
//	   
//	     
//	}

	@When("User enters valid staff {string} and {string}")
	public void user_enters_valid_staff_and(String user, String pass) throws InterruptedException {
		
	   loginPage = new LoginPage(driver);
	    
	    loginPage.userNameAndPass(user, pass);
	     
	}

	@When("User clicks Authorized Login")
	public void user_clicks_authorized_login() {
		  loginPage = new LoginPage(driver);
	     loginPage.clickBtn();
	     
	}

	@Then("User should navigate to staff dashboard")
	public void user_should_navigate_to_staff_dashboard() {
		  loginPage = new LoginPage(driver);
	      Assert.assertEquals(loginPage.suicessMessage(), "HYUNDAIPRO01");
	     
	}

}
