package be.kuleuven.cs.distrinet.rejuse.metric;

import be.kuleuven.cs.distrinet.rejuse.metric.dimension.BaseDimension;
import be.kuleuven.cs.distrinet.rejuse.metric.dimension.CompositeDimension;
import be.kuleuven.cs.distrinet.rejuse.metric.dimension.Dimension;
import be.kuleuven.cs.distrinet.rejuse.metric.unit.BaseUnit;
import be.kuleuven.cs.distrinet.rejuse.metric.unit.CompositeUnit;
import be.kuleuven.cs.distrinet.rejuse.metric.unit.One;
import be.kuleuven.cs.distrinet.rejuse.metric.unit.SpecialUnit;
import be.kuleuven.cs.distrinet.rejuse.metric.unit.TransformedUnit;
import be.kuleuven.cs.distrinet.rejuse.metric.unit.Unit;

/**
 * This is an interface containing constants from the SI system. See 
 * <a href="http://physics.nist.gov/cuu/Units/index.html">http://physics.nist.gov/cuu/Units/index.html</a> for more details.
 * 
 * @version $Revision$
 * @date    $Date$
 * @author  Marko van Dooren
 */
public interface SI {
  
/************
 * PREFIXES *
 ************/

 /*@
   @ public invariant YOCTO.getName().equals("yocto");
   @ public invariant YOCTO.getSymbol().equals("y");
   @ public invariant YOCTO.getBase() == 10;
   @ public invariant YOCTO.getExponent() == -24;
   @*/
  public final static Prefix YOCTO = new Prefix("yocto","y",10,-24);
  
 /*@
   @ public invariant ZEPTO.getName().equals("zepto");
   @ public invariant ZEPTO.getSymbol().equals("z");
   @ public invariant ZEPTO.getBase() == 10;
   @ public invariant ZEPTO.getExponent() == -21;
   @*/
  public final static Prefix ZEPTO = new Prefix("zepto","z",10,-21);
  
 /*@
   @ public invariant ATTO.getName().equals("atto");
   @ public invariant ATTO.getSymbol().equals("a");
   @ public invariant ATTO.getBase() == 10;
   @ public invariant ATTO.getExponent() == -18;
   @*/
  public final static Prefix ATTO= new Prefix("atto","a",10,-18);
  
 /*@
   @ public invariant FEMTO.getName().equals("femto");
   @ public invariant FEMTO.getSymbol().equals("f");
   @ public invariant FEMTO.getBase() == 10;
   @ public invariant FEMTO.getExponent() == -15;
   @*/
  public final static Prefix FEMTO = new Prefix("femto","f",10,-15);
  
 /*@
   @ public invariant PICO.getName().equals("pico");
   @ public invariant PICO.getSymbol().equals("p");
   @ public invariant PICO.getBase() == 10;
   @ public invariant PICO.getExponent() == -12;
   @*/
  public final static Prefix PICO = new Prefix("pico","p",10,-12);
  
 /*@
   @ public invariant NANO.getName().equals("nano");
   @ public invariant NANO.getSymbol().equals("n");
   @ public invariant NANO.getBase() == 10;
   @ public invariant NANO.getExponent() == -9;
   @*/
  public final static Prefix NANO = new Prefix("nano","n",10,-9);
  
 /*@
   @ public invariant MICRO.getName().equals("micro");
   @ public invariant MICRO.getSymbol().equals("\u03BC");
   @ public invariant MICRO.getBase() == 10;
   @ public invariant MICRO.getExponent() == -6;
   @*/
  public final static Prefix MICRO = new Prefix("micro","\u03BC",10,-6);
  
 /*@
   @ public invariant MILLI.getName().equals("milli");
   @ public invariant MILLI.getSymbol().equals("m");
   @ public invariant MILLI.getBase() == 10;
   @ public invariant MILLI.getExponent() == -3;
   @*/
  public final static Prefix MILLI = new Prefix("milli","m",10,-3);
  
 /*@
   @ public invariant CENTI.getName().equals("centi");
   @ public invariant CENTI.getSymbol().equals("c");
   @ public invariant CENTI.getBase() == 10;
   @ public invariant CENTI.getExponent() == -2;
   @*/
  public final static Prefix CENTI = new Prefix("centi","c",10,-2);
  
 /*@
   @ public invariant KILO.getName().equals("deci");
   @ public invariant KILO.getSymbol().equals("d");
   @ public invariant KILO.getBase() == 10;
   @ public invariant KILO.getExponent() == -1;
   @*/
  public final static Prefix DECI = new Prefix("deci","d",10,-1);
  
 /*@
   @ public invariant ONE == Prefix.ONE; 
   @*/
  public final static Prefix ONE = Prefix.ONE;

 /*@
   @ public invariant KILO.getName().equals("deka");
   @ public invariant KILO.getSymbol().equals("da");
   @ public invariant KILO.getBase() == 10;
   @ public invariant KILO.getExponent() == 1;
   @*/
  public final static Prefix DEKA = new Prefix("deka","da",10,1);
   
 /*@
   @ public invariant KILO.getName().equals("hecto");
   @ public invariant KILO.getSymbol().equals("h");
   @ public invariant KILO.getBase() == 10;
   @ public invariant KILO.getExponent() == 2;
   @*/
  public final static Prefix HECTO = new Prefix("hecto","h",10,2);

 /*@
   @ public invariant KILO.getName().equals("kilo");
   @ public invariant KILO.getSymbol().equals("k");
   @ public invariant KILO.getBase() == 10;
   @ public invariant KILO.getExponent() == 3;
   @*/
  public final static Prefix KILO = new Prefix("kilo","k",10,3);

 /*@
   @ public invariant MEGA.getName().equals("mega");
   @ public invariant MEGA.getSymbol().equals("M");
   @ public invariant MEGA.getBase() == 10;
   @ public invariant MEGA.getExponent() == 6;
   @*/
  public final static Prefix MEGA = new Prefix("mega","M",10,6);

 /*@
   @ public invariant GIGA.getName().equals("giga");
   @ public invariant GIGA.getSymbol().equals("G");
   @ public invariant GIGA.getBase() == 10;
   @ public invariant GIGA.getExponent() == 9;
   @*/
  public final static Prefix GIGA = new Prefix("giga","G",10,9);

 /*@
   @ public invariant TERA.getName().equals("tera");
   @ public invariant TERA.getSymbol().equals("T");
   @ public invariant TERA.getBase() == 10;
   @ public invariant TERA.getExponent() == 12;
   @*/
  public final static Prefix TERA = new Prefix("tera","T",10,12);

 /*@
   @ public invariant PETA.getName().equals("peta");
   @ public invariant PETA.getSymbol().equals("P");
   @ public invariant PETA.getBase() == 10;
   @ public invariant PETA.getExponent() == 15;
   @*/
  public final static Prefix PETA = new Prefix("peta","P",10,15);

 /*@
   @ public invariant EXA.getName().equals("exa");
   @ public invariant EXA.getSymbol().equals("E");
   @ public invariant EXA.getBase() == 10;
   @ public invariant EXA.getExponent() == 18;
   @*/
  public final static Prefix EXA = new Prefix("exa","E",10,18);

 /*@
   @ public invariant ZETTA.getName().equals("zetta");
   @ public invariant ZETTA.getSymbol().equals("Z");
   @ public invariant ZETTA.getBase() == 10;
   @ public invariant ZETTA.getExponent() == 21;
   @*/
  public final static Prefix ZETTA = new Prefix("zetta","Z",10,21);

 /*@
   @ public invariant YOTTA.getName().equals("yotta");
   @ public invariant YOTTA.getSymbol().equals("Y");
   @ public invariant YOTTA.getBase() == 10;
   @ public invariant YOTTA.getExponent() == 24;
   @*/
  public final static Prefix YOTTA = new Prefix("yotta","Y",10,24);

/*******************
 * BASE QUANTITIES *
 *******************/

 /*@
   @ public invariant LENGTH.getName().equals("length");
   @*/
  public final static BaseDimension LENGTH = new BaseDimension("length");
  
 /*@
   @ public invariant MASS.getName().equals("mass");
   @*/
  public final static BaseDimension MASS = new BaseDimension("mass");
  
 /*@
   @ public invariant TIME.getName().equals("time");
   @*/
  public final static BaseDimension TIME = new BaseDimension("time");
  
 /*@
   @ public invariant ELECTRIC_CURRENT.getName().equals("electric current");
   @*/
  public final static BaseDimension ELECTRIC_CURRENT = new BaseDimension("electric current");
  
 /*@
   @ public invariant TEMPERATURE.getName().equals("thermodynamic temperature");
   @*/
  public final static BaseDimension TEMPERATURE = new BaseDimension("thermodynamic temperature");
  
 /*@
   @ public invariant AMOUNT_OF_SUBSTANCE.getName().equals("amount of substance");
   @*/
  public final static BaseDimension AMOUNT_OF_SUBSTANCE = new BaseDimension("amount of substance");
  
 /*@
   @ public invariant LUMINOUS_INTENSITY.getName().equals("luminous intensity");
   @*/
  public final static BaseDimension LUMINOUS_INTENSITY = new BaseDimension("luminous intensity");


/**************
 * BASE UNITS *
 **************/

  public final static One UNITY = One.PROTOTYPE;

 /*@
   @ public invariant RAD.getName().equals("radion");
   @ public invariant RAD.getSymbol().equals("rad");
   @ public invariant RAD.getDimension() == Dimensionless.getPrototype();
   @*/
  public final static Unit RADIAN = new TransformedUnit(UNITY,"radian","rad",1.0,0.0);

 /*@
   @ public invariant METER.getName().equals("meter");
   @ public invariant METER.getSymbol().equals("m");
   @ public invariant METER.getDimension() == LENGTH;
   @*/
  public final static BaseUnit METER = new BaseUnit("meter", "m", LENGTH);

 /*@
   @ public invariant GRAM.getName().equals("gram");
   @ public invariant GRAM.getSymbol().equals("g");
   @ public invariant GRAM.getDimension() == MASS;
   @*/
  public final static BaseUnit GRAM = new BaseUnit("gram", "g", MASS);

 /*@
   @ public invariant SECOND.getName().equals("second");
   @ public invariant SECOND.getSymbol().equals("s");
   @ public invariant SECOND.getDimension() == TIME;
   @*/
  public final static BaseUnit SECOND = new BaseUnit("second", "s", TIME);

 /*@
   @ public invariant AMPERE.getName().equals("ampere");
   @ public invariant AMPERE.getSymbol().equals("A");
   @ public invariant AMPERE.getDimension() == ELECTRIC_CURRENT;
   @*/
  public final static BaseUnit AMPERE = new BaseUnit("ampere", "A", ELECTRIC_CURRENT);

 /*@
   @ public invariant KELVIN.getName().equals("kelvin");
   @ public invariant KELVIN.getSymbol().equals("K");
   @ public invariant KELVIN.getDimension() == TEMPERATURE;
   @*/
  public final static BaseUnit KELVIN = new BaseUnit("kelvin", "K", TEMPERATURE);

 /*@
   @ public invariant MOLE.getName().equals("mole");
   @ public invariant MOLE.getSymbol().equals("mol");
   @ public invariant MOLE.getDimension() == AMOUNT_OF_SUBSTANCE;
   @*/
  public final static BaseUnit MOLE = new BaseUnit("mole", "mol", AMOUNT_OF_SUBSTANCE);

 /*@
   @ public invariant CANDELA.getName().equals("candela");
   @ public invariant CANDELA.getSymbol().equals("cd");
   @ public invariant CANDELA.getDimension() == LUMINOUS_INTENSITY;
   @*/
  public final static BaseUnit CANDELA = new BaseUnit("candela", "cd", LUMINOUS_INTENSITY);

/**********************
 * DERIVED QUANTITIES *
 **********************/

 /*@
   @ public invariant AREA.getName().equals("area");
   @ public invariant AREA.getExponent(LENGTH) == 2;
   @ public invariant AREA.getBaseDimensions().size() == 1;
   @*/
  public final static Dimension AREA = new CompositeDimension("area", LENGTH, 2);
  
 /*@
   @ public invariant VOLUME.getName().equals("volume");
   @ public invariant VOLUME.getExponent(LENGTH) == 3;
   @ public invariant VOLUME.getBaseDimensions().size() == 1;
   @*/
  public final static Dimension VOLUME = new CompositeDimension("volume", LENGTH, 3);
  
 /*@
   @ public invariant VELOCITY.getName().equals("velocity");
   @ public invariant VELOCITY.getExponent(LENGTH) == 1;
   @ public invariant VELOCITY.getExponent(TIME) == -1;
   @ public invariant VELOCITY.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension VELOCITY = new CompositeDimension("velocity", LENGTH, 1, TIME, -1);
  
 /*@
   @ public invariant ACCELERATION.getName().equals("acceleration");
   @ public invariant ACCELERATION.getExponent(LENGTH) == 1;
   @ public invariant ACCELERATION.getExponent(TIME) == -2;
   @ public invariant ACCELERATION.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension ACCELERATION = new CompositeDimension("acceleration", LENGTH, 1, TIME, -2);
  
 /*@
   @ public invariant WAVE_NUMBER.getName().equals("wave number");
   @ public invariant WAVE_NUMBER.getExponent(LENGTH) == -1;
   @ public invariant WAVE_NUMBER.getBaseDimensions().size() == 1;
   @*/
  public final static Dimension WAVE_NUMBER = new CompositeDimension("wave number", LENGTH, -1);
  
 /*@
   @ public invariant MASS_DENSITY.getName().equals("mass density");
   @ public invariant MASS_DENSITY.getExponent(MASS) == 1;
   @ public invariant MASS_DENSITY.getExponent(LENGTH) == -3;
   @ public invariant MASS_DENSITY.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension MASS_DENSITY = new CompositeDimension("mass density", MASS, 1, LENGTH, -3);
  
 /*@
   @ public invariant SPECIFIC_VOLUME.getName().equals("specific volume");
   @ public invariant SPECIFIC_VOLUME.getExponent(LENGTH) == 3;
   @ public invariant SPECIFIC_VOLUME.getExponent(MASS) == -1;
   @ public invariant SPECIFIC_VOLUME.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension SPECIFIC_VOLUME = new CompositeDimension("specific volume", LENGTH, 3, MASS, -1);
  
 /*@
   @ public invariant CURRENT_DENSITY.getName().equals("current density");
   @ public invariant CURRENT_DENSITY.getExponent(ELECTRIC_CURRENT) == 1;
   @ public invariant CURRENT_DENSITY.getExponent(LENGTH) == -2;
   @ public invariant CURRENT_DENSITY.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension CURRENT_DENSITY = new CompositeDimension("current density", ELECTRIC_CURRENT, 1, LENGTH, -2);
  
 /*@
   @ public invariant MAGNETIC_FIELD_STRENGTH.getName().equals("magnetic field strength");
   @ public invariant MAGNETIC_FIELD_STRENGTH.getExponent(ELECTRIC_CURRENT) == 1;
   @ public invariant MAGNETIC_FIELD_STRENGTH.getExponent(LENGTH) == -1;
   @ public invariant MAGNETIC_FIELD_STRENGTH.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension MAGNETIC_FIELD_STRENGTH = new CompositeDimension("magnetic field strength", ELECTRIC_CURRENT, 1, LENGTH, -1);
  
 /*@
   @ public invariant AMOUNT_OF_SUBSTANCE_CONCENTRATION.getName().equals("amount-of-substance concentration");
   @ public invariant AMOUNT_OF_SUBSTANCE_CONCENTRATION.getExponent(AMOUNT_OF_SUBSTANCE) == 1;
   @ public invariant AMOUNT_OF_SUBSTANCE_CONCENTRATION.getExponent(LENGTH) == -3;
   @ public invariant AMOUNT_OF_SUBSTANCE_CONCENTRATION.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension AMOUNT_OF_SUBSTANCE_CONCENTRATION = new CompositeDimension("amount-of-substance concentration", AMOUNT_OF_SUBSTANCE, 1, LENGTH, -3);
  
 /*@
   @ public invariant LUMINANCE.getName().equals("luminance");
   @ public invariant LUMINANCE.getExponent(LUMINOUS_INTENSITY) == 1;
   @ public invariant LUMINANCE.getExponent(LENGTH) == -2;
   @ public invariant LUMINANCE.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension LUMINANCE = new CompositeDimension("luminance", LUMINOUS_INTENSITY, 1, LENGTH, -2);

 /*@
   @ public invariant FREQUENCY.getName().equals("frequency");
   @ public invariant FREQUENCY.getExponent(TIME) == -1;
   @ public invariant FREQUENCY.getBaseDimensions().size() == 1;
   @*/
  public final static Dimension FREQUENCY = new CompositeDimension("frequency", TIME, -1);

 /*@
   @ public invariant FORCE.getName().equals("force");
   @ public invariant FORCE.getExponent(LENGTH) == 1;
   @ public invariant FORCE.getExponent(MASS) == 1;
   @ public invariant FORCE.getExponent(TIME) == -2;
   @ public invariant FORCE.getBaseDimensions().size() == 3;
   @*/
  public final static Dimension FORCE = new CompositeDimension("force", LENGTH, 1, MASS, 1, TIME, -2);

 /*@
   @ public invariant PRESSURE.getName().equals("pressure");
   @ public invariant PRESSURE.getExponent(LENGTH) == -1;
   @ public invariant PRESSURE.getExponent(MASS) == 1;
   @ public invariant PRESSURE.getExponent(TIME) == -2;
   @ public invariant PRESSURE.getBaseDimensions().size() == 3;
   @*/
  public final static Dimension PRESSURE = new CompositeDimension("pressure", LENGTH, -1, MASS, 1, TIME, -2);

 /*@
   @ public invariant ENERGY.getName().equals("energy");
   @ public invariant ENERGY.getExponent(LENGTH) == 2;
   @ public invariant ENERGY.getExponent(MASS) == 1;
   @ public invariant ENERGY.getExponent(TIME) == -2;
   @ public invariant ENERGY.getBaseDimensions().size() == 3;
   @*/
  public final static Dimension ENERGY = new CompositeDimension("energy", LENGTH, 2, MASS, 1, TIME, -2);

 /*@
   @ public invariant POWER.getName().equals("power");
   @ public invariant POWER.getExponent(LENGTH) == 2;
   @ public invariant POWER.getExponent(MASS) == 1;
   @ public invariant POWER.getExponent(TIME) == -3;
   @ public invariant POWER.getBaseDimensions().size() == 3;
   @*/
  public final static Dimension POWER = new CompositeDimension("power", LENGTH, 2, MASS, 1, TIME, -3);

 /*@
   @ public invariant ELECTRIC_CHARGE.getName().equals("electric charge");
   @ public invariant ELECTRIC_CHARGE.getExponent(TIME) == 1;
   @ public invariant ELECTRIC_CHARGE.getExponent(ELECTRIC_CURRENT) == 1;
   @ public invariant ELECTRIC_CHARGE.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension ELECTRIC_CHARGE = new CompositeDimension("electric charge", ELECTRIC_CURRENT, 1, TIME, 1);

 /*@
   @ public invariant ELECTRIC_POTENTIAL_DIFFERENCE.getName().equals("electric potential difference");
   @ public invariant ELECTRIC_POTENTIAL_DIFFERENCE.getExponent(METER) == 2;
   @ public invariant ELECTRIC_POTENTIAL_DIFFERENCE.getExponent(MASS) == 1;
   @ public invariant ELECTRIC_POTENTIAL_DIFFERENCE.getExponent(TIME) == -3;
   @ public invariant ELECTRIC_POTENTIAL_DIFFERENCE.getExponent(ELECTRIC_CURRENT) == -1;
   @ public invariant ELECTRIC_POTENTIAL_DIFFERENCE.getBaseDimensions().size() == 4;
   @*/
  public final static Dimension ELECTRIC_POTENTIAL_DIFFERENCE = new CompositeDimension("electric potential difference", LENGTH, 2, MASS, 1,ELECTRIC_CURRENT, -1, TIME, -3);

 /*@
   @ public invariant CAPACITANCE.getName().equals("capacitance");
   @ public invariant CAPACITANCE.getExponent(LENGTH) == -2;
   @ public invariant CAPACITANCE.getExponent(MASS) == -1;
   @ public invariant CAPACITANCE.getExponent(TIME) == 4;
   @ public invariant CAPACITANCE.getExponent(ELECTRIC_CURRENT) == 2;
   @ public invariant CAPACITANCE.getBaseDimensions().size() == 4;
   @*/
  public final static Dimension CAPACITANCE = new CompositeDimension("capacitance", LENGTH, -2, MASS, -1, TIME, 4, ELECTRIC_CURRENT, 2);

 /*@
   @ public invariant ELECTRIC_RESISTANCE.getName().equals("electric resistance");
   @ public invariant ELECTRIC_RESISTANCE.getExponent(LENGTH) == 2;
   @ public invariant ELECTRIC_RESISTANCE.getExponent(MASS) == 1;
   @ public invariant ELECTRIC_RESISTANCE.getExponent(TIME) == -3;
   @ public invariant ELECTRIC_RESISTANCE.getExponent(ELECTRIC_CURRENT) == -2;
   @ public invariant ELECTRIC_RESISTANCE.getBaseDimensions().size() == 4;
   @*/
  public final static Dimension ELECTRIC_RESISTANCE = new CompositeDimension("electric resistance", LENGTH, 2, MASS, 1, TIME, -3, ELECTRIC_CURRENT, -2);

 /*@
   @ public invariant ELECTRIC_CONDUCTANCE.getName().equals("electric conductance");
   @ public invariant ELECTRIC_CONDUCTANCE.getExponent(LENGTH) == -2;
   @ public invariant ELECTRIC_CONDUCTANCE.getExponent(MASS) == -1;
   @ public invariant ELECTRIC_CONDUCTANCE.getExponent(TIME) == 3;
   @ public invariant ELECTRIC_CONDUCTANCE.getExponent(ELECTRIC_CURRENT) == 2;
   @ public invariant ELECTRIC_CONDUCTANCE.getBaseDimensions().size() == 4;
   @*/
  public final static Dimension ELECTRIC_CONDUCTANCE = new CompositeDimension("electric conductance", LENGTH, -2, MASS, -1, TIME, 3, ELECTRIC_CURRENT, 2);

 /*@
   @ public invariant MAGNETIC_FLUX.getName().equals("magnetic flux");
   @ public invariant MAGNETIC_FLUX.getExponent(LENGTH) == 2;
   @ public invariant MAGNETIC_FLUX.getExponent(MASS) == 1;
   @ public invariant MAGNETIC_FLUX.getExponent(TIME) == -2;
   @ public invariant MAGNETIC_FLUX.getExponent(ELECTRIC_CURRENT) == -1;
   @ public invariant MAGNETIC_FLUX.getBaseDimensions().size() == 4;
   @*/
  public final static Dimension MAGNETIC_FLUX = new CompositeDimension("magnetic flux", LENGTH, 2, MASS, 1, TIME, -2, ELECTRIC_CURRENT, -1);

 /*@
   @ public invariant MAGNETIC_FLUX_DENSITY.getName().equals("magnetic flux density");
   @ public invariant MAGNETIC_FLUX_DENSITY.getExponent(MASS) == 1;
   @ public invariant MAGNETIC_FLUX_DENSITY.getExponent(TIME) == -2;
   @ public invariant MAGNETIC_FLUX_DENSITY.getExponent(ELECTRIC_CURRENT) == -1;
   @ public invariant MAGNETIC_FLUX_DENSITY.getBaseDimensions().size() == 3;
   @*/
  public final static Dimension MAGNETIC_FLUX_DENSITY = new CompositeDimension("magnetic flux density", MASS, 1, TIME, -2, ELECTRIC_CURRENT, -1);

 /*@
   @ public invariant INDUCTANCE.getName().equals("inductance");
   @ public invariant INDUCTANCE.getExponent(LENGTH) == 2;
   @ public invariant INDUCTANCE.getExponent(MASS) == 1;
   @ public invariant INDUCTANCE.getExponent(TIME) == -2;
   @ public invariant INDUCTANCE.getExponent(ELECTRIC_CURRENT) == -2;
   @ public invariant INDUCTANCE.getBaseDimensions().size() == 4;
   @*/
  public final static Dimension INDUCTANCE = new CompositeDimension("inductance", LENGTH, 2, MASS, 1, TIME, -2, ELECTRIC_CURRENT, -2);

 /*@
   @ public invariant SPECIFIC_ENERGY.getName().equals("specific energy");
   @ public invariant SPECIFIC_ENERGY.getExponent(LENGTH) == 2;
   @ public invariant SPECIFIC_ENERGY.getExponent(TIME) == -2;
   @ public invariant SPECIFIC_ENERGY.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension SPECIFIC_ENERGY = new CompositeDimension("specific energy", LENGTH, 2, TIME, -2);

 /*@
   @ public invariant CATALYTIC_ACTIVITY.getName().equals("catalytic activity");
   @ public invariant CATALYTIC_ACTIVITY.getExponent(AMOUNT_OF_SUBSTANCE) == 1;
   @ public invariant CATALYTIC_ACTIVITY.getExponent(TIME) == -1;
   @ public invariant CATALYTIC_ACTIVITY.getBaseDimensions().size() == 2;
   @*/
  public final static Dimension CATALYTIC_ACTIVITY = new CompositeDimension("catalytic activity", AMOUNT_OF_SUBSTANCE, 1, TIME, -1);

/*****************
 * SPECIAL UNITS *
 *****************/

/*@
  @ public invariant KILOGRAM.getName().equals("gram");
  @ public invariant KILOGRAM.getSymbol().equals("g");
  @ public invariant KILOGRAM.getDimension() == MASS;
  @*/
 public final static SpecialUnit KILOGRAM = (SpecialUnit)GRAM.prefix(KILO);


/*****************
 * DERIVED UNITS *
 *****************/
  
 /*@
   @ public invariant SQUARE_METER.getName().equals("square meter"); 
   @ public invariant SQUARE_METER.getSymbol().equals("m^2"); 
   @ public invariant SQUARE_METER.getExponent(METER) == 2; 
   @ public invariant SQUARE_METER.getBaseUnits().size() == 1; 
   @ public invariant SQUARE_METER.getDimension() == AREA;
   @*/
  public final static CompositeUnit SQUARE_METER = new CompositeUnit("square meter", "m^2", METER, 2);
  
 /*@
   @ public invariant CUBIC_METER.getName().equals("cubic meter"); 
   @ public invariant CUBIC_METER.getSymbol().equals("m^3"); 
   @ public invariant CUBIC_METER.getExponent(METER) == 3; 
   @ public invariant CUBIC_METER.getBaseUnits().size() == 1; 
   @ public invariant CUBIC_METER.getDimension() == VOLUME;
   @*/
  public final static CompositeUnit CUBIC_METER = new CompositeUnit("cubic meter", "m^3", METER, 3);
  
 /*@
   @ public invariant METER_PER_SECOND.getName().equals("meter per second"); 
   @ public invariant METER_PER_SECOND.getSymbol().equals("m/s"); 
   @ public invariant METER_PER_SECOND.getExponent(METER) == 1; 
   @ public invariant METER_PER_SECOND.getExponent(SECOND) == -1; 
   @ public invariant METER_PER_SECOND.getBaseUnits().size() == 2; 
   @ public invariant METER_PER_SECOND.getDimension() == VELOCITY;
   @*/
  public final static CompositeUnit METER_PER_SECOND = new CompositeUnit("meter per second", "m/s", METER, 1, SECOND, -1);
  
 /*@
   @ public invariant METER_PER_SECOND_SQUARED.getName().equals("meter per second squared"); 
   @ public invariant METER_PER_SECOND_SQUARED.getSymbol().equals("m/(s^2)"); 
   @ public invariant METER_PER_SECOND_SQUARED.getExponent(METER) == 1; 
   @ public invariant METER_PER_SECOND_SQUARED.getExponent(SECOND) == -2; 
   @ public invariant METER_PER_SECOND_SQUARED.getBaseUnits().size() == 2; 
   @ public invariant METER_PER_SECOND_SQUARED.getDimension() == ACCELERATION;
   @*/
  public final static CompositeUnit METER_PER_SECOND_SQUARED = new CompositeUnit("meter per second squared", "m/(s^2)", METER, 1, SECOND, -2);
  
 /*@
   @ public invariant RECIPROCAL_METER.getName().equals("reciprocal meter"); 
   @ public invariant RECIPROCAL_METER.getSymbol().equals("1/m"); 
   @ public invariant RECIPROCAL_METER.getExponent(METER) == -1; 
   @ public invariant RECIPROCAL_METER.getBaseUnits().size() == 1; 
   @ public invariant RECIPROCAL_METER.getDimension() == WAVE_NUMBER;
   @*/
  public final static CompositeUnit RECIPROCAL_METER = new CompositeUnit("reciprocal meter", "1/m", METER, -1);
  
 /*@
   @ public invariant KILOGRAM_PER_CUBIC_METER.getName().equals("kilogram per cubic meter"); 
   @ public invariant KILOGRAM_PER_CUBIC_METER.getSymbol().equals("kg/(m^3)"); 
   @ public invariant KILOGRAM_PER_CUBIC_METER.getExponent(KILOGRAM) == 1; 
   @ public invariant KILOGRAM_PER_CUBIC_METER.getExponent(METER) == -3; 
   @ public invariant KILOGRAM_PER_CUBIC_METER.getBaseUnits().size() == 2; 
   @ public invariant KILOGRAM_PER_CUBIC_METER.getDimension() == MASS_DENSITY;
   @*/
  public final static CompositeUnit KILOGRAM_PER_CUBIC_METER = new CompositeUnit("kilogram per cubic meter", "kg/(m^3)", KILOGRAM, 1, METER, -3);
  
 /*@
   @ public invariant CUBIC_METER_PER_KILOGRAM.getName().equals("cubic meter per kilogram"); 
   @ public invariant CUBIC_METER_PER_KILOGRAM.getSymbol().equals("(m^3)/kg"); 
   @ public invariant CUBIC_METER_PER_KILOGRAM.getExponent(METER) == 3; 
   @ public invariant CUBIC_METER_PER_KILOGRAM.getExponent(KILOGRAM) == -1; 
   @ public invariant CUBIC_METER_PER_KILOGRAM.getBaseUnits().size() == 2; 
   @ public invariant CUBIC_METER_PER_KILOGRAM.getDimension() == VELOCITY;
   @*/
  public final static CompositeUnit CUBIC_METER_PER_KILOGRAM = new CompositeUnit("cubic meter per kilogram", "(m^3)/kg", KILOGRAM, -1, METER, 3);
  
 /*@
   @ public invariant AMPERE_PER_SQUARE_METER.getName().equals("ampere per square meter"); 
   @ public invariant AMPERE_PER_SQUARE_METER.getSymbol().equals("A/(m^2)"); 
   @ public invariant AMPERE_PER_SQUARE_METER.getExponent(AMPERE) == 1; 
   @ public invariant AMPERE_PER_SQUARE_METER.getExponent(METER) == -2; 
   @ public invariant AMPERE_PER_SQUARE_METER.getBaseUnits().size() == 2; 
   @ public invariant AMPERE_PER_SQUARE_METER.getDimension() == CURRENT_DENSITY;
   @*/
  public final static CompositeUnit AMPERE_PER_SQUARE_METER = new CompositeUnit("ampere per square meter", "A/(m^2)", AMPERE, 1, METER, -2);
  
 /*@
   @ public invariant AMPERE_PER_METER.getName().equals("ampere per meter"); 
   @ public invariant AMPERE_PER_METER.getSymbol().equals("A/m"); 
   @ public invariant AMPERE_PER_METER.getExponent(AMPERE) == 1; 
   @ public invariant AMPERE_PER_METER.getExponent(METER) == -1; 
   @ public invariant AMPERE_PER_METER.getBaseUnits().size() == 2; 
   @ public invariant AMPERE_PER_METER.getDimension() == MAGNETIC_FIELD_STRENGTH;
   @*/
  public final static CompositeUnit AMPERE_PER_METER = new CompositeUnit("ampere per meter", "A/m", AMPERE, 1, METER, -1);
  
 /*@
   @ public invariant MOLE_PER_CUBIC_METER.getName().equals("mole per cubic meter"); 
   @ public invariant MOLE_PER_CUBIC_METER.getSymbol().equals("mol/(m^3)"); 
   @ public invariant MOLE_PER_CUBIC_METER.getExponent(MOLE) == 1; 
   @ public invariant MOLE_PER_CUBIC_METER.getExponent(METER) == -3; 
   @ public invariant MOLE_PER_CUBIC_METER.getBaseUnits().size() == 2; 
   @ public invariant MOLE_PER_CUBIC_METER.getDimension() == AMOUNT_OF_SUBSTANCE_CONCENTRATION;
   @*/
  public final static CompositeUnit MOLE_PER_CUBIC_METER = new CompositeUnit("mole per cubic meter", "mol/(m^3)", MOLE, 1, METER, -3);
  
 /*@
   @ public invariant CANDELA_PER_SQUARE_METER.getName().equals("candela per square meter"); 
   @ public invariant CANDELA_PER_SQUARE_METER.getSymbol().equals("cd/(m^2)"); 
   @ public invariant CANDELA_PER_SQUARE_METER.getExponent(CANDELA) == 1; 
   @ public invariant CANDELA_PER_SQUARE_METER.getExponent(METER) == -2; 
   @ public invariant CANDELA_PER_SQUARE_METER.getBaseUnits().size() == 2; 
   @ public invariant CANDELA_PER_SQUARE_METER.getDimension() == LUMINANCE;
   @*/
  public final static CompositeUnit CANDELA_PER_SQUARE_METER = new CompositeUnit("candela per square meter", "cd/(m^2)", CANDELA, 1, METER, -2);

 /*@
   @ public invariant HERTZ.getName().equals("hertz"); 
   @ public invariant HERTZ.getSymbol().equals("1/s"); 
   @ public invariant HERTZ.getExponent(SECOND) == -1; 
   @ public invariant HERTZ.getBaseUnits().size() == 1; 
   @ public invariant HERTZ.getDimension() == FREQUENCY;
   @*/
  public final static CompositeUnit HERTZ = new CompositeUnit("hertz", "1/s", SECOND, -1);

 /*@
   @ public invariant NEWTON.getName().equals("newton"); 
   @ public invariant NEWTON.getSymbol().equals("N"); 
   @ public invariant NEWTON.getExponent(KILOGRAM) == 1; 
   @ public invariant NEWTON.getExponent(METER) == 1; 
   @ public invariant NEWTON.getExponent(SECOND) == -2; 
   @ public invariant NEWTON.getBaseUnits().size() == 3; 
   @ public invariant NEWTON.getDimension() == FORCE;
   @*/
  public final static CompositeUnit NEWTON = new CompositeUnit("newton", "N", KILOGRAM, 1, METER, 1, SECOND, -2);

 /*@
   @ public invariant PASCAL.getName().equals("pascal"); 
   @ public invariant PASCAL.getSymbol().equals("Pa"); 
   @ public invariant PASCAL.getExponent(KILOGRAM) == 1; 
   @ public invariant PASCAL.getExponent(METER) == -1; 
   @ public invariant PASCAL.getExponent(SECOND) == -2; 
   @ public invariant PASCAL.getBaseUnits().size() == 3; 
   @ public invariant PASCAL.getDimension() == PRESSURE;
   @*/
  public final static CompositeUnit PASCAL = new CompositeUnit("pascal", "Pa", KILOGRAM, 1, METER, -1, SECOND, -2);

 /*@
   @ public invariant JOULE.getName().equals("joule"); 
   @ public invariant JOULE.getSymbol().equals("J"); 
   @ public invariant JOULE.getExponent(KILOGRAM) == 1; 
   @ public invariant JOULE.getExponent(METER) == 2; 
   @ public invariant JOULE.getExponent(SECOND) == -2; 
   @ public invariant JOULE.getBaseUnits().size() == 3; 
   @ public invariant JOULE.getDimension() == ENERGY;
   @*/
  public final static CompositeUnit JOULE = new CompositeUnit("joule", "J", KILOGRAM, 1, METER, 2, SECOND, -2);

 /*@
   @ public invariant WATT.getName().equals("watt"); 
   @ public invariant WATT.getSymbol().equals("W"); 
   @ public invariant WATT.getExponent(KILOGRAM) == 1; 
   @ public invariant WATT.getExponent(METER) == 2; 
   @ public invariant WATT.getExponent(SECOND) == -3; 
   @ public invariant WATT.getBaseUnits().size() == 3; 
   @ public invariant WATT.getDimension() == POWER;
   @*/
  public final static CompositeUnit WATT = new CompositeUnit("watt", "W", KILOGRAM, 1, METER, 2, SECOND, -3);

 /*@
   @ public invariant COULOMB.getName().equals("coulomb"); 
   @ public invariant COULOMB.getSymbol().equals("C"); 
   @ public invariant COULOMB.getExponent(AMPERE) == 1; 
   @ public invariant COULOMB.getExponent(SECOND) == 1; 
   @ public invariant COULOMB.getBaseUnits().size() == 2; 
   @ public invariant COULOMB.getDimension() == ELECTRIC_CHARGE;
   @*/
  public final static CompositeUnit COULOMB = new CompositeUnit("coulomb", "C", AMPERE, 1, SECOND, 1);

 /*@
   @ public invariant VOLT.getName().equals("volt"); 
   @ public invariant VOLT.getSymbol().equals("V"); 
   @ public invariant VOLT.getExponent(METER) == 2; 
   @ public invariant VOLT.getExponent(KILOGRAM) == 1; 
   @ public invariant VOLT.getExponent(SECOND) == -3; 
   @ public invariant VOLT.getExponent(AMPERE) == -1; 
   @ public invariant VOLT.getBaseUnits().size() == 4; 
   @ public invariant VOLT.getDimension() == ELECTRIC_POTENTIAL_DIFFERENCE;
   @*/
  public final static CompositeUnit VOLT = new CompositeUnit("volt", "V", METER, 2, KILOGRAM, 1, AMPERE, -1, SECOND, -3);

 /*@
   @ public invariant FARAD.getName().equals("farad"); 
   @ public invariant FARAD.getSymbol().equals("F"); 
   @ public invariant FARAD.getExponent(METER) == -2; 
   @ public invariant FARAD.getExponent(KILOGRAM) == -1; 
   @ public invariant FARAD.getExponent(SECOND) == 4; 
   @ public invariant FARAD.getExponent(AMPERE) == 2; 
   @ public invariant FARAD.getBaseUnits().size() == 4; 
   @ public invariant FARAD.getDimension() == CAPACITANCE;
   @*/
  public final static CompositeUnit FARAD = new CompositeUnit("farad", "F", METER, -2, KILOGRAM, -1, AMPERE, 2, SECOND, 4);

 /*@
   @ public invariant OHM.getName().equals("ohm"); 
   @ public invariant OHM.getSymbol().equals("\u03A9"); 
   @ public invariant OHM.getExponent(METER) == 2; 
   @ public invariant OHM.getExponent(KILOGRAM) == 1; 
   @ public invariant OHM.getExponent(SECOND) == -3; 
   @ public invariant OHM.getExponent(AMPERE) == -2; 
   @ public invariant OHM.getBaseUnits().size() == 4; 
   @ public invariant OHM.getDimension() == ELECTRIC_RESISTANCE;
   @*/
  public final static CompositeUnit OHM = new CompositeUnit("ohm", "\u03A9", METER, 2, KILOGRAM, 1, AMPERE, -2, SECOND, -3);

 /*@
   @ public invariant SIEMENS.getName().equals("siemens"); 
   @ public invariant SIEMENS.getSymbol().equals("S"); 
   @ public invariant SIEMENS.getExponent(METER) == -2; 
   @ public invariant SIEMENS.getExponent(KILOGRAM) == -1; 
   @ public invariant SIEMENS.getExponent(SECOND) == 3; 
   @ public invariant SIEMENS.getExponent(AMPERE) == 2; 
   @ public invariant SIEMENS.getBaseUnits().size() == 4; 
   @ public invariant SIEMENS.getDimension() == ELECTRIC_CONDUCTANCE;
   @*/
  public final static CompositeUnit SIEMENS = new CompositeUnit("siemens", "S", METER, -2, KILOGRAM, -1, AMPERE, 2, SECOND, 3);

 /*@
   @ public invariant WEBER.getName().equals("weber"); 
   @ public invariant WEBER.getSymbol().equals("Wb"); 
   @ public invariant WEBER.getExponent(METER) == 2; 
   @ public invariant WEBER.getExponent(KILOGRAM) == 1; 
   @ public invariant WEBER.getExponent(SECOND) == -2; 
   @ public invariant WEBER.getExponent(AMPERE) == -1; 
   @ public invariant WEBER.getBaseUnits().size() == 4; 
   @ public invariant WEBER.getDimension() == MAGNETIC_FLUX;
   @*/
  public final static CompositeUnit WEBER = new CompositeUnit("weber", "Wb", METER, 2, KILOGRAM, 1, AMPERE, -1, SECOND, -2);

 /*@
   @ public invariant TESLA.getName().equals("tesla"); 
   @ public invariant TESLA.getSymbol().equals("T"); 
   @ public invariant TESLA.getExponent(KILOGRAM) == 1; 
   @ public invariant TESLA.getExponent(SECOND) == -2; 
   @ public invariant TESLA.getExponent(AMPERE) == -1; 
   @ public invariant TESLA.getBaseUnits().size() == 3; 
   @ public invariant TESLA.getDimension() == MAGNETIC_FLUX_DENSITY;
   @*/
  public final static CompositeUnit TESLA = new CompositeUnit("tesla", "T", KILOGRAM, 1, AMPERE, -1, SECOND, -2);

 /*@
   @ public invariant HENRY.getName().equals("henry"); 
   @ public invariant HENRY.getSymbol().equals("H"); 
   @ public invariant HENRY.getExponent(METER) == 2; 
   @ public invariant HENRY.getExponent(KILOGRAM) == 1; 
   @ public invariant HENRY.getExponent(SECOND) == -2; 
   @ public invariant HENRY.getExponent(AMPERE) == -2; 
   @ public invariant HENRY.getBaseUnits().size() == 4; 
   @ public invariant HENRY.getDimension() == MAGNETIC_FLUX;
   @*/
  public final static CompositeUnit HENRY = new CompositeUnit("henry", "H", METER, 2, KILOGRAM, 1, AMPERE, -2, SECOND, -2);

 /*@
   @ public invariant DEGREE_CELCIUS.getName().equals("degree celcius"); 
   @ public invariant DEGREE_CELCIUS.getSymbol().equals("\u00B0C"); 
   @ public invariant DEGREE_CELCIUS.getBaseUnit() == KELVIN;
   @ public invariant DEGREE_CELCIUS.getOffSet() == -273.15;
   @ public invariant DEGREE_CELCIUS.getFactor() == 1;
   @*/
  public final static TransformedUnit DEGREE_CELCIUS = new TransformedUnit(KELVIN, "degree celcius", "\u00B0C", 1, -273.15);

 /*@
   @ public invariant LUMEN.getName().equals("lumen"); 
   @ public invariant LUMEN.getSymbol().equals("lm"); 
   @ public invariant LUMEN.getExponent(CANDELA) == 1; 
   @ public invariant LUMEN.getBaseUnits().size() == 1; 
   @ public invariant LUMEN.getDimension() == LUMINOUS_INTENSITY;
   @*/
  public final static TransformedUnit LUMEN = new TransformedUnit(CANDELA, "lumen", "lm", 1, 0);

 /*@
   @ public invariant LUX.getName().equals("lux"); 
   @ public invariant LUX.getSymbol().equals("lx"); 
   @ public invariant LUX.getExponent(CANDELA) == 1; 
   @ public invariant LUX.getExponent(METER) == -2; 
   @ public invariant LUX.getBaseUnits().size() == 2; 
   @ public invariant LUX.getDimension() == LUMINOUS_INTENSITY;
   @*/
  public final static CompositeUnit LUX = new CompositeUnit("lux", "lx", CANDELA, 1, METER, -2);

 /*@
   @ public invariant BECQUEREL.getName().equals("becquerel"); 
   @ public invariant BECQUEREL.getSymbol().equals("Bq"); 
   @ public invariant BECQUEREL.getExponent(SECOND) == -1; 
   @ public invariant BECQUEREL.getBaseUnits().size() == 1; 
   @ public invariant BECQUEREL.getDimension() == FREQUENCY;
   @*/
  public final static CompositeUnit BECQUEREL = new CompositeUnit("becquerel", "Bq", SECOND, -1);

 /*@
   @ public invariant GRAY.getName().equals("gray"); 
   @ public invariant GRAY.getSymbol().equals("Gy"); 
   @ public invariant GRAY.getExponent(METER) == 2; 
   @ public invariant GRAY.getExponent(SECOND) == -2; 
   @ public invariant GRAY.getBaseUnits().size() == 2; 
   @ public invariant GRAY.getDimension() == SPECIFIC_ENERGY;
   @*/
  public final static CompositeUnit GRAY = new CompositeUnit("gray", "Gy", METER, 2, SECOND, -2);

  /*@
    @ public invariant SIEVERT.getName().equals("sievert"); 
    @ public invariant SIEVERT.getSymbol().equals("Gy"); 
    @ public invariant SIEVERT.getExponent(METER) == 2; 
    @ public invariant SIEVERT.getExponent(SECOND) == -2; 
    @ public invariant SIEVERT.getBaseUnits().size() == 2; 
    @ public invariant SIEVERT.getDimension() == SPECIFIC_ENERGY;
    @*/
  public final static CompositeUnit SIEVERT = new CompositeUnit("sievert", "Sv", METER, 2, SECOND, -2);
  
 /*@
   @ public invariant KATAL.getName().equals("katal"); 
   @ public invariant KATAL.getSymbol().equals("kat"); 
   @ public invariant KATAL.getExponent(MOLE) == 1; 
   @ public invariant KATAL.getExponent(SECOND) == -1; 
   @ public invariant KATAL.getBaseUnits().size() == 2; 
   @ public invariant KATAL.getDimension() == SPECIFIC_ENERGY;
   @*/
  public final static CompositeUnit KATAL = new CompositeUnit("katal", "kat", MOLE, 1, SECOND, -1);

/*****************************************************
 * DERIVED DIMENSIONS COMPOSED OF SPECIAL DIMENSIONS *
 *****************************************************/

 /*@
   @ public invariant DYNAMIC_VISCOSITY == PRESSURE.times(TIME);
   @ public invariant DYNAMIC_VISCOSITY.getName().equals("dynamic viscosity");
   @*/
  public final static Dimension DYNAMIC_VISCOSITY = new CompositeDimension("dynamic viscosity", MASS, 1, LENGTH, -1, TIME, -1);

 /*@
   @ public invariant PASCAL_SECOND == PASCAL.times(SECOND);
   @ public invariant NEWTON_METER.getName().equals("pascal second");
   @ public invariant NEWTON_METER.getSymbol().equals("Pa*s");
   @*/
  public final static Unit PASCAL_SECOND = new CompositeUnit("pascal second", "Pa*s", KILOGRAM, 1, METER, -1, SECOND, -1);
  
 /*@
   @ public invariant MOMENT_OF_FORCE == FORCE.times(LENGTH);
   @ public invariant DYNAMIC_VISCOSITY.getName().equals("moment of force");
   @*/
  public final static Dimension MOMENT_OF_FORCE = new CompositeDimension("moment of force", MASS, 1, LENGTH, 2, TIME, -2);
  
 /*@
   @ public invariant NEWTON_METER == NEWTON.times(METER);
   @ public invariant NEWTON_METER.getName().equals("newton meter");
   @ public invariant NEWTON_METER.getSymbol().equals("N*m");
   @*/
  public final static Unit NEWTON_METER = new CompositeUnit("newton meter", "N*m", KILOGRAM, 1, METER, 2, SECOND, -2);
  
 /*@
   @ public invariant SURFACE_TENSION == FORCE.times(LENGTH);
   @ public invariant SURFACE_TENSION.getName().equals("surface tension");
   @*/
  public final static Dimension SURFACE_TENSION = new CompositeDimension("surface tension", MASS, 1, TIME, -2);
  
 /*@
   @ public invariant NEWTON_PER_METER == NEWTON.times(METER);
   @ public invariant NEWTON_PER_METER.getName().equals("newton per meter");
   @ public invariant NEWTON_PER_METER.getSymbol().equals("N/m");
   @*/
  public final static Unit NEWTON_PER_METER = new CompositeUnit("newton per meter", "N/m", KILOGRAM, 1, SECOND, -2);
  
 /*@
   @ public invariant RADIAN_PER_SECOND == RADIAN.dividedBy(SECOND);
   @ public invariant RADIAN_PER_SECOND.getName().equals("radian per second");
   @ public invariant RADIAN_PER_SECOND.getSymbol().equals("N/m");
   @*/
  public final static Unit RADIAN_PER_SECOND = new CompositeUnit("radian per second", "rad/s", SECOND, -1);
  
}

