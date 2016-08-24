package com.arsenymalkov.sightsofrussiancities.utils.xmlparser;

import android.graphics.Region;

import com.arsenymalkov.sightsofrussiancities.map.Sight;

import java.util.List;

public interface Parser {
    List<Sight> parseSights();
    List<Region> parseRegions();
}
