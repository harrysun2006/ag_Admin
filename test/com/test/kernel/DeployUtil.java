package com.test.kernel;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DeployUtil {

	private List map;
	private static Log _log = LogFactory.getLog(DeployUtil.class);
	private static DeployUtil _instance = new DeployUtil();

	private DeployUtil() {
		if(_log.isDebugEnabled()) {
			_log.debug("Initialize " + DeployUtil.class.getName() + "... ...");
		}
		map = new Vector();
	}

	public static void registerHost(Host host) {
		_instance._registerHost(host);
		_instance._debug();
	}

	private void _debug() {
		if(_log.isDebugEnabled()) {
			StringBuffer sb = new StringBuffer();
			Host host;
			sb.append("registered hosts: ");
			for(Iterator it = map.iterator(); it.hasNext(); ) {
				host = (Host) it.next();
				sb.append(host).append(", ");
			}
			_log.debug(sb);
		}
	}

	private synchronized void _registerHost(Host host) {
		map.add(host);
	}

	public static void main(String[] args) {
	}

}
