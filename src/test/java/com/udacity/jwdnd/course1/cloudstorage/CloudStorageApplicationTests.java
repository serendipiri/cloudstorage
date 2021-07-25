package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String URL = "";
	private String username = "serendip";
	private String password = "123456";


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		URL = "http://localhost:" + port;
		this.driver = new ChromeDriver();
//		this.wait = new WebDriverWait(driver, 60);

	}

	@AfterEach
	public void afterEach() throws InterruptedException {
		if (this.driver != null) {
			driver.quit();
		}
	}


	public void signup() {
		driver.get(URL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Seren", "Ünal", username, password);
	}

	public void login() {
		driver.get(URL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	@Test
	public void getLoginPage() {
		driver.get(URL + "/login");
		Assertions.assertEquals("Cloud Storage App - Login", driver.getTitle());
	}

	@Test
	@Order(1)
	public void getHomePageWithoutLogin() {
		driver.get(URL + "/home");
		Assertions.assertEquals("Cloud Storage App - Login", driver.getTitle());
	}

	@Test
	@Order(2)
	void testSignupLoginHomeLogout() {
		signup();
		login();

		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		homePage.logout();
		Assertions.assertNotEquals("Home", driver.getTitle());
		Assertions.assertEquals("Cloud Storage App - Login", driver.getTitle());
	}


	@Test
	@Order(3)
	public void getHomePageAfterLogin() {
		login();
		driver.get(URL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
	}


//	public void testUnauthorizedUserAccess(){
//		driver.get(baseURL+"/home");
//		assertEquals("Login",driver.getTitle());
//		driver.get(baseURL+"/upload");
//		assertEquals("Login",driver.getTitle());
//		driver.get(baseURL+"/credential/delete/");
//		assertEquals("Login",driver.getTitle());
//		driver.get(baseURL+"/addCredentials");
//		assertEquals("Login",driver.getTitle());
//	}

}
