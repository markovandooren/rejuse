package org.aikodi.rejuse.metric.test;

import org.aikodi.rejuse.java.collections.Collections;
import org.aikodi.rejuse.metric.*;
import org.aikodi.rejuse.metric.dimension.*;

import junit.framework.*;

/**
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public class TestQuantity extends TestCase {
  
  public TestQuantity(String string) {
    super(string);
  }

  public void setup() {
  }

  public void testQuantity() {
    assertTrue(SI.LENGTH.times(SI.LENGTH) == SI.AREA);
    assertTrue(SI.AREA.dividedBy(SI.LENGTH) == SI.LENGTH);
    assertTrue(SI.AREA.dividedBy(SI.LENGTH).dividedBy(SI.LENGTH) == Dimensionless.instance());
    assertTrue(SI.METER.times(SI.METER) == SI.SQUARE_METER);



   assertTrue(SI.LENGTH.name().equals("length"));
   assertTrue(SI.MASS.name().equals("mass"));
   assertTrue(SI.TIME.name().equals("time"));
   assertTrue(SI.ELECTRIC_CURRENT.name().equals("electric current"));
   assertTrue(SI.TEMPERATURE.name().equals("thermodynamic temperature"));
   assertTrue(SI.AMOUNT_OF_SUBSTANCE.name().equals("amount of substance"));
   assertTrue(SI.LUMINOUS_INTENSITY.name().equals("luminous intensity"));
   
   assertTrue(SI.METER.name().equals("meter"));
   assertTrue(SI.METER.symbol().equals("m"));
   assertTrue(SI.METER.dimension() == SI.LENGTH);
   assertTrue(SI.GRAM.name().equals("gram"));
   assertTrue(SI.GRAM.symbol().equals("g"));
   assertTrue(SI.GRAM.dimension() == SI.MASS);
   assertTrue(SI.SECOND.name().equals("second"));
   assertTrue(SI.SECOND.symbol().equals("s"));
   assertTrue(SI.SECOND.dimension() == SI.TIME);
   assertTrue(SI.AMPERE.name().equals("ampere"));
   assertTrue(SI.AMPERE.symbol().equals("A"));
   assertTrue(SI.AMPERE.dimension() == SI.ELECTRIC_CURRENT);
   assertTrue(SI.KELVIN.name().equals("kelvin"));
   assertTrue(SI.KELVIN.symbol().equals("K"));
   assertTrue(SI.KELVIN.dimension() == SI.TEMPERATURE);
   assertTrue(SI.MOLE.name().equals("mole"));
   assertTrue(SI.MOLE.symbol().equals("mol"));
   assertTrue(SI.MOLE.dimension() == SI.AMOUNT_OF_SUBSTANCE);
   assertTrue(SI.CANDELA.name().equals("candela"));
   assertTrue(SI.CANDELA.symbol().equals("cd"));
   assertTrue(SI.CANDELA.dimension() == SI.LUMINOUS_INTENSITY);
   assertTrue(SI.AREA.name().equals("area"));
   assertTrue(SI.AREA.exponentOf(SI.LENGTH) == 2);
   assertTrue(SI.AREA.baseDimensions().size() == 1);
   assertTrue(SI.VOLUME.name().equals("volume"));
   assertTrue(SI.VOLUME.exponentOf(SI.LENGTH) == 3);
   assertTrue(SI.VOLUME.baseDimensions().size() == 1);
   assertTrue(SI.VELOCITY.name().equals("velocity"));
   assertTrue(SI.VELOCITY.exponentOf(SI.LENGTH) == 1);
   assertTrue(SI.VELOCITY.exponentOf(SI.TIME) == -1);
   assertTrue(SI.VELOCITY.baseDimensions().size() == 2);
   assertTrue(SI.ACCELERATION.name().equals("acceleration"));
   assertTrue(SI.ACCELERATION.exponentOf(SI.LENGTH) == 1);
   assertTrue(SI.ACCELERATION.exponentOf(SI.TIME) == -2);
   assertTrue(SI.ACCELERATION.baseDimensions().size() == 2);
   assertTrue(SI.WAVE_NUMBER.name().equals("wave number"));
   assertTrue(SI.WAVE_NUMBER.exponentOf(SI.LENGTH) == -1);
   assertTrue(SI.WAVE_NUMBER.baseDimensions().size() == 1);
   assertTrue(SI.MASS_DENSITY.name().equals("mass density"));
   assertTrue(SI.MASS_DENSITY.exponentOf(SI.MASS) == 1);
   assertTrue(SI.MASS_DENSITY.exponentOf(SI.LENGTH) == -3);
   assertTrue(SI.MASS_DENSITY.baseDimensions().size() == 2);
   assertTrue(SI.SPECIFIC_VOLUME.name().equals("specific volume"));
   assertTrue(SI.SPECIFIC_VOLUME.exponentOf(SI.LENGTH) == 3);
   assertTrue(SI.SPECIFIC_VOLUME.exponentOf(SI.MASS) == -1);
   assertTrue(SI.SPECIFIC_VOLUME.baseDimensions().size() == 2);
   assertTrue(SI.CURRENT_DENSITY.name().equals("current density"));
   assertTrue(SI.CURRENT_DENSITY.exponentOf(SI.ELECTRIC_CURRENT) == 1);
   assertTrue(SI.CURRENT_DENSITY.exponentOf(SI.LENGTH) == -2);
   assertTrue(SI.CURRENT_DENSITY.baseDimensions().size() == 2);
   assertTrue(SI.MAGNETIC_FIELD_STRENGTH.name().equals("magnetic field strength"));
   assertTrue(SI.MAGNETIC_FIELD_STRENGTH.exponentOf(SI.ELECTRIC_CURRENT) == 1);
   assertTrue(SI.MAGNETIC_FIELD_STRENGTH.exponentOf(SI.LENGTH) == -1);
   assertTrue(SI.MAGNETIC_FIELD_STRENGTH.baseDimensions().size() == 2);
   assertTrue(SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION.name().equals("amount-of-substance concentration"));
   assertTrue(SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION.exponentOf(SI.AMOUNT_OF_SUBSTANCE) == 1);
   assertTrue(SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION.exponentOf(SI.LENGTH) == -3);
   assertTrue(SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION.baseDimensions().size() == 2);
   assertTrue(SI.LUMINANCE.name().equals("luminance"));
   assertTrue(SI.LUMINANCE.exponentOf(SI.LUMINOUS_INTENSITY) == 1);
   assertTrue(SI.LUMINANCE.exponentOf(SI.LENGTH) == -2);
   assertTrue(SI.LUMINANCE.baseDimensions().size() == 2);

   assertTrue(Collections.containsExplicitly(Dimension.getAllDimensions(), SI.VELOCITY));

/*****************
 * DERIVED UNITS *
 *****************/

   assertTrue(SI.SQUARE_METER.name().equals("square meter")); 
   assertTrue(SI.SQUARE_METER.symbol().equals("m^2")); 
   assertTrue(SI.SQUARE_METER.exponentOf(SI.METER) == 2); 
   assertTrue(SI.SQUARE_METER.components().size() == 1); 
   assertTrue(SI.SQUARE_METER.dimension() == SI.AREA);
   assertTrue(SI.CUBIC_METER.name().equals("cubic meter")); 
   assertTrue(SI.CUBIC_METER.symbol().equals("m^3")); 
   assertTrue(SI.CUBIC_METER.exponentOf(SI.METER) == 3); 
   assertTrue(SI.CUBIC_METER.components().size() == 1); 
   assertTrue(SI.CUBIC_METER.dimension() == SI.VOLUME);
   assertTrue(SI.METER_PER_SECOND.name().equals("meter per second")); 
   assertTrue(SI.METER_PER_SECOND.symbol().equals("m/s")); 
   assertTrue(SI.METER_PER_SECOND.exponentOf(SI.METER) == 1); 
   assertTrue(SI.METER_PER_SECOND.exponentOf(SI.SECOND) == -1); 
   assertTrue(SI.METER_PER_SECOND.components().size() == 2); 
   assertTrue(SI.METER_PER_SECOND.dimension() == SI.VELOCITY);
   assertTrue(SI.METER_PER_SECOND_SQUARED.name().equals("meter per second squared")); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.symbol().equals("m/(s^2)")); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.exponentOf(SI.METER) == 1); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.exponentOf(SI.SECOND) == -2); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.components().size() == 2); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.dimension() == SI.ACCELERATION);
   assertTrue(SI.RECIPROCAL_METER.name().equals("reciprocal meter")); 
   assertTrue(SI.RECIPROCAL_METER.symbol().equals("1/m")); 
   assertTrue(SI.RECIPROCAL_METER.exponentOf(SI.METER) == -1); 
   assertTrue(SI.RECIPROCAL_METER.components().size() == 1); 
   assertTrue(SI.RECIPROCAL_METER.dimension() == SI.WAVE_NUMBER);
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.name().equals("kilogram per cubic meter")); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.symbol().equals("kg/(m^3)")); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.exponentOf(SI.KILOGRAM) == 1); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.exponentOf(SI.METER) == -3); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.components().size() == 2); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.dimension() == SI.MASS_DENSITY);
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.name().equals("cubic meter per kilogram")); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.symbol().equals("(m^3)/kg")); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.exponentOf(SI.METER) == 3); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.exponentOf(SI.KILOGRAM) == -1); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.components().size() == 2); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.dimension() == SI.SPECIFIC_VOLUME);
   assertTrue(SI.AMPERE_PER_SQUARE_METER.name().equals("ampere per square meter")); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.symbol().equals("A/(m^2)")); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.exponentOf(SI.AMPERE) == 1); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.exponentOf(SI.METER) == -2); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.components().size() == 2); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.dimension() == SI.CURRENT_DENSITY);
   assertTrue(SI.AMPERE_PER_METER.name().equals("ampere per meter")); 
   assertTrue(SI.AMPERE_PER_METER.symbol().equals("A/m")); 
   assertTrue(SI.AMPERE_PER_METER.exponentOf(SI.AMPERE) == 1); 
   assertTrue(SI.AMPERE_PER_METER.exponentOf(SI.METER) == -1); 
   assertTrue(SI.AMPERE_PER_METER.components().size() == 2); 
   assertTrue(SI.AMPERE_PER_METER.dimension() == SI.MAGNETIC_FIELD_STRENGTH);
   assertTrue(SI.MOLE_PER_CUBIC_METER.name().equals("mole per cubic meter")); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.symbol().equals("mol/(m^3)")); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.exponentOf(SI.MOLE) == 1); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.exponentOf(SI.METER) == -3); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.components().size() == 2); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.dimension() == SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION);
   assertTrue(SI.CANDELA_PER_SQUARE_METER.name().equals("candela per square meter")); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.symbol().equals("cd/(m^2)")); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.exponentOf(SI.CANDELA) == 1); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.exponentOf(SI.METER) == -2); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.components().size() == 2); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.dimension() == SI.LUMINANCE);

   assertTrue(SI.FREQUENCY.name().equals("frequency"));
   assertTrue(SI.FREQUENCY.exponentOf(SI.TIME) == -1);
   assertTrue(SI.FREQUENCY.baseDimensions().size() == 1);
   assertTrue(SI.HERTZ.name().equals("hertz")); 
   assertTrue(SI.HERTZ.symbol().equals("1/s")); 
   assertTrue(SI.HERTZ.exponentOf(SI.SECOND) == -1); 
   assertTrue(SI.HERTZ.components().size() == 1); 
   assertTrue(SI.HERTZ.dimension() == SI.FREQUENCY);

   assertTrue(SI.FORCE.name().equals("force"));
   assertTrue(SI.FORCE.exponentOf(SI.LENGTH) == 1);
   assertTrue(SI.FORCE.exponentOf(SI.MASS) == 1);
   assertTrue(SI.FORCE.exponentOf(SI.TIME) == -2);
   assertTrue(SI.FORCE.baseDimensions().size() == 3);
   assertTrue(SI.NEWTON.name().equals("newton")); 
   assertTrue(SI.NEWTON.symbol().equals("N")); 
   assertTrue(SI.NEWTON.exponentOf(SI.KILOGRAM) == 1); 
   assertTrue(SI.NEWTON.exponentOf(SI.METER) == 1); 
   assertTrue(SI.NEWTON.exponentOf(SI.SECOND) == -2); 
   assertTrue(SI.NEWTON.components().size() == 3); 
   assertTrue(SI.NEWTON.dimension() == SI.FORCE);
   
   assertTrue(SI.DYNAMIC_VISCOSITY == SI.PRESSURE.times(SI.TIME));
   assertTrue(SI.PASCAL_SECOND == SI.PASCAL.times(SI.SECOND));
   assertTrue(SI.PASCAL_SECOND.dimension() == SI.DYNAMIC_VISCOSITY);
   
   assertTrue(SI.SIEVERT.convertsIsomorphTo(SI.GRAY));
   assertTrue(SI.GRAY.convertsIsomorphTo(SI.SIEVERT));
  }
}

