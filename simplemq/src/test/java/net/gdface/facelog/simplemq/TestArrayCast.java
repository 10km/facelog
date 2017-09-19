package net.gdface.facelog.simplemq;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import net.gdface.facelog.simplemq.CommonUtils;

public class TestArrayCast {
	
	public <T>T[] testGerericArray(List<T> array){
		@SuppressWarnings("unchecked")
		T[] r = (T[]) new Object[0];
		return CommonUtils.toGenericArray(array);
	}
	
	@Test
	public void test() {
		
		String[] array = new String[]{};//new String[]{"hello","word"};
		ArrayList<String> a2 = new ArrayList<String>(Arrays.asList(array));
		
		//Object[] res = CommonUtils.cleanNull(a2);
		String[] r=CommonUtils.toGenericArray(a2,String.class);
	}

}
