package ua.nure.mihaylichenko.SummaryTask4.db.entity;

import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.CarClass;
import ua.nure.mihaylichenko.SummaryTask4.db.enumeration.CarColor;

/**
 * Car entity
 * @author A.Mihaylichenko
 *
 */
public class Car extends Entity {

	private static final long serialVersionUID = -2564942854202370697L;
	
	private int id;
	private String mark;
	private String model;
	private double engine;
	private CarColor color;
	private int year;
	private CarClass carClass;
	private double price;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getEngine() {
		return engine;
	}
	public void setEngine(double engine) {
		this.engine = engine;
	}
	public CarColor getColor() {
		return color;
	}
	public void setColor(CarColor color) {
		this.color = color;
	}

	public CarClass getCarClass() {
		return carClass;
	}
	public void setCarClass(CarClass carClass) {
		this.carClass = carClass;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((carClass == null) ? 0 : carClass.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		long temp;
		temp = Double.doubleToLongBits(engine);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + year;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (carClass != other.carClass)
			return false;
		if (color != other.color)
			return false;
		if (Double.doubleToLongBits(engine) != Double
				.doubleToLongBits(other.engine))
			return false;
		if (mark == null) {
			if (other.mark != null)
				return false;
		} else if (!mark.equals(other.mark))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Car [id=" + id + ", mark=" + mark + ", model=" + model + ", engine=" + engine + ", color=" + color
				+ ", year=" + year + ", carClass=" + carClass + ", price=" + price + "]";
	}

	
	
}
