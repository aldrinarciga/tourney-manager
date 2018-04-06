package app.networking;

import app.models.ChallongeParticipant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.ArrayList;

/**
 * Created by aldrinarciga on 4/6/2018.
 */
public interface TournamentHttp {

    @GET("{tournament_id}/participants.json")
    Call<ArrayList<ChallongeParticipant>> getParticipants(@Path("tournament_id") String tournamentId, @Query("api_key") String apiKey);

}
