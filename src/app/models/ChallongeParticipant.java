package app.models;

/**
 * Created by aldrinarciga on 4/6/2018.
 */
public class ChallongeParticipant {

    private ParticipantModel participant;

    public ParticipantModel getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantModel participant) {
        this.participant = participant;
    }

    public class ParticipantModel {
        private String name;
        private String display_name;
        private int seed;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public int getSeed() {
            return seed;
        }

        public void setSeed(int seed) {
            this.seed = seed;
        }
    }
}
