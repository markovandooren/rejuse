package org.rejuse.metric.test;

import junit.framework.*;
import org.rejuse.metric.*;
//import org.jutil.metric.unit.*;
import org.rejuse.metric.dimension.*;
//import org.jutil.java.collections.Visitor;
import org.rejuse.java.collections.Collections;

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
    assertTrue(SI.AREA.dividedBy(SI.LENGTH).dividedBy(SI.LENGTH) == Dimensionless.getPrototype());
    assertTrue(SI.METER.times(SI.METER) == SI.SQUARE_METER);



   assertTrue(SI.LENGTH.getName().equals("length"));
   assertTrue(SI.MASS.getName().equals("mass"));
   assertTrue(SI.TIME.getName().equals("time"));
   assertTrue(SI.ELECTRIC_CURRENT.getName().equals("electric current"));
   assertTrue(SI.TEMPERATURE.getName().equals("thermodynamic temperature"));
   assertTrue(SI.AMOUNT_OF_SUBSTANCE.getName().equals("amount of substance"));
   assertTrue(SI.LUMINOUS_INTENSITY.getName().equals("luminous intensity"));
   
   assertTrue(SI.METER.getName().equals("meter"));
   assertTrue(SI.METER.getSymbol().equals("m"));
   assertTrue(SI.METER.getDimension() == SI.LENGTH);
   assertTrue(SI.GRAM.getName().equals("gram"));
   assertTrue(SI.GRAM.getSymbol().equals("g"));
   assertTrue(SI.GRAM.getDimension() == SI.MASS);
   assertTrue(SI.SECOND.getName().equals("second"));
   assertTrue(SI.SECOND.getSymbol().equals("s"));
   assertTrue(SI.SECOND.getDimension() == SI.TIME);
   assertTrue(SI.AMPERE.getName().equals("ampere"));
   assertTrue(SI.AMPERE.getSymbol().equals("A"));
   assertTrue(SI.AMPERE.getDimension() == SI.ELECTRIC_CURRENT);
   assertTrue(SI.KELVIN.getName().equals("kelvin"));
   assertTrue(SI.KELVIN.getSymbol().equals("K"));
   assertTrue(SI.KELVIN.getDimension() == SI.TEMPERATURE);
   assertTrue(SI.MOLE.getName().equals("mole"));
   assertTrue(SI.MOLE.getSymbol().equals("mol"));
   assertTrue(SI.MOLE.getDimension() == SI.AMOUNT_OF_SUBSTANCE);
   assertTrue(SI.CANDELA.getName().equals("candela"));
   assertTrue(SI.CANDELA.getSymbol().equals("cd"));
   assertTrue(SI.CANDELA.getDimension() == SI.LUMINOUS_INTENSITY);
   assertTrue(SI.AREA.getName().equals("area"));
   assertTrue(SI.AREA.getExponent(SI.LENGTH) == 2);
   assertTrue(SI.AREA.getBaseDimensions().size() == 1);
   assertTrue(SI.VOLUME.getName().equals("volume"));
   assertTrue(SI.VOLUME.getExponent(SI.LENGTH) == 3);
   assertTrue(SI.VOLUME.getBaseDimensions().size() == 1);
   assertTrue(SI.VELOCITY.getName().equals("velocity"));
   assertTrue(SI.VELOCITY.getExponent(SI.LENGTH) == 1);
   assertTrue(SI.VELOCITY.getExponent(SI.TIME) == -1);
   assertTrue(SI.VELOCITY.getBaseDimensions().size() == 2);
   assertTrue(SI.ACCELERATION.getName().equals("acceleration"));
   assertTrue(SI.ACCELERATION.getExponent(SI.LENGTH) == 1);
   assertTrue(SI.ACCELERATION.getExponent(SI.TIME) == -2);
   assertTrue(SI.ACCELERATION.getBaseDimensions().size() == 2);
   assertTrue(SI.WAVE_NUMBER.getName().equals("wave number"));
   assertTrue(SI.WAVE_NUMBER.getExponent(SI.LENGTH) == -1);
   assertTrue(SI.WAVE_NUMBER.getBaseDimensions().size() == 1);
   assertTrue(SI.MASS_DENSITY.getName().equals("mass density"));
   assertTrue(SI.MASS_DENSITY.getExponent(SI.MASS) == 1);
   assertTrue(SI.MASS_DENSITY.getExponent(SI.LENGTH) == -3);
   assertTrue(SI.MASS_DENSITY.getBaseDimensions().size() == 2);
   assertTrue(SI.SPECIFIC_VOLUME.getName().equals("specific volume"));
   assertTrue(SI.SPECIFIC_VOLUME.getExponent(SI.LENGTH) == 3);
   assertTrue(SI.SPECIFIC_VOLUME.getExponent(SI.MASS) == -1);
   assertTrue(SI.SPECIFIC_VOLUME.getBaseDimensions().size() == 2);
   assertTrue(SI.CURRENT_DENSITY.getName().equals("current density"));
   assertTrue(SI.CURRENT_DENSITY.getExponent(SI.ELECTRIC_CURRENT) == 1);
   assertTrue(SI.CURRENT_DENSITY.getExponent(SI.LENGTH) == -2);
   assertTrue(SI.CURRENT_DENSITY.getBaseDimensions().size() == 2);
   assertTrue(SI.MAGNETIC_FIELD_STRENGTH.getName().equals("magnetic field strength"));
   assertTrue(SI.MAGNETIC_FIELD_STRENGTH.getExponent(SI.ELECTRIC_CURRENT) == 1);
   assertTrue(SI.MAGNETIC_FIELD_STRENGTH.getExponent(SI.LENGTH) == -1);
   assertTrue(SI.MAGNETIC_FIELD_STRENGTH.getBaseDimensions().size() == 2);
   assertTrue(SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION.getName().equals("amount-of-substance concentration"));
   assertTrue(SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION.getExponent(SI.AMOUNT_OF_SUBSTANCE) == 1);
   assertTrue(SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION.getExponent(SI.LENGTH) == -3);
   assertTrue(SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION.getBaseDimensions().size() == 2);
   assertTrue(SI.LUMINANCE.getName().equals("luminance"));
   assertTrue(SI.LUMINANCE.getExponent(SI.LUMINOUS_INTENSITY) == 1);
   assertTrue(SI.LUMINANCE.getExponent(SI.LENGTH) == -2);
   assertTrue(SI.LUMINANCE.getBaseDimensions().size() == 2);

   assertTrue(Collections.containsExplicitly(Dimension.getAllDimensions(), SI.VELOCITY));

/*****************
 * DERIVED UNITS *
 *****************/

   assertTrue(SI.SQUARE_METER.getName().equals("square meter")); 
   assertTrue(SI.SQUARE_METER.getSymbol().equals("m^2")); 
   assertTrue(SI.SQUARE_METER.getExponent(SI.METER) == 2); 
   assertTrue(SI.SQUARE_METER.getSpecialUnits().size() == 1); 
   assertTrue(SI.SQUARE_METER.getDimension() == SI.AREA);
   assertTrue(SI.CUBIC_METER.getName().equals("cubic meter")); 
   assertTrue(SI.CUBIC_METER.getSymbol().equals("m^3")); 
   assertTrue(SI.CUBIC_METER.getExponent(SI.METER) == 3); 
   assertTrue(SI.CUBIC_METER.getSpecialUnits().size() == 1); 
   assertTrue(SI.CUBIC_METER.getDimension() == SI.VOLUME);
   assertTrue(SI.METER_PER_SECOND.getName().equals("meter per second")); 
   assertTrue(SI.METER_PER_SECOND.getSymbol().equals("m/s")); 
   assertTrue(SI.METER_PER_SECOND.getExponent(SI.METER) == 1); 
   assertTrue(SI.METER_PER_SECOND.getExponent(SI.SECOND) == -1); 
   assertTrue(SI.METER_PER_SECOND.getSpecialUnits().size() == 2); 
   assertTrue(SI.METER_PER_SECOND.getDimension() == SI.VELOCITY);
   assertTrue(SI.METER_PER_SECOND_SQUARED.getName().equals("meter per second squared")); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.getSymbol().equals("m/(s^2)")); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.getExponent(SI.METER) == 1); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.getExponent(SI.SECOND) == -2); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.getSpecialUnits().size() == 2); 
   assertTrue(SI.METER_PER_SECOND_SQUARED.getDimension() == SI.ACCELERATION);
   assertTrue(SI.RECIPROCAL_METER.getName().equals("reciprocal meter")); 
   assertTrue(SI.RECIPROCAL_METER.getSymbol().equals("1/m")); 
   assertTrue(SI.RECIPROCAL_METER.getExponent(SI.METER) == -1); 
   assertTrue(SI.RECIPROCAL_METER.getSpecialUnits().size() == 1); 
   assertTrue(SI.RECIPROCAL_METER.getDimension() == SI.WAVE_NUMBER);
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.getName().equals("kilogram per cubic meter")); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.getSymbol().equals("kg/(m^3)")); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.getExponent(SI.KILOGRAM) == 1); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.getExponent(SI.METER) == -3); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.getSpecialUnits().size() == 2); 
   assertTrue(SI.KILOGRAM_PER_CUBIC_METER.getDimension() == SI.MASS_DENSITY);
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.getName().equals("cubic meter per kilogram")); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.getSymbol().equals("(m^3)/kg")); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.getExponent(SI.METER) == 3); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.getExponent(SI.KILOGRAM) == -1); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.getSpecialUnits().size() == 2); 
   assertTrue(SI.CUBIC_METER_PER_KILOGRAM.getDimension() == SI.SPECIFIC_VOLUME);
   assertTrue(SI.AMPERE_PER_SQUARE_METER.getName().equals("ampere per square meter")); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.getSymbol().equals("A/(m^2)")); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.getExponent(SI.AMPERE) == 1); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.getExponent(SI.METER) == -2); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.getSpecialUnits().size() == 2); 
   assertTrue(SI.AMPERE_PER_SQUARE_METER.getDimension() == SI.CURRENT_DENSITY);
   assertTrue(SI.AMPERE_PER_METER.getName().equals("ampere per meter")); 
   assertTrue(SI.AMPERE_PER_METER.getSymbol().equals("A/m")); 
   assertTrue(SI.AMPERE_PER_METER.getExponent(SI.AMPERE) == 1); 
   assertTrue(SI.AMPERE_PER_METER.getExponent(SI.METER) == -1); 
   assertTrue(SI.AMPERE_PER_METER.getSpecialUnits().size() == 2); 
   assertTrue(SI.AMPERE_PER_METER.getDimension() == SI.MAGNETIC_FIELD_STRENGTH);
   assertTrue(SI.MOLE_PER_CUBIC_METER.getName().equals("mole per cubic meter")); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.getSymbol().equals("mol/(m^3)")); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.getExponent(SI.MOLE) == 1); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.getExponent(SI.METER) == -3); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.getSpecialUnits().size() == 2); 
   assertTrue(SI.MOLE_PER_CUBIC_METER.getDimension() == SI.AMOUNT_OF_SUBSTANCE_CONCENTRATION);
   assertTrue(SI.CANDELA_PER_SQUARE_METER.getName().equals("candela per square meter")); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.getSymbol().equals("cd/(m^2)")); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.getExponent(SI.CANDELA) == 1); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.getExponent(SI.METER) == -2); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.getSpecialUnits().size() == 2); 
   assertTrue(SI.CANDELA_PER_SQUARE_METER.getDimension() == SI.LUMINANCE);

   assertTrue(SI.FREQUENCY.getName().equals("frequency"));
   assertTrue(SI.FREQUENCY.getExponent(SI.TIME) == -1);
   assertTrue(SI.FREQUENCY.getBaseDimensions().size() == 1);
   assertTrue(SI.HERTZ.getName().equals("hertz")); 
   assertTrue(SI.HERTZ.getSymbol().equals("1/s")); 
   assertTrue(SI.HERTZ.getExponent(SI.SECOND) == -1); 
   assertTrue(SI.HERTZ.getSpecialUnits().size() == 1); 
   assertTrue(SI.HERTZ.getDimension() == SI.FREQUENCY);

   assertTrue(SI.FORCE.getName().equals("force"));
   assertTrue(SI.FORCE.getExponent(SI.LENGTH) == 1);
   assertTrue(SI.FORCE.getExponent(SI.MASS) == 1);
   assertTrue(SI.FORCE.getExponent(SI.TIME) == -2);
   assertTrue(SI.FORCE.getBaseDimensions().size() == 3);
   assertTrue(SI.NEWTON.getName().equals("newton")); 
   assertTrue(SI.NEWTON.getSymbol().equals("N")); 
   assertTrue(SI.NEWTON.getExponent(SI.KILOGRAM) == 1); 
   assertTrue(SI.NEWTON.getExponent(SI.METER) == 1); 
   assertTrue(SI.NEWTON.getExponent(SI.SECOND) == -2); 
   assertTrue(SI.NEWTON.getSpecialUnits().size() == 3); 
   assertTrue(SI.NEWTON.getDimension() == SI.FORCE);
   
   assertTrue(SI.DYNAMIC_VISCOSITY == SI.PRESSURE.times(SI.TIME));
   assertTrue(SI.PASCAL_SECOND == SI.PASCAL.times(SI.SECOND));
   assertTrue(SI.PASCAL_SECOND.getDimension() == SI.DYNAMIC_VISCOSITY);
   
   assertTrue(SI.SIEVERT.convertsIsomorphTo(SI.GRAY));
   assertTrue(SI.GRAY.convertsIsomorphTo(SI.SIEVERT));
  }
}
/*
 * <copyright>Copyright (C) 1997-2002. This software is copyrighted by 
 * the people and entities mentioned after the "@author" tags above, on 
 * behalf of the JUTIL.ORG Project. The copyright is dated by the dates 
 * after the "@date" tags above. All rights reserved.
 * This software is published under the terms of the JUTIL.ORG Software
 * License version 1.1 or later, a copy of which has been included with
 * this distribution in the LICENSE file, which can also be found at
 * http://org-jutil.sourceforge.net/LICENSE. This software is distributed 
 * WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the JUTIL.ORG Software License for more details. For more information,
 * please see http://org-jutil.sourceforge.net/</copyright>
 */
