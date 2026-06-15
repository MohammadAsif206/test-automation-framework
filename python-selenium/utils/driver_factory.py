"""
Selenium WebDriver factory.
- No webdriver-manager for Edge (manual driver path via EDGE_DRIVER_PATH)
- Selenium Manager handles Chrome/Firefox automatically (Selenium 4.6+)
- Incognito/private mode support via INCOGNITO=true
"""
import logging
import os
from selenium import webdriver
from selenium.webdriver.chrome.options import Options as ChromeOptions
from selenium.webdriver.firefox.options import Options as FirefoxOptions
from selenium.webdriver.edge.options import Options as EdgeOptions
from selenium.webdriver.chrome.service import Service as ChromeService
from selenium.webdriver.firefox.service import Service as FirefoxService
from selenium.webdriver.edge.service import Service as EdgeService
from selenium.webdriver.remote.webdriver import WebDriver

from config.config import Config

log = logging.getLogger(__name__)


def create_selenium_driver() -> WebDriver:
    """Create and return a configured Selenium WebDriver."""
    browser  = Config.BROWSER
    headless = Config.HEADLESS
    incognito = Config.INCOGNITO
    grid_url = Config.GRID_URL

    log.info("Creating %s driver | headless=%s | incognito=%s", browser, headless, incognito)

    if grid_url:
        return _create_remote_driver(browser, headless, incognito, grid_url)
    return _create_local_driver(browser, headless, incognito)


# ── Options builders ──────────────────────────────────────────────────────────

def _chrome_options(headless: bool, incognito: bool) -> ChromeOptions:
    opts = ChromeOptions()
    if headless:
        opts.add_argument("--headless=new")
    if incognito:
        opts.add_argument("--incognito")
    opts.add_argument("--no-sandbox")
    opts.add_argument("--disable-dev-shm-usage")
    opts.add_argument("--remote-allow-origins=*")
    opts.add_argument("--window-size=1920,1080")
    return opts


def _firefox_options(headless: bool, incognito: bool) -> FirefoxOptions:
    opts = FirefoxOptions()
    if headless:
        opts.add_argument("--headless")
    if incognito:
        opts.add_argument("-private")
    return opts


def _edge_options(headless: bool, incognito: bool) -> EdgeOptions:
    opts = EdgeOptions()
    if headless:
        opts.add_argument("--headless")
    if incognito:
        opts.add_argument("--inprivate")
    opts.add_argument("--no-sandbox")
    opts.add_argument("--disable-dev-shm-usage")
    opts.add_argument("--remote-allow-origins=*")
    opts.add_argument("--window-size=1920,1080")
    return opts


# ── Local driver ──────────────────────────────────────────────────────────────

def _create_local_driver(browser: str, headless: bool, incognito: bool) -> WebDriver:
    if browser == "firefox":
        # Selenium Manager auto-resolves geckodriver
        return webdriver.Firefox(options=_firefox_options(headless, incognito))

    if browser == "edge":
        # Selenium Manager cannot always reach msedgedriver.azureedge.net
        # Use EDGE_DRIVER_PATH env var to point to manually downloaded driver
        edge_driver_path = Config.EDGE_DRIVER_PATH
        if edge_driver_path:
            log.info("Using manual Edge driver: %s", edge_driver_path)
            service = EdgeService(executable_path=edge_driver_path)
        else:
            log.warning(
                "EDGE_DRIVER_PATH not set — falling back to Selenium Manager. "
                "Set EDGE_DRIVER_PATH if you're on a restricted network."
            )
            service = EdgeService()
        return webdriver.Edge(service=service, options=_edge_options(headless, incognito))

    # Default: Chrome — Selenium Manager auto-resolves chromedriver
    return webdriver.Chrome(options=_chrome_options(headless, incognito))


# ── Remote driver ─────────────────────────────────────────────────────────────

def _create_remote_driver(browser: str, headless: bool, incognito: bool, grid_url: str) -> WebDriver:
    log.info("Connecting to Selenium Grid: %s", grid_url)
    if browser == "firefox":
        opts = _firefox_options(headless, incognito)
    elif browser == "edge":
        opts = _edge_options(headless, incognito)
    else:
        opts = _chrome_options(headless, incognito)
    return webdriver.Remote(command_executor=grid_url, options=opts)
