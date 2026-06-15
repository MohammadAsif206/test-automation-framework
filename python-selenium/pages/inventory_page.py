from pages.base_page import BasePage


class InventoryPage(BasePage):
    INVENTORY_ITEMS = ".inventory_item"
    ITEM_NAMES      = ".inventory_item_name"
    CART_LINK       = ".shopping_cart_link"
    CART_BADGE      = ".shopping_cart_badge"
    SORT_DROPDOWN   = "[data-test='product_sort_container']"

    def is_loaded(self) -> bool:
        url = self.get_current_url()
        return "inventory" in url and self.count(self.INVENTORY_ITEMS) > 0

    def get_item_count(self) -> int:
        return self.count(self.INVENTORY_ITEMS)

    def get_first_item_name(self) -> str:
        return self.get_text(f"{self.ITEM_NAMES}:first-of-type") if self._is_playwright \
            else self._get_first_item_name_selenium()

    def _get_first_item_name_selenium(self) -> str:
        from selenium.webdriver.common.by import By
        els = self._driver.find_elements(By.CSS_SELECTOR, self.ITEM_NAMES)
        return els[0].text if els else ""

    def add_first_item_to_cart(self) -> str:
        name = self.get_first_item_name()
        self.click("button[id^='add-to-cart']")
        return name

    def go_to_cart(self) -> None:
        self.click(self.CART_LINK)

    def get_cart_count(self) -> int:
        if not self.is_visible(self.CART_BADGE):
            return 0
        return int(self.get_text(self.CART_BADGE))
