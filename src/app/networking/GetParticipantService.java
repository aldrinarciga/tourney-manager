package app.networking;

import app.models.GetParticipantsCallback;
import app.models.PlayerAndTeamList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by aldrinarciga on 4/6/2018.
 */
public class GetParticipantService extends Service<PlayerAndTeamList> {
    @Override
    protected Task<PlayerAndTeamList> createTask() {
        return null;
    }
}
