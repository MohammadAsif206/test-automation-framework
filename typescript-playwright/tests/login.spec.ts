import { test, expect } from '../src/fixtures';

test.describe('Authentication', () => {
  test.describe('Login', () => {
    test('standard user logs in successfully @smoke', async ({ loginPage, inventoryPage }) => {
      await loginPage.open();
      await loginPage.login('standard_user', 'secret_sauce');

      expect(await inventoryPage.isLoaded()).toBeTruthy();
      expect(await inventoryPage.getItemCount()).toBeGreaterThan(0);
    });

    test('locked out user sees error @regression', async ({ loginPage }) => {
      await loginPage.open();
      await loginPage.login('locked_out_user', 'secret_sauce');

      expect(await loginPage.isErrorVisible()).toBeTruthy();
      expect(await loginPage.getErrorMessage()).toContain('locked out');
    });

    test('empty credentials show username required error @regression', async ({ loginPage }) => {
      await loginPage.open();
      await loginPage.login('', '');

      expect(await loginPage.isErrorVisible()).toBeTruthy();
      expect(await loginPage.getErrorMessage()).toContain('Username is required');
    });

    test('wrong password shows error @regression', async ({ loginPage }) => {
      await loginPage.open();
      await loginPage.login('standard_user', 'wrong_password');

      expect(await loginPage.isErrorVisible()).toBeTruthy();
      expect(await loginPage.getErrorMessage()).toContain('do not match');
    });
  });
});
