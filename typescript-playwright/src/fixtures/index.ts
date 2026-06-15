import { test as base } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { InventoryPage } from '../pages/InventoryPage';

type Pages = {
  loginPage: LoginPage;
  inventoryPage: InventoryPage;
};

type AuthenticatedFixtures = {
  authenticatedPage: InventoryPage;
};

// Base fixtures — page objects for every test
export const test = base.extend<Pages & AuthenticatedFixtures>({
  loginPage: async ({ page }, use) => {
    await use(new LoginPage(page));
  },

  inventoryPage: async ({ page }, use) => {
    await use(new InventoryPage(page));
  },

  // Pre-authenticated session — use in tests that don't test login itself
  authenticatedPage: async ({ page }, use) => {
    const loginPage = new LoginPage(page);
    await loginPage.open();
    await loginPage.login(
      process.env.TEST_USERNAME ?? 'standard_user',
      process.env.TEST_PASSWORD ?? 'secret_sauce',
    );
    const inventoryPage = new InventoryPage(page);
    await inventoryPage.isLoaded();
    await use(inventoryPage);
  },
});

export { expect } from '@playwright/test';
