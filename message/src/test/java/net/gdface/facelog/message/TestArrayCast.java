package net.gdface.facelog.message;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestArrayCast {
	
	public <T>T[] testGerericArray(List<T> array){
		@SuppressWarnings("unchecked")
		T[] r = (T[]) new Object[0];
		return array.toArray((T[]) new Object[0]);
	}
	
	@Test
	public void test() {
		
		String[] array = null;//new String[]{"hello","word"};
		Object[] a2 = new ArrayList<String>().toArray();
		Object[] res = CommonUtils.cleanNull(a2);
//		testGerericArray(Arrays.asList(array));
	}

}
