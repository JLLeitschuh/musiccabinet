package com.github.hakko.musiccabinet.ws.musicbrainz;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.github.hakko.musiccabinet.service.lastfm.WebserviceHistoryService;

public class ReleaseGroupsClientTest {
	
	@Test
	@SuppressWarnings("unchecked")
	public void invokesHttpRequestToExpectedUri() throws Exception {
		ReleaseGroupsClient releaseGroupsClient = getReleaseGroupsClient();

		final String ARTIST_NAME = "Cult of Luna", MBID = "d347406f-839d-4423-9a28-188939282afa",
				EXPECTED_URI = "http://musicbrainz.org/ws/2/release-group/?"
				+ "query=arid%3Ad347406f-839d-4423-9a28-188939282afa"
				+ "+AND+%28primarytype%3AAlbum+OR+primarytype%3AEP+OR+primarytype%3ASingle%29"
				+ "+AND+NOT+secondarytype%3Acompilation"
				+ "+AND+NOT+secondarytype%3Asoundtrack"
				+ "+AND+NOT+secondarytype%3Aspokenword"
				+ "+AND+NOT+secondarytype%3Ainterview"
				+ "+AND+NOT+secondarytype%3Aaudiobook"
				+ "+AND+NOT+secondarytype%3Alive"
				+ "+AND+NOT+secondarytype%3Aremix"
				+ "+AND+NOT+secondarytype%3Aother"
				+ "+AND+status%3Aofficial"
				+ "&limit=100&offset=0";

		ArgumentCaptor<HttpGet> argument = forClass(HttpGet.class);
		releaseGroupsClient.get(ARTIST_NAME, MBID, 0);
		verify(releaseGroupsClient.getHttpClient()).execute(
				argument.capture(), any(ResponseHandler.class));

		assertEquals(EXPECTED_URI, argument.getValue().getURI().toASCIIString());
	}
	
	private ReleaseGroupsClient getReleaseGroupsClient() {
		ReleaseGroupsClient releaseGroupsClient = new ReleaseGroupsClient();
		HttpClient httpClient = Mockito.mock(HttpClient.class);
		releaseGroupsClient.setHttpClient(httpClient);

		WebserviceHistoryService webserviceHistoryService = 
				Mockito.mock(WebserviceHistoryService.class);
		releaseGroupsClient.setWebserviceHistoryService(webserviceHistoryService);
		
		return releaseGroupsClient;
	}

}