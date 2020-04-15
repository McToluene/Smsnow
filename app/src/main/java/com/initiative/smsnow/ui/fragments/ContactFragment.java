package com.initiative.smsnow.ui.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.initiative.smsnow.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {

  private final static String[] FROM_COLUMNS = {ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
  private final static int[] TO_IDS = {android.R.id.text1};
  private static final String[] PROJECTION = {
          ContactsContract.Contacts._ID,
          ContactsContract.Contacts.LOOKUP_KEY,
          ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
  };

  private static final int CONTACT_ID_INDEX = 0;
  private static final int CONTACT_KEY_INDEX = 1;
  private static final String SELECTION = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + "LIKE ?";
  private String searchString;
  private String[] selectionArgs = {searchString};

  private long contactId;
  private String contactKey;
  private Uri contactUri;
  private SimpleCursorAdapter cursorAdapter;

  public ContactFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_contact, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    ListView contactList = (ListView) getActivity().findViewById(R.layout.contact_list_view);

    cursorAdapter = new SimpleCursorAdapter(
            getActivity(),
            R.layout.contact_list_item,
            null,
            FROM_COLUMNS, TO_IDS,
            0);
    contactList.setAdapter(cursorAdapter);
    contactList.setOnItemClickListener(this);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getLoaderManager().initLoader(0, null, this);
  }

  @NonNull
  @Override
  public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
    selectionArgs[0] = "%" + searchString + "%";
    return new CursorLoader(
            Objects.requireNonNull(getActivity()),
            ContactsContract.Contacts.CONTENT_URI,
            PROJECTION,
            SELECTION,
            selectionArgs,
            null
    );
  }

  @Override
  public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
    cursorAdapter.swapCursor(data);
  }

  @Override
  public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    cursorAdapter.swapCursor(null);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    Cursor cursor = ((SimpleCursorAdapter) parent.getAdapter()).getCursor();
    cursor.moveToPosition(position);
    contactId = cursor.getLong(CONTACT_ID_INDEX);
    contactKey = cursor.getString(CONTACT_KEY_INDEX);

    contactUri = ContactsContract.Contacts.getLookupUri(contactId, contactKey);

  }

}
