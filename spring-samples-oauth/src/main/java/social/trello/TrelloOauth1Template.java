package social.trello;

import org.springframework.social.oauth1.OAuth1Parameters;
import org.springframework.social.oauth1.OAuth1Template;
import org.springframework.social.oauth1.OAuth1Version;

public class TrelloOauth1Template extends OAuth1Template{

		private String parameter;
		
		public TrelloOauth1Template(String consumerKey, String consumerSecret, String requestTokenUrl,
									String authorizeUrl, String accesToken, String parameter, OAuth1Version version){
			super(consumerKey, consumerSecret, requestTokenUrl, authorizeUrl, accesToken, version);
			this.parameter = parameter;
		}

		public String getParameter() {
			return parameter;
		}

		public void setParameter(String parameter) {
			this.parameter = parameter;
		}
		
		public String buildAuthorizeUrl(String requestToken, OAuth1Parameters params){
			String url = super.buildAuthorizeUrl(requestToken, params);
			url += "&" + parameter;
			return url;
		}
}
