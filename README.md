
# 🛍️ Tira Beauty Automation Framework

This is a robust automation framework built to test the end-to-end flow of [Tira Beauty](https://www.tirabeauty.com) — from **Login to Order Placement** using **Java, Selenium, TestNG**, and **Allure Reporting**, with **GitHub Actions** for CI/CD.

---

## 🚀 Technologies Used

| Tool/Library       | Purpose                               |
|--------------------|----------------------------------------|
| Java               | Programming language                   |
| Selenium WebDriver | Web automation                         |
| TestNG             | Test framework                         |
| Maven              | Build and dependency management        |
| Allure             | Reporting framework                    |
| Log4j              | Logging framework                      |
| GitHub Actions     | CI/CD pipeline                         |

---

## 📁 Project Structure

```
tira-beauty-framework/
├── src/
│   ├── main/java/
│   │   ├── base/                # Base classes for WebDriver setup
│   │   ├── pages/               # Page Object classes
│   │   └── utils/               # Utilities: config, logger, screenshot
│   └── test/java/
│       ├── tests/               # TestNG test classes
│       └── listeners/           # Allure + screenshot listener
├── screenshots/                 # Screenshots on failure
├── .github/workflows/          # GitHub Actions pipeline YAML
├── pom.xml                     # Maven dependencies
├── testng.xml                  # TestNG suite config
└── README.md                   # Project documentation
```

---

## ✅ Features

- 🔐 **Mocked Login**: Skips OTP step to simulate user login
- 📋 **Page Validations**: Each page validates important elements and titles
- 🖼️ **Screenshot on Failure**: Captured and attached in Allure report
- 📜 **Log4j Logging**: Tracks all test steps and errors
- 📊 **Allure Report**: Clean and professional reporting
- 🚀 **GitHub Actions**: CI pipeline to run tests and generate report

---

## ⚙️ How to Run the Project Locally

### 1. Clone the repository
```bash
git clone <your-repo-url>
cd tira-beauty-framework
```

### 2. Run tests
```bash
mvn clean test
```

### 3. Generate Allure Report
```bash
allure serve allure-results
```

> Make sure Allure CLI is installed: https://docs.qameta.io/allure/

---

## 🤖 GitHub Actions CI

The pipeline is located in:
```
.github/workflows/maven.yml
```

Steps:
- Checkout code
- Setup Java and Maven
- Run tests with `mvn test`
- Upload Allure results as artifact

---

## 🔍 Test Flow (Login to Order)

1. Launch website
2. Click login (mocked logic)
3. Navigate to Makeup → Lipstick
4. Select a product
5. Add to cart
6. Go to cart
7. Proceed to checkout
8. Stop at order summary/payment page

---

## 📬 Reporting Issues

For any issues, please raise a GitHub issue or contact the framework maintainer.

---

## 🧠 Author Notes

This is an extendable starter framework. You can enhance it with:
- Real login (with session mocking or dev access)
- Data-driven testing using Excel/CSV
- Parallel test execution using TestNG XML
