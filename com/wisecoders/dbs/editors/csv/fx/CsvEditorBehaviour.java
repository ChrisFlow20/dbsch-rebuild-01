package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.sys.fx.ControlBehaviour;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class CsvEditorBehaviour extends ControlBehaviour {
  final CsvEditorSkin a;
  
  public CsvEditorBehaviour(CsvEditorSkin paramCsvEditorSkin) {
    this.a = paramCsvEditorSkin;
    put((K)new KeyCodeCombination(KeyCode.R, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> paramCsvEditorSkin.e()));
    put((K)new KeyCodeCombination(KeyCode.S, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> ((CsvEditor)paramCsvEditorSkin.getSkinnable()).saveFile()));
    put((K)new KeyCodeCombination(KeyCode.S, new KeyCombination.Modifier[] { KeyCombination.SHIFT_DOWN }), (V)(paramKeyEvent -> ((CsvEditor)paramCsvEditorSkin.getSkinnable()).saveAsFile()));
    put((K)new KeyCodeCombination(KeyCode.F, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> paramCsvEditorSkin.b()));
    put((K)new KeyCodeCombination(KeyCode.C, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> paramCsvEditorSkin.c()));
    put((K)new KeyCodeCombination(KeyCode.V, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> paramCsvEditorSkin.d()));
    ((CsvEditor)paramCsvEditorSkin.getSkinnable()).addEventHandler(KeyEvent.KEY_PRESSED, this::a);
  }
}
