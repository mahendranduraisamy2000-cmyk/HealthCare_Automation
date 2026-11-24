package com.egypt.utills;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.egypt.hooks.Hooks;

public class BaseClassTest {

	@AfterMethod
	public void resetDriver() {
		BaseClass.driver = null;
	}

	@Test
	public void getAllOptionsReturnsEveryOption() {
		List<WebElement> options = Arrays.asList(
				option("First", true),
				option("Second", false),
				option("Third", false));
		WebElement select = webElement((method, args) -> {
			if ("getTagName".equals(method.getName())) {
				return "select";
			}
			if ("findElements".equals(method.getName())) {
				return options;
			}
			if ("isDisplayed".equals(method.getName()) || "isEnabled".equals(method.getName())) {
				return true;
			}
			return defaultValue(method.getReturnType());
		});

		Assert.assertEquals(new BaseClass().getAllOptions(select),
				Arrays.asList("First", "Second", "Third"));
	}

	@Test
	public void clearClearsTheElementInsteadOfClickingIt() {
		AtomicInteger clearCalls = new AtomicInteger();
		AtomicInteger clickCalls = new AtomicInteger();
		WebElement element = webElement((method, args) -> {
			if ("isDisplayed".equals(method.getName()) || "isEnabled".equals(method.getName())) {
				return true;
			}
			if ("clear".equals(method.getName())) {
				clearCalls.incrementAndGet();
			}
			if ("click".equals(method.getName())) {
				clickCalls.incrementAndGet();
			}
			return defaultValue(method.getReturnType());
		});
		BaseClass.driver = proxy(WebDriver.class, (method, args) ->
				defaultValue(method.getReturnType()));

		new BaseClass().clear(element);

		Assert.assertEquals(clearCalls.get(), 1);
		Assert.assertEquals(clickCalls.get(), 0);
	}

	@Test
	public void clickableWaitRejectsDisabledElements() {
		WebElement element = webElement((method, args) -> {
			if ("isDisplayed".equals(method.getName())) {
				return true;
			}
			if ("isEnabled".equals(method.getName())) {
				return false;
			}
			return defaultValue(method.getReturnType());
		});
		BaseClass.driver = proxy(WebDriver.class, (method, args) ->
				defaultValue(method.getReturnType()));

		boolean timedOut = false;
		try {
			new BaseClass().clickableOfElementWait(0, element);
		} catch (TimeoutException expected) {
			timedOut = true;
		}

		Assert.assertTrue(timedOut, "A disabled element must not satisfy a clickable wait");
	}

	@Test
	public void windowHandleSwitchesToAnotherWindow() {
		AtomicReference<String> switchedWindow = new AtomicReference<>();
		AtomicReference<WebDriver> driverReference = new AtomicReference<>();
		Set<String> handles = new LinkedHashSet<>(Arrays.asList("main", "popup"));
		WebDriver driver = proxy(WebDriver.class, (method, args) -> {
			if ("getWindowHandle".equals(method.getName())) {
				return "main";
			}
			if ("getWindowHandles".equals(method.getName())) {
				return handles;
			}
			if ("switchTo".equals(method.getName())) {
				return proxy(WebDriver.TargetLocator.class, (targetMethod, targetArgs) -> {
					if ("window".equals(targetMethod.getName())) {
						switchedWindow.set((String) targetArgs[0]);
						return driverReference.get();
					}
					return defaultValue(targetMethod.getReturnType());
				});
			}
			return defaultValue(method.getReturnType());
		});
		driverReference.set(driver);
		BaseClass.driver = driver;

		Assert.assertTrue(new BaseClass().windowHandle());
		Assert.assertEquals(switchedWindow.get(), "popup");
	}

	@Test
	public void windowHandleReturnsFalseWhenThereIsNoOtherWindow() {
		BaseClass.driver = proxy(WebDriver.class, (method, args) -> {
			if ("getWindowHandle".equals(method.getName())) {
				return "main";
			}
			if ("getWindowHandles".equals(method.getName())) {
				return Set.of("main");
			}
			return defaultValue(method.getReturnType());
		});

		Assert.assertFalse(new BaseClass().windowHandle());
	}

	@Test
	public void afterHookQuitsAndClearsTheDriver() {
		AtomicInteger quitCalls = new AtomicInteger();
		BaseClass.driver = proxy(WebDriver.class, (method, args) -> {
			if ("quit".equals(method.getName())) {
				quitCalls.incrementAndGet();
			}
			return defaultValue(method.getReturnType());
		});

		new Hooks().closeBrowser();

		Assert.assertEquals(quitCalls.get(), 1);
		Assert.assertNull(BaseClass.driver);
	}

	private static WebElement option(String text, boolean selected) {
		return webElement((method, args) -> {
			if ("getText".equals(method.getName())) {
				return text;
			}
			if ("isSelected".equals(method.getName())) {
				return selected;
			}
			if ("isDisplayed".equals(method.getName()) || "isEnabled".equals(method.getName())) {
				return true;
			}
			return defaultValue(method.getReturnType());
		});
	}

	private static WebElement webElement(MethodHandler handler) {
		return proxy(WebElement.class, handler);
	}

	private static <T> T proxy(Class<T> type, MethodHandler handler) {
		return type.cast(Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type },
				(proxy, method, args) -> handler.invoke(method, args)));
	}

	private static Object defaultValue(Class<?> type) {
		if (!type.isPrimitive()) {
			return null;
		}
		if (type == boolean.class) {
			return false;
		}
		if (type == char.class) {
			return '\0';
		}
		if (type == byte.class) {
			return (byte) 0;
		}
		if (type == short.class) {
			return (short) 0;
		}
		if (type == int.class) {
			return 0;
		}
		if (type == long.class) {
			return 0L;
		}
		if (type == float.class) {
			return 0.0f;
		}
		if (type == double.class) {
			return 0.0d;
		}
		return null;
	}

	@FunctionalInterface
	private interface MethodHandler {
		Object invoke(Method method, Object[] args);
	}
}
