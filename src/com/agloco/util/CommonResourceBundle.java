package com.agloco.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public abstract class CommonResourceBundle extends ResourceBundle {
	/**
	 * @return a resource bundle
	 */
	public static ResourceBundle getBundle() {
		if (instance == null)
			instance = new DefResourceBundle();
		return instance;
	}

	/**
	 * @return an array of all resource bundle base names
	 */
	public String[] getBaseName() {
		return baseName;
	}

	/**
	 * Adds a resource bundle to the collection of bundles
	 * 
	 * @param bundle
	 *          the ResourceBundle to add
	 */
	public static void addResourceBundle(ResourceBundle bundle) {
		bundles.add(bundle);
	}

	/**
	 * Removes a resource bundle from the collection of bundles
	 * 
	 * @param bundle
	 *          the ResourceBundle to remove
	 */
	public static void removeResourceBundle(ResourceBundle bundle) {
		bundles.remove(bundle);
	}

	/**
	 * @return Enumeration of the keys
	 */
	public abstract Enumeration getKeys();

	/**
	 * Gets an object for the given key from this resource bundle and null if this
	 * resource bundle does not contain an object for the given key
	 */
	protected abstract Object handleGetObject(String key);

	/**
	 * Sets the resource bundle base names as an array
	 */
	protected CommonResourceBundle(String[] baseName) {
		this.baseName = baseName;
	}

	/**
	 * Sets the resource bundle base names as an array from a string like:
	 * test1,test2 etc or test1 test2 etc
	 */
	protected CommonResourceBundle(String baseName) {
		buildBaseName(baseName, " ,");
	}

	public CommonResourceBundle() {
		this(new String[0]);
	}

	/**
	 * Builds the resource bundle base names as an array from a string like:
	 * test1,test2 etc or test1 test2 etc
	 */
	protected void buildBaseName(String base, String delim) {
		String s = null;
		try {
			s = System.getProperty(base);
			if (s == null)
				return;
			StringTokenizer st = new StringTokenizer(s, delim);
			baseName = new String[st.countTokens()];
			int i = 0;
			while (st.hasMoreTokens())
				baseName[i++] = st.nextToken().trim();
		} catch (Exception e) {
			throw new RuntimeException("Can not resolve base name: " + s);
		}
	}

	/** Resource bundle base names */
	protected String[] baseName;

	/** Default implementation of this abstract class */
	private static DefResourceBundle instance;

	/** Collection of resource bundles */
	private static ArrayList bundles = new ArrayList();;

	/**
	 * Default implementation
	 */
	static class DefResourceBundle extends CommonResourceBundle {

		public DefResourceBundle(String[] baseName) {
			super(baseName);
		}

		public DefResourceBundle(String baseName) {
			super(baseName);
		}

		public DefResourceBundle() {
			this(new String[0]);
		}

		public Enumeration getKeys() {
			return new Enumeration() {
				Enumeration e = null;

				int i = 0;

				public boolean hasMoreElements() {
					boolean b = false;
					while (e == null || !(b = e.hasMoreElements())) {
						if (i >= bundles.size()) {
							e = null;
							return b;
						}
						e = ((ResourceBundle) bundles.get(i++)).getKeys();
					}
					return b;
				}

				public Object nextElement() {
					if (e == null)
						throw new NoSuchElementException();
					return e.nextElement();
				}
			};
		}

		protected Object handleGetObject(String key) {
			ResourceBundle rb = null;
			String val = null;
			for (int i = 0; i < bundles.size(); i++) {
				rb = (ResourceBundle) bundles.get(i);
				try {
					val = rb.getString(key);
				} catch (Exception e) {
				}
				if (val != null)
					break;
			}
			return val;
		}

	}
}
