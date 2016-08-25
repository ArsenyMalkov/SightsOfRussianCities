package com.arsenymalkov.sightsofrussiancities.utils.xmlparser;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

import com.arsenymalkov.sightsofrussiancities.main.Region;
import com.arsenymalkov.sightsofrussiancities.map.Sight;

import java.util.ArrayList;
import java.util.List;

public class AndroidSaxParser extends BaseParser {

    public AndroidSaxParser(String url) {
        super(url);
    }

    public List<Sight> parseSights() {
        final Sight currentSight = new Sight();
        RootElement root = new RootElement(ROOT_ELEMENT);
        final List<Sight> sightList = new ArrayList<>();
        Element items = root.getChild(ITEMS);
        Element item = items.getChild(ITEM);

//        item.setStartElementListener();
        item.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                sightList.add(currentSight.copy());
            }
        });
        Element name = item.getChild(NAME);
        name.getChild(TEXT).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String s) {
                currentSight.setName(s);
            }
        });

        Element photos = item.getChild(PHOTOS);
        Element photo = photos.getChild(PHOTO);
        photo.getChild(FILE).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String s) {
                currentSight.setImageUrl(s);
            }
        });

        item.getChild(GEO).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String s) {
                currentSight.setGeo(s);
            }
        });

        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return sightList;
    }

    @Override
    public List<Region> parseRegions() {
        final Region currentRegion = new Region();
        RootElement root = new RootElement(ROOT_ELEMENT);
        final List<Region> regionList = new ArrayList<>();
        Element items = root.getChild(ITEMS);
        Element item = items.getChild(ITEM);

        item.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                regionList.add(currentRegion.copy());
            }
        });
        Element name = item.getChild(NAME);
        name.getChild(TEXT).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String s) {
                currentRegion.setName(s);
            }
        });

        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return regionList;
    }

}
