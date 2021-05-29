package com.gencode.issuetool.unittest;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

public class FutureTest {

	@Test
	public void runSupplyAsync() {
		CompletableFuture result = CompletableFuture.supplyAsync(() -> {
			try {
			TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
			throw new IllegalStateException(e);
			}
			return "Knolders!";
			}).thenApply(name -> "Hello " + name);
			//.thenApply(greeting -> greeting + " Welcome to Knoldus Inc!");
		
		result.thenApply(name -> "dasfasdfas");
		try {
			
			System.out.println(result.get());
			CompletableFuture<String> completableFuture = new CompletableFuture<String>();
			completableFuture.obtrudeValue("dasfasdfas");
			System.out.println(completableFuture.get(3,SECONDS));
			completableFuture.obtrudeValue("adfasdfasddddddddd");
			System.out.println(completableFuture.get(3,SECONDS));
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
