package jp.ac.chitose;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.core.request.mapper.MountedMapper;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.UrlPathPageParametersEncoder;

import com.google.inject.Binder;
import com.google.inject.Module;

import jp.ac.chitose.service.Class.AnnotationEventDispatcher;
import jp.ac.chitose.ui.LoginPage;
import jp.ac.chitose.ui.LogoutCompletedPage;
import jp.ac.chitose.ui.MySession;
import jp.ac.chitose.ui.TopPage;
import jp.ac.chitose.ui.account.RegisterAccountPage;
import jp.ac.chitose.ui.content.content.ContentPage;
import jp.ac.chitose.ui.practice.PracticePage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see jp.ac.chitose.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return LoginPage.class;
	}


	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();
		mount(new MountedMapper("/login", LoginPage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/logout", LogoutCompletedPage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/registerAccount", RegisterAccountPage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/top", TopPage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/practice", PracticePage.class, new UrlPathPageParametersEncoder()));
		mount(new MountedMapper("/content", ContentPage.class, new UrlPathPageParametersEncoder()));
		
		getFrameworkSettings().add(new AnnotationEventDispatcher());

		initGuice();

		// add your configuration here
	}

	/**
	 * Guice の初期化用メソッド.
	 */
	protected void initGuice() {
		getComponentInstantiationListeners().add(
				new GuiceComponentInjector(this, getGuiceModule()));
	}

	/**
	 * @return injection module.
	 */
	protected Module getGuiceModule() {
		return new Module() {
			@Override
			public void configure(Binder binder) {
				binder.bind(IDBCP.class).to(DBCP.class).asEagerSingleton();
			}
		};
	}


	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return MySession.class;
	}


	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		MySession.get().invalidate();
		return LoginPage.class;
	}

	@Override
	public RuntimeConfigurationType getConfigurationType() {
		return RuntimeConfigurationType.DEVELOPMENT;
	}

}
