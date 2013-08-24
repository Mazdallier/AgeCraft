package elcon.mods.agecraft.lang;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import elcon.mods.agecraft.ACLog;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.AgeCraft;

public class LanguageManager {

	private static String currentLanguage = "en_US";
	private static HashMap<String, Language> languages = new HashMap<String, Language>();
	private static boolean loaded = false;

	private static final Splitter splitter = Splitter.on('=').limit(2);
	private static final Pattern pattern = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");

	public static File languageDirectory;

	public static void load() {
		if(!loaded) {
			ACLog.info("[LanguageManager] Loading languages...");
			languageDirectory = new File(AgeCraft.minecraftDir, "/lang/agecraft/");
			languageDirectory.delete();
			languageDirectory.mkdirs();
			if(AgeCraft.modContainerSource.isDirectory()) {
				try {
					FileUtils.copyDirectory(new File(AgeCraft.modContainerSource, "/assets/agecraft/lang/"), languageDirectory);
				} catch(Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					JarFile jar = new JarFile(AgeCraft.modContainerSource);
					Enumeration entries = jar.entries();
					while(entries.hasMoreElements()) {
						JarEntry file = (JarEntry) entries.nextElement();
						if(file.getName().contains("assets/agecraft/lang/")) {
							File f = new File(languageDirectory + File.separator + file.getName());
							if(file.isDirectory()) {
								f.mkdir();
								continue;
							}
							InputStream is = jar.getInputStream(file);
							FileOutputStream fos = new FileOutputStream(f);
							while(is.available() > 0) {
								fos.write(is.read());
							}
							fos.close();
							is.close();
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			searchLanguageDirectory(languageDirectory);
			loaded = true;
		}
	}

	private static void searchLanguageDirectory(File dir) {
		File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory() || ACUtil.getFileExtension(file).equals("lang");
			}
		});
		for(int i = 0; i < files.length; i++) {
			if(files[i] != null) {
				if(files[i].isDirectory()) {
					searchLanguageDirectory(files[i]);
				} else {
					loadLanguageFile(files[i]);
				}
			}
		}
	}

	public static void loadLanguageFile(File file) {
		String dirName = file.getParentFile().getName();
		String fileName = file.getName();
		try {
			ACLog.info("[LanguageManager] Loading language file: " + dirName + "/" + fileName);
			FileInputStream inputStream = new FileInputStream(file);

			String language = fileName.replace(ACUtil.getFileExtension(fileName), "").replace(".", "");
			if(!languages.containsKey(language)) {
				languages.put(language, new Language(language));
			}
			Iterator iterator = IOUtils.readLines(inputStream, Charsets.UTF_8).iterator();
			while(iterator.hasNext()) {
				String s = (String) iterator.next();
				if(!s.isEmpty() && s.charAt(0) != 35) {
					String[] split = (String[]) Iterables.toArray(splitter.split(s), String.class);

					if(split != null && split.length == 2) {
						String key = split[0];
						String localization = pattern.matcher(split[1]).replaceAll("%$1s");
						setLocatization(language, key, localization);
					}
				}
			}
		} catch(Exception e) {
			ACLog.severe("[LanguageManger] Error while reading language file (" + fileName + "): ");
			e.printStackTrace();
		}
	}

	public static String getCurrentLanguage() {
		return currentLanguage;
	}

	public static void setCurrentLanguage(String language) {
		currentLanguage = language;
	}

	public static Language getLanguage() {
		return getLanguage(currentLanguage);
	}

	public static Language getLanguage(String name) {
		if(languages.containsKey(name)) {
			return languages.get(name);
		}
		return languages.get("en_US");
	}

	public static void setLanguage(String name, Language language) {
		languages.put(name, language);
	}

	public static String getLocalization(String key) {
		if(!loaded) {
			return key;
		}
		return getLanguage().getLocalization(key);
	}

	public static void setLocatization(String key, String localization) {
		getLanguage().setLocalization(key, localization);
	}

	public static String getLocalization(String language, String key) {
		if(!loaded) {
			return key;
		}
		return getLanguage(language).getLocalization(key);
	}

	public static void setLocatization(String language, String key, String localization) {
		getLanguage(language).setLocalization(key, localization);
	}
}
