package app.networking;

import app.models.*;
import app.utils.OtherUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aldrinarciga on 4/6/2018.
 */
public class NetworkingUtil {

    private static final String BASE_URL = "https://api.challonge.com/v1/tournaments/";
    private static final String API_KEY = "Ab4HCSCFuL2BoHWma6AdEGpkpggYNk4irUU3rWnR";

    private static TournamentHttp tournamentHttp;

    private static void init() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tournamentHttp = retrofit.create(TournamentHttp.class);
    }

    public static void getPlayerAndTeamList(String challongeId, boolean isDoubles, String splitter, GetParticipantsCallback callback) {
        if(callback == null) {
            return;
        }

        if(tournamentHttp == null) {
            init();
        }

        tournamentHttp.getParticipants(challongeId, API_KEY).enqueue(new Callback<ArrayList<ChallongeParticipant>>() {
            @Override
            public void onResponse(Call<ArrayList<ChallongeParticipant>> call, Response<ArrayList<ChallongeParticipant>> response) {
                if(response.body() != null) {
                    if(response.body().size() > 0) {
                        processParticipants(response.body(), isDoubles, splitter, callback);
                    } else {
                        callback.onFailure("No participants found found");
                    }
                } else {
                    callback.onFailure("No Tournament found");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ChallongeParticipant>> call, Throwable throwable) {
                throwable.printStackTrace();
                System.out.println(call.request().toString());
                callback.onFailure(throwable.getMessage());
            }
        });
    }

    private static void processParticipants(ArrayList<ChallongeParticipant> participants, boolean isDoubles, String splitter, GetParticipantsCallback callback) {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Team> teams = new ArrayList<>();

        if(!isDoubles) {
            for(ChallongeParticipant challongeParticipant : participants) {
                ChallongeParticipant.ParticipantModel model = challongeParticipant.getParticipant();
                Player player = OtherUtils.parseNameToPlayer(model.getName());
                if(player != null) {
                    ArrayList<Player> teamList = new ArrayList<>();
                    teamList.add(player);
                    Team team = new Team(model.getSeed(), teamList);
                    players.add(player);
                    teams.add(team);
                }
            }
        } else {
            for(ChallongeParticipant challongeParticipant : participants) {
                ChallongeParticipant.ParticipantModel model = challongeParticipant.getParticipant();
                Team team = OtherUtils.parseNameToTeam(model.getName(), model.getSeed(), splitter);
                if(team != null && team.getPlayers().size() > 0) {
                    teams.add(team);
                    players.addAll(team.getPlayers());
                }
            }
        }

        if(teams.size() > 0 && players.size() > 0) {
            PlayerAndTeamList playerAndTeamList = new PlayerAndTeamList(players, teams);
            callback.onSuccess(playerAndTeamList);
        } else {
            callback.onFailure("No Tournament found");
        }
    }
}
