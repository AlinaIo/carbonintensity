import org.junit.Before;
import org.junit.Test;
import restclients.RegionalResource;

import java.util.ArrayList;
import java.util.Map;

public class TestScenarios {
    private ArrayList allRegions;
    private Map<Object, Object> intensityForEachRegion;
    private Map<Object, Object> forecastForEachRegion;
    private Map<Object, Object> sortRegionsHighToLowIntensity;
    private Map<Object, Object> generationMixForEachRegion;

    @Before
    public void setUp() {
        allRegions = RegionalResource.getCarbonIntensityForAllRegions();
    }

    @Test
    public void Scenario1() {
        //1. Get carbon intensity for each region
        intensityForEachRegion = RegionalResource.getShortnameAndIntensity(allRegions);

        //2. Gen intensity value forecast
        forecastForEachRegion = RegionalResource.getIntensityValueForecast(allRegions);

        //3. Sort regions for highest to lowest intensity
        sortRegionsHighToLowIntensity = RegionalResource.sortByValue(intensityForEachRegion);

        //4. Print sorted list in the logs starting with value followed by short name of the region
        System.out.println("High to Low - Carbon Intensity data for current half hour for GB regions ");
        sortRegionsHighToLowIntensity.forEach((key,value) -> System.out.println(value + " Carbon Intensity - " + key));
    }

    @Test
    public void Scenario2() {
        //1. Get carbon intensity for each region
        intensityForEachRegion = RegionalResource.getShortnameAndIntensity(allRegions);

        //2. Assert that generation mix sums to 100
        generationMixForEachRegion = RegionalResource.getShortnameAndGenerationMix(allRegions);
        RegionalResource.AssertGenerationMixDifferent100(generationMixForEachRegion);

    }

    @Test
    public void Scenario3() {
        //1. Get carbon intensity for each region
        intensityForEachRegion = RegionalResource.getShortnameAndIntensity(allRegions);

        generationMixForEachRegion = RegionalResource.getShortnameAndGenerationMix(allRegions);

        //ToDo: show for all fuel types first 5 regions. Currently showing sorted region values for "wind"
        //2. For each fuel type list five regions where the generation percentage is the highest
        RegionalResource.sortOnFuelType(generationMixForEachRegion);
    }
}
