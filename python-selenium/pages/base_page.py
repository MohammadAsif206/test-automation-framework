"""
BasePage — supports both Selenium WebDriver and Playwright Page objects.
Set DRIVER=selenium or DRIVER=playwright in your .env (default: playwright).
"""
import logging
from typing import Union

log = logging.getLogger(__name__)


class BasePage:
    """
    Unified base page class.
    Pass either a Selenium WebDriver or a Playwright Page as `driver`.
    """

    def __init__(self, driver):
        self._driver = driver
        self._is_playwright = _is_playwright(driver)

    # ── Navigation ────────────────────────────────────────────────────────────

    def navigate(self, url: str) -> None:
        log.info("Navigating to %s", url)
        if self._is_playwright:
            self._driver.goto(url)
        else:
            self._driver.get(url)

    def get_current_url(self) -> str:
        if self._is_playwright:
            return self._driver.url
        return self._driver.current_url

    # ── Interactions ──────────────────────────────────────────────────────────

    def click(self, locator: str) -> None:
        if self._is_playwright:
            self._driver.locator(locator).click()
        else:
            from selenium.webdriver.support.ui import WebDriverWait
            from selenium.webdriver.support import expected_conditions as EC
            from selenium.webdriver.common.by import By
            WebDriverWait(self._driver, 15).until(
                EC.element_to_be_clickable((By.CSS_SELECTOR, locator))
            ).click()

    def fill(self, locator: str, value: str) -> None:
        if self._is_playwright:
            self._driver.locator(locator).fill(value)
        else:
            from selenium.webdriver.support.ui import WebDriverWait
            from selenium.webdriver.support import expected_conditions as EC
            from selenium.webdriver.common.by import By
            el = WebDriverWait(self._driver, 15).until(
                EC.visibility_of_element_located((By.CSS_SELECTOR, locator))
            )
            el.clear()
            el.send_keys(value)

    def get_text(self, locator: str) -> str:
        if self._is_playwright:
            return self._driver.locator(locator).inner_text()
        else:
            from selenium.webdriver.support.ui import WebDriverWait
            from selenium.webdriver.support import expected_conditions as EC
            from selenium.webdriver.common.by import By
            el = WebDriverWait(self._driver, 15).until(
                EC.visibility_of_element_located((By.CSS_SELECTOR, locator))
            )
            return el.text

    def is_visible(self, locator: str) -> bool:
        try:
            if self._is_playwright:
                return self._driver.locator(locator).is_visible()
            else:
                from selenium.webdriver.support.ui import WebDriverWait
                from selenium.webdriver.support import expected_conditions as EC
                from selenium.webdriver.common.by import By
                WebDriverWait(self._driver, 5).until(
                    EC.visibility_of_element_located((By.CSS_SELECTOR, locator))
                )
                return True
        except Exception:
            return False

    def count(self, locator: str) -> int:
        if self._is_playwright:
            return self._driver.locator(locator).count()
        else:
            from selenium.webdriver.common.by import By
            return len(self._driver.find_elements(By.CSS_SELECTOR, locator))

    def take_screenshot(self, path: str) -> bytes:
        if self._is_playwright:
            return self._driver.screenshot(path=path, full_page=True)
        else:
            self._driver.save_screenshot(path)
            with open(path, "rb") as f:
                return f.read()


def _is_playwright(driver) -> bool:
    """Detect whether driver is a Playwright Page or a Selenium WebDriver."""
    return hasattr(driver, "goto") and hasattr(driver, "locator")
