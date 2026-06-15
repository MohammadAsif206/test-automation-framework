# Test Automation Framework

A multi-language, multi-tool UI test automation monorepo supporting:

- **Java + Selenium** (Maven, TestNG, Allure)
- **TypeScript + Playwright** (Playwright Test, Allure)
- **Python + Selenium / Playwright** (pytest, Allure)

---

## Repository structure

```
test-automation-framework/
├── shared/                        # Shared test data and environment configs
│   ├── config/                    # base-config.json, environments
│   └── test-data/                 # users.json, products.json
├── java-selenium/                 # Java + Selenium module
├── typescript-playwright/         # TypeScript + Playwright module
├── python-selenium-playwright/    # Python + Selenium/Playwright module
├── reports/                       # Unified Allure reports output
├── docker/                        # Selenium Grid docker-compose
└── .github/workflows/             # CI/CD pipelines
```

---

## Quick start

### Java + Selenium
```bash
cd java-selenium
mvn clean test
mvn allure:serve        # open Allure report
```

### TypeScript + Playwright
```bash
cd typescript-playwright
npm install
npx playwright install
npm test
npx playwright show-report
```

### Python
```bash
cd python-selenium-playwright
pip install -r requirements.txt
playwright install      # if using Playwright
pytest --alluredir=allure-results
allure serve allure-results
```

---

## Running all modules in CI

Push to `main` or open a PR — GitHub Actions runs all three modules in parallel.
See `.github/workflows/` for pipeline details.

---

## Selenium Grid (Docker)

```bash
cd docker
docker-compose up -d
# Grid UI: http://localhost:4444
```

Set `GRID_URL=http://localhost:4444` in your `.env` to route tests through the grid.
