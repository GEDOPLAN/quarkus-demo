/*
 * Copyright (c) 2011-2023 PrimeFaces Extensions
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.primefaces.showcase.view.input;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.extensions.model.monaco.MonacoDiffEditorModel;
import org.primefaces.extensions.model.monacoeditor.DiffEditorOptions;
import org.primefaces.extensions.model.monacoeditor.ELanguage;
import org.primefaces.extensions.model.monacoeditor.ETheme;
import org.primefaces.extensions.model.monacoeditor.EditorOptions;
import org.primefaces.extensions.model.monacoeditor.EditorStandaloneTheme;
import org.primefaces.showcase.util.MonacoEditorSettings;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.abbreviate;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * MonacoEditorView
 *
 * @author Andre Wachsmuth
 */
@Named
@ViewScoped
public class MonacoEditorView implements Serializable {
  private static final long serialVersionUID = 20210216L;

  private static final String CUSTOM_CODE_EXTENDER_CODE = "custom_code.extender.code.";
  private static final String CUSTOM_CODE_EXTENDER_DIFF = "custom_code.extender.diff.";
  private static final String INLINE = "inline";
  private static final String CODE = "code";
  private static final String MY_CUSTOM_THEME = "MyCustomTheme";

  private Map<String, EditorStandaloneTheme> customThemes;

  private EditorOptions editorOptions;
  private EditorOptions editorOptionsFramed;
  private EditorOptions editorOptionsExtender;
  private EditorOptions editorOptionsCss;

  private DiffEditorOptions editorOptionsDiff;
  private DiffEditorOptions editorOptionsFramedDiff;
  private DiffEditorOptions editorOptionsExtenderDiff;

  private ELanguage editorLangDiff;
  private ELanguage editorLangFramedDiff;
  private ELanguage editorLangExtenderDiff;

  private transient ResourceBundle examples;

  private List<String> languages;

  private String extenderError;

  private String extenderExample;
  private List<SelectItem> extenderExamples;
  private List<SelectItem> extenderExamplesDiff;
  private String extenderInfo;
  private String extenderName;

  private List<String> themes;

  private String type;
  private String mode;

  private Locale locale;
  private Locale localeFramed;

  private List<Locale> locales;

  private String value;
  private String valueFramed;
  private String valueExtender;
  private String valueCss;

  private MonacoDiffEditorModel valueDiff;
  private MonacoDiffEditorModel valueFramedDiff;

  private boolean required = false;
  private boolean requiredFramed = false;

  private boolean disabled = false;
  private boolean disabledFramed = false;

  private boolean readOnly = false;
  private boolean readOnlyFramed = false;

  private boolean originalRequired = false;
  private boolean originalRequiredFramed = false;

  private boolean originalDisabled = true;
  private boolean originalDisabledFramed = true;

  private boolean originalReadOnly = false;
  private boolean originalReadOnlyFramed = false;

  @PostConstruct
  protected void init() {
    this.examples = ResourceBundle.getBundle("monaco-examples");

    this.editorOptions = new EditorOptions();
    this.editorOptions.setTheme(ETheme.VS);
    this.editorOptions.setFontSize(12);

    this.editorOptionsDiff = new DiffEditorOptions();
    this.editorOptionsDiff.setTheme(ETheme.VS);
    this.editorOptionsDiff.setFontSize(12);
    this.editorOptionsDiff.setRenderSideBySide(true);

    this.editorOptionsFramed = new EditorOptions();
    this.editorOptionsFramed.setTheme(ETheme.VS);
    this.editorOptionsFramed.setFontSize(12);

    this.editorOptionsFramedDiff = new DiffEditorOptions();
    this.editorOptionsFramedDiff.setTheme(ETheme.VS);
    this.editorOptionsFramedDiff.setFontSize(12);
    this.editorOptionsFramedDiff.setRenderSideBySide(true);

    this.extenderExamples = MonacoEditorSettings.createEditorExamples();
    this.extenderExamplesDiff = MonacoEditorSettings.createEditorExamplesDiff();

    this.editorOptionsExtender = MonacoEditorSettings.createEditorOptionsExtender();
    this.editorOptionsExtenderDiff = MonacoEditorSettings.createEditorOptionsExtenderDiff();
    this.editorOptionsExtenderDiff.setRenderSideBySide(true);
    this.editorLangExtenderDiff = ELanguage.JAVASCRIPT;

    this.editorOptionsCss = MonacoEditorSettings.createEditorOptionsCss();

    this.type = INLINE;

    this.languages = Arrays.stream(ELanguage.values()).map(ELanguage::toString).sorted().collect(toList());
    this.themes = Arrays.stream(ETheme.values()).map(ETheme::toString).sorted().collect(toList());
    this.locales = MonacoEditorSettings.getBuiltInLocales();
  }

  /**
   * @return Custom styling themes that should be made available in the Monaco code editor.
   */
  public Map<String, EditorStandaloneTheme> getCustomThemes() {
    return this.customThemes;
  }

  /**
   * @return Options to apply to the Monaco code editor.
   */
  public EditorOptions getEditorOptions() {
    return this.editorOptions;
  }

  /**
   * @return Options to apply to the Monaco diff editor.
   */
  public DiffEditorOptions getEditorOptionsDiff() {
    return this.editorOptionsDiff;
  }

  /**
   * @return Options to apply to the framed Monaco code editor.
   */
  public EditorOptions getEditorOptionsFramed() {
    return this.editorOptionsFramed;
  }

  /**
   * @return Options to apply to the Monaco editor for editing the custom CSS for the extender showcase.
   */
  public EditorOptions getEditorOptionsCss() {
    return this.editorOptionsCss;
  }

  /**
   * @return Options to apply to the framed Monaco code editor.
   */
  public DiffEditorOptions getEditorOptionsFramedDiff() {
    return this.editorOptionsFramedDiff;
  }

  /**
   * @return Options to apply to the Monaco editor for editing the custom extender.
   */
  public EditorOptions getEditorOptionsExtender() {
    return this.editorOptionsExtender;
  }

  /**
   * @return Options to apply to the Monaco editor for editing the custom extender.
   */
  public DiffEditorOptions getEditorOptionsExtenderDiff() {
    return this.editorOptionsExtenderDiff;
  }

  /**
   * @return The error that was throw by the custom extender code in the extender showcase.
   */
  public String getExtenderError() {
    return this.extenderError;
  }

  /**
   * @return The currently selected example for the extender showcase.
   */
  public String getExtenderExample() {
    return this.extenderExample;
  }

  /**
   * @param extenderExample The currently selected example for the extender showcase.
   */
  public void setExtenderExample(final String extenderExample) {
    this.extenderExample = extenderExample;
  }

  /***
   * @return All examples available in the extender showcase.
   */
  public List<SelectItem> getExtenderExamples() {
    return this.extenderExamples;
  }

  /***
   * @return All examples available in the extender showcase.
   */
  public List<SelectItem> getExtenderExamplesDiff() {
    return this.extenderExamplesDiff;
  }

  /**
   * @return HTML string with additional info about the loaded extender example.
   */
  public String getExtenderInfo() {
    return this.extenderInfo;
  }

  /**
   * @return Name of the loaded extender example.
   */
  public String getExtenderName() {
    return this.extenderName;
  }

  /**
   * @return The currently selected code language for the Monaco editor.
   */
  public String getLanguage() {
    if (CODE.equals(this.mode)) {
      return this.editorOptions.getLanguage();
    } else {
      return this.editorLangDiff.toString();
    }
  }

  /**
   * @param language The currently selected code language for the monaco editor.
   */
  public void setLanguage(final String language) {
    if (CODE.equals(this.mode)) {
      if (isEmpty(language)) {
        this.editorOptions.setLanguage(ELanguage.JAVASCRIPT);
      } else {
        this.editorOptions.setLanguage(language);
      }
    } else {
      if (isEmpty(language)) {
        this.editorLangDiff = ELanguage.JAVASCRIPT;
      } else {
        this.editorLangDiff = ELanguage.parseString(language);
      }
    }
  }

  /**
   * @return The currently selected code language for the Monaco editor for the extender example.
   */
  public String getLanguageExtender() {
    if (CODE.equals(this.mode)) {
      return this.editorOptionsExtender.getLanguage();
    } else {
      return this.editorLangExtenderDiff.toString();
    }
  }

  /**
   * @return The currently selected code language for the framed Monaco editor.
   */
  public String getLanguageFramed() {
    if (CODE.equals(this.mode)) {
      return this.editorOptionsFramed.getLanguage();
    } else {
      return this.editorLangFramedDiff.toString();
    }
  }

  /**
   * @param language The currently selected code language for the framed Monaco editor.
   */
  public void setLanguageFramed(final String language) {
    if (CODE.equals(this.mode)) {
      if (isEmpty(language)) {
        this.editorOptionsFramed.setLanguage(ELanguage.JAVASCRIPT);
      } else {
        this.editorOptionsFramed.setLanguage(language);
      }
    } else {
      if (isEmpty(language)) {
        this.editorLangFramedDiff = ELanguage.JAVASCRIPT;
      } else {
        this.editorLangFramedDiff = ELanguage.parseString(language);
      }
    }
  }

  /**
   * @return All available languages for the Monaco editor.
   */
  public List<String> getLanguages() {
    return this.languages;
  }

  /**
   * @return The current color theme of the editor.
   */
  public String getTheme() {
    if (CODE.equals(this.mode)) {
      return this.editorOptions.getTheme();
    } else {
      return this.editorOptionsDiff.getTheme();
    }
  }

  /**
   * @param theme The current color theme of the editor.
   */
  public void setTheme(final String theme) {
    if (CODE.equals(this.mode)) {
      if (isEmpty(theme)) {
        this.editorOptions.setTheme(ETheme.VS);
      } else {
        this.editorOptions.setTheme(theme);
      }
    } else {
      if (isEmpty(theme)) {
        this.editorOptionsDiff.setTheme(ETheme.VS);
      } else {
        this.editorOptionsDiff.setTheme(theme);
      }
    }
  }

  /**
   * @return The current color theme of the framed editor.
   */
  public String getThemeFramed() {
    if (CODE.equals(this.mode)) {
      return this.editorOptionsFramed.getTheme();
    } else {
      return this.editorOptionsFramedDiff.getTheme();
    }
  }

  /**
   * @param theme The current color theme of the framed editor.
   */
  public void setThemeFramed(final String theme) {
    if (CODE.equals(this.mode)) {
      if (isEmpty(theme)) {
        this.editorOptionsFramed.setTheme(ETheme.VS);
      } else {
        this.editorOptionsFramed.setTheme(theme);
      }
    } else {
      if (isEmpty(theme)) {
        this.editorOptionsFramedDiff.setTheme(ETheme.VS);
      } else {
        this.editorOptionsFramedDiff.setTheme(theme);
      }
    }
  }

  /**
   * @return All available themes for the editor.
   */
  public List<String> getThemes() {
    return this.themes;
  }

  /**
   * @return Whether to show the inline or framed version of the Monaco code editor.
   */
  public String getType() {
    return this.type;
  }

  /**
   * @param type Whether to show the inline or framed version of the Monaco code editor.
   */
  public void setType(final String type) {
    this.type = type;
  }

  /**
   * @return The current UI language used in the Monaco code editor.
   */
  public Locale getLocale() {
    return this.locale;
  }

  /**
   * @param locale The current UI language used in the Monaco code editor.
   */
  public void setLocale(final Locale locale) {
    this.locale = locale;
  }

  /**
   * @return The current UI language used in the framed Monaco code editor.
   */
  public Locale getLocaleFramed() {
    return this.localeFramed;
  }

  /**
   * @param localeFramed The current UI language used in the framed Monaco code editor.
   */
  public void setLocaleFramed(final Locale localeFramed) {
    this.localeFramed = localeFramed;
  }

  /**
   * @return A list of all built-in UI languages for the Monaco code editor.
   */
  public List<Locale> getLocales() {
    return this.locales;
  }

  /**
   * @return The code currently being edited by the editor.
   */
  public String getValue() {
    return this.value;
  }

  /**
   * @param value The code currently being edited by the editor.
   */
  public void setValue(final String value) {
    this.value = value;
  }

  /**
   * @return The code currently being edited by the editor.
   */
  public MonacoDiffEditorModel getValueDiff() {
    return this.valueDiff;
  }

  /**
   * @param valueDiff The code currently being edited by the editor.
   */
  public void setValueDiff(final MonacoDiffEditorModel valueDiff) {
    this.valueDiff = valueDiff;
  }

  /**
   * @return Code for the Monaco extender that can be edited on the extender showcase page.
   */
  public String getValueExtender() {
    return this.valueExtender;
  }

  /**
   * @param valueExtender Code for the Monaco extender that can be edited on the extender showcase page.
   */
  public void setValueExtender(final String valueExtender) {
    this.valueExtender = valueExtender;
  }

  /**
   * @return The code currently being edited by the framed editor.
   */
  public String getValueFramed() {
    return this.valueFramed;
  }

  /**
   * @param valueFramed The code currently being edited by the framed editor.
   */
  public void setValueFramed(final String valueFramed) {
    this.valueFramed = valueFramed;
  }

  /**
   * @return The code currently being edited by the framed editor.
   */
  public MonacoDiffEditorModel getValueFramedDiff() {
    return this.valueFramedDiff;
  }

  /**
   * @param valueFramedDiff The code currently being edited by the framed editor.
   */
  public void setValueFramedDiff(final MonacoDiffEditorModel valueFramedDiff) {
    this.valueFramedDiff = valueFramedDiff;
  }

  /**
   * @return The custom CSS for the extender showcase.
   */
  public String getValueCss() {
    return this.valueCss;
  }

  /**
   * @param valueCss The custom CSS for the extender showcase.
   */
  public void setValueCss(final String valueCss) {
    this.valueCss = valueCss;
  }

  /**
   * @return Whether the original editor (left side of the diff editor) is disabled.
   */
  public boolean isOriginalDisabled() {
    return this.originalDisabled;
  }

  /**
   * @param originalDisabled Whether the original editor (left side of the diff editor) is disabled.
   */
  public void setOriginalDisabled(final boolean originalDisabled) {
    this.originalDisabled = originalDisabled;
  }

  /**
   * @return Whether the original editor (left side of the diff editor) is disabled.
   */
  public boolean isOriginalDisabledFramed() {
    return this.originalDisabledFramed;
  }

  /**
   * @param originalDisabledFramed Whether the original editor (left side of the diff editor) is disabled.
   */
  public void setOriginalDisabledFramed(final boolean originalDisabledFramed) {
    this.originalDisabledFramed = originalDisabledFramed;
  }

  /**
   * @return Whether the original editor (left side of the diff editor) is read-only.
   */
  public boolean isOriginalReadOnly() {
    return this.originalReadOnly;
  }

  /**
   * @param originalReadOnly Whether the original editor (left side of the diff editor) is read-only.
   */
  public void setOriginalReadOnly(final boolean originalReadOnly) {
    this.originalReadOnly = originalReadOnly;
  }

  /**
   * @return Whether the original framed editor (left side of the diff editor) is read-only.
   */
  public boolean isOriginalReadOnlyFramed() {
    return this.originalReadOnlyFramed;
  }

  /**
   * @param originalReadOnlyFramed Whether the original framed editor (left side of the diff editor) is read-only.
   */
  public void setOriginalReadOnlyFramed(final boolean originalReadOnlyFramed) {
    this.originalReadOnlyFramed = originalReadOnlyFramed;
  }

  /**
   * @return Whether the editor is disabled and does not accept submitted values.
   */
  public boolean isDisabled() {
    return this.disabled;
  }

  /**
   * @param disabled Whether the editor is disabled and does not accept submitted values.
   */
  public void setDisabled(final boolean disabled) {
    this.disabled = disabled;
  }

  /**
   * @return Whether the framed editor is disabled and does not accept submitted values.
   */
  public boolean isDisabledFramed() {
    return this.disabledFramed;
  }

  /**
   * @param disabledFramed Whether the framed editor is disabled and does not accept submitted values.
   */
  public void setDisabledFramed(final boolean disabledFramed) {
    this.disabledFramed = disabledFramed;
  }

  /**
   * @return Whether the modified editor (right side of the diff editor) is read-only.
   */
  public boolean isReadOnly() {
    return this.readOnly;
  }

  /**
   * @param readOnly Whether the modified editor (right side of the diff editor) is read-only.
   */
  public void setReadOnly(final boolean readOnly) {
    this.readOnly = readOnly;
  }

  /**
   * @return Whether the modified editor (right side of the diff editor) is read-only.
   */
  public boolean isReadOnlyFramed() {
    return this.readOnlyFramed;
  }

  /**
   * @param readOnlyFramed Whether the modified editor (right side of the diff editor) is read-only.
   */
  public void setReadOnlyFramed(final boolean readOnlyFramed) {
    this.readOnlyFramed = readOnlyFramed;
  }

  /**
   * @return Whether the editor is required to have a value.
   */
  public boolean isRequired() {
    return this.required;
  }

  /**
   * @param required Whether the editor is required to have a value.
   */
  public void setRequired(final boolean required) {
    this.required = required;
  }

  /**
   * @return Whether the framed editor is required to have a value.
   */
  public boolean isRequiredFramed() {
    return this.requiredFramed;
  }

  /**
   * @param requiredFramed Whether the framed editor is required to have a value.
   */
  public void setRequiredFramed(final boolean requiredFramed) {
    this.requiredFramed = requiredFramed;
  }

  /**
   * @return Whether the original editor is required to have a value.
   */
  public boolean isOriginalRequired() {
    return this.originalRequired;
  }

  /**
   * @param originalRequired Whether the original editor is required to have a value.
   */
  public void setOriginalRequired(final boolean originalRequired) {
    this.originalRequired = originalRequired;
  }

  /**
   * @return Whether the original framed editor is required to have a value.
   */
  public boolean isOriginalRequiredFramed() {
    return this.originalRequiredFramed;
  }

  /**
   * @param originalRequiredFramed Whether the original framed editor is required to have a value.
   */
  public void setOriginalRequiredFramed(final boolean originalRequiredFramed) {
    this.originalRequiredFramed = originalRequiredFramed;
  }

  /**
   * @return Whether to render the diff side-by-side in two editors or inline in one editor.
   */
  public boolean isRenderSideBySide() {
    if (INLINE.equals(this.type)) {
      return this.editorOptionsDiff.isRenderSideBySide();
    } else {
      return this.editorOptionsFramedDiff.isRenderSideBySide();
    }
  }

  /**
   * @param renderSideBySide Whether to render the diff side-by-side in two editors or inline in one editor.
   */
  public void setRenderSideBySide(final boolean renderSideBySide) {
    if (INLINE.equals(this.type)) {
      this.editorOptionsDiff.setRenderSideBySide(renderSideBySide);
    } else {
      this.editorOptionsFramedDiff.setRenderSideBySide(renderSideBySide);
    }
  }

  /**
   * @param mode Whether to show the code or diff version of the Monaco code editor.
   */
  public void setTypeMode(final String mode) {
    this.mode = mode;
  }

  /**
   * Callback for when the code language was changed. Loads the code sample for that language.
   */
  public void onLanguageChange() {
    if (INLINE.equals(this.type)) {
      loadDefaultCode();
    } else {
      loadDefaultCodeFramed();
    }
  }

  /**
   * Callback when the selected example was changed in the extender showcase.
   */
  public void onExtenderExampleChange() {
    loadExtenderExample(this.extenderExample);
  }

  /**
   * Callback when the run button was pressed in the extender showcase. Resets the error message.
   */
  public void onMonacoExtenderRun() {
    this.extenderError = null;
  }

  /**
   * Remote command listener when an error occurred in the custom extender entered by the user in the extender showcase.
   */
  public void onMonacoExtenderError() {
    final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
      .getRequest();
    this.extenderError = req.getParameter("monacoExtenderError");
  }

  /**
   * Loads the default code sample for the current language, if available.
   */
  private void loadDefaultCode() {
    final String language = CODE.equals(this.mode) ? this.editorOptions.getLanguage() : this.editorLangDiff.toString();
    final String propertyKey = "sample_code." + language;
    loadCode(propertyKey);
  }

  /**
   * Loads the default code sample for the current language, if available.
   */
  private void loadDefaultCodeFramed() {
    final String language = CODE.equals(this.mode) ? this.editorOptionsFramed.getLanguage() : this.editorLangFramedDiff.toString();
    final String propertyKey = "sample_code." + language;
    loadCodeFramed(propertyKey);
  }

  /**
   * Loads the code with the given key from the properties file into the code editor.
   *
   * @param propertyKey Key at which the code is stored in {@code monaco-examples.properties}
   */
  private void loadCode(final String propertyKey) {
    if (CODE.equals(this.mode)) {
      try {
        this.value = propertyKey != null ? this.examples.getString(propertyKey) : "";
      } catch (final MissingResourceException e) {
        this.value = "";
      }
    } else {
      try {
        final String original = propertyKey != null ? this.examples.getString(propertyKey) : "";
        final String modified = MonacoEditorSettings.deriveModifiedContent(original);
        this.valueDiff = new MonacoDiffEditorModel(original, modified);
      } catch (final MissingResourceException e) {
        this.valueDiff = MonacoDiffEditorModel.empty();
      }
    }
  }

  /**
   * Loads the code with the given key from the properties file into the extender editor.
   *
   * @param propertyKey Key at which the code is stored in {@code monaco-examples.properties}
   */
  private void loadCodeExtender(final String propertyKey) {
    try {
      this.valueExtender = propertyKey != null ? this.examples.getString(propertyKey) : "";
    } catch (final MissingResourceException e) {
      this.valueExtender = "";
    }
  }

  /**
   * Loads the code with the given key from the properties file into the framed editor.
   *
   * @param propertyKey Key at which the code is stored in {@code monaco-examples.properties}
   */
  private void loadCodeFramed(final String propertyKey) {
    if (CODE.equals(this.mode)) {
      try {
        this.valueFramed = propertyKey != null ? this.examples.getString(propertyKey) : "";
      } catch (final MissingResourceException e) {
        this.valueFramed = "";
      }
    } else {
      try {
        final String original = propertyKey != null ? this.examples.getString(propertyKey) : "";
        final String modified = MonacoEditorSettings.deriveModifiedContent(original);
        this.valueFramedDiff = new MonacoDiffEditorModel(original, modified);
      } catch (final MissingResourceException e) {
        this.valueFramedDiff = MonacoDiffEditorModel.empty();
      }
    }
  }

  /**
   * Loads the info about the extender example with the given key from the properties file.
   *
   * @param propertyKey Key at which the info is stored in {@code monaco-examples.properties}
   */
  private void loadExtenderInfo(final String propertyKey) {
    try {
      this.extenderInfo = propertyKey != null ? this.examples.getString(propertyKey) : "";
    } catch (final MissingResourceException e) {
      this.extenderInfo = "No info available for this extender. Check the ?example=... URL parameter.";
    }
  }

  /**
   * Loads the CSS for the extender example with the given key from the properties file.
   *
   * @param propertyKey Key at which the CSS is stored in {@code monaco-examples.properties}
   */
  private void loadExtenderCss(final String propertyKey) {
    try {
      this.valueCss = propertyKey != null ? this.examples.getString(propertyKey) : "";
    } catch (final MissingResourceException e) {
      this.valueCss = "";
    }
  }

  /**
   * Loads the code language for the extender example with the given key from the properties file.
   *
   * @param propertyKey Key at which the code language is stored in {@code monaco-examples.properties}
   */
  private void loadExtenderLanguage(final String propertyKey) {
    try {
      String language = propertyKey != null ? this.examples.getString(propertyKey) : "";
      language = StringUtils.defaultIfBlank(language, "plaintext");
      if (CODE.equals(this.mode)) {
        try {
          this.editorOptions.setLanguage(language);
        } catch (final MissingResourceException e) {
          this.editorOptions.setLanguage(ELanguage.TYPESCRIPT);
        }
      } else {
        try {
          this.editorLangDiff = ELanguage.parseString(language);
        } catch (final MissingResourceException e) {
          this.editorLangDiff = ELanguage.TYPESCRIPT;
        }
      }
    } catch (final MissingResourceException e) {
      if (CODE.equals(this.mode)) {
        this.editorOptions.setLanguage(ELanguage.PLAINTEXT);
      } else {
        this.editorLangDiff = ELanguage.PLAINTEXT;
      }
    }
  }

  /**
   * Loads the name of the extender example with the given key from the properties file.
   *
   * @param propertyKey Key at which the name is stored in {@code monaco-examples.properties}
   */
  private void loadExtenderName(final String propertyKey) {
    try {
      this.extenderName = propertyKey != null ? this.examples.getString(propertyKey) : "";
    } catch (final MissingResourceException e) {
      this.extenderName = "Unknown extender";
    }
  }

  /**
   * Loads the given example for the extender showcase.
   *
   * @param key Key of the extender example.
   */
  public void loadExtenderExample(final String key) {
    loadCode(getExtenderExampleKey(key, "sample"));
    loadCodeExtender(getExtenderExampleKey(key, "script"));
    loadExtenderName(getExtenderExampleKey(key, "name"));
    loadExtenderInfo(getExtenderExampleKey(key, "info"));
    loadExtenderCss(getExtenderExampleKey(key, "css"));
    loadExtenderLanguage(getExtenderExampleKey(key, "language"));
  }

  /**
   * Demo submit to show that the data can be transferred to the server. Shows a message with the current code.
   */
  public void submitContent() {
    if (CODE.equals(this.mode)) {
      final String content = INLINE.equals(this.type) ? this.value : this.valueFramed;
      final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
        "MonacoEditor content: " + abbreviate(content, "...", 300));
      FacesContext.getCurrentInstance().addMessage(null, msg);
    } else {
      final MonacoDiffEditorModel value = INLINE.equals(this.type) ? this.valueDiff : this.valueFramedDiff;
      final String left = abbreviate(value.getOriginalValue(), "...", 150);
      final String right = abbreviate(value.getModifiedValue(), "...", 150);
      final FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
        "MonacoEditor content original: " + left);
      final FacesMessage msg2 = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
        "MonacoEditor content modified: " + right);
      FacesContext.getCurrentInstance().addMessage(null, msg1);
      FacesContext.getCurrentInstance().addMessage(null, msg2);
    }
  }

  /**
   * Set the initial values for the {@code basicUsage} showcase.
   *
   * @param mode Whether to use the code of diff editor.
   */
  public void initBasicUsage(final String mode) {
    this.mode = mode;
    if (CODE.equals(mode)) {
      this.editorOptions.setLanguage(ELanguage.TYPESCRIPT);
      this.editorOptionsFramed.setLanguage(ELanguage.TYPESCRIPT);
    } else {
      this.editorLangDiff = ELanguage.TYPESCRIPT;
      this.editorLangFramedDiff = ELanguage.TYPESCRIPT;
    }
    loadDefaultCode();
    loadDefaultCodeFramed();
  }

  /**
   * Set the initial values for the {@code customTheme} showcase.
   *
   * @param mode Whether to use the code of diff editor.
   */
  public void initCustomTheme(final String mode) {
    this.mode = mode;
    this.customThemes = singletonMap(MY_CUSTOM_THEME, MonacoEditorSettings.createDemoCustomTheme());
    if (CODE.equals(mode)) {
      this.editorOptions.setLanguage(ELanguage.HTML);
      this.editorOptions.setTheme(MY_CUSTOM_THEME);
      this.editorOptionsFramed.setLanguage(ELanguage.HTML);
      this.editorOptionsFramed.setTheme(MY_CUSTOM_THEME);
    } else {
      this.editorLangDiff = ELanguage.HTML;
      this.editorOptionsDiff.setTheme(MY_CUSTOM_THEME);
      this.editorLangFramedDiff = ELanguage.HTML;
      this.editorOptionsFramedDiff.setTheme(MY_CUSTOM_THEME);
    }
    loadCode("custom_code.htmlCustomTheme");
    loadCodeFramed("custom_code.htmlCustomTheme");
  }

  /**
   * Set the initial values for the {@code customLocalization} showcase.
   *
   * @param mode Whether to use the code of diff editor.
   */
  public void initCustomLocalization(final String mode) {
    this.mode = mode;
    if (CODE.equals(mode)) {
      this.editorOptions.setLanguage(ELanguage.TYPESCRIPT);
      this.editorOptionsFramed.setLanguage(ELanguage.TYPESCRIPT);
    } else {
      this.editorLangDiff = ELanguage.TYPESCRIPT;
      this.editorLangFramedDiff = ELanguage.TYPESCRIPT;
    }
    loadDefaultCode();
    loadDefaultCodeFramed();
  }

  /**
   * Set the initial values for the {@code events} showcase.
   *
   * @param mode Whether to use the code of diff editor.
   */
  public void initEvents(final String mode) {
    this.mode = mode;
    if (CODE.equals(mode)) {
      this.editorOptions.setLanguage(ELanguage.TYPESCRIPT);
      this.editorOptionsFramed.setLanguage(ELanguage.TYPESCRIPT);
    } else {
      this.editorLangDiff = ELanguage.TYPESCRIPT;
      this.editorLangFramedDiff = ELanguage.TYPESCRIPT;
    }
    loadDefaultCode();
    loadDefaultCodeFramed();
  }

  /**
   * Set the initial values for the {@code extender} showcase.
   *
   * @param mode Whether to use the code of diff editor.
   */
  public void initExtender(final String mode) {
    this.mode = mode;
    if (CODE.equals(mode)) {
      this.editorOptions.setLanguage(ELanguage.JAVASCRIPT);
    } else {
      this.editorLangExtenderDiff = ELanguage.JAVASCRIPT;
    }
    final String requested = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
      .get("example");
    if (CODE.equals(mode)) {
      this.extenderExample = StringUtils.defaultIfBlank(requested, "jquery");
    } else {
      this.extenderExample = StringUtils.defaultIfBlank(requested, "diffnavi");
    }
    loadExtenderExample(this.extenderExample);
  }

  /**
   * @param exampleName Name of the example.
   * @param subType Type of the data to get, e.g. {@code name} or {@code description}.
   * @return The key for the given extender example property.
   */
  private String getExtenderExampleKey(final String exampleName, final String subType) {
    if (CODE.equals(this.mode)) {
      return CUSTOM_CODE_EXTENDER_CODE + exampleName + "." + subType;
    } else {
      return CUSTOM_CODE_EXTENDER_DIFF + exampleName + "." + subType;
    }
  }
}
