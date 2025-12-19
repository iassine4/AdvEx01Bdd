package fr.fms.adv;

public class Article {
    private int id;
    private String description;
    private String brand;
    private double unitaryPrice;

    public Article(int id, String description, String brand, double unitaryPrice) {
        this.id = id;
        this.description = description;
        this.brand = brand;
        this.unitaryPrice = unitaryPrice;
    }
    
    public Article() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

    /**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @param unitaryPrice the price to set
	 */
	public void setUnitaryPrice(double unitaryPrice) {
		this.unitaryPrice = unitaryPrice;
	}


	public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public double getUnitaryPrice() {
        return unitaryPrice;
    }
    @Override
    public String toString() {
        return id + " - " + description + " - " + brand + " - " + unitaryPrice;
    }

}
