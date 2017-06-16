# cucumber-appium-java-framework

 The framework was designed to help you to get started with Appium quickly. 
 Offers multi-platform support for your cross-platform applications with a single code.
 It is mavenized and jenkins friendly.
 
## Running Your Tests

  **This repository itself does not contains any test. This is just a framework.**
  
  **See repository [cucumber-appium-java-test](https://github.com/mosclofri/cucumber-appium-java-test) for the framework usage and execution**

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
 * Appium
 * Cucumber
 * Maven
 * Spring
  
### Highlights:
 * Page object pattern
 * Mavenized
 * Behaviour driven development
 * Standardized code across all of your test projects
 * Basic helper methods
 * Business readable HTML test results
 
### Future Work:
 * Selenium support
 * Network mocking module
 * Improved logging to replace appium built-in log output
 * Parallel and feature parallel test execution
 * Provide maven archetype for test project creation

 