# cucumber-ui-automation-framework

 The framework was designed to help you to get started with Appium and Selenium quickly. 
 Offers multi-platform support for your cross-platform applications with a single code and
 standardization for all ui automation tests.
 It is mavenized and jenkins friendly.
 
## Running Your Tests

  **This repository itself does not contains any test. This is just a framework.**
  
  **See repository [cucumber-ui-automation-test](https://github.com/mosclofri/cucumber-ui-automation-test) for the framework usage and execution**

### Prerequisites

    if (osx || linux) {
    install https://brew.sh || https://linuxbrew.sh
        brew install maven node
    }
    if (windows) {
    install http://scoop.sh
        scoop install maven node
    }
	npm install -g appium
	npm install -g appium-doctor

run `appium-doctor` to ensure your system is set up properly

### Technology:
 * Selenium
 * Appium
 * Cucumber
 * Maven
 * Spring
  
### Highlights:
 * Selenium and Appium Support
 * Page object pattern
 * Mavenized
 * Behaviour driven development
 * Standardized code across all of your test projects
 * Basic helper methods
 * Business readable HTML test results
 
### Future Work:
 * Network mocking module
 * Improved logging to replace built-in log output
 * Parallel and feature parallel test execution
 * Provide maven archetype for test project creation

 