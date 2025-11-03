import model.*;

import java.util.*;

public class Timetable {

    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);
        if (trainingsForDay == null) {
            trainingsForDay = new TreeMap<>();
            timetable.put(dayOfWeek, trainingsForDay);
        }

        List<TrainingSession> trainingSessions = trainingsForDay.get(timeOfDay);
        if (trainingSessions == null) {
            trainingSessions = new ArrayList<>();
            trainingsForDay.put(timeOfDay, trainingSessions);
        }

        for (TrainingSession trainingSession1 : trainingSessions) {
            if (trainingSession.getCoach().equals(trainingSession1.getCoach())) {
                System.out.println("Тренер уже занят в это время.");
                return;
            }
        }

        trainingSessions.add(trainingSession);
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

        Collections.sort(counterOfTrainings);

        return counterOfTrainings;
    }
}
