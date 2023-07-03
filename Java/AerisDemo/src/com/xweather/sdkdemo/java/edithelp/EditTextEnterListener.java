package com.xweather.sdkdemo.java.edithelp; 

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Listener for the enter pressed on a keyboard for a EditText. 
 * @author bcollins
 *
 */
public class EditTextEnterListener implements OnKeyListener{
	/**
	 * Delegate to trigger method call when enter is pressed. 
	 */
	protected EnterPressedDelegate delegate; 
	
	
	/**
	 * Constructor for the listener
	 * @param delegate
	 */
	public EditTextEnterListener(EnterPressedDelegate delegate){
		this.delegate = delegate;
	}
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnKeyListener#onKey(android.view.View, int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            switch (keyCode)
            {
                case KeyEvent.KEYCODE_DPAD_CENTER:
                case KeyEvent.KEYCODE_ENTER:
                	InputMethodManager imm = (InputMethodManager)v.getContext()
                		.getSystemService(Context.INPUT_METHOD_SERVICE);
                	imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                	//Passes the button that was pressed to handle the event
                	delegate.onEnterPressed(v.getId(), (EditText)v); 
                    return true;
                default:
                    break;
            }
        }
        return false;
   }
	

}
