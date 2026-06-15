import { test, expect } from '../src/fixtures';

test.describe('Inventory', () => {
  test('inventory page shows 6 items @smoke', async ({ authenticatedPage }) => {
    expect(await authenticatedPage.getItemCount()).toBe(6);
  });

  test('can add item to cart @smoke', async ({ authenticatedPage }) => {
    const itemName = await authenticatedPage.addFirstItemToCart();
    expect(itemName).toBeTruthy();
    // Cart badge should update to 1
    const cartBadge = authenticatedPage.page.locator('.shopping_cart_badge');
    await expect(cartBadge).toHaveText('1');
  });

  test('sort by price low-to-high @regression', async ({ authenticatedPage }) => {
    await authenticatedPage.sortBy('lohi');
    // Verify first item is the cheapest by checking the first price label
    const prices = authenticatedPage.page.locator('.inventory_item_price');
    const firstPrice  = parseFloat(((await prices.first().textContent()) ?? '').replace('$', ''));
    const secondPrice = parseFloat(((await prices.nth(1).textContent()) ?? '').replace('$', ''));
    expect(firstPrice).toBeLessThanOrEqual(secondPrice);
  });
});
