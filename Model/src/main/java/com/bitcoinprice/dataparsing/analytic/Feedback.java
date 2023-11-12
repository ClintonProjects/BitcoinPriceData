package com.bitcoinprice.dataparsing.analytic;

import javax.persistence.Id;

import org.bson.types.ObjectId;

public class Feedback {
	
	@Id
	public ObjectId _id;
	public String rating;
	public String type;
	public String text;
	
	public Feedback(String rating, String type, String text) throws Exception {
		//setRating(rating); 
		this.rating = rating;
		this.type = type;
		this.text = text;
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}	
}
