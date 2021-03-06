package edu.rit.csh.components;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.wicket.markup.html.panel.Panel;

import edu.rit.csh.models.Book;

public class GiveBookPanel extends Panel {
	private static final long serialVersionUID = 1L;
	
	public GiveBookPanel(String id, Book book) throws LdapException, CursorException {
		super(id);
		add(new GiveBookForm("giveBookForm", book));
	}

}
