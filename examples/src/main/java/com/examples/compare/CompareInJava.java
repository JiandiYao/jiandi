package com.examples.compare;

import java.util.Comparator;

public class CompareInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

 class Movie implements Comparable<Movie>{
	private double rating;
	private String name;

	public int compareTo(Movie m) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				if(this.rating > m.rating )
					return 1;
				if(this.rating < m.rating)
					return -1;
				else
					return 0;
	}
	
	
}
 class RatingComparator implements Comparator<Movie>{

	public int compare(Movie o1, Movie o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	 
 }
