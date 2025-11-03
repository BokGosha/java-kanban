import model.*;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        if (!this.timetable.containsKey(trainingSession.getDayOfWeek())) {
            List<TrainingSession> list = new ArrayList<>();
            list.add(trainingSession);

            TreeMap<TimeOfDay, List<TrainingSession>> trainingSessions = new TreeMap<>();

            trainingSessions.put(trainingSession.getTimeOfDay(), list);

            this.timetable.put(trainingSession.getDayOfWeek(), trainingSessions);
        } else if (!this.timetable.get(trainingSession.getDayOfWeek()).containsKey(trainingSession.getTimeOfDay())) {
            TreeMap<TimeOfDay, List<TrainingSession>> trainingSessions = this.timetable.get(trainingSession.getDayOfWeek());

            List<TrainingSession> list = new ArrayList<>();

            list.add(trainingSession);

            trainingSessions.put(trainingSession.getTimeOfDay(), list);
        } else {
            TreeMap<TimeOfDay, List<TrainingSession>> trainingSessions = this.timetable.get(trainingSession.getDayOfWeek());

            List<TrainingSession> list = trainingSessions.get(trainingSession.getTimeOfDay());

            for (TrainingSession trainingSession1 : list) {
                if (trainingSession.getCoach().equals(trainingSession1.getCoach())) {
                    System.out.println("Тренер уже занят в это время.");
                    return;
                }
            }

            list.add(trainingSession);
        }
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return this.timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return this.timetable.getOrDefault(dayOfWeek, new TreeMap<>()).getOrDefault(timeOfDay, new ArrayList<>());
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> coaches = new HashMap<>();
        for (DayOfWeek dayOfWeek : this.timetable.keySet()) {
            for (List<TrainingSession> trainingSessions : this.timetable.get(dayOfWeek).values()) {
                for (TrainingSession trainingSession : trainingSessions) {
                    coaches.put(trainingSession.getCoach(), coaches.getOrDefault(trainingSession.getCoach(), 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> counterOfTrainings = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : coaches.entrySet()) {
            Coach coach = entry.getKey();
            Integer count = entry.getValue();
            CounterOfTrainings counter = new CounterOfTrainings(coach, count);
            counterOfTrainings.add(counter);
        }

        counterOfTrainings.sort((o1, o2) -> o2.getCountOfTrainings() - o1.getCountOfTrainings());

        return counterOfTrainings;
    }
}
