package org.geometerplus.fbreader.widget;

import java.util.ArrayList;
import java.util.List;

import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.*;

public class BooksModel implements BookCollectionShadow.Listener {
	private List<Book> myBooks;
	private IBookCollection myCollection;
	
	private List<Listener> myListeners = new ArrayList<Listener>();
	
	public interface Listener {
		public void update();
	}
	
	public void addListener(Listener listener) {
		myListeners.add(listener);
	}
	
	public void removeListener(Listener listener) {
		myListeners.remove(listener);
	}
	
	public List<Book> getBooks() {
		return myBooks;
	}

	private void takeListOfBooks() {
		myBooks = new ArrayList<Book>();
		for (BookQuery query = new BookQuery(new Filter.Empty(), 20); ; query = query.next()) {
			final List<Book> partOfBooks = myCollection.books(query);
			if (partOfBooks.isEmpty()) {
				break;
			}
			myBooks.addAll(partOfBooks);
		}
	}
	
	public void initialize(IBookCollection collection) {
		myCollection = collection;
		myCollection.addListener(this);
		takeListOfBooks();
	}
	
	@Override
	protected void finalize() {
		myCollection.removeListener(this);
	}
	
	@Override
	public void onBookEvent(BookEvent event, Book book) {
		if (event.equals(BookEvent.Added)) {
			myBooks.add(book);
			update();
		}
	}

	private void update() {
		for (Listener l : myListeners) {
			l.update();
		}
	}
	
	@Override
	public void onBuildEvent(IBookCollection.Status status) {
		
	}
}
