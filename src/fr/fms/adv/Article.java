package fr.fms.adv;

public class Article {
    private int id;
    private String description;
    private String brand;
    private double price;

    public Article(int id, String description, String brand, double price) {
        this.id = id;
        this.description = description;
        this.brand = brand;
        this.price = price;
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
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
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

    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return id + " - " + description + " - " + brand + " - " + price;
    }

}
