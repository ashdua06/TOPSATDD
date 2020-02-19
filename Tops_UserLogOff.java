package com.optum.topsuat.pages;

import java.io.File;
import java.net.URI;

import com.hp.lft.sdk.Desktop;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.SDK;
import com.hp.lft.sdk.te.Field;
import com.hp.lft.sdk.te.FieldDescription;
import com.hp.lft.sdk.te.Keys;
import com.hp.lft.sdk.te.Protocol;
import com.hp.lft.sdk.te.Screen;
import com.hp.lft.sdk.te.ScreenDescription;
import com.hp.lft.sdk.te.Window;
import com.hp.lft.sdk.te.WindowDescription;
import com.optum.topsuat.functionlib.Mainframe_GlobalFunctionLib;
import com.optum.topsuat.utils.TestNGHelper;


public class Tops_UserLogOff {
	

public static void screenclear() throws Exception{
	Window teWindow=Desktop.describe(Window.class, new WindowDescription.Builder().protocol(Protocol.PR3270).build());
	Screen teScreen = teWindow.describe(Screen.class, new ScreenDescription.Builder().size(24,80).build());
	teScreen.sendTEKeys(Keys.CLEAR);
	Thread.sleep(2000);
	teScreen.sendTEKeys(Keys.CLEAR);
	Thread.sleep(2000);
	teScreen.setText("clear-transid");
	teScreen.sendTEKeys(Keys.ENTER);
	Thread.sleep(1000);
	teScreen.setText("off");
	teScreen.sendTEKeys(Keys.ENTER);
	Thread.sleep(1000);	
	boolean regionnameexist = teScreen.describe(Field.class, new FieldDescription.Builder().startPosition(3,1).build()).exists();
	TestNGHelper.assertEqual(true,regionnameexist , "yes","User Logout successful");
}



public static void CloseTeWindow() throws Exception{
	
	com.hp.lft.sdk.stdwin.Window wnd = Desktop.describe(com.hp.lft.sdk.stdwin.Window.class, new com.hp.lft.sdk.stdwin.WindowDescription.Builder()
	.windowClassRegExp("PCSWS:Main:00400000").windowTitleRegExp(" [24*80]").childWindow(false).ownedWindow(false).build());
	wnd.highlight();
	wnd.close();
}

}
