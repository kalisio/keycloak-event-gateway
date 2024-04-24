// File: keycloak-event-gateway/test/integration_tests.sh
//
// Run these tests with the following commands:
//
//     $ docker-compose up -d
//     $ npm install
//     $ SELENIUM_REMOTE_URL=http://localhost:4444/wd/hub npx mocha integration_tests.js

import { assert } from 'chai';
import webdriver from 'selenium-webdriver';
import { By } from 'selenium-webdriver';
import fs from 'fs';

const driver = new webdriver.Builder()
	//.forBrowser('firefox')
	.withCapabilities(webdriver.Capabilities.firefox()) // Uses RemoteWebDriver
	.build();

var screenshotCount = 0;

const storeScreenshot = (data) => {

	++screenshotCount;
	const fileName = screenshotCount.toString().padStart(8, '0') + '.png';
	console.log('    -> screenshot: ' + fileName);
	if (!fs.existsSync('screenshots')) {
		fs.mkdirSync('screenshots');
	}
	fs.writeFileSync('screenshots/' + fileName, data, 'base64', (error) => {
		if (error) {
			console.log(error);
			assert.fail('While taking screenshot: ' + fileName);
		}
	})
};

describe('integration_tests', () => {

	it('browses Keycloak config', (done) => {
		
		driver
		
		// Login page
			.navigate().to('http://localhost:8080/admin/master/console/')
			.then(() => driver.sleep(3000))
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Credentials
			.then(() => driver.findElement(By.id('username')).sendKeys('admin'))
			.then(() => driver.findElement(By.id('password')).sendKeys('adminp'))
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Submit the login form
			.then(() => driver.findElement(By.id('kc-login')).click())
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Deploy the realm list
			.then(() => driver.sleep(3000))
			.then(() => driver.findElement(By.id('realm-select')).click())
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Add a realm
			.then(() => driver.findElement(By.xpath("//a[@data-testid = 'add-realm']")).click())
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))

		// Fill in the realm form
			.then(() => driver.findElement(By.id('kc-realm-name')).sendKeys('canigou'))
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Submit the realm form
			.then(() => driver.findElement(By.css('button.pf-m-primary')).click())
			.then(() => driver.sleep(3000))
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Go to the realm settings
			.then(() => driver.findElement(By.id('nav-item-realm-settings')).click())
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Open the Events tab
			.then(() => driver.findElement(By.xpath("//span[text() = 'Events']")).click())
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Open the popup listbox
			.then(() => driver.findElement(By.xpath("//button[contains(@aria-labelledby, 'eventsListeners')]")).click())
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Select: "feathers-emitter" in the listbox
			.then(() => driver.findElement(By.xpath("//button[. = 'feathers-emitter']")).click())
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))
			
		// Deploy the action menu
			.then(() => driver.findElement(By.xpath("//div[@data-testid = 'action-dropdown']")).click())
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))

		// Ask to delete the realm
			.then(() => driver.findElement(By.xpath("//a[text() = 'Delete']")).click())
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))

		// Confirm the realm deletion
			.then(() => driver.findElement(By.xpath("//button[@id = 'modal-confirm']")).click())
			.then(() => driver.sleep(3000))
			.then(() => driver.takeScreenshot())
			.then((data) => storeScreenshot(data))

		// End
			.then(() => done())
			.catch((error) => {
				console.log(error);
				done(error);
			});
			
	});
	
	after((done) => {

		driver
			.sleep(3000)
			.then(() => driver.quit())
			.then(() => done())
			.catch((error) => {
				console.log(error);
				done(error);
			});
			
	});

});
