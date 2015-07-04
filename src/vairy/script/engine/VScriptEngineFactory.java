package vairy.script.engine;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.Reader;
import java.util.HashMap;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import vairy.script.msg.JMsgDialogFactory;
import vairy.script.robo.VMouseUtil;
import vairy.script.robo.VRobot;
import vairy.script.variable.JScriptVarFactory;

public class VScriptEngineFactory{
	public static Integer ROBO_MASK = 0x01;
	public static Integer MSG_MASK = 0x02;
	public static Integer VAR_MASK = 0x04;

	ScriptEngine localengine;

	private VScriptEngineFactory() {
	}

	public static ScriptEngine getEngineByName(String shortName, final Integer bindmask){
		ScriptEngine engineByName = new ScriptEngineManager().getEngineByName(shortName);
		bind(engineByName, bindmask);
		return engineByName;
	}

	private static void bind(final ScriptEngine target, final Integer bindmask){
		Bindings bindings = target.getBindings(ScriptContext.GLOBAL_SCOPE);

		bindings.put("util_out", System.out);

		if(ROBO_MASK == (ROBO_MASK & bindmask)){
			bindRobot(target);
		}

		if(MSG_MASK == (MSG_MASK & bindmask)){
			bindMsg(target);
		}

		if(VAR_MASK == (VAR_MASK & bindmask)){
			bindVar(target);
		}
	}

	private static void bindRobot(final ScriptEngine target){
		Bindings bindings = target.getBindings(ScriptContext.GLOBAL_SCOPE);

		bindings.put("util_mouse", new VMouseUtil());
		try {
			bindings.put("util_robo", new VRobot());
		} catch (AWTException e) {
		}


		bindings.put("M_1", InputEvent.BUTTON1_MASK);
		bindings.put("M_2", InputEvent.BUTTON2_MASK);
		bindings.put("M_3", InputEvent.BUTTON3_MASK);

		bindings.put("VK_A", KeyEvent.VK_A);
		bindings.put("VK_B", KeyEvent.VK_B);
		bindings.put("VK_C", KeyEvent.VK_C);
		bindings.put("VK_D", KeyEvent.VK_D);
		bindings.put("VK_E", KeyEvent.VK_E);
		bindings.put("VK_F", KeyEvent.VK_F);
		bindings.put("VK_G", KeyEvent.VK_G);
		bindings.put("VK_H", KeyEvent.VK_H);
		bindings.put("VK_I", KeyEvent.VK_I);
		bindings.put("VK_J", KeyEvent.VK_J);
		bindings.put("VK_K", KeyEvent.VK_K);
		bindings.put("VK_L", KeyEvent.VK_L);
		bindings.put("VK_M", KeyEvent.VK_M);
		bindings.put("VK_N", KeyEvent.VK_N);
		bindings.put("VK_O", KeyEvent.VK_O);
		bindings.put("VK_P", KeyEvent.VK_P);
		bindings.put("VK_Q", KeyEvent.VK_Q);
		bindings.put("VK_R", KeyEvent.VK_R);
		bindings.put("VK_S", KeyEvent.VK_S);
		bindings.put("VK_T", KeyEvent.VK_T);
		bindings.put("VK_U", KeyEvent.VK_U);
		bindings.put("VK_V", KeyEvent.VK_V);
		bindings.put("VK_W", KeyEvent.VK_W);
		bindings.put("VK_X", KeyEvent.VK_X);
		bindings.put("VK_Y", KeyEvent.VK_Y);
		bindings.put("VK_Z", KeyEvent.VK_Z);
		bindings.put("VK_0", KeyEvent.VK_0);
		bindings.put("VK_1", KeyEvent.VK_1);
		bindings.put("VK_2", KeyEvent.VK_2);
		bindings.put("VK_3", KeyEvent.VK_3);
		bindings.put("VK_4", KeyEvent.VK_4);
		bindings.put("VK_5", KeyEvent.VK_5);
		bindings.put("VK_6", KeyEvent.VK_6);
		bindings.put("VK_7", KeyEvent.VK_7);
		bindings.put("VK_8", KeyEvent.VK_8);
		bindings.put("VK_9", KeyEvent.VK_9);
		bindings.put("VK_ALT", KeyEvent.VK_ALT);
		bindings.put("VK_AMPERSAND", KeyEvent.VK_AMPERSAND);
		bindings.put("VK_ASTERISK", KeyEvent.VK_ASTERISK);
		bindings.put("VK_AT", KeyEvent.VK_AT);
		bindings.put("VK_BACK_QUOTE", KeyEvent.VK_BACK_QUOTE);
		bindings.put("VK_BACK_SLASH", KeyEvent.VK_BACK_SLASH);
		bindings.put("VK_BACK_SPACE", KeyEvent.VK_BACK_SPACE);
		bindings.put("VK_CIRCUMFLEX", KeyEvent.VK_CIRCUMFLEX);
		bindings.put("VK_CLOSE_BRACKET", KeyEvent.VK_CLOSE_BRACKET);
		bindings.put("VK_COLON", KeyEvent.VK_COLON);
		bindings.put("VK_COMMA", KeyEvent.VK_COMMA);
		bindings.put("VK_CONTEXT_MENU", KeyEvent.VK_CONTEXT_MENU);
		bindings.put("VK_CONTROL", KeyEvent.VK_CONTROL);
		bindings.put("VK_CONVERT", KeyEvent.VK_CONVERT);
		bindings.put("VK_COMPOSE", KeyEvent.VK_COMPOSE);
		bindings.put("VK_DECIMAL", KeyEvent.VK_DECIMAL);
		bindings.put("VK_DELETE", KeyEvent.VK_DELETE);
		bindings.put("VK_DIVIDE", KeyEvent.VK_DIVIDE);
		bindings.put("VK_DOLLAR", KeyEvent.VK_DOLLAR);
		bindings.put("VK_DOWN", KeyEvent.VK_DOWN);
		bindings.put("VK_UP", KeyEvent.VK_UP);
		bindings.put("VK_LEFT", KeyEvent.VK_LEFT);
		bindings.put("VK_RIGHT", KeyEvent.VK_RIGHT);
		bindings.put("VK_ENTER", KeyEvent.VK_ENTER);
		bindings.put("VK_EQUALS", KeyEvent.VK_EQUALS);
		bindings.put("VK_HOME", KeyEvent.VK_HOME);
		bindings.put("VK_END", KeyEvent.VK_END);
		bindings.put("VK_ESCAPE", KeyEvent.VK_ESCAPE);
		bindings.put("VK_EXCLAMATION_MARK", KeyEvent.VK_EXCLAMATION_MARK);
		bindings.put("VK_F1", KeyEvent.VK_F1);
		bindings.put("VK_F2", KeyEvent.VK_F2);
		bindings.put("VK_F3", KeyEvent.VK_F3);
		bindings.put("VK_F4", KeyEvent.VK_F4);
		bindings.put("VK_F5", KeyEvent.VK_F5);
		bindings.put("VK_F6", KeyEvent.VK_F6);
		bindings.put("VK_F7", KeyEvent.VK_F7);
		bindings.put("VK_F8", KeyEvent.VK_F8);
		bindings.put("VK_F9", KeyEvent.VK_F9);
		bindings.put("VK_F10", KeyEvent.VK_F10);
		bindings.put("VK_F11", KeyEvent.VK_F11);
		bindings.put("VK_F12", KeyEvent.VK_F12);
		bindings.put("VK_F13", KeyEvent.VK_F13);
		bindings.put("VK_F14", KeyEvent.VK_F14);
		bindings.put("VK_F15", KeyEvent.VK_F15);
		bindings.put("VK_F16", KeyEvent.VK_F16);
		bindings.put("VK_F17", KeyEvent.VK_F17);
		bindings.put("VK_F18", KeyEvent.VK_F18);
		bindings.put("VK_F19", KeyEvent.VK_F19);
		bindings.put("VK_F20", KeyEvent.VK_F20);
		bindings.put("VK_F21", KeyEvent.VK_F21);
		bindings.put("VK_F22", KeyEvent.VK_F22);
		bindings.put("VK_F23", KeyEvent.VK_F23);
		bindings.put("VK_F24", KeyEvent.VK_F24);
		bindings.put("VK_INPUT_METHOD_ON_OFF", KeyEvent.VK_INPUT_METHOD_ON_OFF);
		bindings.put("VK_INSERT", KeyEvent.VK_INSERT);
		bindings.put("VK_KANJI", KeyEvent.VK_KANJI);
		bindings.put("VK_KANA_LOCK", KeyEvent.VK_KANA_LOCK);
		bindings.put("VK_KATAKANA", KeyEvent.VK_KATAKANA);
		bindings.put("VK_HIRAGANA", KeyEvent.VK_HIRAGANA);
		bindings.put("VK_KP_DOWN", KeyEvent.VK_KP_DOWN);
		bindings.put("VK_KP_LEFT", KeyEvent.VK_KP_LEFT);
		bindings.put("VK_KP_RIGHT", KeyEvent.VK_KP_RIGHT);
		bindings.put("VK_KP_UP", KeyEvent.VK_KP_UP);
		bindings.put("VK_NONCONVERT", KeyEvent.VK_NONCONVERT);
		bindings.put("VK_NUM_LOCK", KeyEvent.VK_NUM_LOCK);
		bindings.put("VK_NUMBER_SIGN", KeyEvent.VK_NUMBER_SIGN);
		bindings.put("VK_NUMPAD0", KeyEvent.VK_NUMPAD0);
		bindings.put("VK_NUMPAD1", KeyEvent.VK_NUMPAD1);
		bindings.put("VK_NUMPAD2", KeyEvent.VK_NUMPAD2);
		bindings.put("VK_NUMPAD3", KeyEvent.VK_NUMPAD3);
		bindings.put("VK_NUMPAD4", KeyEvent.VK_NUMPAD4);
		bindings.put("VK_NUMPAD5", KeyEvent.VK_NUMPAD5);
		bindings.put("VK_NUMPAD6", KeyEvent.VK_NUMPAD6);
		bindings.put("VK_NUMPAD7", KeyEvent.VK_NUMPAD7);
		bindings.put("VK_NUMPAD8", KeyEvent.VK_NUMPAD8);
		bindings.put("VK_NUMPAD9", KeyEvent.VK_NUMPAD9);
		bindings.put("VK_OPEN_BRACKET", KeyEvent.VK_OPEN_BRACKET);
		bindings.put("VK_PAGE_DOWN", KeyEvent.VK_PAGE_DOWN);
		bindings.put("VK_PAGE_UP", KeyEvent.VK_PAGE_UP);
		bindings.put("VK_PERIOD", KeyEvent.VK_PERIOD);
		bindings.put("VK_PLUS", KeyEvent.VK_PLUS);
		bindings.put("VK_PRINTSCREEN", KeyEvent.VK_PRINTSCREEN);
		bindings.put("VK_RIGHT_PARENTHESIS", KeyEvent.VK_RIGHT_PARENTHESIS);
		bindings.put("VK_LEFT_PARENTHESIS", KeyEvent.VK_LEFT_PARENTHESIS);
		bindings.put("VK_SCROLL_LOCK", KeyEvent.VK_SCROLL_LOCK);
		bindings.put("VK_SEMICOLON", KeyEvent.VK_SEMICOLON);
		bindings.put("VK_SEPARATOR", KeyEvent.VK_SEPARATOR);
		bindings.put("VK_SHIFT", KeyEvent.VK_SHIFT);
		bindings.put("VK_SPACE", KeyEvent.VK_SPACE);
		bindings.put("VK_SUBTRACT", KeyEvent.VK_SUBTRACT);
		bindings.put("VK_TAB", KeyEvent.VK_TAB);
		bindings.put("VK_UNDERSCORE", KeyEvent.VK_UNDERSCORE);
	}

	private static void bindMsg(final ScriptEngine target){
		Bindings bindings = target.getBindings(ScriptContext.GLOBAL_SCOPE);

		bindings.put("util_msg", new JMsgDialogFactory());
	}

	private static void bindVar(final ScriptEngine target){
		Bindings bindings = target.getBindings(ScriptContext.GLOBAL_SCOPE);

		JScriptVarFactory varfactory = new JScriptVarFactory(target);
		bindings.put("util_vf", varfactory);
		bindings.put("util_sram", new HashMap<String, Object>());
	}
}
