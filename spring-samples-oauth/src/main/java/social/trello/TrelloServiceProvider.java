package social.trello;

import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuth1Version;

import com.julienvey.trello.Trello;
import com.julienvey.trello.impl.TrelloImpl;

public class TrelloServiceProvider extends AbstractOAuth1ServiceProvider<Trello> {
	public TrelloServiceProvider(String consumerKey, String consumerSecret) {
		super(consumerKey, consumerSecret, new TrelloOauth1Template(consumerKey, consumerSecret,
				"https://trello.com/1/OAuthGetRequestToken",
				"https://trello.com/1/OAuthAuthorizeToken",
				"https://trello.com/1/OAuthGetAccessToken",
				"expiration=never",
				OAuth1Version.CORE_10_REVISION_A));
	}
	
	public Trello getApi(String accesToken, String secret){
		return new TrelloImpl(getConsumerKey(),accesToken);
	}
}
