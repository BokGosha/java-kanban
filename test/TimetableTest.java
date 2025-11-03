import model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TimetableTest {

    @Test
    public void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());

        List<TrainingSession> expectedTrainingSessions = List.of(thursdayChildTrainingSession,
                thursdayAdultTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> timeOfDayTrainingSessionTreeMap =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        List<TrainingSession> resultTrainingSessions = new ArrayList<>();
        for (List<TrainingSession> list : timeOfDayTrainingSessionTreeMap.values()) {
            resultTrainingSessions.addAll(list);
        }

        assertArrayEquals(expectedTrainingSessions.toArray(), resultTrainingSessions.toArray());

        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());
        assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)).size());
    }

    @Test
    public void testGetTrainingSessionsForDayAndTimeMultipleSessions() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Васильев", "Иван", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        //Проверить, что за понедельник вернулось два занятия
        assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)).size());
    }

    @Test
    public void testCoachConflictSameTime() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group2, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие, а не два, так как тренер уже занят
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(10, 0)).size());
    }

    @Test
    public void testCoachNoConflictDifferentTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Петров", "Пётр", "Петрович");

        TrainingSession session1 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession session2 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
    }

    @Test
    public void testGetCounterOfTrainingSessions() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Васильев", "Иван", "Сергеевич");
        TrainingSession firstCoachTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession secondCoachTrainingSession1 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));
        TrainingSession secondCoachTrainingSession2 = new TrainingSession(group2, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(11, 0));

        timetable.addNewTrainingSession(firstCoachTrainingSession);
        timetable.addNewTrainingSession(secondCoachTrainingSession1);
        timetable.addNewTrainingSession(secondCoachTrainingSession2);

        assertEquals(2, timetable.getCountByCoaches().getFirst().getCountOfTrainings());
        assertEquals(1, timetable.getCountByCoaches().get(1).getCountOfTrainings());
    }

    @Test
    public void testGetCounterOfTrainingSessionsWithZeroCounter() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Васильев", "Иван", "Сергеевич");
        TrainingSession firstCoachTrainingSession = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(firstCoachTrainingSession);

        assertEquals(1, timetable.getCountByCoaches().getFirst().getCountOfTrainings());
        assertEquals(1, timetable.getCountByCoaches().size());
    }
}
