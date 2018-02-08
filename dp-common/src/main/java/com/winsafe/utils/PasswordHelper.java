package com.winsafe.utils;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordHelper {
	private final String algorithmName = "md5";
	private int hashIterations = 2;

	public String encryptPassword(String username, String password) {
		String newPassword = new SimpleHash(algorithmName, password, ByteSource.Util.bytes(username), hashIterations).toHex();
		return newPassword;
	}
	public static void main(String[] args) {
		PasswordHelper passwordHelper = new PasswordHelper();
		System.out.println(passwordHelper.encryptPassword("admin", "admin"));
	}
}
