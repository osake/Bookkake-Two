package edu.rit.csh.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.directory.api.ldap.model.cursor.CursorException;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.DefaultCssAutoCompleteTextField;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;

import edu.rit.csh.Resources;
import edu.rit.csh.auth.LDAPUser;
import edu.rit.csh.auth.UserWebSession;
import edu.rit.csh.models.Book;
import edu.rit.csh.pages.HomePage;

public class GiveBookForm extends Form<Book> {
	private static final long serialVersionUID = 1L;
	private final UserRealNameAutoCompleteTextField titleField;
	private final Button submitButton;
	private String username;
	private Book book;

	public GiveBookForm(String id, Book book) throws LdapException, CursorException {
		super(id);
		this.book = book;
		setDefaultModel(new CompoundPropertyModel<GiveBookForm>(this));
		
		List<LDAPUser> users;
		users = Resources.ldapProxy.getActiveUsers();
		String userId = ((UserWebSession)getSession()).getUser().getUidnumber();
		//remove the authenticated user from the list.
		for (int i = 0; i < users.size(); i++){
			if (users.get(i).getUidnumber().equals(userId)){
				users.remove(i);
				break;
			}
		}
		titleField = new UserRealNameAutoCompleteTextField("username", users);
		submitButton = new Button("giveBookSubmit"); 
		submitButton.add(AttributeModifier.replace("class", "ui blue disabled button"));

		add(titleField);
		add(submitButton);
	}
	
	@Override
	public void onSubmit(){
		LDAPUser recipient;
		try {
			recipient = Resources.ldapProxy.getUserByUsername(username);
		} catch (LdapException | CursorException e) {
			e.printStackTrace();
			return;
		}
		if (recipient != null){
			String uidnum = recipient.getUidnumber();
			book.give(uidnum);
			setResponsePage(HomePage.class);
		}
	}
	
	
	public class UserRealNameAutoCompleteTextField extends DefaultCssAutoCompleteTextField<String>{
		private static final long serialVersionUID = 1L;
		private List<LDAPUser> users;

		public UserRealNameAutoCompleteTextField(String id, List<LDAPUser> users) {
			super(id);
			this.users = users;
			
			add(new OnChangeAjaxBehavior() {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					String input = UserRealNameAutoCompleteTextField.this.getValue();
					/**
					 * Determine if the button should be disabled or not.
					 */
					boolean activate = false;
					for (LDAPUser user: UserRealNameAutoCompleteTextField.this.users){
						if (user.getUid().equals(input)){
							activate = true;
							break;
						}
					}
					if (activate){
						submitButton.add(AttributeModifier.replace("class", "ui blue button"));
						target.add(submitButton);
					}else{
						submitButton.add(AttributeModifier.replace("class", "ui blue disabled button"));
						target.add(submitButton);
					}
				}
			});
		}

		@Override
		protected Iterator<String> getChoices(String input) {
			String target = input.toUpperCase();
			List<String> suggestions = new ArrayList<String>(16);
			for (int i = 0; i < users.size(); i++){
				LDAPUser user = users.get(i);
				if (user.getCommonname().toUpperCase().contains(target) ||
					user.getUid().toUpperCase().contains(target)){
					suggestions.add(user.getUid());
				}
				if (suggestions.size() >= 16){
					break;
				}
			}
			
			return suggestions.iterator();
		}
	}
}
