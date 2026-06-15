import { Page, Locator } from '@playwright/test';
import { BasePage } from './BasePage';

export class InventoryPage extends BasePage {
  readonly inventoryItems: Locator;
  readonly itemNames: Locator;
  readonly cartLink: Locator;
  readonly sortDropdown: Locator;

  constructor(page: Page) {
    super(page);
    this.inventoryItems = page.locator('.inventory_item');
    this.itemNames      = page.locator('.inventory_item_name');
    this.cartLink       = page.locator('.shopping_cart_link');
    this.sortDropdown   = page.locator('[data-test="product-sort-container"]');
  }

  async isLoaded(): Promise<boolean> {
    await this.waitForUrl(/inventory/);
    return this.inventoryItems.count().then((c) => c > 0);
  }

  async getItemCount(): Promise<number> {
    return this.inventoryItems.count();
  }

  async getFirstItemName(): Promise<string> {
    return this.getText(this.itemNames.first());
  }

  async addItemToCartByName(name: string): Promise<void> {
    const item = this.page.locator(`.inventory_item:has-text("${name}")`);
    const addButton = item.locator('button[id^="add-to-cart"]');
    await this.click(addButton);
  }

  async addFirstItemToCart(): Promise<string> {
    const firstName = await this.getFirstItemName();
    const addButton = this.inventoryItems.first().locator('button[id^="add-to-cart"]');
    await this.click(addButton);
    return firstName;
  }

  async goToCart(): Promise<void> {
    await this.click(this.cartLink);
  }

  async sortBy(value: 'az' | 'za' | 'lohi' | 'hilo'): Promise<void> {
    await this.sortDropdown.selectOption(value);
  }
}
