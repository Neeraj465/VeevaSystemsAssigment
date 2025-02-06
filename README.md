# Veeva Technical Assessment

This projects contains Test Automation of three products namely Core Product, Derived Product 1 and Derived Product 2 and has total 4 scenarios covered.

It has 4 Modules:

■ Module 1: automation-framework

■ Module 2: core-product-tests

■ Module 3: derived-product1-tests

■ Module 4: derived-product2-tests

The automation-framework module holds all the reusable code and it is shared to all 3 test modules.Test modules hold their product specific codes.

## Instalation

Clone the project folder in desired location and import each modules as new projects in your IDE

### Prerequisites

run maven build

```bash
mvn clean install
```
import automation-framework as module in all test modules. Install any one of the browsers from chrome, firefox, safari to run UI Test Cases

## Usage

Run the scenarios using test.xml file where user can input thread counts, browser value etc.
Once tests are executed report is stored in 'reports/tests/cucumber/report.html'. One can open report.html to find details of execution result

## Technologies

_Name the technologies used in the project._ 
* [Cucumber](https://cucumber.io/) - Framework Used
* [Selenium](https://www.selenium.dev/) - Automation Tool
* [Maven](https://maven.apache.org/) - Build Tool
