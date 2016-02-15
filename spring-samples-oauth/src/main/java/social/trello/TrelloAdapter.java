package social.trello;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Member;

public class TrelloAdapter implements ApiAdapter<Trello>{
	
	public UserProfile fetchUserProfile(Trello trello) {
		Member member = trello.getMemberInformation("me");
		return new UserProfileBuilder().setName(member.getUsername())
									   .setUsername(member.getEmail())
									   .setEmail(member.getEmail())
									   .build();
	}

	public void setConnectionValues(Trello trello, ConnectionValues values) {
		Member member = trello.getMemberInformation("me");
		values.setProviderUserId(member.getId());
		values.setDisplayName(member.getEmail());
		values.setProfileUrl(member.getUrl());
	}

	public boolean test(Trello trello) {
		return true;
	}

	public void updateStatus(Trello arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
