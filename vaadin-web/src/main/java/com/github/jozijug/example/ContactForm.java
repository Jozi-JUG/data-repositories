package com.github.jozijug.example;


import com.github.jozijug.jpadomain.Company;
import com.github.jozijug.jpadomain.Contact;
import com.github.jozijug.service.ContactService;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import javax.inject.Inject;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.fields.TypedSelect;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MMarginInfo;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * This class introduces a Form to edit Contact entities. It is a good habit
 * to separate logical pieces of your UI code to classes. This will improve
 * re-usability, readability, maintainability, testability.
 *
 * @author Matti Tahvonen
 */
public class ContactForm extends AbstractForm<Contact> {

    TextField name = new MTextField("Name");
    TextField phone = new MTextField("Phone");
    TextField email = new MTextField("Email");
    TypedSelect<Company> company = new TypedSelect<Company>("Company");

    @Inject
    ContactService service;

    @Override
    protected Component createContent() {
        company.setOptions(service.findCompanies());
        return new MVerticalLayout(
                getToolbar(),
                new MFormLayout(
                        name,
                        phone,
                        email,
                        company
                ).withMargin(false)
        ).withMargin(new MMarginInfo(false, true));
    }

}
