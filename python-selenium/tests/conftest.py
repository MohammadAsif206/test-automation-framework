"""
conftest.py — pytest fixtures for Selenium and Playwright drivers.
Select engine with DRIVER=selenium or DRIVER=playwright in your .env (default: selenium).
Only the requested engine is instantiated — no wasted resources.
"""
import logging
import pytest
import allure
from config.config import Config

log = logging.getLogger(__name__)


# ── Selenium fixture ──────────────────────────────────────────────────────────

@pytest.fixture(scope="function")
def selenium_driver():
    """Yields a Selenium WebDriver and quits after the test."""
    from utils.driver_factory import create_selenium_driver
    driver = create_selenium_driver()
    driver.implicitly_wait(Config.IMPLICIT_WAIT)
    driver.maximize_window()
    yield driver
    driver.quit()
    log.info("Selenium driver closed")


# ── Playwright fixtures ───────────────────────────────────────────────────────

@pytest.fixture(scope="session")
def playwright_instance():
    """Session-scoped Playwright instance."""
    from playwright.sync_api import sync_playwright
    with sync_playwright() as pw:
        yield pw


@pytest.fixture(scope="function")
def playwright_page(playwright_instance):
    """Function-scoped Playwright browser + page."""
    browser_type = getattr(playwright_instance, Config.BROWSER, playwright_instance.chromium)
    browser = browser_type.launch(
        headless=Config.HEADLESS,
        slow_mo=Config.SLOW_MO,
        args=["--incognito"] if Config.INCOGNITO else [],
    )
    context = browser.new_context(
        viewport={"width": 1920, "height": 1080},
        record_video_dir="test-results/videos" if not Config.HEADLESS else None,
    )
    page = context.new_page()
    page.set_default_timeout(Config.DEFAULT_TIMEOUT)

    yield page

    if hasattr(pytest, "current_test_failed") and pytest.current_test_failed:
        screenshot = page.screenshot(full_page=True)
        allure.attach(screenshot, name="screenshot", attachment_type=allure.attachment_type.PNG)

    context.close()
    browser.close()


# ── Generic 'driver' fixture — lazy, picks only what's needed ─────────────────

@pytest.fixture(scope="function")
def driver(request):
    """
    Returns either a Selenium WebDriver or Playwright Page based on DRIVER config.
    Only instantiates the requested engine — not both.
    """
    if Config.DRIVER == "selenium":
        drv = request.getfixturevalue("selenium_driver")
    else:
        drv = request.getfixturevalue("playwright_page")
    return drv


# ── Pre-authenticated fixture ─────────────────────────────────────────────────

@pytest.fixture(scope="function")
def authenticated_driver(driver):
    """Logs in and returns the driver ready on the inventory page."""
    from pages.login_page import LoginPage
    from pages.inventory_page import InventoryPage

    login = LoginPage(driver)
    login.open().login(Config.TEST_USERNAME, Config.TEST_PASSWORD)

    inventory = InventoryPage(driver)
    assert inventory.is_loaded(), "Expected to land on inventory page after login"
    return driver


# ── Hooks ─────────────────────────────────────────────────────────────────────

@pytest.hookimpl(tryfirst=True, hookwrapper=True)
def pytest_runtest_makereport(item, call):
    outcome = yield
    rep = outcome.get_result()
    setattr(item, "rep_" + rep.when, rep)
    pytest.current_test_failed = rep.when == "call" and rep.failed
