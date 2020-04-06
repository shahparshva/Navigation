package modle;

import android.widget.ImageView;

public class 	catagory_second_modle {
	private String title;
	private String thumbnailUrl;
	private String year;
	private String rating;
	private String details;
	private String about;
	private String item_id;
	private String max_item;

	public String getMax_item() {
		return max_item;
	}

	public void setMax_item(String max_item) {
		this.max_item = max_item;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	private String user_id;
	ImageView addtocart;
	/*private int year;
	private double rating;*/
	//private ArrayList<String> genre;

	public catagory_second_modle() {
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public catagory_second_modle(String name, String thumbnailUrl, String year, String rating, String details, String about, String item_id) {
		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.rating = rating;
        this.details=details;
        this.about=about;
		this.item_id=item_id;
		//this.genre = genre;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getdetails(){return details;}
    public void setDetails(String details){this.details=details;}

    public String getAbout(){return about;}
    public void setAbout(String about){this.about=about;}

}
