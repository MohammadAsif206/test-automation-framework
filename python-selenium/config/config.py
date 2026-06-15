"""
Config loader — reads from environment variables, .env file, then falls back to defaults.
"""
import os
from pathlib import Path
from dotenv import load_dotenv

# Load .env from module root
load_dotenv(dotenv_path=Path(__file__).parent.parent / ".env", override=False)


class Config:
    BASE_URL: str        = os.getenv("BASE_URL", "https://www.saucedemo.com")
    BROWSER: str         = os.getenv("BROWSER", "chromium").lower()
    HEADLESS: bool       = os.getenv("HEADLESS", "true").lower() == "true"
    INCOGNITO: bool      = os.getenv("INCOGNITO", "false").lower() == "true"
    GRID_URL: str        = os.getenv("GRID_URL", "")
    DRIVER: str          = os.getenv("DRIVER", "selenium").lower()  # 'selenium' | 'playwright'
    DEFAULT_TIMEOUT: int = int(os.getenv("DEFAULT_TIMEOUT", "15000"))
    IMPLICIT_WAIT: int   = int(os.getenv("IMPLICIT_WAIT", "10"))
    SLOW_MO: int         = int(os.getenv("SLOW_MO", "0"))

    TEST_USERNAME: str   = os.getenv("TEST_USERNAME", "standard_user")
    TEST_PASSWORD: str   = os.getenv("TEST_PASSWORD", "secret_sauce")

    # Edge: path to manually downloaded msedgedriver.exe
    # Download from: https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver
    # Example: C:/drivers/msedgedriver.exe  or  ./drivers/msedgedriver
    EDGE_DRIVER_PATH: str = os.getenv("EDGE_DRIVER_PATH", "")
