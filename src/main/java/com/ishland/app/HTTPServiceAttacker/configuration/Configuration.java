package com.ishland.app.HTTPServiceAttacker.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class Configuration {
    private File configfile = new File(System.getProperty("user.dir") + "/config.yml");
    private static final Logger logger = LoggerFactory.getLogger("Configuration loader");
    private ArrayList<Map<String, Object>> target = null;
    private boolean showExceptions = false;
    private boolean isSuccess = true;

    public Configuration(@NonNull File config) {
	this.configfile = config;
	logger.info("Loading configurations...");
	FileReader reader = null;
	try {
	    logger.debug("Preparing configuration file stream...");
	    reader = new FileReader(configfile);
	} catch (FileNotFoundException e) {
	    logger.error("Unable to load configuration file, creating one and exit...", e);
	    createFile();
	    setSuccess(false);
	    return;
	}
	try {
	    logger.debug("Parsing YAML root...");
	    @SuppressWarnings("unchecked")
	    Map<String, Object> conf = (Map<String, Object>) new YamlReader(reader).read();
	    setShowExceptions(Boolean.valueOf(String.valueOf(conf.get("showExceptions"))));
	    logger.debug("Parsing YAML root.target...");
	    @SuppressWarnings("unchecked")
	    ArrayList<Map<String, Object>> targeta = (ArrayList<Map<String, Object>>) conf.get("target");
	    setTarget(targeta);
	    // logger.debug(target);
	} catch (ClassCastException | YamlException e) {
	    logger.error("Invaild configuration file!", e);
	    setSuccess(false);
	    return;
	}
    }

    public Configuration() {
	logger.info("Loading configurations...");
	FileReader reader = null;
	try {
	    logger.debug("Preparing configuration file stream...");
	    reader = new FileReader(configfile);
	} catch (FileNotFoundException e) {
	    logger.error("Unable to load configuration file, creating one and exit...", e);
	    createFile();
	    setSuccess(false);
	    return;
	}
	try {
	    logger.debug("Parsing YAML root...");
	    @SuppressWarnings("unchecked")
	    Map<String, Object> conf = (Map<String, Object>) new YamlReader(reader).read();
	    setShowExceptions(Boolean.valueOf(String.valueOf(conf.get("showExceptions"))));
	    logger.debug("Parsing YAML root.target...");
	    @SuppressWarnings("unchecked")
	    ArrayList<Map<String, Object>> targeta = (ArrayList<Map<String, Object>>) conf.get("target");
	    setTarget(targeta);
	    // logger.debug(target.toString());
	} catch (ClassCastException | YamlException e) {
	    logger.error("Invaild configuration file!", e);
	    setSuccess(false);
	    return;
	}
    }

    public void createFile() {
	FileOutputStream out = null;
	InputStream confin = null;
	try {
	    configfile.createNewFile();
	    out = new FileOutputStream(configfile);
	    confin = Configuration.class.getClassLoader().getResourceAsStream("config.yml");
	} catch (IOException e) {
	    logger.error("Unable to create configuration file!", e);
	    return;
	}
	try {
	    while (confin.available() > 0)
		out.write(confin.read());
	    out.close();
	} catch (IOException e) {
	    logger.error("Unable to write configuration file!", e);
	    System.exit(1);
	}
    }

    /**
     * @return the isSuccess
     */
    public boolean isSuccess() {
	return isSuccess;
    }

    /**
     * @param isSuccess the isSuccess to set
     */
    private void setSuccess(boolean isSuccess) {
	this.isSuccess = isSuccess;
    }

    /**
     * @return the target
     */
    public ArrayList<Map<String, Object>> getTarget() {
	return target;
    }

    /**
     * @param target the target to set
     */
    private void setTarget(ArrayList<Map<String, Object>> target) {
	this.target = target;
    }

    /**
     * @return the showExceptions
     */
    public boolean isShowExceptions() {
	return showExceptions;
    }

    /**
     * @param showExceptions the showExceptions to set
     */
    private void setShowExceptions(boolean showExceptions) {
	this.showExceptions = showExceptions;
    }
}
