// File: keycloak-event-gateway/test/integration_tests.sh
//
// Run these tests with the following commands:
//
//     $ docker-compose up -d
//     $ npm install
//     $ SELENIUM_REMOTE_URL=http://localhost:4444/wd/hub npx mocha integration_tests.js

import { assert } from 'chai';
import webdriver from 'selenium-webdriver';
import fs from 'fs';

const driver = new webdriver.Builder()
	//.forBrowser('firefox')
	.withCapabilities(webdriver.Capabilities.firefox()) // Uses RemoteWebDriver
	.build();

describe('integration_tests', () => {

	it('browses Keycloak config', (done) => {
		
		driver		
			.navigate().to('http://localhost:8080/')
			.then(() => driver.sleep(3000))
			.then(() => driver.takeScreenshot())
			.then((data) => {
				if (!fs.existsSync('screenshots')) {
					fs.mkdirSync('screenshots');
				}
				fs.writeFileSync('screenshots/out.png', data, 'base64', (error) => {
					if (error) {
						console.log(error);
						assert.fail('While taking screenshot');
					}
				})
			})
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

