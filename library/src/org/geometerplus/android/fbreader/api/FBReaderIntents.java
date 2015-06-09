/*
 * Copyright (C) 2009-2015 FBReader.ORG Limited <contact@fbreader.org>
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

package org.geometerplus.android.fbreader.api;

import android.content.Intent;

import org.geometerplus.fbreader.book.*;

public abstract class FBReaderIntents {
	public static final String DEFAULT_PACKAGE = "com.fbreader";

	public interface Action {
		String API                              = "com.fbreader.action.API";
		String API_CALLBACK                     = "com.fbreader.action.API_CALLBACK";
		String VIEW                             = "com.fbreader.action.VIEW";
		String CANCEL_MENU                      = "com.fbreader.action.CANCEL_MENU";
		String CONFIG_SERVICE                   = "com.fbreader.action.CONFIG_SERVICE";
		String LIBRARY_SERVICE                  = "com.fbreader.action.LIBRARY_SERVICE";
		String BOOK_INFO                        = "com.fbreader.action.BOOK_INFO";
		String LIBRARY                          = "com.fbreader.action.LIBRARY";
		String EXTERNAL_LIBRARY                 = "com.fbreader.action.EXTERNAL_LIBRARY";
		String BOOKMARKS                        = "com.fbreader.action.BOOKMARKS";
		String EXTERNAL_BOOKMARKS               = "com.fbreader.action.EXTERNAL_BOOKMARKS";
		String PREFERENCES                      = "com.fbreader.action.PREFERENCES";
		String NETWORK_LIBRARY                  = "com.fbreader.action.NETWORK_LIBRARY";
		String OPEN_NETWORK_CATALOG             = "com.fbreader.action.OPEN_NETWORK_CATALOG";
		String ERROR                            = "com.fbreader.action.ERROR";
		String CRASH                            = "com.fbreader.action.CRASH";
		String PLUGIN                           = "com.fbreader.action.PLUGIN";
		String CLOSE                            = "com.fbreader.action.CLOSE";
		String PLUGIN_CRASH                     = "com.fbreader.action.PLUGIN_CRASH";
		String EDIT_STYLES                      = "com.fbreader.action.EDIT_STYLES";
		String EDIT_BOOKMARK                    = "com.fbreader.action.EDIT_BOOKMARK";
		String SWITCH_YOTA_SCREEN               = "com.fbreader.action.SWITCH_YOTA_SCREEN";
                                            
		String PLUGIN_VIEW						= "com.fbreader.action.plugin.VIEW";
		String PLUGIN_KILL						= "com.fbreader.action.plugin.KILL";
		String PLUGIN_CONNECT_COVER_SERVICE		= "com.fbreader.action.plugin.CONNECT_COVER_SERVICE";
	}

	public interface Key {
		String BOOK                     = "fbreader.book";
		String BOOKMARK                 = "fbreader.bookmark";
		String PLUGIN                   = "fbreader.plugin";
		String TYPE                     = "fbreader.type";
	}

	public static Intent defaultInternalIntent(String action) {
		return internalIntent(action).addCategory(Intent.CATEGORY_DEFAULT);
	}

	public static Intent internalIntent(String action) {
		return new Intent(action).setPackage(DEFAULT_PACKAGE);
	}

	public static void putBookExtra(Intent intent, String key, Book book) {
		intent.putExtra(key, SerializerUtil.serialize(book));
	}

	public static void putBookExtra(Intent intent, Book book) {
		putBookExtra(intent, Key.BOOK, book);
	}

	public static <B extends AbstractBook> B getBookExtra(Intent intent, String key, AbstractSerializer.BookCreator<B> creator) {
		return SerializerUtil.deserializeBook(intent.getStringExtra(key), creator);
	}

	public static <B extends AbstractBook> B getBookExtra(Intent intent, AbstractSerializer.BookCreator<B> creator) {
		return getBookExtra(intent, Key.BOOK, creator);
	}

	public static void putBookmarkExtra(Intent intent, String key, Bookmark bookmark) {
		intent.putExtra(key, SerializerUtil.serialize(bookmark));
	}

	public static void putBookmarkExtra(Intent intent, Bookmark bookmark) {
		putBookmarkExtra(intent, Key.BOOKMARK, bookmark);
	}

	public static Bookmark getBookmarkExtra(Intent intent, String key) {
		return SerializerUtil.deserializeBookmark(intent.getStringExtra(key));
	}

	public static Bookmark getBookmarkExtra(Intent intent) {
		return getBookmarkExtra(intent, Key.BOOKMARK);
	}
}
