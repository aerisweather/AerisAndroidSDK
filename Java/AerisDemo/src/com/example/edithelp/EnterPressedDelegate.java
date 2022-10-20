package com.example.edithelp;

import android.widget.EditText;

/**
 * Delegate that aids in the EditTextEnterListners handling
 * @author bcollins
 *
 */
public interface EnterPressedDelegate {
	/**
	 * Handles the keyboard press for the EditText
	 * @param viewId - id of the EditText
	 * @param edit - the EditText for direction String grabs 
	 */
	void onEnterPressed(int viewId, EditText edit);
}
