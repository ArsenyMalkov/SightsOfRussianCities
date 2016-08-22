package com.arsenymalkov.sightsofrussiancities.utils.xmlparser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public abstract class BaseSightsParser implements SightsParser {

    static final String ROOT_ELEMENT = "response";
    static final String ITEMS = "items";
    static final String ITEM = "item";

    static final String NAME = "name";
    static final String TEXT = "text";

    static final String PHOTOS = "photos";
    static final String PHOTO = "photo";
    static final String FILE = "file";

    static final String GEO = "geo";


    InputStream sightsIS;

    protected BaseSightsParser(String sightsXml){
        try {
            this.sightsIS = new ByteArrayInputStream(sightsXml.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    protected InputStream getInputStream() {
        return sightsIS;
    }

}
