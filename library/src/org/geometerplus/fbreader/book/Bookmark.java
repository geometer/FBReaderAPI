/*
 * Copyright (C) 2009-2013 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.fbreader.book;

import java.util.*;

import org.geometerplus.zlibrary.text.view.*;

public final class Bookmark extends ZLTextFixedPosition {
	public enum DateType {
		Creation,
		Modification,
		Access,
		Latest
	}

	private long myId;
	private long myBookId;
	private final String myBookTitle;
	private String myText;
	private final Date myCreationDate;
	private Date myModificationDate;
	private Date myAccessDate;
	private int myAccessCount;
	private Date myLatestDate;
	ZLTextFixedPosition myEnd;
	int myLength;
	private int myStyleId;

	public final String ModelId;
	public final boolean IsVisible;

	Bookmark(
		long id, long bookId, String bookTitle, String text,
		Date creationDate, Date modificationDate, Date accessDate, int accessCount,
		String modelId,
		int start_paragraphIndex, int start_elementIndex, int start_charIndex,
		int end_paragraphIndex, int end_elementIndex, int end_charIndex,
		boolean isVisible,
		int styleId
	) {
		super(start_paragraphIndex, start_elementIndex, start_charIndex);

		myId = id;
		myBookId = bookId;
		myBookTitle = bookTitle;
		myText = text;
		myCreationDate = creationDate;
		myModificationDate = modificationDate;
		myLatestDate = (modificationDate != null) ? modificationDate : creationDate;
		if (accessDate != null) {
			myAccessDate = accessDate;
			if (myLatestDate.compareTo(accessDate) < 0) {
				myLatestDate = accessDate;
			}
		}
		myAccessCount = accessCount;
		ModelId = modelId;
		IsVisible = isVisible;

		if (end_charIndex >= 0) {
			myEnd = new ZLTextFixedPosition(end_paragraphIndex, end_elementIndex, end_charIndex);
		} else {
			myLength = end_paragraphIndex;
		}

		myStyleId = styleId;
	}

	public Bookmark(Book book, String modelId, ZLTextPosition start, ZLTextPosition end, String text, boolean isVisible) {
		super(start);

		myId = -1;
		myBookId = book.getId();
		myBookTitle = book.getTitle();
		myText = text;
		myCreationDate = new Date();
		ModelId = modelId;
		IsVisible = isVisible;
		myEnd = new ZLTextFixedPosition(end);
		myStyleId = 1;
	}

	public long getId() {
		return myId;
	}

	public long getBookId() {
		return myBookId;
	}

	public int getStyleId() {
		return myStyleId;
	}

	public void setStyleId(int styleId) {
		myStyleId = styleId;
	}

	public String getText() {
		return myText;
	}

	public String getBookTitle() {
		return myBookTitle;
	}

	public Date getDate(DateType type) {
		switch (type) {
			case Creation:
				return myCreationDate;
			case Modification:
				return myModificationDate;
			case Access:
				return myAccessDate;
			default:
			case Latest:
				return myLatestDate;
		}
	}

	public int getAccessCount() {
		return myAccessCount;
	}

	public ZLTextPosition getEnd() {
		return myEnd;
	}

	public int getLength() {
		return myLength;
	}

	public void setText(String text) {
		if (!text.equals(myText)) {
			myText = text;
			myModificationDate = new Date();
			myLatestDate = myModificationDate;
		}
	}

	public void markAsAccessed() {
		myAccessDate = new Date();
		++myAccessCount;
		myLatestDate = myAccessDate;
	}

	public static class ByTimeComparator implements Comparator<Bookmark> {
		public int compare(Bookmark bm0, Bookmark bm1) {
			final Date date0 = bm0.getDate(DateType.Latest);
			final Date date1 = bm1.getDate(DateType.Latest);
			if (date0 == null) {
				return date1 == null ? 0 : -1;
			}
			return date1 == null ? 1 : date1.compareTo(date0);
		}
	}

	public void setId(long id) {
		myId = id;
	}

	public void update(Bookmark other) {
		// TODO: copy other fields (?)
		if (other != null) {
			myId = other.myId;
		}
	}

	public void setBookId(long id) {
		myBookId = id;
	}
}
