//@@author lmtaek

package duke.commands.task;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.assignedtasks.UpcomingTasks;
import duke.models.patients.PatientManager;
import duke.models.tasks.TaskManager;
import duke.storages.StorageManager;
import duke.util.DukeUi;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UpcomingTasksCommand implements Command {

    LocalDateTime fromDate;
    ArrayList<LocalDateTime> dates = new ArrayList<LocalDateTime>();
    ArrayList<UpcomingTasks> upcomingTaskList = new ArrayList<UpcomingTasks>();
    Boolean isTypedCommand = false;

    /**
     * The constructor for the UpcomingTasksCommand. Takes today's date and prepares UpcomingTask lists
     * for each day within the following week.
     * @param fromDate The current date.
     */
    public UpcomingTasksCommand(LocalDateTime fromDate, Boolean isTypedCommand) {
        this.fromDate = fromDate;
        this.isTypedCommand = isTypedCommand;
        for (int i = 0; i < 7; i++) {
            LocalDateTime currentDate = fromDate.plusDays(i);
            dates.add(currentDate);
        }
    }

    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks,
                        PatientManager patientList, DukeUi dukeUi,
                        StorageManager storageManager) throws DukeException {
        for (LocalDateTime date : dates) {
            UpcomingTasks upcomingTaskForDay = new UpcomingTasks(date, patientTask, tasks, patientList);
            upcomingTaskList.add(upcomingTaskForDay);
            if (isTypedCommand) {
                dukeUi.showUpcomingTasks(upcomingTaskForDay);
            }
        }
        if (isTypedCommand) {
            isTypedCommand = false;
        }
    }

    public ArrayList<UpcomingTasks> getUpcomingTaskLists() {
        return upcomingTaskList;
    }


    @Override
    public boolean isExit() {
        return false;
    }


}
