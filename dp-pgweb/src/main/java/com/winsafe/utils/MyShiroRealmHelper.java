package com.winsafe.utils;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;

import com.winsafe.shiro.MyShiroRealm;

public class MyShiroRealmHelper {

	public void clearCachedAuthorization() {
		RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();    
        MyShiroRealm realm = (MyShiroRealm)rsm.getRealms().iterator().next();   
        realm.clearCachedAuthorization();
	}
}
