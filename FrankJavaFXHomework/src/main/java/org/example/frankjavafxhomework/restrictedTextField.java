package org.example.frankjavafxhomework;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class restrictedTextField extends TextField {

    private String prompt;
    private String regex;
    private String helpTooltip;
    private Tooltip tooltip;

    //for TEXT textfields.
    private String promptTEXT = "Input text";
    private String regexTEXT = "^[a-zA-ZæøåÆØÅ\\s]*$";
    private String helpTooltipTEXT = "Textfield only takes input consisting of text and spaces.";

    //for NUMBER textfields.
    private String promptNUMBER = "Input numbers";
    private String regexNUMBER = "^\\d*(\\.?|\\,?)\\d*$";
    private String helpTooltipNUMBER = "Textfield only takes input consisting of numbers, commas and full stops.";

    //for DATE textfields.
    private String promptDATE = "DDMMYYYY";
    /*fra StackOverflow*/private String regexDATE = "^(0[1-9]|[1-2][0-9]|31(?!(?:0[2469]|11))|30(?!02))(0[1-9]|1[0-2])([12]\\d{3})$";
    private String helpTooltipDATE ="Must be a valid date and follow format DDMMYYYY.";

    //for CPR textfields.
    private String promptCPR = "DDMMYY-XXXX";
    private String regexCPR = "^(0[1-9]|[1-2][0-9]|31(?!(?:0[2469]|11))|30(?!02))(0[1-9]|1[0-2])(\\d{2})(-?| ?)\\d{4}$";
    private String helpTooltipCPR = "CPR-number must follow format DDMMYY-XXXX.";

    final String ERROR = "-fx-background-color: red;";

    /**
     * creates a restricted TextField based on the given enum
     * @param restriction see enum TextFieldType
     */
    public restrictedTextField(TextFieldType restriction)
    {
        switch (restriction)
        {
            case TEXT -> restrictedTextFieldText();
            case NUMBER -> restrictedTextFieldNumber();
            case DATE -> restrictedTextFieldDate();
            case CPR -> restrictedTextFieldCPR();
        }
        setPromptText(prompt);
        tooltip = new Tooltip(helpTooltip);
        addListener();
    }

    private void restrictedTextFieldText()
    {
        setRegex(regexTEXT);
        setPrompt(promptTEXT);
        setHelpTooltip(helpTooltipTEXT);
    }

    private void restrictedTextFieldNumber()
    {
        setRegex(regexNUMBER);
        setPrompt(promptNUMBER);
        setHelpTooltip(helpTooltipNUMBER);
    }

    private void restrictedTextFieldDate()
    {
        setRegex(regexDATE);
        setPrompt(promptDATE);
        setHelpTooltip(helpTooltipDATE);
    }

    private void restrictedTextFieldCPR()
    {
        setRegex(regexCPR);
        setPrompt(promptCPR);
        setHelpTooltip(helpTooltipCPR);
    }

    private void addListener() {
        this.textProperty().addListener(new ChangeListener<String>()
        {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1)
            {
                if (!t1.matches(regex))
                {
                    setStyle(ERROR);

                    setTooltip(tooltip);
                    tooltip.show(getScene().getWindow());
                }
                else
                {
                    setStyle(null);

                    tooltip.hide();
                }
            }
        });
    }

    private void setPrompt(String prompt)
    {
        this.prompt = prompt;
    }

    private void setRegex(String regex)
    {
        this.regex = regex;
    }

    private void setHelpTooltip(String helpTooltip)
    {
        this.helpTooltip = helpTooltip;
    }
}
