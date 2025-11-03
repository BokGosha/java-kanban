import model.*;

public class Main {

    public static void main(String[] args) {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach1 = new Coach("Васильев", "Иван", "Сергеевич");
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.SATURDAY, new TimeOfDay(20, 0));
        TrainingSession saturdayChildTrainingSession1 = new TrainingSession(groupAdult, coach1,
                DayOfWeek.SATURDAY, new TimeOfDay(21, 0));
        TrainingSession saturdayChildTrainingSession2 = new TrainingSession(groupAdult, coach1,
                DayOfWeek.SATURDAY, new TimeOfDay(22, 0));


        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession fridayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(20, 0));
        TrainingSession fridayAdultTrainingSession1 = new TrainingSession(groupAdult, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(21, 0));
        TrainingSession fridayAdultTrainingSession2 = new TrainingSession(groupAdult, coach,
                DayOfWeek.FRIDAY, new TimeOfDay(22, 0));

        timetable.addNewTrainingSession(saturdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession1);
        timetable.addNewTrainingSession(saturdayChildTrainingSession2);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(fridayAdultTrainingSession);
        timetable.addNewTrainingSession(fridayAdultTrainingSession1);
        timetable.addNewTrainingSession(fridayAdultTrainingSession2);

        for (CounterOfTrainings counter : timetable.getCountByCoaches()) {
            System.out.println(counter.getCoach() + " - Количество тренировок: " + counter.getCountOfTrainings());
        }
    }
}
