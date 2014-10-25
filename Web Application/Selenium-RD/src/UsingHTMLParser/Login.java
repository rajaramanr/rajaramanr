package UsingHTMLParser;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;


public class Login{
	@FindBy(how = How.XPATH, using ="XPATH")
private WebElement XPATH_1;


	@FindBy(how = How.ID_OR_NAME, using ="RKMA.Login.label.UserID")
private WebElement UserID;


	@FindBy(how = How.ID_OR_NAME, using ="RKMA.Login.label.Password")
private WebElement Password;


	@FindBy(how = How.ID_OR_NAME, using ="auth")
private WebElement auth;


}