/**
 * 
 */
package org.usfirst.frc.team1072.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author joelmanning
 *
 */
public class SmartEnum<E extends Enum<?>> {
	
	private static int id = 0;
	
	private String name;
	private E init;
	private SendableChooser<E> choose;
	
	public SmartEnum(E init) {
		this(init, "SmartEnum" + id);
		id++;
	}
	
	@SuppressWarnings("unchecked")
	public SmartEnum(E init, String name) {
		this.name = name;
		this.init = init;
		choose = new SendableChooser<E>();
		E[] all = (E[]) init.getDeclaringClass().getEnumConstants();
		for(E e : all) {
			choose.addObject(e.name(), e);
		}
		choose.addDefault(init.name(), init);
		SmartDashboard.putData(name, choose);
	}
	
	public void refresh() {
		if(!SmartDashboard.containsKey(name)) {
			SmartDashboard.putData(name, choose);
		}
	}
	
	public E get() {
		return choose.getSelected();
	}
}
