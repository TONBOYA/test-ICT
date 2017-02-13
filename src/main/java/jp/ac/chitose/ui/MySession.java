package jp.ac.chitose.ui;

import java.io.Serializable;

import jp.ac.chitose.bean.AccountBean;
import jp.ac.chitose.service.Interface.ILoginService;
import lombok.Getter;
import lombok.Setter;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.guice.GuiceInjectorHolder;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;

import com.google.inject.Inject;

public class MySession extends AbstractAuthenticatedWebSession implements Serializable {

	private static final long serialVersionUID = -3843577091273866186L;

	@Inject
	private ILoginService loginService;

	@Getter
	@Setter
	private AccountBean accountBean;

	private final Roles roles;

	private volatile boolean signedIn;

	public MySession(Request request) {
		super(request);
		this.roles = new Roles();
		WebApplication.get().getMetaData(GuiceInjectorHolder.INJECTOR_KEY).getInjector().injectMembers(this);
	}

	@Override
	public Roles getRoles() {
		if(isSignedIn()){
			return roles;
		}
		return new Roles();
	}

	@Override
	public boolean isSignedIn() {
		return signedIn;
	}

	public void signOut(){
		signedIn = false;
		replaceSession();
	}

	@Override
	public void invalidate() {
		signOut();
		super.invalidate();
	}

	public final boolean signIn(final String loginName,final String passphrase){
		signedIn = authenticate(loginName,passphrase);

//		if(signedIn){
//			replaceSession();
//		}

		return signedIn;
	}
//75はpassいらないかもしれない
	public boolean authenticate(String loginName,String passphrase){
		if(loginService.login(loginName, passphrase)){
			accountBean = loginService.getAccount(loginName, passphrase);
			roles.add(accountBean.getRole());
			replaceSession();
			return true;
		}
		return false;
	}

	public static MySession get(){
		return (MySession) Session.get();
	}
}
