package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		driver.manage().window().maximize();
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


	@Test
	@Order(4)
	public void addNote() throws InterruptedException {
//		signup();
		login();
		String title = "Test First Note";
		String descp = "Test First Note Description";

		HomePage homePage = new HomePage(driver);
		homePage.navNotes();

		//There is no notes at first
		Assertions.assertEquals(0, homePage.getListSize());

		// Add a new Note
		WebDriverWait wait = new WebDriverWait(driver, 20);
		homePage.createNote(title, descp);

		//List is not empty anymore
		Assertions.assertEquals(1, homePage.getListSize());
		Assertions.assertTrue(homePage.noteExists(title, descp));
	}


	@Test
	@Order(5)
	public void editNote() throws InterruptedException {

		signup();
		login();

		String title = "Test Second Note";
		String descp = "Test Second Note Description";
		String editedTitle = "Test Second Note - Edited";
		String editedDescp = "Test Second Note Description - Edited";

		HomePage homePage = new HomePage(driver);
		homePage.navNotes();

		// Add a new Note
		WebDriverWait wait = new WebDriverWait(driver, 20);
		homePage.createNote(title, descp);

		// Then edit it..
		wait = new WebDriverWait(driver, 20);
		homePage.editNote(title, descp, editedTitle, editedDescp);
		Assertions.assertTrue(homePage.noteExists(editedTitle, editedDescp));

	}

}
