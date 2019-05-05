/*Culesberry technolegy Co. Ltd. (c) 2019-2020
 * 
 * Stating that the software,the software belongs Gulesberry
 * noyark open source group,noyark has all the power to interpret
 * and copyright information for the software prohibit organizations
 * and individuals conduct their business practices and illegal practices,
 * projects of: magiclu,Chinese name *Changcun Lu*.The software has nothing
 * to do with current politics,free software is the purpose of noyark
 * 
 * noyark-system info:
 * 	****************************************************
 * 											www.noyark.net
 *		 ****************************************************
 * 
 */
package net.mariataframework.noyark.nukkit.utils;



import cn.nukkit.command.Command;
import cn.nukkit.event.Listener;
import net.mariataframework.noyark.nukkit.core.FrameworkCore;
import net.mariataframework.noyark.nukkit.exception.NotImplementListenerException;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is used to extensively load all Config
 * annotated classes and create corresponding configuration
 * file objects,then load the Root or Node annotations,load
 * them into hierarchy and key-value pairs,and various data types
 * to achieve the recommended configuration.
 * <BR>How to load using this class
 * <BR>if the config class in the a.b.c.d package,the packageFile
 * can be a or a.b or a.b.c or a.b.c.d
 * <BR>&nbsp;new ReflectSet("the parent package",this).loadAnnotation();
 * @author magiclu550
 * @since JDK1.8
 * @since mariata 001
 *
 */

public class ReflectSet {

	/** the package file*/
	private String[] packageFiles;
	/** usually is 'this' */
	private String classpath;
	
	/**
	 * After the constructor constructs the object,
	 * the <CODE>loadAnnotation</CODE> method is 
	 * called to load the configuration file.
	 * @param packageFiles the parent package file
	 * if the config class in the a.b.c.d package,the packageFile
	 * can be a or a.b or a.b.c or a.b.c.d
	 * @param classpath the classpath
	 */
	
	public ReflectSet(String[] packageFiles, String classpath) {
		this.packageFiles = packageFiles;
		this.classpath = classpath;
	}
	
	/**
	 * Used to load all the classes with Config annotation 
	 * under the specified package ,and parse it into a 
	 * configuration file,and generate a configuration file object
	 */
	
	public void loadAnnotation(URLClassLoader loader,String name,RefelectCreater creater) {
		try {
			scanPackage(loader,name,creater);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Its role is to further parse
	 * the obtained class and resolve it to
	 * an instance; 
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 */
	private void scanPackage(URLClassLoader loader,String name,RefelectCreater creater) throws IllegalArgumentException, IllegalAccessException,ClassNotFoundException, InstantiationException,NotImplementListenerException{
	    String classPath = classpath;
		for(String packageFile:packageFiles){
			String packagePath = classPath+packageFile.replaceAll("\\.","/");
			File file = new File(packagePath);
			List<File> fileName = loadClass(file);
			for(File fn:fileName) {
				File[] fs = fn.listFiles();
				if(fs==null&&fn.isFile()){
					fs = new File[1];
					fs[0] = fn;
				}
				if(fs!=null) {
					for(File f1:fs) {
						if(f1.getName().endsWith(".class")) {
							String classpath = f1.getPath();
							classpath = classpath.substring(classpath.indexOf("classes")+"classes".length()+1,classpath.indexOf(".class")).replaceAll("/",".");
							String real = classpath.substring(classPath.indexOf("mariataframework")+("mariataframework"+name).length()+2).trim();
							Class<?> clz = loader.loadClass(real);
							Object obj = clz.newInstance();
							creater.create(obj,clz);
						}
					}
				}
			}
		}
	}

	/**
	 * It can get Package and Classes next the root package
	 * @param file the classpath file
	 * @return the file about classes and package
	 */
	
	private List<File> loadClass(File file) {

		List<File> allFiles = new ArrayList<File>();
		File[] files = file.listFiles();
		searchFile(files, allFiles);
		return allFiles;
	}
	
	/**
	 * It can get all of the file about package
	 * @param files the file objects
	 * @param allFiles the package file
	 */
	
	private void searchFile(File[] files,List<File> allFiles) {
		if(files!=null) {
			for(File f:files) {
				if(f.isDirectory()){
					allFiles.add(f);
					File[] files2 = f.listFiles();
					searchFile(files2, allFiles);
				}else if(f.getName().endsWith(".class")) {
					allFiles.add(f);
				}
			}
		}
	}
}
