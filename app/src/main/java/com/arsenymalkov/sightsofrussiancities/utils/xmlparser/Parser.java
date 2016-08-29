package com.arsenymalkov.sightsofrussiancities.utils.xmlparser;

import com.arsenymalkov.sightsofrussiancities.main.City;
import com.arsenymalkov.sightsofrussiancities.main.Region;
import com.arsenymalkov.sightsofrussiancities.map.Sight;

import java.util.List;

public interface Parser {
    List<Sight> parseSights();
    List<Region> parseRegions();
    List<City> parseCities();
}
