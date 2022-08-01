package com.luv2code.utils;

import java.util.Optional;
import java.util.function.Supplier;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtils {

	public static <T> Optional<T> optionalChaining(Supplier<T> resolver) {
		try {
			T result = resolver.get();
			return Optional.ofNullable(result);
		} catch (NullPointerException e) {
			return Optional.empty();
		}
	}
}
