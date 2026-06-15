import pytest
import allure
from pages.login_page import LoginPage
from pages.inventory_page import InventoryPage


@allure.epic("Authentication")
@allure.feature("Login")
class TestLogin:

    @allure.story("Valid login")
    @allure.severity(allure.severity_level.CRITICAL)
    @pytest.mark.smoke
    def test_successful_login(self, driver):
        login = LoginPage(driver)
        login.open().login("standard_user", "secret_sauce")

        inventory = InventoryPage(driver)
        assert inventory.is_loaded(), "Inventory page should load after valid login"
        assert inventory.get_item_count() > 0, "Inventory should have items"

    @allure.story("Invalid login — locked out")
    @allure.severity(allure.severity_level.NORMAL)
    @pytest.mark.regression
    def test_locked_out_user(self, driver):
        login = LoginPage(driver)
        login.open().login("locked_out_user", "secret_sauce")

        assert login.is_error_displayed(), "Error message should be displayed"
        assert "locked out" in login.get_error_message()

    @allure.story("Invalid login — bad credentials")
    @pytest.mark.regression
    @pytest.mark.parametrize("username,password,expected_fragment", [
        ("wrong_user", "secret_sauce", "do not match"),
        ("standard_user", "wrong_pass", "do not match"),
        ("", "", "Username is required"),
        ("standard_user", "", "Password is required"),
    ])
    def test_invalid_credentials(self, driver, username, password, expected_fragment):
        login = LoginPage(driver)
        login.open().login(username, password)

        assert login.is_error_displayed()
        assert expected_fragment in login.get_error_message()
