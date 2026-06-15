import pytest
import allure
from pages.inventory_page import InventoryPage


@allure.epic("Shopping")
@allure.feature("Inventory")
class TestInventory:

    @allure.story("Page load")
    @allure.severity(allure.severity_level.CRITICAL)
    @pytest.mark.smoke
    def test_inventory_shows_six_items(self, authenticated_driver):
        inventory = InventoryPage(authenticated_driver)
        assert inventory.get_item_count() == 6

    @allure.story("Add to cart")
    @allure.severity(allure.severity_level.CRITICAL)
    @pytest.mark.smoke
    def test_add_item_to_cart(self, authenticated_driver):
        inventory = InventoryPage(authenticated_driver)
        item_name = inventory.add_first_item_to_cart()

        assert item_name, "Item name should not be empty"
        assert inventory.get_cart_count() == 1, "Cart should have 1 item"
