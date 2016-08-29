package com.arsenymalkov.sightsofrussiancities.utils.xmlparser;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.sax.TextElementListener;
import android.util.Xml;

import com.arsenymalkov.sightsofrussiancities.main.City;
import com.arsenymalkov.sightsofrussiancities.main.Region;
import com.arsenymalkov.sightsofrussiancities.map.Sight;

import org.xml.sax.Attributes;

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

        item.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                regionList.add(currentRegion.copy());
                currentRegion.setId(attributes.getValue("id"));
            }
        });
        Element name = item.getChild(NAME);
        name.getChild(TEXT).setTextElementListener(new TextElementListener() {
            private boolean rus = false;

            @Override
            public void end(String s) {
                if (rus) {
                    currentRegion.setName(s);
                    rus = false;
                }
            }

            @Override
            public void start(Attributes attributes) {
                if (attributes.getValue("lang").equals("rus")) {
                    rus = true;
                }
            }
        });

        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return regionList;
    }

    @Override
    public List<City> parseCities() {
        final City currentCity = new City();
        RootElement root = new RootElement(ROOT_ELEMENT);
        final List<City> cityList = new ArrayList<>();
        Element items = root.getChild(ITEMS);
        Element item = items.getChild(ITEM);

        item.setStartElementListener(new StartElementListener() {
            @Override
            public void start(Attributes attributes) {
                cityList.add(currentCity.copy());
                currentCity.setId(attributes.getValue("id"));
            }
        });
        Element name = item.getChild(NAME);
        name.getChild(TEXT).setTextElementListener(new TextElementListener() {
            private boolean rus = false;

            @Override
            public void end(String s) {
                if (rus) {
                    currentCity.setName(s);
                    rus = false;
                }
            }

            @Override
            public void start(Attributes attributes) {
                if (attributes.getValue("lang").equals("rus")) {
                    rus = true;
                }
            }
        });

        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return cityList;
    }

}
