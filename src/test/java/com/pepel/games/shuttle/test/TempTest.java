package com.pepel.games.shuttle.test;

import static org.junit.Assert.*;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.gson.Gson;
import com.pepel.games.shuttle.controller.WorldMapGenerator;
import com.pepel.games.shuttle.model.geography.Province;

public class TempTest {

	@Test
	public void test() {
		System.out.println("+7 (913) 460-20-50 abc".replaceAll("[^\\d]", ""));
		System.out.println(Pattern.compile("[^\\d]").matcher("+7 (913) 460-20-50 abc").replaceAll(""));
		System.out.println(Pattern.compile("\\d*").matcher("+7 (913) 460-20-50 abc").matches());
		System.out.println(Pattern.compile("\\d*").matcher("").matches());
	}
}
