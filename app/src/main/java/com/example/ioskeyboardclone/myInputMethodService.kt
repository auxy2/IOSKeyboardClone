package com.example.ioskeyboardclone

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.KeyboardView
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView.*
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View

class myInputMethodService : InputMethodService(), OnKeyboardActionListener {

    private var keyboardView: KeyboardView? = null
    private var keyboard: Keyboard? = null

    private var caps = false


    override fun onPress(p0: Int) {
    }

    override fun onRelease(p0: Int) {
    }

    override fun onKey(primaryCode: Int, keyCodes:  IntArray?) {
        var inputConnection = currentInputConnection
        if(inputConnection !== null ){
            when(primaryCode){
                Keyboard.KEYCODE_DELETE -> {
                    val selectedText = inputConnection.getSelectedText(0)
                    if(TextUtils.isEmpty(selectedText)){
                        inputConnection.deleteSurroundingText(1, 0)
                    }else{
                        inputConnection.commitText("", 1)
                    }
                    caps = !caps
                    keyboard!!.isShifted = caps
                    keyboardView!!.invalidateAllKeys()

                }
                Keyboard.KEYCODE_SHIFT -> {
                    caps = !caps
                    keyboard!!.isShifted = caps
                    keyboardView!!.invalidateAllKeys()
                }
                Keyboard.KEYCODE_DONE -> inputConnection.sendKeyEvent(
                    KeyEvent(
                          KeyEvent.ACTION_DOWN,
                          KeyEvent.KEYCODE_ENTER
                    )
                )
                else -> {
                    var code = primaryCode as Char
                    if(Character.isLetter(code) && caps){
                        code = Character.toUpperCase(code)
                    }
                    inputConnection.commitText(code.toString(), 1)

                }
            }

        }

    }

    override fun onText(p0: CharSequence?) {
    }

    override fun swipeLeft() {
    }

    override fun swipeRight() {
    }

    override fun swipeDown() {
    }

    override fun swipeUp() {
    }

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(com.example.ioskeyboardclone.R.layout.keyboard_view, null) as KeyboardView
        keyboard = Keyboard(this, com.example.ioskeyboardclone.R.xml.key_layout)
        keyboardView!!.keyboard = keyboard
        keyboardView!!.setOnKeyboardActionListener(this)
        return keyboardView!!
    }

}