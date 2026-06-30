
//Triple A
// Arrange
import { test, expect } from '@playwright/test';

// AAA
test("Validate lgonin fuctionality with valid credential", async ({ page }) => {
    //Arrange
    const userS = 'standard_user';
    const password = 'secret_sauce';
    const userL = 'locked_out_user';
    const userP = 'problem_user';
    const userPe = 'performance_glitch_user';
    const userE = 'error_user';
    const userV = 'visual_user';

    const userSm = '/inventory.html';
    const userLm = 'Epic sadface: Sorry, this user has been locked out.';
    const userPm = 'problem_user';
    const userPem = 'performance_glitch_user';
    const userEm = 'error_user';
    const userVm = 'visual_user';
        // Act   
    await page.goto("https://www.saucedemo.com/");
    const title = await page.locator(".login_logo").textContent();
    // Assert
    expect(title).toEqual("Swag Labs");
    await page.locator('#user-name').fill(userS);
    await page.locator('#password').fill(password);
    await page.locator('#login-button').click();

    expect(page).toHaveURL(/inventory/);
    await page.locator('#react-burger-menu-btn').click();
    await page.locator('#logout_sidebar_link').click();


    await page.locator('#user-name').fill(userL);
    await page.locator('#password').fill(password);
    await page.locator('#login-button').click();

    expect(await page.locator('//*[@id="login_button_container"]/div/form/div[3]/h3').textContent()).toContain(userLm);
    await page.reload();

    await page.locator('#user-name').fill(userL);
    await page.locator('#password').fill(password);
    await page.locator('#login-button').click();

    expect(await page.locator('//*[@id="login_button_container"]/div/form/div[3]/h3').textContent()).toContain(userLm);
    await page.reload();

});
