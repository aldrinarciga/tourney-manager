package app.models;

/**
 * Created by aldrinarciga on 4/6/2018.
 */
public interface GetParticipantsCallback {
    void onSuccess(PlayerAndTeamList playerAndTeamList);
    void onFailure(String err);
}
