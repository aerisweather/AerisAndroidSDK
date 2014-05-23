package com.example.db;

import java.util.ArrayList;
import java.util.List;

public class MyPlacesSubject {

	public interface MyPlacesObserver {
		void notifyMyPlaceChanged(MyPlace place);
	}

	private static MyPlacesSubject inst;
	private List<MyPlacesObserver> observers;
	
	public static MyPlacesSubject getInstance() {
		synchronized (MyPlacesSubject.class) {
			if (inst == null) {
				inst = new MyPlacesSubject();
			}
			return inst;
		}
	}
	
	public void notifyObservers(MyPlace place) {
		if (observers != null) {
			for (MyPlacesObserver ob : observers) {
				ob.notifyMyPlaceChanged(place);
			}
		}
	}
	
	public void registerObserver(MyPlacesObserver ob) {
		if (observers == null) {
			observers = new ArrayList<MyPlacesObserver>();
		}
		observers.add(ob);
	}

	public void unregisterObserver(MyPlacesObserver ob) {
		if (observers == null) {
			return;
		}
		observers.remove(ob);
	}

}

